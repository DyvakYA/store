<%@ page import="model.constants.UrlHolder" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 4/5/2017
  Time: 6:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="REGISTRATION" value="<%=UrlHolder.REGISTRATION%>" />
<fmt:bundle basename="labels">
    <div class="modal fade" id="modal-1">
        <div class="modal-dialog modal-lg">

            <div class="modal-content">

                <div class="modal-header">
                    <button class="close" type="button" data-dismiss="modal">
                        <i class="fa fa-close"></i>
                    </button>
                    <h4 class="modal-title"><fmt:message key="Registration"/></h4>
                </div>

                <div class="modal-body">
                    <form id="register-form" method="get" action="/shop/registration" class="form-horizontal">

                        <!-- Блок для ввода логина -->
                        <div class="form-group has-feedback">
                            <label class="control-label col-xs-3">Имя:</label>
                            <div class="col-xs-6">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                    <input type="text" class="form-control" required="required" name="user_name">
                                </div>
                                <span class="glyphicon form-control-feedback"></span>
                            </div>
                        </div>

                        <!-- Блок для ввода email -->
                        <div class="form-group has-feedback">
                            <label class="control-label col-xs-3">Email:</label>
                            <div class="col-xs-6">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
                                    <input type="email" class="form-control" required="required" name="email">
                                </div>
                                <span class="glyphicon form-control-feedback"></span>
                            </div>
                        </div>
                        <!-- Конец блока для ввода email-->

                        <!-- Блок для воода пароля-->
                        <div class="form-group has-feedback">
                            <label class="control-label col-xs-3">Password:</label>
                            <div class="col-xs-6">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-tag"></i></span>
                                    <input type="password" class="form-control" required="required" name="password">
                                </div>
                                <span class="glyphicon form-control-feedback"></span>
                            </div>
                        </div>

                        <!-- Блок для подтверждения воода пароля-->
                        <div class="form-group has-feedback">
                            <label class="control-label col-xs-3">Confirm password:</label>
                            <div class="col-xs-6">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-tags"></i></span>
                                    <input type="password" class="form-control" required="required" name="confirmPassword">
                                </div>
                                <span class="glyphicon form-control-feedback"></span>
                            </div>
                        </div>

                        <div class="modal-footer">
                            <button class="btn btn-danger" type="button" data-dismiss="modal"><fmt:message
                                    key="Close"/></button>
                            <input id="#register-form" type="submit" class="btn btn-success" value="<fmt:message key="Registration"/>">
                        </div>

                    </form>
                </div>
            </div>
            </div>
    </div>
</fmt:bundle>
