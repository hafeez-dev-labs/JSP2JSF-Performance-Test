# JSP2JSF Performance Test

A focused migration lab comparing a legacy JSP/JSTL application with an equivalent Jakarta Faces (JSF) component-based application.

## Objective

Make the JSP-to-JSF modernization story concrete: keep the underlying data and user-facing functionality equivalent, expand the legacy view layer to expose representative JSP patterns, and establish a disciplined performance measurement process before drawing conclusions.

## Architecture

- **Shared model/service:** `DashboardData` and `DashboardService` provide deterministic application data to both implementations.
- **Legacy JSP:** `src/main/webapp/jsp/` contains the JSP/JSTL dashboard, ticket workflow, reports view, and shared navigation.
- **JSP interaction layer:** `JspApplicationServlet` routes the expanded JSP experience, performs ticket form validation, and applies server-side ticket filtering.
- **Modern JSF:** `src/main/webapp/jsf/dashboard.xhtml` uses Jakarta Faces components and a view-scoped backing bean.
- **Benchmark:** `performance/benchmark.md` defines a reproducible comparison protocol.

## Legacy JSP surface

The Phase 2 baseline now includes:

- Dashboard navigation shared through a JSP include.
- Ticket queue rendered with JSTL iteration.
- Status filtering performed server-side.
- Ticket request form with server-side validation and confirmation.
- Reports view reusing the same application data.
- Shared styling across the expanded views.

This surface intentionally remains a legacy JSP/JSTL implementation so the later JSF migration can compare equivalent behavior rather than a toy single-page view.

## Run

Requirements: Java 17+ and Maven 3.9+.

```bash
mvn clean package
```

Deploy `target/jsp2jsf-performance-test.war` to a Jakarta EE 10-compatible runtime.

Legacy JSP application entry point:

`/jsp/app/`

Legacy ticket workflow:

`/jsp/app/tickets`

Legacy reports:

`/jsp/app/reports`

JSF dashboard:

`/jsf/dashboard.xhtml`

## Performance story

Run the benchmark under controlled, identical conditions and record measured results in `performance/benchmark.md`. No performance improvement is claimed until it has been measured.

## Portfolio framing

> Built a comparative Java web modernization lab that expands a legacy JSP/JSTL application, migrates equivalent functionality toward JSF, and uses controlled benchmarking to evaluate rendering and request-performance trade-offs.

## Constraints

- No GitHub Actions.
- No external database is required.
- Both implementations use deterministic application data.
- Performance claims must be supported by measured results.
