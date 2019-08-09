<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <title>Shopping Cart</title>
</head>
<body>
<%
    String pagePath = "pages/home.jsp";
    if (request.getParameter("page") != null) {
        pagePath = "pages/" + request.getParameter("page") + ".jsp";
    }
%>

<jsp:include page="<%= pagePath %>"></jsp:include>
</body>
</html>