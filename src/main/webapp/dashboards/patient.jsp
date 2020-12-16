<%--
  Created by IntelliJ IDEA.
  User: tomgo
  Date: 11/12/2020
  Time: 02:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=PT+Sans&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css" />
    <title>Patient Dashboard</title>
</head>
<body>
    <p>Patient dashboard!</p>
    <form method="post" action="${pageContext.request.contextPath}/login">
        <button>Log Out</button>
    </form>
</body>
</html>
