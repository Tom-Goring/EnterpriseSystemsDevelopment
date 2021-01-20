<%--
  Created by IntelliJ IDEA.
  User: tomgo
  Date: 22/11/2020
  Time: 16:05
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=PT+Sans&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css"/>
    <title>Login</title>
</head>
<body>
<div class="outer">
    <div class="inner">
        <img src="${pageContext.request.contextPath}/images/temp_logo.png" alt="logo" class="center">
        <h1 class="title">Login</h1>
        <form method="post" action="${pageContext.request.contextPath}/login">
            <label>
                Email<span class="highlight-span">*</span>
                <input name="submitted-email" type="text" autocomplete="email">
            </label>
            <br/>
            <label>
                Password<span class="highlight-span">*</span>
                <input name="submitted-password" type="password" autocomplete="off">
            </label>
            <br/>
            <button class="blueButton">Submit</button>
        </form>
        <br/>
        <a href="${pageContext.request.contextPath}/register" id="link">Register</a>
        ${sessionScope.loginFailed ? "Invalid email or password, please try again" : ""}
        ${sessionScope.approvalNeeded ? "A request has been sent to the Admin team to approve your account." : ""}
        <c:set var="approvalNeeded" value="false" scope="session"/>
    </div>
</div>
</body>
</html>
