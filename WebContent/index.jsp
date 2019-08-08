<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <title>Title</title>
</head>
<body>
<!--%@ include file="pages/test.html"%-->
<%= request.getParameter("page") %>
</body>
</html>