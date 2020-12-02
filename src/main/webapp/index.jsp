<%--
  Created by IntelliJ IDEA.
  User: tomgo
  Date: 20/11/2020
  Time: 19:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
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
    <br/>
    <a href="dashboard/patient.jsp">Patient Page</a>
</body>
</html>
