<%-- 
    Document   : IssuePrescription
    Created on : 15-Dec-2020, 17:17:29
    Author     : Bredan
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=PT+Sans&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css" />
        <title>Issue Invoice Form</title>
    </head>
    <body>
        <div class="outer">
            <form method="post" action="${pageContext.request.contextPath}/login" id="logout"></form>
            <div class="header">
                <div class="innerDashboard">
                    <img src="${pageContext.request.contextPath}/images/temp_logo.png" alt="logo" id="header-logo">
                    <ul>
        <!--                <li><a>${requestScope.hello}</a></li>-->
                        <li>
                            <button type="submit" name="action" value="" form="logout" id="pinkButtonAlternative">Logout</button>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="innerDashboard">
                <details open>
                    <summary>Issue Invoice</summary>
                        <form method="post" action="${pageContext.request.contextPath}/IssueInvoice">
                            <p>Creating an Invoice for: ${appointment.patient.surname}, ${appointment.patient.firstName}</p>                           
                            <p value="service">Length: ${appointment.length.toMinutes()} minute appointment</p>
                            <p value="charge">Charges: £${charge}.00</p>
                            <label>
                                Issue date: <input name="submitted-issuedate"  type="date" value="${appointment.date}" required>
                            </label>
                            <label>
                                Due date: <input name="submitted-duedate"  type="date" required>
                            </label>
                            <input type="hidden" value ="${appointment.patient.ID}" name="patientID">
                            <input type="hidden" value ="${appointment.length.toMinutes()} minute appointment" name="service">
                            <input type="hidden" value ="${appointment.type}" name="type">
                            <input type="hidden" value ="${charge}" name="charge">
                            <br/><br/>
                            <button id="blueButtonAlternative">Submit</button>
                        </form>
                </details>
            </div>
        </div>
    </body>
</html>
