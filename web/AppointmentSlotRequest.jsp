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
        <form action="AppointmentServlet" method="post" >
            <label> 
                Available Doctors:
                <select name="doctor">
                    <c:forEach items="${requestScope.doctorsList}" var="current">
                        <option>${current}</option>
                    </c:forEach>  
                </select>
            </label>
            
            <label> 
                Appointment Date:
                <input type="date" name="date" required="">
            </label>
            
            <label> 
                Appointment Length:
                <select name="length">
                    <option>15</option>
                    <option>30</option>
                    <option>60</option>
                </select>
            </label>
            
            <input type="submit" name="action" value="Availability" />
        </form>
    </center>
</body>
</html>
