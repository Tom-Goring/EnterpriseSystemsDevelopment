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
        <h1>Issue Invoice</h1>
        <form method="post" action="${pageContext.request.contextPath}/IssueInvoice">
            <label>
                Patient: 
                <select name="submitted-patientid" required>
                    <c:forEach items="${requestScope.users}" var="user">
                        <option id="userID" value="${user.ID}">${user.surname}, ${user.firstName}</option>
                    </c:forEach>
                </select>
            </label>
<!--            <br/>-->
            <label>
                Service:   <input name="submitted-service" required>
            </label>
<!--            <br/>-->
            <label>
                Amount:   <input name="submitted-amount" type="number" step=".01" required>
            </label>
<!--            <br/>-->
            <label>
                Issue date: <input name="submitted-issuedate"  type="date" required>
            </label>
            <br/><br/>
            <button>Submit</button>
        </form>
    </body>
</html>
