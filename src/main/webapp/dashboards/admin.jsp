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
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=PT+Sans&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css"/>
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

    <h3>User Account List</h3>
    <ol style="list-style-type: none">
        <c:forEach items="${requestScope.users}" var="userAccount">
            <c:if test="${userAccount.active}">
                <li>${userAccount.firstName} ${userAccount.surname}</li>
            </c:if>
            <c:if test="${!userAccount.active}">
                <li>${userAccount.firstName} ${userAccount.surname} [INACTIVE]</li>
            </c:if>
        </c:forEach>
    </ol>

    <c:set var="count" value="0" scope="page"/>
    <form method="post">
        <c:if test="${requestScope.approvals.size() > 0}">
            <h3>Pending Account Approvals</h3>
            <table border="1" cellpadding="5">
                <tr>
                    <th>Number</th>
                    <th>Email</th>
                    <th>First Name</th>
                    <th>Surname</th>
                    <th>Requested Role</th>
                    <th>Approve</th>
                    <th>Deny</th>
                </tr>
                <c:forEach items="${requestScope.approvals}" var="approval">
                    <c:set var="count" value="${count + 1}" scope="page"/>
                    <tr>
                        <th>${count}</th>
                        <th>${approval.account.email}</th>
                        <th>${approval.account.firstName}</th>
                        <th>${approval.account.surname}</th>
                        <th>${approval.account.role}</th>
                        <th><label><input type="radio" value="${approval.ID}-approved" name="approval-${count}"></label>
                        </th>
                        <th><label><input type="radio" value="${approval.ID}-denied" name="approval-${count}"></label></th>
                    </tr>
                </c:forEach>
            </table>
            <input type="hidden" id="action" name="action" value="submit-approvals">
            <br/>
            <button>Submit Account Approvals</button>
        </c:if>
        <c:if test="${requestScope.approvals.size() == 0}">
            <h3>No pending account approvals</h3>
        </c:if>
    </form>

    <form method="post">
        <table border="1" cellpadding="5">
            <caption>Set Working days</caption>
            <tr>
                <th>Staff Name</th>
                <th>Monday</th>
                <th>Tuesday</th>
                <th>Wednesday</th>
                <th>Thursday</th>
                <th>Friday</th>
            </tr>
            <c:forEach items="${requestScope.staff}" var="current">
                <tr>
                    <td><c:out value="${current.firstName} ${current.surname}"/></td>
                    <td><label>
                        <c:if test="${current.schedule.getDayBoolean(0)}">
                            <input type="checkbox" name="checkrows" value="${current.ID}-0" checked/>
                        </c:if>
                        <c:if test="${!current.schedule.getDayBoolean(0)}">
                            <input type="checkbox" name="checkrows" value="${current.ID}-0"/>
                        </c:if>
                    </label></td>
                    <td><label>
                        <c:if test="${current.schedule.getScheduleBoolean(1)}">
                            <input type="checkbox" name="checkrows" value="${current.ID}-1" checked/>
                        </c:if>
                        <c:if test="${!current.schedule.getScheduleBoolean(1)}">
                            <input type="checkbox" name="checkrows" value="${current.ID}-1"/>
                        </c:if>
                    </label></td>
                    <td><label>
                        <c:if test="${current.schedule.getDayBoolean(2)}">
                            <input type="checkbox" name="checkrows" value="${current.ID}-2" checked/>
                        </c:if>
                        <c:if test="${!current.schedule.getDayBoolean(2)}">
                            <input type="checkbox" name="checkrows" value="${current.ID}-2"/>
                        </c:if>
                    </label></td>
                    <td><label>
                        <c:if test="${current.schedule.getDayBoolean(3)}">
                            <input type="checkbox" name="checkrows" value="${current.ID}-3" checked/>
                        </c:if>
                        <c:if test="${!current.schedule.getDayBoolean(3)}">
                            <input type="checkbox" name="checkrows" value="${current.ID}-3"/>
                        </c:if>
                    </label></td>
                    <td><label>
                        <c:if test="${current.schedule.getDayBoolean(4)}">
                            <input type="checkbox" name="checkrows" value="${current.ID}-4" checked/>
                        </c:if>
                        <c:if test="${!current.schedule.getDayBoolean(4)}">
                            <input type="checkbox" name="checkrows" value="${current.ID}-4"/>
                        </c:if>
                    </label></td>

                </tr>
            </c:forEach>
        </table>
        <br>
        <input type="submit" name="action" value="Confirm Working Days"/>
    </form>

    <form method="post">
        <input type="hidden" name="action" value="recreate-tables">
        <button>Recreate Tables</button>
    </form>

    <form method="get" action="${pageContext.request.contextPath}/dashboard/patientinformation">
        <label>
            <select name="userID">
                <c:forEach items="${requestScope.users}" var="user">
                    <option id="userID" value="${user.ID}">${user.firstName} ${user.surname}</option>
                </c:forEach>
            </select>
        </label>

        <button>View User</button>
    </form>

    <form method="post" action="${pageContext.request.contextPath}/login">
        <button>Log Out</button>
    </form>
</body>
</html>