<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<!doctype html>

<html>

<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="pages/style.css">
    <title style="font-family:sans-serif;">Shopping Cart</title>

    <script type="text/javascript" src="js/infra/url.js"></script>
    <script type="text/javascript" src="js/infra/request.js"></script>
    <script type="text/javascript" src="js/factory/productFactory.js"></script>
    <script type="text/javascript" src="js/factory/userFactory.js"></script>
    <script type="text/javascript" src="js/factory/cartFactory.js"></script>
    <script type="text/javascript" src="js/factory/orderFactory.js"></script>
</head>
<body>
<div>
    <a href="index.jsp" style="font-family: sans-serif;">Home</a>
    <a href="index.jsp?page=product/list" style="font-family:sans-serif">Product</a>
    <a href="index.jsp?page=user/usersList" style="font-family:sans-serif">Users</a>
</div>
<div style="margin-top: 50px;">
    <%
        String pagePath = "pages/home.jsp";
        if (request.getParameter("page") != null) {
            pagePath = "pages/" + request.getParameter("page") + ".jsp";
        }
    %>
    <jsp:include page="<%= pagePath %>"></jsp:include>
</div>
</body>
</html>