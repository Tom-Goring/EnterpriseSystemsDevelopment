<%-- 
    Document   : ViewDoctorAppointments
    Created on : 11-Dec-2020, 21:53:05
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
</body>
</html>
