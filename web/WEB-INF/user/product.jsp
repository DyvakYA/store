<%@ page import="model.constants.UrlHolder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%@ page errorPage="/WEB-INF/error/errorPage.jsp" %>--%>
<c:set var="ADD_TO_ORDER" value="<%=UrlHolder.ADD_TO_ORDER%>" />
<jsp:include page="../elem/head.jsp"/>
<fmt:bundle basename="labels">
<body>
<jsp:include page="../elem/user-header.jsp"/>

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
                        <form method="get" action="${ADD_TO_ORDER}">
                        <h3><a href="">${products.name}</a></h3>
                        <p>${products.description}</p>
                        <h4><fmt:message key="Price"/><b> ${products.getRealPrice()} uah</b></h4>
                             <div class="cart-item-info much">
                                 <h4><fmt:message key="Quantity"/><input type="text" value="1" name="quantity" size="10"></h4>
                            </div>
                            <input type=hidden name="product_id" VALUE="${products.id}">
                            <button type="submit" class="btn btn-success btn-default">
                                <i class="fa"><fmt:message key="AddToOrder"/></i>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<jsp:include page="../elem/footer.jsp"/>
</body>
</html>
</fmt:bundle>
