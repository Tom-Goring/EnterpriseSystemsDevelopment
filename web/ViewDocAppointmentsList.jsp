<%-- 
    Document   : ViewDocAppointmentsList
    Created on : 11-Dec-2020, 23:26:45
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
</body>
</html>
