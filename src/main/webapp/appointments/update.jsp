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
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=PT+Sans&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css"/>
        <title>Patient Dashboard</title>
    </head>
    <body>
        <div class="outer">
            <div class="header">
                <div class="innerDashboard">
                    <img src="${pageContext.request.contextPath}/images/temp_logo.png" alt="logo" id="header-logo">
                    <ul>
                        <li><a>${requestScope.user}</a></li>
                        <li>
                            <button type="submit" name="action" value="" form="logout" id="pinkButtonAlternative">Logout</button>
                        </li>
                    </ul>
                    <form method="post" action="${pageContext.request.contextPath}/login" id="logout"></form>
                </div>
            </div>
            <div class="innerDashboard">
                <form method="post" action="${pageContext.request.contextPath}/appointments/update">
                    <table id="appointments">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Doctor</th>
                                <th>Slot</th>
                                <th>Type</th>
                                <th>Date</th>
                            </tr>
                        </thead>
                        <tr>
                            <td><input type="text" name="customerName" value="${sessionScope.currentUser.firstName} ${sessionScope.currentUser.surname}" readonly></td>
                            <td><input type="email" name="email" value="${sessionScope.currentUser.email}" readonly></td>
                            <td>${requestScope.appointment.staffMember.firstName} ${requestScope.appointment.staffMember.surname}</td>
                            <td>${requestScope.appointment.startTime}-${requestScope.appointment.endTime}</td>
                            <td>
                                <select name="serviceType">
                                    <option>public (NHS)</option>
                                    <option>private</option>
                                </select>
                            </td>
                            <td>${requestScope.appointment.date}</td>
                        </tr>
                    </table>                    
                    <input name="appointmentID" type="hidden" value="${requestScope.appointment.ID}">
                    <button id="pinkButtonAlternative">Submit changes</button>
                </form>
            </div>
        </div>
    </body>
</html>
