<%--suppress XmlPathReference --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="/WEB-INF/error/errorPage.jsp" %>
<jsp:include page="elem/head.jsp"/>
<body>
<c:if test="${sessionScope.user == null}">
    <jsp:include page="elem/header.jsp"/>
</c:if>
<c:if test="${sessionScope.user != null}">
    <c:if test="${sessionScope.user.isAdmin() == true}">
        <jsp:include page="elem/admin-header.jsp"/>
    </c:if>
    <c:if test="${sessionScope.user.isAdmin() == false}">
         <jsp:include page="elem/user-header.jsp"/>
    </c:if>
</c:if>
<fmt:bundle basename="labels">
<div class="container">
    <c:if test="${!empty result}">
        <div class="alert alert-warning">
            <c:out value="${result}"/>
        </div>
    </c:if>
    <h3 class="text-center"><fmt:message key="Welcome"/></h3>
    <h4 class="text-center"><fmt:message key="description"/></h4>
</div>
    <div style="min-height: 600px">


    </div>
<jsp:include page="elem/footer.jsp"/>
</body>
</fmt:bundle>
