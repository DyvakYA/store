<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 3/22/2017
  Time: 9:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--<%@ page import="coffee.machine.view.config.Attributes" %>--%>
<%--<%@ page import="coffee.machine.view.config.ErrorMessage" %>--%>
<%--<%@ page import="org.apache.log4j.Logger" %>--%>
<%--<%! private static final Logger logger = Logger.getLogger("JSP ERROR HANDLER PAGE"); %>--%>
<%--
<% logger.error(String
        .format(ErrorMessage.JSP_HANDLER_MESSAGE_FORMAT,
                pageContext.getSession().getAttribute(Attributes.USER_ROLE),
                pageContext.getSession().getAttribute(Attributes.USER_ID),
                pageContext.getErrorData().getStatusCode(),
                pageContext.getErrorData().getServletName(),
                pageContext.getException().getMessage()), pageContext.getException()); %>
                --%>
<!DOCTYPE html>
<html>
<head>
    <title>ERROR / ОШИБКА / ПОХИБКА</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div align="center">
    <div align="center" style="max-width: 1024px">
        <br><br><br><br><br><br>
        <h3><strong> EN: An internal error has been occurred. Please, contact your application support team. </strong></h3>
        <br><br>
        <h3><strong> RU: Произошла внутренняя ошибка. Пожалуйста, обратитесь в службу поддержки вашего приложения. </strong></h3>
        <br><br>
        <h3><strong> UA: Сталася внутрішня помилка. Будь ласка, зверніться в службу підтримки вашого додатку. </strong></h3>
        <br><br>
        <h1><strong> support@some.domain.com / 0-800-00-00 </strong></h1>

    </div>
</div>

</body>
</html>
