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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/appointment.css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <title>Admin Page</title>
    </head>
    <body>    
        <header class="header">          
            <ul>
                <img src="${pageContext.request.contextPath}/images/temp_logo.png" alt="logo" id="header-logo">
                <li><a>${requestScope.username}</a></li>
                <li><button type="submit" name ="action" value="" form="logout">Logout</button></li>
            </ul>           
        </header>
        <div>
            <h1>Admin</h1>
            <p>Add new User</p>
        </div>
    <center>                 
        <details>
            <summary>Audit Logs</summary>
            <div id="table">
                <table id="appointments">                   
                    <thead>
                        <tr>
                            <th>Date</th>
                            <th>Event</th>
                        </tr>                        
                    </thead>
                    <c:forEach items="${requestScope.events}" var="event">
                        <tr>
                            <td>${event.createdAt}</td>
                            <td>${event.description}</td>
                        </tr>
                    </c:forEach>            
                </table>
            </div>
        </details>
        <details>
            <summary>Users Accounts</summary>
            <div id="table">
                <table id="appointments">  
                    <c:set var="count" value="0" scope="page" />
                    <thead>
                        <tr>
                            <th>Number</th>
                            <th>Name</th>
                            <th>Role</th>
                            <th>Active</th>
                        </tr>
                    </thead>
                    <c:forEach items="${requestScope.users}" var="userAccount">
                        <c:set var="count" value="${count + 1}" scope="page"/>
                        <tr>
                            <td>${count}</td>
                            <td>${userAccount.firstName} ${userAccount.surname}</td>
                            <td>${userAccount.role}</td>
                            <c:if test="${userAccount.active}">  
                                <td><i class="fa fa-check-circle" aria-hidden="true"></i></td>
                                </c:if>
                                <c:if test="${!userAccount.active}">
                                <td><i class="fa fa-times-circle-o" aria-hidden="true"></i></td>
                                </c:if>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </details>
        <c:set var="count" value="0" scope="page"/>
        <form method="post">
            <details>
                <summary>Pending Account Approvals</summary>
                    <c:if test="${requestScope.approvals.size() > 0}">              
                    <table id="appointments">
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
                    <button id="options">Submit Account Approvals</button>
                </c:if>
                <c:if test="${requestScope.approvals.size() == 0}">
                    <h3>No pending account approvals</h3>
                </c:if>
            </details>
        </form>      
        <details>
            <summary>Set Working Days</summary>
            <form method="post">

                <table id="appointments">                   
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
        </details>

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
    </center>
</body>
</html>