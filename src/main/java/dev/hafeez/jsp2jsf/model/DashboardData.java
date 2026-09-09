package dev.hafeez.jsp2jsf.model;

import java.util.List;

public record DashboardData(String username, int openTickets, int completedTickets, int activeUsers, List<Activity> activities, List<ReportRow> reports, List<Ticket> tickets) {
    public record Activity(String time, String message, String status) {}
    public record ReportRow(String name, int requests, String trend) {}
    public record Ticket(int id, String title, String owner, String status, String priority) {}
}
