<%--
  Created by IntelliJ IDEA.
  User: tomgo
  Date: 11/12/2020
  Time: 02:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Staff Dashboard</title>
</head>
<body>
    <p>Staff dashboard</p>
    <form method="post" action="${pageContext.request.contextPath}/IssuePrescription.jsp">
        <button>Issue Prescription to Patient</button>
    </form>
    <form method="post" action="${pageContext.request.contextPath}/login">
        <button>Log Out</button>
    </form>
</body>
</html>
