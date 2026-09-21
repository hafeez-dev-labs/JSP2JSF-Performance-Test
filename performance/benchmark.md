# Performance Benchmark

This lab compares the two view implementations under consistent conditions. Run both views with the same JVM, container, dataset, warm-up period, and request mix.

## Instrumentation definitions

- Server duration: elapsed wall-clock time measured by the servlet filter around the complete JSP/JSF request, including view rendering and response generation handled by the application.
- Server-Timing: the same server duration is exposed as the HTTP Server-Timing response header so browser developer tools can distinguish application time from total browser-observed timing.
- Request count: number of instrumented requests for the selected implementation.
- Error count: instrumented requests that terminate with an IOException, ServletException, or runtime exception.
- Total duration: sum of server durations across instrumented requests.
- Max duration: slowest instrumented request observed by the in-memory collector.
- Average duration: total duration / request count.
- Client/browser timing: measure separately with browser DevTools or an external HTTP client. It includes network and browser overhead and must not be treated as equivalent to server duration.

## Local metrics

After exercising the JSP and JSF endpoints, inspect /metrics. The endpoint reports aggregate in-memory counters for jsp and jsf. Restarting the application resets these counters.

## Baseline collection procedure

1. Build with mvn clean package.
2. Deploy the WAR to the same Jakarta-compatible runtime for both implementations.
3. Use the same dataset and request mix.
4. Warm each endpoint with 100 requests.
5. Measure at least 1,000 requests per implementation.
6. Record server duration from the instrumentation and client timing separately.
7. Repeat three runs and compare medians.
8. Record the actual values below only after measurement.

## Endpoints

- JSP: /jsp/dashboard.jsp
- JSF: /jsf/dashboard.xhtml
- Metrics: /metrics

## Results

| Metric | JSP | JSF | Notes |
|---|---:|---:|---|
| Request count | TBD | TBD | Measured run |
| Average server duration | TBD | TBD | From instrumentation |
| P95 server duration | TBD | TBD | Derived from raw samples |
| P99 server duration | TBD | TBD | Derived from raw samples |
| Max server duration | TBD | TBD | From instrumentation |
| Throughput | TBD | TBD | Same runtime/request mix |
| Error rate | TBD | TBD | Errors / requests |

No performance improvement is claimed until measured under the controlled protocol.
