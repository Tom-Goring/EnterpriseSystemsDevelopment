<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: tomgo
  Date: 11/12/2020
  Time: 23:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Page</title>
</head>
<body>
    <h1>SUPER SECRET ADMIN STUFF</h1>
    <h2>Audit Log</h2>
    <ol style="list-style-type: none">
        <c:forEach items="${requestScope.events}" var="event">
            <li>${event.createdAt} INFO ${event.description}</li>
        </c:forEach>
    </ol>

    <h2>Users</h2>
    <ol style="list-style-type: none">
        <c:forEach items="${requestScope.users}" var="user">
            <li>${user.firstName} ${user.surname}</li>
        </c:forEach>
    </ol>
    <form method="post">
        <input type="hidden" id="recreate-tables" value="">
        <button>Recreate Tables</button>
    </form>
    <form method="post" action="${pageContext.request.contextPath}/login">
        <button>Log Out</button>
    </form>
</body>
</html>