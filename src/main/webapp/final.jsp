<%-- 
    Document   : final
    Created on : 30-Nov-2020, 19:43:45
    Author     : maxwell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

    </head>
    <body>
    <center style="display:block; font-size: 20px">
        <h3>Appointment Added</h3>
        <label for="appointmentDate">Date:</label>
        <input name="appointmentDate" value="${requestScope.event.date}" readonly/>

        <label for="selectedDoctor">Doctor Name:</label>
        <input name="selectedDoctor" value="${requestScope.event.doctorName}" readonly/>

        <label for="customerName">Customer Name:</label>
        <input name="customerName" value="${requestScope.event.customerName}" readonly/>

        <label for="startTime">Start Time:</label>
        <input name="startTime" value="${requestScope.event.startTime}" readonly/>

        <label for="endTime">End Time:</label>
        <input name="endTime" value="${requestScope.event.endTime}" readonly/>

        <label for="endTime">Type:</label>
        <input name="endTime" value="${requestScope.event.type}" readonly/>

        <form action="NewServlet.do" method="get">
            <input type="submit" value="Home" style="font-size: 20px; margin-top:10px"/>      
        </form>
    </center>
</body>
</html>
