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
        <title>Issue Prescription Form</title>
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
                <details>
                    <summary>Issue Prescription</summary>                
                    <form method="post" action="${pageContext.request.contextPath}/IssuePrescription" id="prescription">
                        <label>
                            Patient<span class="highlight-span">*</span>
                            <select name="submitted-patientid" required="">
                                <c:forEach items="${requestScope.users}" var="user">
                                    <option id="userID" value="${user.ID}">${user.firstName} ${user.surname}</option>
                                </c:forEach>
                            </select>
                        </label>         
                        <label>
                            Medicine<span class="highlight-span">*</span>
                            <input name="submitted-medicine" type="text" required="">
                        </label>
                        <label>
                            Quantity<span class="highlight-span">*</span>   
                            <input name="submitted-quantity" type="number" required="">
                        </label>
                        <label>
                            Repeating<span class="highlight-span">*</span>  
                            <select name="submitted-repeating" required="">
                                <option>True</option>
                                <option>False</option>
                            </select>
                        </label>           
                        <label>
                            Issue date<span class="highlight-span">*</span> 
                            <input name="submitted-issuedate"  type="date" required="" min="${requestScope.minimumDate}" max="2099-01-18">
                        </label>
                        <label>
                            End date<span class="highlight-span">*</span> 
                            <input name="submitted-enddate" type="date" required="" min="${requestScope.minimumDate}" max="2099-01-18">
                        </label>
                    </form>
                    <br>
                    <button id="blueButtonAlternative" form="prescription">Submit</button>
                </details>
                <details>
                    <summary>Prescription Reordering Approvals</summary>                     
                </details>

                <details>
                    <summary>Upcoming Appointments</summary>
                </details>
            </div>
        </div>
    </body>
</html>
