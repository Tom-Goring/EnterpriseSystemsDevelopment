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
        <div class="outer">
            <form method="post" action="${pageContext.request.contextPath}/login" id="logout"></form>
            <div class="header">
                <div class="innerDashboard">
                    <img src="${pageContext.request.contextPath}/images/temp_logo.png" alt="logo" id="header-logo">
                    <ul>
                        <li><a>${requestScope.user}</a></li>
                        <li>
                            <button type="submit" name="action" value="" form="logout" id="pinkButtonAlternative">Logout</button>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="innerDashboard">
                <details>
                    <summary>Issue Prescription</summary>  
                    <form method="post" action="${pageContext.request.contextPath}/IssuePrescription" id="prescription">
                        <label>
                            Patient<span class="highlight-span">*</span>
                            <select name="submitted-patientid" required="">
                                <c:forEach items="${requestScope.users}" var="user">
                                    <option id="userID" value="${user.ID}">${user.firstName} ${user.surname}</option>
                                </c:forEach>
                            </select>
                        </label>         
                        <label>
                            Medicine<span class="highlight-span">*</span>
                            <input name="submitted-medicine" type="text" required="">
                        </label>
                        <label>
                            Quantity<span class="highlight-span">*</span>   
                            <input name="submitted-quantity" type="number" required="">
                        </label>
                        <label>
                            Repeating<span class="highlight-span">*</span>  
                            <select name="submitted-repeating" required="">
                                <option>true</option>
                                <option>false</option>
                            </select>
                        </label>           
                        <label>
                            Issue date<span class="highlight-span">*</span> 
                            <input name="submitted-issuedate"  type="date" required="" min="${requestScope.minimumDate}" max="2099-01-18">
                        </label>
                        <label>

                            End date<span class="highlight-span">*</span> 
                            <input name="submitted-enddate" type="date" required="" min="${requestScope.minimumDate}" max="2099-01-18">
                        </label>
                        <p>&nbsp</p>                      
                        <button type="submit" id="blueButtonAlternative" form="prescription" value="issueprescription">Submit</button>                      
                    </form>
                </details>
                <details>
                    <summary>Prescription Reordering Approvals</summary>  
                        <c:if test="${requestScope.approvals.size() == 0}">
                        <h3>No prescriptions approvals</h3>
                    </c:if>
                    <c:if test="${requestScope.approvals.size() != 0}">
                        <form method="post">
                            <table id="appointments">
                                <thead>
                                    <tr>
                                        <th>Patient</th>
                                        <th>Medicine</th>
                                        <th>Quantity</th>
                                        <th>Issue Date</th>
                                        <th>End Date</th>
                                        <th>Approve Reorder</th>
                                    </tr>                                
                                </thead>
                                <c:forEach items="${requestScope.approvals}" var="approvals">
                                    <tr>
                                        <td>${approvals.prescription.patient.firstName} ${approvals.prescription.patient.surname}</td>
                                        <td>${approvals.prescription.medicine}</td>
                                        <td>${approvals.prescription.quantity}</td>
                                        <td>${approvals.prescription.issueDate}</td>
                                        <td>${approvals.prescription.endDate}</td>
                                        <td><input type="checkbox" value="${approvals.ID}" name="approvedPrescription"></td>
                                    </tr>
                                </c:forEach>
                            </table>
                            <br>
                            <button type="submit" name="action" value="approve" id="pinkButtonAlternative">Approve Selected</button>
                        </form>
                    </c:if>
                </details>
                <details>
                    <summary>Upcoming Appointments</summary>
                        <c:if test="${requestScope.appointments.size() == 0}">
                        <h3>No appointments scheduled</h3>
                    </c:if>
                    <c:if test="${requestScope.appointments.size() != 0}">
                    <table border="1" cellpadding="5">
                        <tr>
                            <th>Date</th>
                            <th>Patient</th>
                            <th>Start Time</th>
                            <th>End Time</th>
                            <th>Public/Private</th>
                            <th>Issue Invoice</th>
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
                                    <form method="get" action="${pageContext.request.contextPath}/IssueInvoice">
                                        <input type="hidden" name="appointmentID" value="${appointment.ID}">
                                        <button id="blueButtonAlternative">Create Invoice</button>
                                    </form>
                                </td>
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
            </details>
            <details open>
                <summary>Issue a Prescription</summary>
                    <form method="get" action="${pageContext.request.contextPath}/IssuePrescription">
                        <button id="blueButtonAlternative">Create prescription</button>
                    </form>                
            </details>
        </div>
    </body>       
</html>
