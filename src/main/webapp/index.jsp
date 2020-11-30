<%-- 
    Document   : index
    Created on : 28-Nov-2020, 14:27:19
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
        <form action="NewServlet.do" method="post" >
            <label for="category">Available Doctors:</label>
            <select name="doctor">
                <c:forEach items="${requestScope.doctorsList}" var="current">
                    <option>${current}</option>
                </c:forEach>  
            </select>
            <label for="appointmentDate">Appointment Date:</label>
            <input type="date" id="appointment" name="appointmentDate" required="">

            <label for="appointmentLenght">Appointment Length: </label>
            <select name="appointmentLenght">
                <option>15</option>
                <option>30</option>
                <option>60</option
            </select>
            
            <input type="submit" value="Get Availability" />
        </form>
    </center>
</body>
</html>
