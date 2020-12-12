<%-- 
    Document   : AppointmentTaskConfirmation
    Created on : 12-Dec-2020, 09:46:47
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
    <center>
        <h3> ${requestScope.successMessage} </h3>
       <form method="GET" action="AppointmentServlet">    
            <input type="submit" name ="action" value="Add" />
            <input type="submit" name ="action" value="Update" />
            <input type="submit" name ="action" value="Delete" />
            <input type="submit" name ="action" value="View" />
        </form>
    </center>
    </body>
</html>
