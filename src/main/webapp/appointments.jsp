<%--
    Document   : AppointmentPages
    Created on : 13-Dec-2020, 19:02:50
    Author     : maxwell
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP  Page</title>
</head>
<body>
<center>
    <h1>Appointments Page</h1>
</center>
<c:choose>
    <c:when test="${requestScope.task == null}">
        <center>
            <form method="GET" action="${pageContext.request.contextPath}/dashboard/appointments">
                <input type="submit" name ="action" value="Add" />
                <input type="submit" name ="action" value="Update" />
                <input type="submit" name ="action" value="Delete" />
                <input type="submit" name ="action" value="View" />
            </form>
        </center>
    </c:when>

    <c:when test="${requestScope.task == 'add'}">
        <center>
            <h3>Availability</h3>
            <form action="${pageContext.request.contextPath}/dashboard/appointments" method="post" >
                <label>
                    Appointment Date:
                    <input type="date" name="date" required="">
                </label>
                <br>
                <br>
                <label>
                    Appointment Length:
                    <select name="length">
                        <option>15</option>
                        <option>30</option>
                        <option>60</option>
                    </select>
                </label>
                <br>
                <br>
                <input type="submit" name="action" value="Availability" />
            </form>
        </center>
    </c:when>

    <c:when test="${requestScope.task == 'availableSlots'}">
        <center>
            <h3>Add Appointment</h3>
            <c:set var="min" scope="request" value="${0}" />
            <c:if test="${requestScope.slots.size()==min}">
                <h3>0 slots available</h3>
            </c:if>
            <c:if test="${requestScope.slots.size() > min}">
                <form action="${pageContext.request.contextPath}/dashboard/appointments" method="post">
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
                                    <select name="selectedSlot">
                                        <c:forEach items="${doctorToSlots.y}" var="slot">
                                            <option value="${slot}/${doctorToSlots.x.ID}">${slot}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <input type="radio" name="staffID" value="${doctorToSlots.x.ID}">
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
                    <input type="submit" name="action" value="Confirm" />
                </form>
            </c:if>
        </center>
    </c:when>

    <c:when test="${requestScope.task == 'delete'}">
        <center>
            <h3>Delete Appointment</h3>
            <form action="${pageContext.request.contextPath}/dashboard/appointments" method="post">
                <div>
                    <c:set var="min" scope="request" value="${0}" />
                    <c:if test="${requestScope.appointments.size()==min}">
                        <h3>No appointments booked</h3>
                    </c:if>
                    <c:if test="${requestScope.appointments.size() > min}">
                        <c:set var="count" value="0" scope="page" />
                        <table border="1" cellpadding="5">
                            <caption>List of Appointments</caption>
                            <tr>
                                <th>Number</th>
                                <th>Date</th>
                                <th>Start</th>
                                <th>End</th>
                                <th>Type</th>
                                <th>Staff Member</th>
                                <th>Select</th>

                            </tr>
                            <c:forEach items="${requestScope.appointments}" var="current">
                                <c:set var="count" value="${count + 1}" scope="page"/>
                                <tr>
                                    <td><c:out value="${count}"/></td>
                                    <td><c:out value= "${current.date}"/></td>
                                    <td><c:out value= "${current.startTime}"/></td>
                                    <td><c:out value= "${current.endTime}"/></td>
                                    <td><c:out value= "${current.type}"/></td>
                                    <td><c:out value= "${current.staffMember.firstName} ${current.staffMember.surname}"/></td>
                                    <td><label>
                                        <input type="checkbox" name="selected" value="${current.ID}"/>
                                    </label></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>
                </div>
                <br>
                <input type="submit" name="action" value="Delete Selected"/>
            </form>
        </center>
    </c:when>
    <c:when test="${requestScope.task == 'update'}">
        <center>
            <h3>Update Appointment</h3>
            <form action="${pageContext.request.contextPath}/dashboard/appointments" method="post">
                <div>
                    <c:set var="min" scope="request" value="${0}" />
                    <c:if test="${requestScope.appointments.size()==min}">
                        <h3>No appointments booked</h3>
                    </c:if>
                    <c:if test="${requestScope.appointments.size() > min}">
                        <c:set var="count" value="0" scope="page" />
                        <table border="1" cellpadding="5">
                            <caption>List of Appointments</caption>
                            <tr>
                                <th>Number</th>
                                <th>Date</th>
                                <th>Start</th>
                                <th>End</th>
                                <th>Type</th>
                                <th>Doctor</th>
                                <th>Select</th>
                            </tr>
                            <c:forEach items="${requestScope.appointments}" var="current">
                                <c:set var="count" value="${count + 1}" scope="page"/>
                                <tr>
                                    <td><c:out value= "${count}"/></td>
                                    <td><c:out value= "${current.date}"/></td>
                                    <td><c:out value= "${current.startTime}"/></td>
                                    <td><c:out value= "${current.endTime}"/></td>
                                    <td>
                                        <c:set var="type" scope="request" value="${current.type}" />
                                        <c:if test="${current.type.equals(type)}">
                                            <label>
                                                <select name="types">
                                                    <option>public (NHS)</option>
                                                    <option>private</option>
                                                </select>
                                            </label>
                                        </c:if>
                                        <c:if test="${current.type!=type}">
                                        <label>
                                            <select name="types">
                                                <option>private</option>
                                                <option>public (NHS)</option>
                                            </select>
                                        </label>
                                    </td>
                                    </c:if>
                                    <td><c:out value= "${current.staffMember.firstName} ${current.staffMember.surname}"/></td>
                                    <td><label>
                                        <input type="checkbox" name="selected" value="${count}"/>
                                    </label></td>
                                </tr>
                                <label>
                                        <%-- I hate absolutely everything about this. ヽ(ಠ_ಠ)ノ --%>
                                    <input type="hidden" name="appointmentIDs" value="${current.ID}">
                                    <input type="hidden" name="staffIDs" value="${current.staffMember.ID}">
                                    <input type="hidden" name="patientIDs" value="${current.patient.ID}">
                                    <input type="hidden" name="startTimes" value="${current.startTime}">
                                    <input type="hidden" name="endTimes" value="${current.endTime}">
                                    <input type="hidden" name="dates" value="${current.date}">
                                </label>
                            </c:forEach>
                        </table>
                    </c:if>
                </div>
                <br>
                <input type="submit" name="action" value="Update Selected"/>
            </form>
        </center>
    </c:when>
    <c:when test="${requestScope.task == 'view'}">
        <center>
            <h3>Your Appointments</h3>
            <c:set var="min" scope="request" value="${0}" />
            <c:if test="${requestScope.appointments.size()==min}">
                <h3>No appointments booked</h3>
            </c:if>
            <c:if test="${requestScope.appointments.size() > min}">
                <table border="1" cellpadding="5">
                    <caption>List of Appointments</caption>
                    <tr>
                        <th>Staff Name</th>
                        <th>Date</th>
                        <th>Start</th>
                        <th>End</th>
                        <th>Type</th>
                    </tr>
                    <c:forEach items="${requestScope.appointments}" var="current">
                        <tr>
                            <td><c:out value= "${current.staffMember.firstName} ${current.staffMember.surname}"/></td>
                            <td><c:out value= "${current.date}"/></td>
                            <td><c:out value= "${current.startTime}"/></td>
                            <td><c:out value= "${current.endTime}"/></td>
                            <td><c:out value= "${current.type}"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
            <br>
        </center>
    </c:when>
    <c:when test="${requestScope.task == 'viewSelected' || requestScope.task == 'viewAll' }">
        <center>
            <h3>View Appointment</h3>
            <div>
                <c:set var="min" scope="request" value="${0}" />
                <c:if test="${requestScope.appointments.size()==min}">
                    <h3>No appointments booked</h3>
                </c:if>
                <c:if test="${requestScope.appointments.size() > min}">
                    <table border="1" cellpadding="5">
                        <caption>List of Appointments</caption>
                        <tr>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Date</th>
                            <th>Start</th>
                            <th>End</th>
                            <th>Type</th>
                            <th>Doctor</th>
                        </tr>
                        <c:forEach items="${requestScope.appointments}" var="current">
                            <tr>
                                <td><c:out value= "${current.customerName}"/></td>
                                <td><c:out value= "${current.email}"/></td>
                                <td><c:out value= "${current.date}"/></td>
                                <td><c:out value= "${current.startTime}"/></td>
                                <td><c:out value= "${current.endTime}"/></td>
                                <td><c:out value= "${current.type}"/></td>
                                <td><c:out value= "${current.doctorName}"/></td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
            </div>
            <br>
            <form method="GET" action="${pageContext.request.contextPath}/dashboard/appointments">
                <input type="submit" name ="action" value="Add" />
                <input type="submit" name ="action" value="Update" />
                <input type="submit" name ="action" value="Delete" />
                <input type="submit" name ="action" value="View" />
            </form>
        </center>
    </c:when>
    <c:when test="${requestScope.task == 'displayTaskSuccess'}">
        <center>
            <h3> ${requestScope.successMessage} </h3>
            <form method="GET" action="${pageContext.request.contextPath}/dashboard/appointments">
                <input type="submit" name ="action" value="Add" />
                <input type="submit" name ="action" value="Update" />
                <input type="submit" name ="action" value="Delete" />
                <input type="submit" name ="action" value="View" />
            </form>
        </center>
    </c:when>
</c:choose>

</body>
</html>