<%--
  Created by IntelliJ IDEA.
  User: tomgo
  Date: 22/11/2020
  Time: 16:05
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/main.css" />
    <title>Login</title>
</head>
<body>
    <form method="post" action="${pageContext.request.contextPath}/login">
        <label>
            Email
            <input name="submitted-email" autocomplete="email">
        </label>
        <br/>
        <label>
            Password:
            <input name="submitted-password" autocomplete="off">
        </label>
        <button>Submit</button>
    </form>
    <br/>
    ${requestScope.login_failed ? "Invalid email or password, please try again" : ""}
</body>
</html>
