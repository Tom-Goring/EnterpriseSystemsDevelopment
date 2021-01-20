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
    <title>Title</title>
</head>
<body>
    <center>
        <c:choose>
            <c:when test="${requestScope.stage == 'pickingDateAndLength'}">
                <h3>Pick a date and appointment length</h3>
                <form action="${pageContext.request.contextPath}/appointments/create" method="get">
                    <label>
                        Appointment Date:
                        <input type="date" name="date" min="${requestScope.minimumDate}" max="2099-01-18" required="">
                    </label>
                    <br>
                    <br>
                    <label>
                        Appointment Length:
                        <select name="length">
                            <c:forEach begin="${requestScope.slotPrices.slotSize}" end="${requestScope.slotPrices.maxSlotSize}" step="${requestScope.slotPrices.slotSize}" varStatus="slot">
                                <option value="${slot.current}">${slot.current} minutes</option>
                            </c:forEach>
                        </select>
                    </label>
                    <br>
                    <br>
                    <input type="submit" name="stage" value="pickingDoctorAndSlot"/>
                </form>
            </c:when>
            <c:when test="${requestScope.stage == 'selectingStaffAndSlot'}">
                <center>
                    <h3>Select an available member of staff and a time slot</h3>
                    <c:set var="min" scope="request" value="${0}" />
                    <c:if test="${requestScope.slots.size()==min}">
                        <h3>0 slots available</h3>
                    </c:if>
                    <c:if test="${requestScope.slots.size() > min}">
                        <form action="${pageContext.request.contextPath}/appointments/create" method="post">
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
                            <br>
                            <br>
                            <table border="1" cellpadding="5">
                                <tr>
                                    <th>Doctor</th>
                                    <th>Slots</th>
                                    <th>Selection</th>
                                </tr>
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
                                        <td>
                                            <input required type="radio" name="staffID" value="${doctorToSlots.x.ID}">
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                            <br>
                            <br>
                            <table border="1" cellpadding="5">
                                <tr>
                                    <th>Service Type</th>
                                    <th>Date</th>
                                </tr>
                                <tr>
                                    <td><select name="serviceType">
                                        <option>public (NHS)</option>
                                        <option>private</option>
                                    </select>
                                    </td>
                                    <td>
                                        <input name="date" value="${requestScope.date}" readonly>
                                    </td>
                                </tr>
                            </table>
                            <br>
                            <br>
                            <input type="submit"/>
                        </form>
                    </c:if>
                </center>
            </c:when>
        </c:choose>

    </center>
</body>
</html>
