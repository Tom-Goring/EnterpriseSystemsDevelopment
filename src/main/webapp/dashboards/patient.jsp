<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <h3>Your Prescriptions</h3>
    <c:if test="${requestScope.prescriptions.size() == 0}">
        <h3>No prescriptions available</h3>
    </c:if>
    <c:if test="${requestScope.prescriptions.size() != 0}">
        <table border="1" cellpadding="5">
            <tr>
                <th>Medicine</th>
                <th>Quantity</th>
                <th>Repeating</th>
                <th>Issue Date</th>
                <th>End Date</th>
            </tr>
            <c:forEach items="${requestScope.prescriptions}" var="prescription">
                <tr>
                    <td>${prescription.medicine}</td>
                    <td>${prescription.quantity}</td>
                    <td>${prescription.repeating}</td>
                    <td>${prescription.issueDate}</td>
                    <td>${prescription.endDate}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <br/>
    <a href="${pageContext.request.contextPath}/dashboard/appointments">Manage your appointments</a>
    <form method="post" action="${pageContext.request.contextPath}/login">
        <button>Log Out</button>
    </form>
</body>
</html>
