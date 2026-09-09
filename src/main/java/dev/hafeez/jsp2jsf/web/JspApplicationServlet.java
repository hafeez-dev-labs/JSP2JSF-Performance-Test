package dev.hafeez.jsp2jsf.web;

import dev.hafeez.jsp2jsf.service.DashboardService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/jsp/app/*")
public class JspApplicationServlet extends HttpServlet {
    private final DashboardService service = new DashboardService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String view = view(request);
        request.setAttribute("data", service.loadDashboard());
        if ("tickets".equals(view)) {
            String status = request.getParameter("status");
            request.setAttribute("selectedStatus", status == null || status.isBlank() ? "All" : status);
            request.setAttribute("tickets", service.filterTickets(status));
            request.getRequestDispatcher("/jsp/tickets.jsp").forward(request, response);
            return;
        }
        if ("reports".equals(view)) {
            request.getRequestDispatcher("/jsp/reports.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/jsp/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String title = request.getParameter("title");
        String owner = request.getParameter("owner");
        if (title == null || title.isBlank() || owner == null || owner.isBlank()) {
            request.setAttribute("formError", "Title and owner are required.");
        } else {
            request.setAttribute("formMessage", "Ticket request received for '" + title.trim() + "' and assigned to " + owner.trim() + ".");
        }
        request.setAttribute("data", service.loadDashboard());
        request.setAttribute("tickets", service.filterTickets("All"));
        request.setAttribute("selectedStatus", "All");
        request.getRequestDispatcher("/jsp/tickets.jsp").forward(request, response);
    }

    private String view(HttpServletRequest request) {
        String path = request.getPathInfo();
        if (path == null || path.isBlank() || "/".equals(path)) {
            return "dashboard";
        }
        return path.substring(1);
    }
}
