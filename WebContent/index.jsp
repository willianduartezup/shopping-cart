<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <title>Shopping Cart</title>
</head>
<body>
<div>
    <a href="index.jsp">Home</a>
    <a href="index.jsp?page=product/list">Product</a>
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