package dev.hafeez.performance;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/metrics")
public final class PerformanceMetricsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        for (var entry : PerformanceMetrics.snapshot().entrySet()) {
            response.getWriter().println(entry.getKey() + "=" + entry.getValue());
        }
    }
}
