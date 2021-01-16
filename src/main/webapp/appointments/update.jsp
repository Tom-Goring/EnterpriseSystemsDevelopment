<%--
  Created by IntelliJ IDEA.
  User: tomgo
  Date: 16/01/2021
  Time: 18:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Update</h1>
<p>${requestScope.appointment}</p>
<table>

</table>
<form method="post" action="${pageContext.request.contextPath}/appointments/update">
    <table border="1" cellpadding="5">
        <tr>
            <th>Patient Name</th>
            <th>Patient Email</th>
        </tr>
        <tr>
            <td><input type="text" name="customerName" value="${sessionScope.currentUser.firstName} ${sessionScope.currentUser.surname}" readonly></td>
            <td><input type="email" name="email" value="${sessionScope.currentUser.email}" readonly></td>
        </tr>
    </table>
    <br/>
    <br/>
    <table border="1" cellpadding="1">
        <tr>
            <th>Doctor</th>
            <th>Slot</th>
        </tr>
        <tr>
            <td>${requestScope.appointment.staffMember.firstName} ${requestScope.appointment.staffMember.surname}</td>
            <td>${requestScope.appointment.startTime}-${requestScope.appointment.endTime}</td>
        </tr>
    </table>
    <br/>
    <br/>
    <table border="1" cellpadding="5">
        <tr>
            <th>Type</th>
            <th>Date</th>
        </tr>
        <tr>
            <td>
                <label>
                    <select name="serviceType">
                        <option>Public (NHS)</option>
                        <option>Private</option>
                    </select>
                </label>
            </td>
            <td>${requestScope.appointment.date}</td>
        </tr>
    </table>
    <input name="appointmentID" type="hidden" value="${requestScope.appointment.ID}">
    <button>Submit changes</button>
</form>
</body>
</html>
