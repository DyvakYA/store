<%@ page import="model.constants.UrlHolder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="/WEB-INF/error/errorPage.jsp" %>
<c:set var="ADMIN_GET_ALL_USERS" value="<%=UrlHolder.ADMIN_GET_ALL_USERS_POST%>" />
<c:set var="ADMIN_CREATE_USER" value="<%=UrlHolder.ADMIN_CREATE_USER%>" />
<c:set var="ADMIN_UPDATE_USER" value="<%=UrlHolder.ADMIN_UPDATE_USER%>" />
<c:set var="ADMIN_DELETE_USER" value="<%=UrlHolder.ADMIN_DELETE_USER%>" />

<jsp:include page="../elem/head.jsp"/>
<body>
<jsp:include page="../elem/admin-header.jsp"/>
<fmt:bundle basename="labels">
    <div class="container">
        <c:if test="${!empty result}">
        <div class="alert alert-warning">
            <c:out value="${result}"/>
        </div>
        </c:if>
        <div class="row">
            <table class="table table-condensed">
                <tr>
                    <th><fmt:message key="Id"/></th>
                    <th><fmt:message key="Name"/></th>
                    <th><fmt:message key="Email"/></th>
                    <th><fmt:message key="Password"/></th>
                    <th><fmt:message key="isAdmin"/></th>
                    <th><fmt:message key="isBlocked"/></th>
                </tr>
                <c:forEach var="users" items="${usersList}">
                    <tr>
                        <td>${users.id}</td>
                        <td>${users.name}</td>
                        <td>${users.email}</td>
                        <td>${users.passwordHash}</td>
                        <td>${users.admin}</td>
                        <td>${users.blocked}</td>
                    </tr>
                </c:forEach>
            </table>

            <form method="post" action="./MainController">
                <table class="table">
                    <tr>
                        <td><fmt:message key="Id"/></td>
                        <td><fmt:message key="Name"/></td>
                        <td><fmt:message key="Email"/></td>
                        <td><fmt:message key="Password"/></td>
                        <td><fmt:message key="isAdmin"/></td>
                        <td><fmt:message key="isBlocked"/></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="user_id" size="12"></td>
                        <br/>
                        <td><input type="text" name="user_name" size="12"></td>
                        <br/>
                        <td><input type="text" name="email" size="12"></td>
                        <br/>
                        <td><input type="text" name="password" size="12"></td>
                        <br/>
                        <td><input type="checkbox" name="isAdmin" checked="checked" value="true"></td>
                        <br/>
                        <td><input type="checkbox" name="isBlocked" checked="checked" value="true"></td>
                        <br/>
                    </tr>
                </table>
                <select name="command">
                    <option value="${ADMIN_CREATE_USER}"><fmt:message key="CreateCommand"/></option>
                    <option value="${ADMIN_UPDATE_USER}"><fmt:message key="UpdateCommand"/></option>
                    <option value="${ADMIN_DELETE_USER}"><fmt:message key="DeleteCommand"/></option>
                    <option value="${ADMIN_GET_ALL_USERS}"><fmt:message key="GetAllCommand"/></option>
                </select>
                <button type="submit" class="btn btn-success btn-default">
                    <fmt:message key="ExecuteCommand"/>
                </button>
                <br/>
            </form>
        </div>
     </div>


    <jsp:include page="../elem/footer.jsp"/>
    </body>
    </html>
</fmt:bundle>
