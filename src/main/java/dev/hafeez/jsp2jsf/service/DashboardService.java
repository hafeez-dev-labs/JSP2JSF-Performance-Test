package dev.hafeez.jsp2jsf.service;

import dev.hafeez.jsp2jsf.model.DashboardData;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class DashboardService {
    private final List<DashboardData.Ticket> tickets = List.of(
        new DashboardData.Ticket(1042, "Payment service latency", "Maya", "Open", "High"),
        new DashboardData.Ticket(1039, "Monthly report export", "Aarav", "In Progress", "Medium"),
        new DashboardData.Ticket(1035, "Cohort import validation", "Maya", "Resolved", "Low"),
        new DashboardData.Ticket(1031, "Cache refresh alert", "Noah", "Open", "Medium"),
        new DashboardData.Ticket(1028, "User access review", "Aarav", "Resolved", "High")
    );

    public DashboardData loadDashboard() {
        return new DashboardData("Aaqib",18,142,37,
            List.of(new DashboardData.Activity("09:42","Payment service recovered","Healthy"),new DashboardData.Activity("09:18","Monthly report generated","Done"),new DashboardData.Activity("08:55","New user cohort imported","Done"),new DashboardData.Activity("08:31","Cache refresh completed","Healthy")),
            List.of(new DashboardData.ReportRow("API latency",12480,"+12%"),new DashboardData.ReportRow("Successful requests",11842,"+8%"),new DashboardData.ReportRow("Error responses",638,"-21%")),
            tickets);
    }

    public List<DashboardData.Ticket> filterTickets(String status) {
        if (status == null || status.isBlank() || "All".equalsIgnoreCase(status)) {
            return tickets;
        }
        return tickets.stream().filter(ticket -> ticket.status().equalsIgnoreCase(status)).toList();
    }
}
