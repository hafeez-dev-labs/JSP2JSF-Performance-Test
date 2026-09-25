#!/usr/bin/env python3
"""Run repeatable latency and throughput measurements against JSP and JSF endpoints."""

from __future__ import annotations

import argparse
import csv
import json
import statistics
import time
import urllib.error
import urllib.request
from concurrent.futures import ThreadPoolExecutor
from pathlib import Path


def fetch(url: str, timeout: float) -> tuple[float, bool, int | None]:
    started = time.perf_counter()
    try:
        with urllib.request.urlopen(url, timeout=timeout) as response:
            response.read()
            status = response.status
        return (time.perf_counter() - started) * 1000.0, 200 <= status < 400, status
    except urllib.error.HTTPError as exc:
        return (time.perf_counter() - started) * 1000.0, False, exc.code
    except (urllib.error.URLError, TimeoutError, OSError):
        return (time.perf_counter() - started) * 1000.0, False, None


def percentile(values: list[float], fraction: float) -> float:
    if not values:
        return 0.0
    ordered = sorted(values)
    position = (len(ordered) - 1) * fraction
    lower = int(position)
    upper = min(lower + 1, len(ordered) - 1)
    return ordered[lower] + (ordered[upper] - ordered[lower]) * (position - lower)


def run_load(url: str, requests: int, concurrency: int, timeout: float) -> tuple[dict, list[tuple[float, int | None]]]:
    started = time.perf_counter()
    with ThreadPoolExecutor(max_workers=concurrency) as executor:
        results = list(executor.map(lambda _: fetch(url, timeout), range(requests)))
    wall_seconds = time.perf_counter() - started

    latencies = [item[0] for item in results]
    successes = sum(1 for item in results if item[1])
    errors = requests - successes
    summary = {
        "requests": requests,
        "successes": successes,
        "errors": errors,
        "errorRate": errors / requests,
        "averageMs": statistics.fmean(latencies),
        "p95Ms": percentile(latencies, 0.95),
        "p99Ms": percentile(latencies, 0.99),
        "maxMs": max(latencies),
        "throughputRequestsPerSecond": requests / wall_seconds,
        "wallSeconds": wall_seconds,
    }
    samples = [(latency, status) for latency, _, status in results]
    return summary, samples


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--base-url", default="http://localhost:8080/jsp2jsf-performance-test")
    parser.add_argument("--warmup", type=int, default=100)
    parser.add_argument("--requests", type=int, default=1000)
    parser.add_argument("--concurrency", type=int, default=1)
    parser.add_argument("--runs", type=int, default=3)
    parser.add_argument("--timeout", type=float, default=10.0)
    parser.add_argument("--output-dir", type=Path, default=Path("performance/results"))
    args = parser.parse_args()

    if min(args.warmup, args.requests, args.concurrency, args.runs) <= 0:
        parser.error("warmup, requests, concurrency, and runs must be positive")

    endpoints = {
        "jsp": args.base_url.rstrip("/") + "/jsp/dashboard.jsp",
        "jsf": args.base_url.rstrip("/") + "/jsf/dashboard.xhtml",
    }

    args.output_dir.mkdir(parents=True, exist_ok=True)
    raw_rows: list[dict] = []
    summaries: dict[str, list[dict]] = {}

    for implementation, url in endpoints.items():
        for _ in range(args.warmup):
            fetch(url, args.timeout)

        runs: list[dict] = []
        for run_number in range(1, args.runs + 1):
            summary, samples = run_load(url, args.requests, args.concurrency, args.timeout)
            summary.update({
                "implementation": implementation,
                "run": run_number,
                "url": url,
                "warmupRequests": args.warmup,
                "concurrency": args.concurrency,
            })
            runs.append(summary)

            for request_number, (latency, status) in enumerate(samples, start=1):
                raw_rows.append({
                    "implementation": implementation,
                    "run": run_number,
                    "request": request_number,
                    "latencyMs": f"{latency:.3f}",
                    "status": status if status is not None else "",
                })

        summaries[implementation] = runs

    raw_path = args.output_dir / "raw.csv"
    summary_path = args.output_dir / "summary.json"

    with raw_path.open("w", newline="", encoding="utf-8") as handle:
        writer = csv.DictWriter(handle, fieldnames=["implementation", "run", "request", "latencyMs", "status"])
        writer.writeheader()
        writer.writerows(raw_rows)

    summary_path.write_text(json.dumps({
        "baseUrl": args.base_url,
        "warmupRequests": args.warmup,
        "requestsPerRun": args.requests,
        "concurrency": args.concurrency,
        "runs": args.runs,
        "endpoints": endpoints,
        "results": summaries,
    }, indent=2), encoding="utf-8")

    for implementation, runs in summaries.items():
        print(
            f"{implementation}: median-average={statistics.median(run['averageMs'] for run in runs):.3f} ms, "
            f"median-p95={statistics.median(run['p95Ms'] for run in runs):.3f} ms, "
            f"median-p99={statistics.median(run['p99Ms'] for run in runs):.3f} ms, "
            f"median-throughput={statistics.median(run['throughputRequestsPerSecond'] for run in runs):.3f} req/s"
        )

    print(f"Raw measurements: {raw_path}")
    print(f"Summary: {summary_path}")


if __name__ == "__main__":
    main()
