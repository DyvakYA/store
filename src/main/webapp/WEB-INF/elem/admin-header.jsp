<%@ page import="model.constants.UrlHolder" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 4/2/2017
  Time: 10:22 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="LOCALE" value="<%=UrlHolder.LOCALE%>" />
<c:set var="ADMIN_GET_ALL_USERS" value="<%=UrlHolder.ADMIN_GET_ALL_USERS%>" />
<c:set var="ADMIN_PRODUCTS" value="<%=UrlHolder.ADMIN_PRODUCTS%>" />
<c:set var="ADMIN_ORDERS" value="<%=UrlHolder.ADMIN_ORDERS%>" />
<c:set var="LOGOUT" value="<%=UrlHolder.LOGOUT%>" />

<c:if test="${sessionScope.userLocale != null}">
    <fmt:setLocale value="${sessionScope.userLocale}" scope="session" />
</c:if>
<fmt:bundle basename="labels">
    <div class="navbar navbar-static-top navbar-inverse bg-inverse">
        <div class="container">
            <div class="collapse navbar-collapse" orderId="responsive-menu">
                <ul class="nav navbar-nav">
                    <li><a href="/index.jsp"><fmt:message key="Home"/></a></li>
                    <li><a href="${ADMIN_PRODUCTS}"><fmt:message key="Products"/></a></li>
                    <li><a href="${LOCALE}"><fmt:message key="ChangeLocale" /></a></li>
                    <li><a href="${ADMIN_ORDERS}"><fmt:message key="Orders"/></a></li>
                    <li><a href="${ADMIN_GET_ALL_USERS}"><fmt:message key="Users"/></a></li>
                </ul>
                <form method="get" action="${LOGOUT}" class="navbar-form navbar-right hidden-sm">
                    <button type="submit" class="btn btn-success btn-default">
                        <i class="fa"><fmt:message key="Logout"/></i>
                    </button>
                </form>
            </div>
        </div>
    </div>
    <jsp:include page="../registration.jsp"/>
</fmt:bundle>
