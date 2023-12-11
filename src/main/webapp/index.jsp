<%@ page import="model.bean.Account" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
    <% Account account = (Account) session.getAttribute("account");
    if (account == null) {
    %>
    <jsp:include page="./WEB-INF/login.jsp" />
    <% } else { %>
    <jsp:include page="./WEB-INF/home.jsp" />
    <% } %>
</body>
</html>