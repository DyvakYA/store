<%@ page import="model.constants.UrlHolder" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="LOCALE" value="<%=UrlHolder.LOCALE%>"/>
<c:set var="PRODUCTS" value="<%=UrlHolder.PRODUCTS%>"/>
<c:set var="LOGIN" value="<%=UrlHolder.LOGIN%>"/>

<c:if test="${sessionScope.userLocale != null}">
    <fmt:setLocale value="${sessionScope.userLocale}" scope="session"/>
</c:if>
<fmt:bundle basename="labels">
    <div class="navbar navbar-static-top navbar-inverse bg-inverse">
        <div class="container">
            <div class="collapse navbar-collapse" orderId="responsive-menu">
                <ul class="nav navbar-nav">
                    <li><a href="/index.jsp"><fmt:message key="Home"/></a></li>
                    <li><a href="${PRODUCTS}"><fmt:message key="Products"/></a></li>
                    <li><a href="${LOCALE}"><fmt:message key="ChangeLocale"/></a></li>
                </ul>
                <form method="get" action="${LOGIN}" class="navbar-form navbar-right hidden-sm">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Email" value="Email" name="email">
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control" placeholder="Password" value="Password"
                               name="password">
                    </div>
                    <button type="submit" class="btn btn-success btn-default">
                        <i class="fa"><fmt:message key="Enter"/></i>
                    </button>
                    <button type="button" class="btn btn-success btn-default" data-toggle="modal"
                            data-target="#modal-1">
                        <i class="fa"><fmt:message key="Registration"/></i>
                    </button>
                </form>
            </div>
        </div>
    </div>
    <jsp:include page="../registration.jsp"/>
</fmt:bundle>
