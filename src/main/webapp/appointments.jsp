<%--
    Document   : AppointmentPages
    Created on : 13-Dec-2020, 19:02:50
    Author     : maxwell
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=PT+Sans&display=swap" rel="stylesheet">
<!--        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/appointment.css"/>-->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css"/>
        <title>Appointments</title>
    </head>
    <body>
        <div class="outer">
            <div class="header">
                <div class="innerDashboard">
                    <img src="${pageContext.request.contextPath}/images/temp_logo.png" alt="logo" id="header-logo">
                    <ul>
                        <li><a>${requestScope.username}</a></li>
                        <li>
                                <button type="submit" name="action" value="" form="logout" id="pinkButton">Logout</button>                            
                        </li>
                    </ul>
                </div>
            </div>
            <div class="innerDashboard">
                <details open>
                    <summary>Your Appointments</summary>
                        <c:set var="min" scope="request" value="${0}"/>
                        <c:if test="${requestScope.appointments.size()==min}">
                        <h3>No appointments booked</h3>
                    </c:if>
                    <c:if test="${requestScope.appointments.size() > min}">
                        <div id="table">
                            <table id="appointments">                                
                                <thead>
                                    <tr>
                                        <th>Staff Name</th>
                                        <th>Date</th>
                                        <th>Start</th>
                                        <th>End</th>
                                        <th>Type</th>
                                    </tr>
                                </thead>
                                <c:forEach items="${requestScope.appointments}" var="current">
                                    <tr>
                                        <td><c:out
                                                value="${current.staffMember.firstName} ${current.staffMember.surname}"/></td>
                                        <td><c:out value="${current.date}"/></td>
                                        <td><c:out value="${current.startTime}"/></td>
                                        <td><c:out value="${current.endTime}"/></td>
                                        <td><c:out value="${current.type}"/></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </c:if>                
                    <form method="post" action="${pageContext.request.contextPath}/login" id="logout"></form>
                    <form method="GET" action="${pageContext.request.contextPath}/dashboard/appointments"
                          id="optionsForm"></form>                                              
                </details>           
                <details>
                    <summary>Add Appointment</summary>                               
                    <form action="${pageContext.request.contextPath}/dashboard/appointments" method="post"
                          id="availability">
                        <div id="addbooking">
                            <label>
                                Date:
                                <input type="date" name="date" required="">
                            </label>
                            <label>
                                Length:
                                <select name="length">
                                    <option>15</option>
                                    <option>30</option>
                                    <option>60</option>
                                </select>
                            </label>                        
                        </div>
                        <button type="submit" name="action" value="Availability" id="blueButton">Generate Slots</button>
                    </form>
                    <br>
                </details>                              
                <c:set var="min" scope="request" value="${0}"/>
                <c:if test="${requestScope.slots.size()==min}">
                    <h3>0 slots available</h3>
                </c:if>
                <c:if test="${requestScope.slots.size() > min}">             
                    <form action="${pageContext.request.contextPath}/dashboard/appointments" method="post">
                        <table id="appointments">          
                            <tr>
                                <th>Doctor</th>
                                <th>Slots</th>
                                <th>Service Type</th>
                                <th>Date</th>
                                <th>Selection</th>
                            </tr>
                            <c:forEach items="${requestScope.doctorSlots}" var="doctorToSlots">
                                <tr>
                                    <td><c:out
                                            value="${doctorToSlots.x.firstName} ${doctorToSlots.x.surname}"/></td>
                                    <td>
                                        <select name="selectedSlot">
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
                                        <input type="radio" name="staffID" value="${doctorToSlots.x.ID}" required>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>  
                        <br>
                        <button type="submit" name="action" value="Confirm" id="blueButton">Confirm</button>
                    </form>
                    <br>
                </c:if>                                
                <details>
                    <summary>Delete Appointment</summary>
                    <form action="${pageContext.request.contextPath}/dashboard/appointments" method="post">

                        <c:set var="min" scope="request" value="${0}"/>
                        <c:if test="${requestScope.appointments.size()==min}">
                            <h3>No appointments booked</h3>
                        </c:if>
                        <c:if test="${requestScope.appointments.size() > min}">
                            <c:set var="count" value="0" scope="page"/>
                            <div id="table">
                                <table id="appointments">
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
                                            <td><c:out value="${current.date}"/></td>
                                            <td><c:out value="${current.startTime}"/></td>
                                            <td><c:out value="${current.endTime}"/></td>
                                            <td><c:out value="${current.type}"/></td>
                                            <td><c:out
                                                    value="${current.staffMember.firstName} ${current.staffMember.surname}"/></td>
                                            <td><label>
                                                    <input type="checkbox" name="selected" value="${current.ID}"/>
                                                </label></td>
                                        </tr>
                                    </c:forEach>
                                </table>
                                <br>
                                <button type="submit" name="action" value="Delete Selected" id="pinkButton">Delete Selected</button>
                            </div>
                        </c:if>                        
                    </form>
                    <br>
                </details>
                <details>
                    <summary>Update Appointment</summary>
                    <form action="${pageContext.request.contextPath}/dashboard/appointments" method="post">
                        <div>
                            <c:set var="min" scope="request" value="${0}"/>
                            <c:if test="${requestScope.appointments.size()==min}">
                                <h3>No appointments booked</h3>
                            </c:if>
                            <c:if test="${requestScope.appointments.size() > min}">
                                <c:set var="count" value="0" scope="page"/>
                                <c:set var="type" scope="request" value="private"/>
                                <table id="appointments">
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
                                            <td><c:out value="${count}"/></td>
                                            <td><c:out value="${current.date}"/></td>
                                            <td><c:out value="${current.startTime}"/></td>
                                            <td><c:out value="${current.endTime}"/></td>
                                            <td>
                                                <c:if test="${current.type.equals(type)}">
                                                    <label>
                                                        <select name="types">
                                                            <option>private</option>
                                                            <option>public (NHS)</option>
                                                        </select>
                                                    </label>
                                                </c:if>
                                                <c:if test="${current.type!=type}">
                                                    <label>
                                                        <select name="types">
                                                            <option>public (NHS)</option>
                                                            <option>private</option>
                                                        </select>
                                                    </label>
                                                </td>
                                            </c:if>
                                            <td><c:out
                                                    value="${current.staffMember.firstName} ${current.staffMember.surname}"/></td>
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
                            <br>
                            <button type="submit" name="action" value="Update Selected" id="pinkButton">Update Selected</button>
                        </div>                                               
                    </form>
                    <br>
                </details>   
                <details>
                    <summary>Prescriptions</summary>
                    <button type="submit" name="action" value="Home" id="pinkButton" form="patientInfo">
                        View Prescriptions
                    </button>
                    <form method="GET" action="${pageContext.request.contextPath}/dashboard/appointments"
                          id="patientInfo"></form> 
                </details>           
            </div>
        </div>
    </body>
</html>