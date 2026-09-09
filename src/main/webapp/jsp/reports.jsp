<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html><head><title>JSP Reports</title><link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/dashboard.css"></head><body><main>
<jsp:include page="/WEB-INF/jsp/nav.jsp" />
<header><h1>Reports</h1><p>Legacy JSP/JSTL report rendering</p></header>
<section class="panel"><h2>Request metrics</h2><table><thead><tr><th>Metric</th><th>Requests</th><th>Trend</th></tr></thead><tbody><c:forEach var="row" items="${data.reports()}"><tr><td>${row.name()}</td><td>${row.requests()}</td><td>${row.trend()}</td></tr></c:forEach></tbody></table></section>
<section class="panel"><h2>Activity status</h2><ul class="activity-list"><c:forEach var="item" items="${data.activities()}"><li><strong>${item.time()}</strong><span>${item.message()}</span><em>${item.status()}</em></li></c:forEach></ul></section>
</main></body></html>
