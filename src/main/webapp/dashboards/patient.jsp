<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: tomgo
  Date: 11/12/2020
  Time: 02:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=PT+Sans&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/appointment.css"/>
    <title>Patient Dashboard</title>
</head>
<body>
<div class="outer">
    <div class="header">
        <div class="innerDashboard">
            <img src="${pageContext.request.contextPath}/images/temp_logo.png" alt="logo" id="header-logo">
            <ul>
                <li><a>${requestScope.username}</a></li>
                <li>
                    <button type="submit" name="action" value="" form="logout">Logout</button>
                </li>
            </ul>
        </div>
    </div>
    <div class="innerDashboard">
        <c:if test="${requestScope.prescriptions.size() == 0}">
            <h3>No prescriptions available</h3>
        </c:if>
        <c:if test="${requestScope.prescriptions.size() != 0}">
            <table id="appointments">
                <caption>Yours Prescriptions</caption>
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
        <form method="post" action="${pageContext.request.contextPath}/login" id="logout"></form>
        <button class="pinkAlternateButton" type="submit" name="action" value="prescriptions" form="prescription"
                disabled>Repeat Order
        </button>
        <a href="${pageContext.request.contextPath}/dashboard/appointments">
            <button type="button" class="pinkAlternateButton">Appointments</button>
        </a>

    </div>
</div>
</body>
</html>
