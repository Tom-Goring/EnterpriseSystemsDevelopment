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
            <form action="AppointmentServlet" method="post">            
                <label> 
                    Name:
                    <input type="text" name="customerName" required="">
                </label>
                
                <label> 
                    Email:
                    <input type="email" name="email" required="" autocomplete="off">
                </label>

                <label>
                    Available Slots: 
                    <select name="selectedSlot">
                        <c:forEach items="${requestScope.slots}" var="current">
                            <option>${current}</option>
                        </c:forEach>
                    </select>
                </label>

                <label>
                    Service Type: 
                    <select name="serviceType">
                        <option>public</option>
                        <option>private</option>                    
                    </select>
                </label>

                <label>
                    Date:
                    <input name="date" value="${requestScope.date}" readonly/>
                </label>

                <label>
                    Doctor:
                    <input name="doctor" value="${requestScope.doctor}" readonly/>
                </label>

                <input type="submit" name="action" value="Confirm" />
            </form>   
        </c:if>
    </center>

</html>
