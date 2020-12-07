<%--
  Created by IntelliJ IDEA.
  User: tomgo
  Date: 20/11/2020
  Time: 19:27
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/main.css" />
    <title>Index</title>
</head>
<body>
    <h1>All good!</h1>
    <br/>
    Database String: ${requestScope.DatabaseString}
    <br/>
    <br/>
    <a href="login.jsp">Login Page</a>
    <br/>
    <a href="register.jsp">Registration Page</a>
</body>
</html>
