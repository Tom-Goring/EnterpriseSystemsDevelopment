<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Louis
  Date: 15/12/2020
  Time: 18:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h3>${requestScope.user.firstName} ${requestScope.user.surname} ${requestScope.prescriptions}</h3>
    <table border="1" cellpadding="5">
        <tr>
            <th>Firstname</th>
            <th>Surname</th>
            <th>Email</th>
            <th>User Role</th>
        </tr>
        <tr>
            <td>${requestScope.user.firstName}</td>
            <td>${requestScope.user.surname}</td>
            <td>${requestScope.user.email}</td>
            <td>${requestScope.user.role}</td>
        </tr>
    </table>
    <br/>
    <table border="1" cellpadding="5">
        <tr>
            <th>Staff Member</th>
            <th>Patient</th>
            <th>Date</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Type</th>
        </tr>
    <c:forEach items="${requestScope.appointments}" var="appointment">
        <tr>
            <td>${appointment.staffMember.firstName} ${appointment.staffMember.surname}</td>
            <td>${appointment.patient.firstName} ${appointment.patient.surname}</td>
            <td>${appointment.date}</td>
            <td>${appointment.startTime}</td>
            <td>${appointment.endTime}</td>
            <td>${appointment.type}</td>
        </tr>
    </c:forEach>
    </table>
    <br/>
<%--Table for prescriptions--%>
    <table cellpadding="5" border="1">
        <tr>
            <th>Patient</th>
            <th>Medicine</th>
            <th>Quantity</th>
            <th>Repeating</th>
            <th>Issue Date</th>
            <th>End Date</th>
        </tr>
        <c:forEach items="${requestScope.prescriptions}" var="prescriptions">
            <tr>
                <td>${prescriptions.patient}</td>
                <td>${prescriptions.medicine}</td>
                <td>${prescriptions.quantity}</td>
                <td>${prescriptions.repeating}</td>
                <td>${prescriptions.issueDate}</td>
                <td>${prescriptions.endDate}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
