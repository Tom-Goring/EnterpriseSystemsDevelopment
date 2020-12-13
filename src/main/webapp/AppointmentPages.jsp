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
        <title>JSP Page</title>
    </head>
    <body>
    <center>
        <h1>Appointments Page</h1>
    </center>
    <c:choose>
        <c:when test="${requestScope.task == null}">
            <center>               
                <form method="GET" action="AppointmentServlet">    
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
                <form action="AppointmentServlet" method="post" >
                    <label> 
                        Available Doctors:
                        <select name="doctor">
                            <c:forEach items="${requestScope.doctorsList}" var="current">
                                <option>${current}</option>
                            </c:forEach>  
                        </select>
                    </label>
                    <br>
                    <br>
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
                    <form action="AppointmentServlet" method="post">            
                        <label> 
                            Name:
                            <input type="text" name="customerName" required="">
                        </label>
                        <br>
                        <br>
                        <label> 
                            Email:
                            <input type="email" name="email" required="" autocomplete="off">
                        </label>
                        <br>
                        <br>
                        <label>
                            Available Slots: 
                            <select name="selectedSlot">
                                <c:forEach items="${requestScope.slots}" var="current">
                                    <option>${current}</option>
                                </c:forEach>
                            </select>
                        </label>
                        <br>
                        <br>
                        <label>
                            Service Type: 
                            <select name="serviceType">
                                <option>public</option>
                                <option>private</option>                    
                            </select>
                        </label>
                        <br>
                        <br> 
                        <label>
                            Date:
                            <input name="date" value="${requestScope.date}" readonly> 
                        </label>
                        <br>
                        <br>
                        <label>
                            Doctor:
                            <input name="doctor" value="${requestScope.doctor}" readonly> 
                        </label>
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
                <form action="AppointmentServlet" method="post">
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
                                    <th>Select</th>  

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
                                        <td><input type="checkbox" name="selected" value="${current.ID}"</td>
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
                <form action="AppointmentServlet" method="post">
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
                                    <th>Name</th> 
                                    <th>Email</th> 
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
                                        <td><input type="text" value= "${current.customerName}" name="customerName"></td>
                                        <td><input type="text" value= "${current.email}" name="email"></td>
                                        <td><c:out value= "${current.date}"/></td>
                                        <td><c:out value= "${current.startTime}"/></td>
                                        <td><c:out value= "${current.endTime}"/></td>
                                        <td>
                                            <c:set var="min" scope="request" value="${'private'}" />
                                            <c:if test="${current.type.equals(min)}">
                                                <select name="type">
                                                    <option>private</option>
                                                    <option>public</option>                    
                                                </select>
                                            </c:if>
                                            <c:if test="${current.type!=min}">
                                                <select name="type">
                                                    <option>public</option>
                                                    <option>private</option>                    
                                                </select>
                                            </td>
                                        </c:if>
                                        <td><c:out value= "${current.doctorName}"/></td>
                                        <td><input type="checkbox" name="selected" value="${current.ID}-${count-1}"</td>
                                    </tr>
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
                <h3>View Appointment</h3>
                <form id ="form1" action="AppointmentServlet" method="post" >
                    <label> 
                        Available Doctors:
                        <select name="doctor">
                            <c:forEach items="${requestScope.doctors}" var="current">
                                <option>${current}</option>
                            </c:forEach>  
                        </select>
                    </label>

                    <label> 
                        Appointment Date:
                        <input type="date" name="date" required="">
                    </label>            
                </form>

                <form id="form2" action="AppointmentServlet" method="post">            
                </form>
                <br>
                <input type="submit" name="action" value="View Selected" form="form1" />         
                <input type="submit" name="action" value="View All" form="form2" />

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
                <form method="GET" action="AppointmentServlet">    
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
                <form method="GET" action="AppointmentServlet">    
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
