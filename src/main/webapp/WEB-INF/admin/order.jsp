<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%@ page errorPage="/WEB-INF/error/errorPage.jsp" %>--%>
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
<c:forEach var="users" items="${userMap}">
        <div class="row masonry" data-columns>
            <c:forEach var="orders" items="${users.value}">
                <div class="item">
                    <form action="/shop/admin/adminDeleteOrder" method="GET">
                        <INPUT TYPE=hidden NAME=order_id VALUE="${orders.key.id}">
                        <button class="close" type="submit">
                            <i class="fa fa-close"></i>
                        </button>
                    </form>
                    <div class="thumbnail">
                        <div class="caption">
                            <h5><b><fmt:message key="User"/>${users.key.email}</b><br>
                            <b><fmt:message key="Number"/> ${orders.key.id}</b><br>
                            <b><fmt:message key="Date"/> ${orders.key.getRealDate()}</b><br>
                            <b><fmt:message key="Sum"/> ${orders.key.getRealTotalPrice()} <fmt:message key="UAH"/></b></h5>
                            <form action="/shop/admin/adminUpdateOrderStatus" method="GET">
                                <div class="form-group">
                                    <input type="text" name="order_status" size="auto" class="form-control"
                                           value="${orders.key.orderStatus}">
                                    <INPUT TYPE=hidden NAME=order_id VALUE="${orders.key.id}">
                                </div>
                                <div class="form-group">
                                    <button type="submit" class="btn btn-success btn-default">
                                        <i class="fa"><fmt:message key="ChangeStatus"/></i>
                                    </button>
                                </div>
                            </form>
                            <c:forEach var="products" items="${orders.value}">
                                <div id="accordion" class="panel-group">
                                    <div class="panel panel-success">
                                        <div class="panel-heading">
                                                 <form action="/shop/admin/adminDeleteProduct" method="GET">
                                                     <h4 class="panel-title">
                                                    <INPUT TYPE=hidden NAME=order_id VALUE="${orders.key.id}">
                                                    <INPUT TYPE=hidden NAME=product_id VALUE="${products.value.id}">
                                                    <button class="close" type="submit">
                                                        <i class="fa fa-close"></i>
                                                    </button>
                                                <a href="#collapse-${products.key.id}"
                                                   data="#accordion"
                                                   data-toggle="collapse">${products.value.name}</a></h4>
                                                </form>
                                            </h4>
                                        </div>
                                        <div class="panel-collapse collapse"
                                             id="collapse-${products.key.id}">
                                            <div class="panel-body">
                                                <h5><fmt:message key="Price"/> <b>${products.value.getRealPrice()} <fmt:message key="UAH"/></b>
                                                    <br><fmt:message key="Quantity"/> <b>${products.key.quantity} <fmt:message key="pc"/></b>
                                                    <br><fmt:message key="Sum"/><b>${products.key.getRealProductSum()} <fmt:message key="UAH"/></b></h5>
                                                <p>${products.value.description}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </c:forEach>
      </div>
</c:forEach>
    </div>
    <jsp:include page="../elem/footer.jsp"/>
    </body>
    </html>
</fmt:bundle>