<%-- 
    Document   : UpdateSelectedAppointment
    Created on : 12-Dec-2020, 12:54:59
    Author     : maxwell
--%>

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    <center>
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
</body>
</html>