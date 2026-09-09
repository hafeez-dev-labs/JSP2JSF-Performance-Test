<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html><head><title>JSP Tickets</title><link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/dashboard.css"></head><body><main>
<jsp:include page="/WEB-INF/jsp/nav.jsp" />
<header><h1>Tickets</h1><p>JSP/JSTL form handling and filtered table rendering</p></header>
<c:if test="${not empty formMessage}"><div class="notice success">${formMessage}</div></c:if>
<c:if test="${not empty formError}"><div class="notice error">${formError}</div></c:if>
<section class="panel"><h2>Create ticket request</h2><form method="post" action="${pageContext.request.contextPath}/jsp/app/tickets" class="form-grid"><label>Title<input name="title" value="${param.title}" /></label><label>Owner<input name="owner" value="${param.owner}" /></label><button type="submit">Submit request</button></form></section>
<section class="panel"><h2>Filter tickets</h2><form method="get" action="${pageContext.request.contextPath}/jsp/app/tickets" class="filter"><label>Status<select name="status"><option ${selectedStatus == 'All' ? 'selected' : ''}>All</option><option ${selectedStatus == 'Open' ? 'selected' : ''}>Open</option><option ${selectedStatus == 'In Progress' ? 'selected' : ''}>In Progress</option><option ${selectedStatus == 'Resolved' ? 'selected' : ''}>Resolved</option></select></label><button type="submit">Apply filter</button></form></section>
<section class="panel"><h2>Ticket queue</h2><table><thead><tr><th>ID</th><th>Title</th><th>Owner</th><th>Status</th><th>Priority</th></tr></thead><tbody><c:forEach var="ticket" items="${tickets}"><tr><td>${ticket.id()}</td><td>${ticket.title()}</td><td>${ticket.owner()}</td><td>${ticket.status()}</td><td>${ticket.priority()}</td></tr></c:forEach></tbody></table><c:if test="${empty tickets}"><p>No tickets match the selected status.</p></c:if></section>
</main></body></html>
