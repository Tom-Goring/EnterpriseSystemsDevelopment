<%-- 
    Document   : results
    Created on : 29-Nov-2020, 10:21:02
    Author     : maxwell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <center>
        <c:set var="min" scope="request" value="${0}" />
        <c:if test="${requestScope.slots.size()==min}">
            <h3>0 slots available</h3>
        </c:if>
        <c:if test="${requestScope.slots.size() > min}">
            <form action="AppointmentDetails.do" method="post">            
                <label for="customerName">Name:</label>
                <input type="text" id="customerName" name="customerName" required="">

                <label for="selectedSlot">Available Slots: </label>
                <select name="selectedSlot">
                    <c:forEach items="${requestScope.slots}" var="current">
                        <option>${current}</option>
                    </c:forEach>
                </select>

                <label for="serviceType">Service Type: </label>
                <select name="serviceType">
                    <option>public</option>
                    <option>private</option>                    
                </select>

                <label for="appointmentDate">Date:</label>
                <input name="appointmentDate" value="${requestScope.date}" readonly/>

                <label for="selectedDoctor">Doctor:</label>
                <input name="selectedDoctor" value="${requestScope.doctor}" readonly/>

                <input type="submit" value="Confirm Apppointment" />
            </form>   
        </c:if>
    </center>

</html>
