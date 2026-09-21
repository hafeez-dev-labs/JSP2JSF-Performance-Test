package dev.hafeez.performance;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public final class PerformanceMetricsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest httpRequest) || !(response instanceof HttpServletResponse httpResponse)) {
            chain.doFilter(request, response);
            return;
        }
        var path = httpRequest.getRequestURI();
        var implementation = path.contains("/jsf/") ? "jsf" : path.contains("/jsp/") ? "jsp" : null;
        if (implementation == null) {
            chain.doFilter(request, response);
            return;
        }
        var started = System.nanoTime();
        var error = false;
        try {
            chain.doFilter(request, response);
        } catch (IOException | ServletException | RuntimeException exception) {
            error = true;
            throw exception;
        } finally {
            var elapsed = System.nanoTime() - started;
            PerformanceMetrics.record(implementation, elapsed, error);
            httpResponse.setHeader("Server-Timing", "server;dur=" + (elapsed / 1_000_000.0));
        }
    }
}
