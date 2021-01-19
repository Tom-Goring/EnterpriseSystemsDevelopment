<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: tomgo
  Date: 16/01/2021
  Time: 17:48
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
                <c:choose>
                    <c:when test="${requestScope.stage == 'pickingDateAndLength'}">
                        <details open>
                            <summary>Appointment Date and Length</summary>
                            <form action="${pageContext.request.contextPath}/appointments/create" method="get" id="addbooking">
                                <label>
                                    Appointment Date:<span class="highlight-span">*</span> 
                                    <input type="date" name="date" min="${requestScope.minimumDate}" max="2099-01-18" required="">
                                </label>
                                <label>
                                    Appointment Length:<span class="highlight-span">*</span> 
                                    <select name="length">
                                        <option>15</option>
                                        <option>30</option>
                                        <option>60</option>
                                    </select>
                                </label>
                                <br>
                                <br>
                                <button type="submit" name="stage" value="pickingDoctorAndSlot" id="blueButtonAlternative">Generate Slots</button>
                            </form>
                        </details>
                    </c:when>
                    <c:when test="${requestScope.stage == 'selectingStaffAndSlot'}">
                        <details open>
                            <summary>Available member of staff and time slot</summary>
                            <c:set var="min" scope="request" value="${0}" />
                            <c:if test="${requestScope.slots.size()==min}">
                                <p>0 slots available</p>
                            </c:if>
                            <c:if test="${requestScope.slots.size() > min}">
                                <form action="${pageContext.request.contextPath}/appointments/create" method="post">                                                                        
                                    <table id="appointments">
                                        <thead>
                                            <tr>                             
                                                <th>Doctor</th>
                                                <th>Slots</th>
                                                <th>Service Type</th>
                                                <th>Date</th>
                                                <th>Selection</th>
                                            </tr>
                                        </thead>
                                        <c:forEach items="${requestScope.doctorSlots}" var="doctorToSlots">
                                            <tr>
                                                <td><c:out value="${doctorToSlots.x.firstName} ${doctorToSlots.x.surname}"/></td>
                                                <td>
                                                    <select name="selectedSlot" required>
                                                        <c:forEach items="${doctorToSlots.y}" var="slot">
                                                            <option value="${slot}/${doctorToSlots.x.ID}">${slot}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td><select name="serviceType">
                                                        <option>public (NHS)</option>
                                                        <option>private</option>
                                                    </select>
                                                </td>

                                                <td>
                                                    <input name="date" value="${requestScope.date}" readonly>
                                                </td>

                                                <td>
                                                    <input required type="radio" name="staffID" value="${doctorToSlots.x.ID}">
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>                                               
                                    <input type="hidden" name="customerName" value="${sessionScope.currentUser.firstName} ${sessionScope.currentUser.surname}"></td>
                                    <input type="hidden" name="email" value="${sessionScope.currentUser.email}">
                                    <button type="submit" id="pinkButtonAlternative">Confirm Appointment</button>
                                    <!--                                <input type="submit"/>-->
                                </form>
                            </c:if>
                        </details>
                    </c:when>
                </c:choose>
            </div>
        </div>
    </body>
</html>
