import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch

import benchmark


class BenchmarkTests(unittest.TestCase):
    def test_percentile_interpolates_between_samples(self):
        self.assertEqual(15.0, benchmark.percentile([10.0, 20.0], 0.5))

    def test_run_load_reports_http_status_distribution(self):
        with patch.object(benchmark, "fetch", side_effect=[
            (10.0, True, 200),
            (20.0, False, 503),
            (30.0, False, None),
        ]):
            summary, samples = benchmark.run_load("http://example.test", 3, 1, 1.0)

        self.assertEqual({"200": 1, "503": 1, "unavailable": 1}, summary["statusCounts"])
        self.assertEqual(1, summary["successes"])
        self.assertEqual(2, summary["errors"])
        self.assertEqual(3, len(samples))

    def test_invalid_timeout_fails_before_network_requests(self):
        with tempfile.TemporaryDirectory() as directory:
            with patch("sys.argv", [
                "benchmark.py",
                "--timeout", "0",
                "--output-dir", directory,
            ]), patch.object(benchmark, "fetch") as fetch:
                with self.assertRaises(SystemExit):
                    benchmark.main()
                fetch.assert_not_called()
                self.assertEqual([], list(Path(directory).iterdir()))

    def test_unavailable_endpoint_fails_before_writing_results(self):
        with tempfile.TemporaryDirectory() as directory:
            with patch("sys.argv", [
                "benchmark.py",
                "--warmup", "1",
                "--requests", "1",
                "--runs", "1",
                "--output-dir", directory,
            ]), patch.object(benchmark, "fetch", return_value=(1.0, False, 503)):
                with self.assertRaises(SystemExit):
                    benchmark.main()
                self.assertEqual([], list(Path(directory).iterdir()))


if __name__ == "__main__":
    unittest.main()
