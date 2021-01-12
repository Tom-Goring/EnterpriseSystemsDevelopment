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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/appointment.css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>
    <body>
    <center>
        <h1>Appointments Page</h1>
    </center>
    <c:choose>
        <c:when test="${requestScope.task == null}">         
            <center>
                <h3>Your Appointments</h3>
                <c:set var="min" scope="request" value="${0}" />
                <c:if test="${requestScope.appointments.size()==min}">
                    <h3>No appointments booked</h3>
                </c:if>
                <c:if test="${requestScope.appointments.size() > min}">
                    <table id="appointments">
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

            <center>
                <form method="GET" action="${pageContext.request.contextPath}/dashboard/appointments">
                    <input type="submit" name ="action" value="Add" id="options"/>
                    <input type="submit" name ="action" value="Update" id="options" />                    
                    <!--                                    <input type="submit" name ="action" value="Delete" />-->
                    <!--                                    <input type="submit" name ="action" value="View" />-->
                </form>
            </center>
        </c:when>

        <c:when test="${requestScope.task == 'add'}">
            <center>               
                <center>               
                    <c:set var="min" scope="request" value="${0}" />
                    <c:if test="${requestScope.appointments.size()==min}">
                        <h3>No appointments booked</h3>
                    </c:if>
                    <c:if test="${requestScope.appointments.size() > min}">
                        <table id="appointments">
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
                <div id="slots">
                    <form action="${pageContext.request.contextPath}/dashboard/appointments" method="post" >


                        <label><i class="fa fa-calendar" aria-hidden="true"></i>
                            <input type="date" name="date" required="" id="dateselection">
                        </label>
                        <label>  
                            <i class="fa fa-clock-o" aria-hidden="true"></i>  
                            <select name="length" id="select-css">                
                                <option>15</option>
                                <option>30</option>
                                <option>60</option>
                            </select> 
                        </label>       
                        <input type="submit" name="action" value="Availability" />

                    </form>
                </div>
            </center>
        </c:when>

        <c:when test="${requestScope.task == 'availableSlots'}">
            <center>                
                <c:set var="min" scope="request" value="${0}" />
                <c:if test="${requestScope.slots.size()==min}">
                    <h3>0 slots available</h3>
                </c:if>
                <c:if test="${requestScope.slots.size() > min}">
                    <center>
                        <h3>Your Appointments</h3>
                        <c:set var="min" scope="request" value="${0}" />
                        <c:if test="${requestScope.appointments.size()==min}">
                            <h3>No appointments booked</h3>
                        </c:if>
                        <c:if test="${requestScope.appointments.size() > min}">
                            <table id="appointments">
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


                    <form action="${pageContext.request.contextPath}/dashboard/appointments" method="post">
                        <table id="appointments">
                            <tr>
                                <th>Doctor</th>
                                <th>Selected Date</th>
                                <th>Slots</th>
                                <th>Service Type</th>                               
                                <th>Add</th>
                            </tr>
                            <c:forEach items="${requestScope.doctorSlots}" var="doctorToSlots">
                                <tr>
                                    <td><c:out value="${doctorToSlots.x.firstName} ${doctorToSlots.x.surname}"/></td>
                                    <td>
                                        <input name="date" value="${requestScope.date}" readonly>
                                    </td>
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
                                <input type="hidden" name="staffID" value="${doctorToSlots.x.ID}">                                                                  
                                <td><button style="font-size:24px" type="submit" name="action" value="Confirm"><i class="fa fa-plus-circle" aria-hidden="true"></i></button></td>                                   
                                </tr>
                            </c:forEach>
                        </table>                                                       
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
                                    <th>Delete</th>
                                    <th>Update</th>

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
                                        <td><button style="font-size:24px" type="submit" name="action" value="Delete Selected"><i class="fa fa-trash-o"></i></button></td>
                                        <td><button style="font-size:24px"><i class="fa fa-refresh"></i></button></td>
                                        <td><input type="submit" name="action" value="Delete Selected"/></td>
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
                    <div id="update">
                        <c:set var="min" scope="request" value="${0}" />
                        <c:if test="${requestScope.appointments.size()==min}">
                            <h3>No appointments booked</h3>
                        </c:if>
                        <c:if test="${requestScope.appointments.size() > min}">
                            <c:set var="count" value="0" scope="page" />
                            <table id="appointments">
                                <caption>List of Appointments</caption>
                                <tr>
                                    <th>Number</th>
                                    <th>Date</th>
                                    <th>Start</th>
                                    <th>End</th>
                                    <th>Type</th>
                                    <th>Doctor</th>
                                    <th>Selected</th>
                                    <th>Delete</th>
                                    <th>Update</th>
                                </tr>
                                <c:forEach items="${requestScope.appointments}" var="current">
                                    <c:set var="count" value="${count + 1}" scope="page"/>
                                    <tr>
                                        <td><c:out value= "${count}"/></td>
                                        <td><c:out value= "${current.date}"/></td>
                                        <td><c:out value= "${current.startTime}"/></td>
                                        <td><c:out value= "${current.endTime}"/></td>
                                        <td>
                                            <c:set var="type" scope="request" value="private" />
                                            <c:if test="${current.type!=type}">
                                                <label>
                                                    <select name="types">
                                                        <option>public (NHS)</option>
                                                        <option>private</option>
                                                    </select>
                                                </label>
                                            </c:if>
                                            <c:if test="${current.type==type}">
                                                <label>
                                                    <select name="types">
                                                        <option>private</option>
                                                        <option>public (NHS)</option>
                                                    </select>
                                                </label>
                                            </td>
                                        </c:if>
                                        <td><c:out value= "${current.staffMember.firstName} ${current.staffMember.surname}"/></td>

                                        <td><input type="checkbox" name="selected" value="${current.ID}"/></td>

                                        <td><button style="font-size:24px" type="submit" name="action" value="Delete Selected"><i class="fa fa-trash-o"></i></button></td>
                                        <td><button style="font-size:24px" type="submit" name="action" value="Update Selected"><i class="fa fa-refresh"></i></button></td>
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
                    <table id="appointments">
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
                    <!--                <input type="submit" name ="action" value="Delete" />
                                    <input type="submit" name ="action" value="View" />-->
                </form>
            </center>
        </c:when>
        <c:when test="${requestScope.task == 'displayTaskSuccess'}">
            <center>
                <h3>Your Appointments</h3>
                <c:set var="min" scope="request" value="${0}" />
                <c:if test="${requestScope.appointments.size()==min}">
                    <h3>No appointments booked</h3>
                </c:if>
                <c:if test="${requestScope.appointments.size() > min}">
                    <table id="appointments">
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
            <center>
                <h3> ${requestScope.successMessage} </h3>
                <form method="GET" action="${pageContext.request.contextPath}/dashboard/appointments">
                    <input type="submit" name ="action" value="Add" />
                    <input type="submit" name ="action" value="Update" />
                    <!--                                    <input type="submit" name ="action" value="Delete" />-->
                    <input type="submit" name ="action" value="View" />
                </form>
            </center>
        </c:when>
    </c:choose>

</body>
</html>