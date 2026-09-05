# JSP2JSF Performance Test

A focused migration lab comparing a legacy JSP/JSTL dashboard with an equivalent Jakarta Faces (JSF) component-based dashboard.

## Objective

Make the JSP-to-JSF modernization story concrete: keep the underlying data and user-facing functionality equivalent, replace view composition with component-based JSF, and establish a disciplined performance measurement process.

## Architecture

- **Shared model/service:** `DashboardData` and `DashboardService` feed both views.
- **Legacy JSP:** `src/main/webapp/jsp/dashboard.jsp` uses JSP/JSTL.
- **Modern JSF:** `src/main/webapp/jsf/dashboard.xhtml` uses Jakarta Faces components and a view-scoped backing bean.
- **Benchmark:** `performance/benchmark.md` defines a reproducible comparison protocol.

## Run

Requirements: Java 17+ and Maven 3.9+.

```bash
mvn clean package
```

Deploy `target/jsp2jsf-performance-test.war` to a Jakarta EE 10-compatible runtime and open both endpoints.

## Performance story

The repository deliberately does **not** invent a 75% result. Run the benchmark under controlled, identical conditions and record the actual measurements in `performance/benchmark.md`. If the measured result is approximately 75%, the resume claim can credibly use that figure; otherwise use the measured result.

## Portfolio framing

> Built a comparative Java web application demonstrating migration from JSP/JSTL to JSF, introduced component-based views, and established a reproducible benchmark for validating frontend render-time improvements.

## Constraints

- No GitHub Actions.
- No external database is required.
- Both implementations render the same representative dashboard data.
