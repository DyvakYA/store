<%@ page import="model.constants.UrlHolder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="/WEB-INF/error/errorPage.jsp" %>
<c:set var="ADMIN_GET_ALL_PRODUCTS" value="<%=UrlHolder.ADMIN_GET_ALL_PRODUCTS_POST%>" />
<c:set var="ADMIN_CREATE_PRODUCT" value="<%=UrlHolder.ADMIN_CREATE_PRODUCT%>" />
<c:set var="ADMIN_UPDATE_PRODUCT" value="<%=UrlHolder.ADMIN_UPDATE_PRODUCT%>" />
<c:set var="ADMIN_DELETE_PRODUCT" value="<%=UrlHolder.ADMIN_DELETE_PRODUCT_POST%>" />
<jsp:include page="../elem/head.jsp"/>
<body>
<jsp:include page="../elem/admin-header.jsp"/>
<fmt:bundle basename="labels">
    <fmt:requestEncoding value="UTF-8"/>
    <div class="container">
        <c:if test="${!empty result}">
            <div class="alert alert-warning">
                <c:out value="${result}"/>
            </div>
        </c:if>

            <div id="accordion" class="panel-group">
                <div class="panel panel-success">

                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a href="#collapse-1"
                               data-parent="#accordion"
                               data-toggle="collapse"><fmt:message key="Filter"/></a>
                        </h4>
                    </div>

                    <div class="panel-collapse collapse" id="collapse-1">
                        <div class="panel-body">
                            <form method="get" action="/shop/findByPrice" class="navbar-form hidden-sm">

                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="from" required="required" name="first">
                                </div>

                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="before" required="required" name="second">
                                </div>
                                <button type="submit" class="btn btn-success btn-default">
                                    <i class="fa"><fmt:message key="FindByPrice"/></i>
                                </button>

                            </form>

                            <form method="get" action="/shop/findByName" class="navbar-form hidden-sm navbar-row">

                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="name" required="required" name="product_name">
                                </div>

                                <button type="submit" class="btn btn-success btn-default">
                                    <i class="fa"><fmt:message key="FindByName"/></i>
                                </button>

                            </form>
                        </div>
                    </div>
                </div>
            </div>

        <div class="row masonry" data-columns>
            <c:forEach var="products" items="${productsList}">
                <div class="item">
                    <div class="thumbnail">
                        <img src="http://placehold.it/600x340" alt="" class="img-responsive">
                        <div class="caption">
                            <h4><a href="">${products.id} ${products.name}</a></h4>
                            <p>${products.description}</p>
                            <h5><fmt:message key="Price"/> <b>${products.getRealPrice()}<fmt:message key="UAH"/></b></h5>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <form method="POST" action="./MainController">
            <table class="table">
                <tr>
                    <td><fmt:message key="Id"/></td>
                    <td><fmt:message key="Name"/></td>
                    <td><fmt:message key="Description"/></td>
                    <td><fmt:message key="Price"/></td>
                </tr>
                <tr>
                    <td><input type="text" name="product_id" size="12"></td>
                    <br/>
                    <td><input type="text" name="product_name" size="12"></td>
                    <br/>
                    <td><input type="text" name="product_description" size="25"></td>
                    <br/>
                    <td><input type="text" name="product_price" size="12"></td>
                    <br/>
                </tr>
            </table>
            <select name="command">
                <option value="${ADMIN_CREATE_PRODUCT}"><fmt:message key="CreateCommand"/></option>
                <option value="${ADMIN_UPDATE_PRODUCT}"><fmt:message key="UpdateCommand"/></option>
                <option value="${ADMIN_DELETE_PRODUCT}"><fmt:message key="DeleteCommand"/></option>
                <option value="${ADMIN_GET_ALL_PRODUCTS}"><fmt:message key="GetAllCommand"/></option>
            </select>
            <button type="submit" class="btn btn-success btn-default">
                <fmt:message key="ExecuteCommand"/>
            </button>
            <br/>
        </form>
    </div>

    <jsp:include page="../elem/footer.jsp"/>
    </body>
    </html>
</fmt:bundle>