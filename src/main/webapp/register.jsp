<%--
  Created by IntelliJ IDEA.
  User: tomgo
  Date: 22/11/2020
  Time: 16:48
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=PT+Sans&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css"/>
    <title>Register</title>
</head>
<body>
<div class="outer">
    <div class="inner">
        <img src="${pageContext.request.contextPath}/images/temp_logo.png" alt="logo" class="center">
        <h1 class="title">Register</h1>
        <form method="post" action="${pageContext.request.contextPath}/register">
            <label>
                First Name<span class="highlight-span">*</span>
                <input name="submitted-name" type="text" autocomplete="name">
            </label>
            <br/>
            <label>
                Last Name<span class="highlight-span">*</span>
                <input name="submitted-surname" type="text" autocomplete="surname">
            </label>
            <br/>
            <label>
                Email<span class="highlight-span">*</span>
                <input name="submitted-email" type="text" autocomplete="email">
            </label>
            <br/>
            <label>
                Password<span class="highlight-span">*</span>
                <input name="submitted-password" type="text" autocomplete="off">
            </label>
            <br/>
            <label>
                Role<span class="highlight-span">*</span>
                <select name="submitted-role">
                    <option value="patient">Patient</option>
                    <option value="nurse">Nurse</option>
                    <option value="doctor">Doctor</option>
                    <option value="admin">Admin</option>
                </select>
            </label>
            <br/>
            <button class="pinkButton">Submit</button>
        </form>
        <br/>
        <a href="${pageContext.request.contextPath}/login" id="link">Login</a>
        ${requestScope.duplicate_email_error ? "That email already has an associated account. Would you like to login instead?" : ""}
    </div>
</div>
</body>
</html>
