# Performance Benchmark

This lab compares the JSP and JSF view implementations under consistent, repeatable conditions. Measurements must be collected from the same deployed application/runtime with the same dataset and request mix.

## Controlled variables

Use the same values for JSP and JSF:

| Variable | Required control |
| --- | --- |
| Java/JVM | Same Java major/minor version and JVM vendor |
| Jakarta runtime | Same application server/container and configuration |
| Application build | Same WAR build |
| Dataset | Same application data |
| Endpoint mix | Same JSP/JSF request mix |
| Warm-up | 100 requests per implementation |
| Measured requests | 1,000 requests per implementation per run |
| Concurrency | Same configured concurrency |
| Runs | 3 repeated runs |
| Environment | Same host/container resources and network path |

Document any deviation before interpreting the results.

## Benchmark runner

The Python benchmark runner in performance/benchmark.py sends the same warm-up and measured request pattern to both implementations. It calculates average latency, P95, P99, maximum latency, throughput, and error rate for each run, then writes raw samples and a JSON summary.

Example:

~~~text
python performance/benchmark.py --base-url http://localhost:8080/jsp2jsf-performance-test
~~~

For a different concurrency level:

~~~text
python performance/benchmark.py --base-url http://localhost:8080/jsp2jsf-performance-test --concurrency 4
~~~

The runner writes performance/results/raw.csv and performance/results/summary.json.

## Collection procedure

1. Build with mvn clean package.
2. Deploy the WAR to a Jakarta-compatible runtime.
3. Record the Java/JVM version and runtime/container version.
4. Confirm the same application build and dataset are used for both implementations.
5. Start the application and allow it to reach a stable state.
6. Warm the JSP endpoint with 100 requests.
7. Run the measured JSP load for 1,000 requests at the selected concurrency.
8. Repeat for three runs.
9. Warm and measure the JSF endpoint using the same values.
10. Review the raw CSV for failed requests or anomalous runs.
11. Compare the three-run medians rather than relying on a single run.
12. Record actual measured values in the results table below.

## Instrumentation definitions

- Server duration: elapsed wall-clock time measured by the servlet filter around the JSP/JSF request.
- Server-Timing: the same server duration exposed as an HTTP response header.
- Client/browser timing: separate browser or external-client measurement that includes network and browser overhead.
- Error rate: failed requests divided by measured requests.
- Throughput: measured requests completed divided by wall-clock load duration.

## Endpoints

- JSP: /jsp/dashboard.jsp
- JSF: /jsf/dashboard.xhtml
- Metrics: /metrics

## Results

| Metric | JSP | JSF | Notes |
| --- | ---: | ---: | --- |
| Request count | TBD | TBD | Measured run |
| Average server duration | TBD | TBD | From raw samples |
| P95 server duration | TBD | TBD | From raw samples |
| P99 server duration | TBD | TBD | From raw samples |
| Max server duration | TBD | TBD | From raw samples |
| Throughput | TBD | TBD | Same runtime/request mix |
| Error rate | TBD | TBD | Errors / requests |

No performance improvement is claimed until measured under the controlled protocol.
