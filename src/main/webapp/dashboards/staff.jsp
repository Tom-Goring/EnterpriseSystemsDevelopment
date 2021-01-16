<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=PT+Sans&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css" />
    <title>Staff Dashboard</title>
</head>
<body>
    <p>Staff dashboard</p>
    <form method="get" action="${pageContext.request.contextPath}/IssuePrescription">
        <button>Issue Prescription to Patient</button>
    </form>
    <c:if test="${requestScope.appointments.size() == 0}">
        <h3>No appointments scheduled</h3
    </c:if>
    <c:if test="${requestScope.appointments.size() != 0}">
        <table border="1" cellpadding="5">
            <tr>
                <th>Date</th>
                <th>Patient</th>
                <th>Start Time</th>
                <th>End Time</th>
                <th>Public/Private</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
            <c:forEach items="${requestScope.appointments}" var="appointment">
                <tr>
                    <td>${appointment.date}</td>
                    <td>${appointment.patient.firstName} ${appointment.patient.surname}</td>
                    <td>${appointment.startTime}</td>
                    <td>${appointment.endTime}</td>
                    <td>${appointment.type}</td>
                    <td>
                        <form method="get" action="${pageContext.request.contextPath}/appointments/update">
                            <button>Edit</button>
                            <input type="hidden" name="appointmentID" value="${appointment.ID}">
                        </form>
                    </td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/appointments/delete">
                            <button>Delete</button>
                            <input type="hidden" name="appointmentID" value="${appointment.ID}">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/login">
        <button>Log Out</button>
    </form>
</body>
</html>
