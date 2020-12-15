<%-- 
    Document   : IssuePrescription
    Created on : 15-Dec-2020, 17:17:29
    Author     : Bredan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Issue Prescription Form</title>
    </head>
    <body>
        <h1>Issue Prescription</h1>
        <form method="post" action="${pageContext.request.contextPath}/IssuePrescription">
            <label>
                Patient ID: <input name="submitted-patientid">
            </label>
<!--            <br/>-->
            <label>
                Medicine:   <input name="submitted-medicine">
            </label>
<!--            <br/>-->
            <label>
                Quantity:   <input name="submitted-quantity">
            </label>
<!--            <br/>-->
            <label>
                Repeating:  <input name="submitted-repeating" placeholder="True or False format">
            </label>
<!--            <br/>-->
            <label>
                Issue date: <input name="submitted-issuedate"  type="date">
            </label>
<!--            <br/>-->
            <label>
                End date: <input name="submitted-enddate" type="date">
            </label>
            <br/><br/>
            <button>Submit</button>
        </form>
    </body>
</html>
