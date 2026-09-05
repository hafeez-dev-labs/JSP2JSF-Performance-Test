# Performance Benchmark

This is a comparison lab, not a fabricated performance claim. Run both views with the same JVM, container, dataset, warm-up period, and request mix.

## Endpoints
- JSP: `/jsp/dashboard.jsp`
- JSF: `/jsf/dashboard.xhtml`

## Protocol
1. Build with `mvn clean package`.
2. Deploy the WAR to the same Jakarta-compatible runtime.
3. Warm each endpoint with 100 requests.
4. Measure at least 1,000 requests per implementation.
5. Record average, P95, P99, throughput, and error rate.
6. Repeat three times and compare medians.

## Calculation
`render-time improvement = ((JSP average - JSF average) / JSP average) * 100`

Do not publish a 75% improvement unless the controlled benchmark measures approximately that result.

| Metric | JSP | JSF | Improvement |
|---|---:|---:|---:|
| Average request/render time | TBD | TBD | TBD |
| P95 | TBD | TBD | TBD |
| P99 | TBD | TBD | TBD |
| Throughput | TBD | TBD | TBD |
| Error rate | TBD | TBD | TBD |
