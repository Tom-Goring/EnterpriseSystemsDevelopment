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
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=PT+Sans&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css"/>
        <title>User Information</title>
    </head>
    <body>        
        <div class="outer">
            <form method="post" action="${pageContext.request.contextPath}/login" id="logout"></form>
            <div class="header">
                <div class="innerDashboard">
                    <img src="${pageContext.request.contextPath}/images/temp_logo.png" alt="logo" id="header-logo">
                    <ul>
                        <li><a>${requestScope.user.firstName} ${requestScope.user.surname}</a></li>
                        <li>
                            <button type="submit" name="action" value="" form="logout" id="pinkButtonAlternative">Logout</button>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="innerDashboard">            
                <details><summary>User Details</summary>
                    <table id="appointments">
                        <tr>
                            <th>Firstname</th>
                            <th>Surname</th>
                            <th>DOB</th>
                            <th>City</th>
                            <th>Street</th>
                            <th>Post Code</th>
                            <th>Gender</th>
                            <th>Email</th>
                            <th>User Role</th>
                        </tr>
                        <tr>
                            <td>${requestScope.user.firstName}</td>
                            <td>${requestScope.user.surname}</td>                            
                            <td>${requestScope.user.DOB}</td>
                            <td>${requestScope.user.address.city}</td>
                            <td>${requestScope.user.address.street}</td>
                            <td>${requestScope.user.address.postcode}</td>
                            <td>${requestScope.user.gender}</td>
                            <td>${requestScope.user.email}</td>
                            <td>${requestScope.user.role}</td>
                        </tr>
                    </table>
                </details>
                <details>
                    <summary>Upcoming Appointments</summary>
                        <c:if test="${requestScope.appointments.size() == 0}">
                        <h3>No upcoming appointments</h3>
                    </c:if>
                    <c:if test="${requestScope.appointments.size() != 0}">                       
                        <table id="appointments">
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
                    </c:if>
                </details>
                <details>
                    <summary>Prescriptions</summary>
                        <c:if test="${requestScope.prescriptions.size() == 0}">
                        <h3>No prescriptions available</h3>
                    </c:if>
                    <c:if test="${requestScope.prescriptions.size() != 0}">  
                        <table id="appointments">
                            <tr>                           
                                <th>Medicine</th>
                                <th>Quantity</th>
                                <th>Repeating</th>
                                <th>Issue Date</th>
                                <th>End Date</th>
                            </tr>
                            <c:forEach items="${requestScope.prescriptions}" var="prescriptions">
                                <tr>                               
                                    <td>${prescriptions.medicine}</td>
                                    <td>${prescriptions.quantity}</td>
                                    <td>${prescriptions.repeating}</td>
                                    <td>${prescriptions.issueDate}</td>
                                    <td>${prescriptions.endDate}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>
                </details>
            </div>
        </div>
    </body>
</html>
