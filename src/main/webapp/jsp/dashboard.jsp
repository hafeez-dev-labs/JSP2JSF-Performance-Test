<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<% request.setAttribute("data", new dev.hafeez.jsp2jsf.service.DashboardService().loadDashboard()); %>
<!DOCTYPE html><html><head><title>JSP Dashboard</title><link rel="stylesheet" href="../resources/assets/dashboard.css"></head><body><main>
<header><h1>JSP Dashboard</h1><p>Legacy server-rendered implementation</p></header>
<section class="cards"><article><span>Open tickets</span><strong>${data.openTickets()}</strong></article><article><span>Completed</span><strong>${data.completedTickets()}</strong></article><article><span>Active users</span><strong>${data.activeUsers()}</strong></article></section>
<section class="panel"><h2>Recent activity</h2><table><thead><tr><th>Time</th><th>Activity</th><th>Status</th></tr></thead><tbody><c:forEach var="item" items="${data.activities()}"><tr><td>${item.time()}</td><td>${item.message()}</td><td>${item.status()}</td></tr></c:forEach></tbody></table></section>
<section class="panel"><h2>Reports</h2><table><thead><tr><th>Metric</th><th>Requests</th><th>Trend</th></tr></thead><tbody><c:forEach var="row" items="${data.reports()}"><tr><td>${row.name()}</td><td>${row.requests()}</td><td>${row.trend()}</td></tr></c:forEach></tbody></table></section>
</main></body></html>
