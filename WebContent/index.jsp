<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <title>Shopping Cart</title>

    <script type="text/javascript" src="js/infra/url.js"></script>
    <script type="text/javascript" src="js/infra/request.js"></script>
    <script type="text/javascript" src="js/factory/productFactory.js"></script>
    <script type="text/javascript" src="js/factory/userFactory.js"></script>
</head>
<body>
<div>
    <a href="index.jsp">Home</a>
    <a href="index.jsp?page=product/list">Product</a>
    <a href="index.jsp?page=user/usersList">Users</a>
    <a href="index.jsp?page=cart/manager&user_id=3242hjg32jjkh234">Cart</a>
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