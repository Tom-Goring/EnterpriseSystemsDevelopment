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
                <details>
                    <summary>Your Prescriptions</summary>
                        <c:if test="${requestScope.prescriptions.size() == 0}">
                        <h3>No prescriptions available</h3>
                    </c:if>
                    <c:if test="${requestScope.prescriptions.size() != 0}">
                        <div id="table">
                            <table id="appointments">
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
                        </div>
                    </c:if>
                </details>
                <details>
                    <summary>Re-Order Prescription</summary>
                        <c:if test="${requestScope.prescriptions.size() == 0}">
                        <h3>No prescriptions available</h3>
                    </c:if>
                    <c:if test="${requestScope.prescriptions.size() != 0}">
                        <div id="table">
                            <form id="prescriptions" method="post" action="${pageContext.request.contextPath}/IssuePrescription">
                                <table id="appointments">
                                    <tr>
                                        <th>Medicine</th>
                                        <th>Quantity</th>
                                        <th>Repeating</th>
                                        <th>Issue Date</th>
                                        <th>End Date</th>
                                        <th>Repeat</th>
                                    </tr>
                                    <c:forEach items="${requestScope.prescriptions}" var="prescription">
                                        <c:if test="${prescription.repeating=='false'}">
                                            <tr>
                                                <td>${prescription.medicine}</td>
                                                <td>${prescription.quantity}</td>
                                                <td>${prescription.repeating}</td>
                                                <td>${prescription.issueDate}</td>
                                                <td>${prescription.endDate}</td>
                                                <td><input type="radio" name="selectedPrescription" value="${prescription.ID}"></td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </table>
                                <br>
                                <button id="pinkButtonAlternative" type="submit" name="action" value="repeatPrescriptions" form="prescriptions"
                                        >Repeat Order
                                </button>
                            </form>

                        </div>
                    </c:if>
                </details>
                <details>
                    <summary>Appointments</summary>
                        <c:if test="${requestScope.appointments.size() == 0}">
                        <h3>No appointments scheduled</h3>
                    </c:if>
                    <c:if test="${requestScope.appointments.size() != 0}">
                        <table id="appointments">
                            <tr>
                                <th>Date</th>
                                <th>Staff Member</th>
                                <th>Start Time</th>
                                <th>End Time</th>
                                <th>Type</th>
                                <th>Edit</th>
                                <th>Delete</th>
                            </tr>
                            <c:forEach items="${requestScope.appointments}" var="appointment">
                                <tr>
                                    <td>${appointment.date}</td>
                                    <td>${appointment.staffMember.firstName} ${appointment.staffMember.surname}</td>
                                    <td>${appointment.startTime}</td>
                                    <td>${appointment.endTime}</td>
                                    <td>${appointment.type}</td>
                                    <td>
                                        <form method="get" action="${pageContext.request.contextPath}/appointments/update">
                                            <button id="pinkButtonAlternative">Edit</button>
                                            <input type="hidden" name="appointmentID" value="${appointment.ID}">
                                        </form>
                                    </td>
                                    <td>
                                        <form method="post" action="${pageContext.request.contextPath}/appointments/delete">
                                            <button id="pinkButtonAlternative">Delete</button>
                                            <input type="hidden" name="appointmentID" value="${appointment.ID}">
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>
                    <br/>
                    <a href="${pageContext.request.contextPath}/appointments/create">
                        <button type="button" id="pinkButtonAlternative">Schedule a new appointment</button>
                    </a>
                    <br>
                </details>
            </div>
        </div>
    </body>
</html>
