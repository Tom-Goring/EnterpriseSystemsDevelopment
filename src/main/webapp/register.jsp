<%--
  Created by IntelliJ IDEA.
  User: tomgo
  Date: 22/11/2020
  Time: 16:48
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=PT+Sans&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css" />
    <title>Register</title>
</head>
<body>
    <h1>User Registration 0.1</h1>
    <form method="post" action="${pageContext.request.contextPath}/register">
        <label>
            First Name:
            <input name="submitted-name" autocomplete="name">
        </label>
        <br/>
        <label>
            Last Name:
            <input name="submitted-surname" autocomplete="surname">
        </label>
        <br/>
        <label>
            Email
            <input name="submitted-email" autocomplete="email">
        </label>
        <br/>
        <label>
            Password:
            <input name="submitted-password" autocomplete="off">
        </label>
        <br/>
        <label>
            Role:
            <input name="submitted-role" autocomplete="off">
        </label>
        <br/>
        <button>Submit</button>
    </form>
    <br/>
    <a href="${pageContext.request.contextPath}/login">Login</a>
    ${requestScope.duplicate_email_error ? "That email already has an associated account. Would you like to login instead?" : ""}
</body>
</html>
