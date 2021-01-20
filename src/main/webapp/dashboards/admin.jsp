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
<div class="outer">
    <form method="post" action="${pageContext.request.contextPath}/login" id="logout"></form>
    <div class="header">
        <div class="innerDashboard">
            <img src="${pageContext.request.contextPath}/images/temp_logo.png" alt="logo" id="header-logo">
            <ul>
                 <li><a>${requestScope.user}</a></li>
                <li>
                    <button type="submit" name="action" value="" form="logout" id="pinkButtonAlternative">Logout</button>
                </li>
            </ul>
        </div>
    </div>
    <div class="innerDashboard">
        <h1>Admin</h1>
        <details>
            <summary>Add new User</summary>
            <div id="parent">
                <form method="post" action="${pageContext.request.contextPath}/users/create" autocomplete="off">
                    <label>
                        First Name<span class="highlight-span">*</span>
                        <input name="submitted-name" type="text" autocomplete="off" required>
                    </label>

                    <label>
                        Surname<span class="highlight-span">*</span>
                        <input name="submitted-surname" type="text" required>
                    </label>

                    <label>
                        Email<span class="highlight-span">*</span>
                        <input name="submitted-email" type="text" autocomplete="off" required>
                    </label>
                    <label>
                        Password<span class="highlight-span">*</span>
                        <input name="submitted-password" type="text" autocomplete="off" required>
                    </label>
                    <label>
                        Role<span class="highlight-span">*</span>
                        <select name="submitted-role" required="">
                            <option>doctor</option>
                            <option>patient</option>
                            <option>nurse</option>
                            <option>admin</option>
                        </select>
                    </label>
                    <label>
                        Address<span class="highlight-span">*</span>
                        <input type="text" name="street" placeholder="street">
                        <input type="text" name="city" placeholder="city">
                        <input type="text" name="postcode" placeholder="postcode">
                    </label>
                    <br/>
                    <label>
                        Gender<span class="highlight-span">*</span>
                        <select name="gender">
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                            <option value="Other">Other</option>
                        </select>
                    </label>
                    <label>
                        Date of Birth<span class="highlight-span">*</span>
                        <input type="date" max="${requestScope.minimumDate}" name="DOB">
                    </label>
                    <br/>
                    <label>
                        Public/Private<span class="highlight-span">*</span>
                        <select name="type">
                            <option>Public</option>
                            <option>Private</option>
                        </select>
                    </label>
                    <button type="submit" id="blueButtonAlternative">
                        Submit
                    </button>
                </form>
            </div>
        </details>
        <details>
            <summary>Edit User</summary>
            <form method="post" action="${pageContext.request.contextPath}/users/update" id="updateUser">
                <c:set var="count" value="0" scope="page"/>
                <div id="table">
                    <table id="appointments">
                        <thead>
                        <tr>
                            <th>Email</th>
                            <th>First Name</th>
                            <th>Surname</th>
                            <th>Role</th>
                            <th>Selection</th>
                        </tr>
                        </thead>
                        <c:forEach items="${requestScope.users}" var="userAccount">
                            <c:set var="count" value="${count + 1}" scope="page"/>
                            <tr>
                                <input type="hidden" value="${userAccount.ID}" name="userIDS">
                                <td><input type="text" name="email" value="${userAccount.email}"></td>
                                <td><input type="text" name="firstname" value="${userAccount.firstName}"></td>
                                <td><input type="text" name="surname" value="${userAccount.surname}"></td>
                                <td>
                                    <c:if test="${userAccount.role.equals('doctor')}">
                                        <select name="role">
                                            <option>doctor</option>
                                            <option>nurse</option>
                                            <option>admin</option>
                                            <option>patient</option>
                                        </select>
                                    </c:if>
                                    <c:if test="${userAccount.role.equals('nurse')}">
                                        <select name="role">
                                            <option>nurse</option>
                                            <option>doctor</option>
                                            <option>admin</option>
                                            <option>patient</option>
                                        </select>
                                    </c:if>
                                    <c:if test="${userAccount.role.equals('admin')}">
                                        <select name="role">
                                            <option>admin</option>
                                            <option>doctor</option>
                                            <option>nurse</option>
                                            <option>patient</option>
                                        </select>
                                    </c:if>
                                    <c:if test="${userAccount.role.equals('patient')}">
                                        <select name="role">
                                            <option>patient</option>
                                            <option>doctor</option>
                                            <option>nurse</option>
                                            <option>admin</option>
                                        </select>
                                    </c:if>                                   
                                </td>
                                <td><input type="checkbox" name="selected" value="${count}"></td>
                            </tr>
                        </c:forEach>
                    </table>

                    <br>
                    <button id="blueButtonAlternative" type="submit" name="action" form="updateUser" value="update-selected">Edit
                        Selected
                    </button>
                </div>
            </form>

        </details>
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
            <summary>Users</summary>
            <div id="table">
                <table id="appointments">
                    <c:set var="count" value="0" scope="page"/>
                    <form id="userview" method="get"
                          action="${pageContext.request.contextPath}/dashboard/patientinformation"></form>
                    <thead>
                    <tr>
                        <th>Number</th>
                        <th>Name</th>
                        <th>Role</th>
                        <th>Active</th>
                        <th>View Profile</th>
                    </tr>
                    </thead>
                    <c:forEach items="${requestScope.users}" var="userAccount">
                        <c:set var="count" value="${count + 1}" scope="page"/>
                        <tr>
                            <td>${count}</td>
                            <td>${userAccount.firstName} ${userAccount.surname}</td>
                            <td>${userAccount.role}</td>
                            <c:if test="${userAccount.active}">
                                <td><input type="checkbox" checked></td>
                            </c:if>
                            <c:if test="${!userAccount.active}">
                                <td><input type="checkbox"></td>
                            </c:if>
                            <td>
                                <button name="userID" id="view" value="${userAccount.ID}" type="submit" form="userview">
                                    View
                                </button
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </details>
        <details>
            <summary>Pending Approvals</summary>
            <form method="post">
                <c:if test="${requestScope.approvals.size() == 0}">
                    <h3>No pending account approvals</h3>
                </c:if>
                <c:if test="${requestScope.approvals.size() > 0}">
                    <table id="appointments">
                        <c:set var="count" value="0" scope="page"/>
                        <thead>
                        <tr>
                            <th>Number</th>
                            <th>Email</th>
                            <th>First Name</th>
                            <th>Surname</th>
                            <th>Requested Role</th>
                            <th>Approve</th>
                            <th>Deny</th>
                        </tr>
                        </thead>
                        <c:forEach items="${requestScope.approvals}" var="approval">
                            <c:set var="count" value="${count + 1}" scope="page"/>
                            <tr>
                                <td>${count}</td>
                                <td>${approval.account.email}</td>
                                <td>${approval.account.firstName}</td>
                                <td>${approval.account.surname}</td>
                                <td>${approval.account.role}</td>
                                <td><label><input type="radio" value="${approval.ID}-approved" name="approval-${count}"></label>
                                </td>
                                <td><label><input type="radio" value="${approval.ID}-denied"
                                                  name="approval-${count}"></label></td>
                            </tr>
                        </c:forEach>
                    </table>
                    <br>
                    <button type="submit" id="blueButtonAlternative" value="submit-approvals" name="action">Submit Account
                        Approvals
                    </button>
                </c:if>
            </form>
        </details>
        <details>
            <summary>Edit Working Days</summary>
            <form method="post" id="workingdays">
                <div id="table">
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
                    <button type="submit" name="action" value="Confirm Working Days" form="workingdays" id="blueButtonAlternative">
                        Confirm Working Days
                    </button>                   
                </div>
            </form>
        </details>
        <details>
            <summary>Delete User</summary>
            <form action="${pageContext.request.contextPath}/users/delete" id="delete" method="post">
                <div id="table">
                    <table id="appointments">
                        <thead>
                        <tr>
                            <th>Email</th>
                            <th>Name</th>
                            <th>Role</th>
                            <th>Selection</th>
                        </tr>
                        </thead>
                        <c:forEach items="${requestScope.users}" var="userAccount">
                            <tr>
                                <td>${userAccount.email}</td>
                                <td>${userAccount.firstName} ${userAccount.surname}</td>
                                <td>${userAccount.role}</td>
                                <td><input type="checkbox" name="selected" value="${userAccount.ID}"></td>
                            </tr>
                        </c:forEach>
                    </table>
                    <br>
                    <button id="pinkButtonAlternative" type="submit" name="action" value="delete-selected" form="delete">Delete
                        Selected
                    </button>
                </div>
            </form>
        </details>
        <details ${requestScope.changed_display_type ? 'open' : ''}>
            <summary>View Patients</summary>
            <form>
                <label>
                    NHS Patients:
                    <input name="displayType" type="radio" value="public" ${sessionScope.displayType == 'public' ? 'checked' : ''}>
                </label>
                <label>
                    Private Patients:
                    <input name="displayType" type="radio" value="private" ${sessionScope.displayType == 'private' ? 'checked' : ''}>
                </label>
                <input type="hidden" name="changed-display-type" value="true">
                <button type="submit" id="blueButtonAlternative">Change displayed patient types</button>
            </form>
            <c:if test="${requestScope.users.size() > 0}">
                <div id="table">
                    <table id="appointments">
                        <tr>
                            <th>Patient Name</th>
                            <th>Email</th>
                            <th>DOB</th>
                            <th>City</th>
                            <th>Street</th>
                            <th>Postcode</th>
                            <th>Gender</th>
                        </tr>
                        <c:forEach items="${requestScope.users}" var="user">
                            <c:if test="${sessionScope.displayType == 'private'}">
                                <c:if test="${user.type.private}">
                                    <tr>
                                        <td>${user.fullName}</td>
                                        <td>${user.email}</td>
                                        <td>${user.DOB}</td>
                                        <td>${user.address.street}</td>
                                        <td>${user.address.postcode}</td>
                                        <td>${user.gender}</td>
                                    </tr>
                                </c:if>
                            </c:if>
                            <c:if test="${sessionScope.displayType == 'public'}">
                                <c:if test="${user.type.public}">
                                    <tr>
                                        <td>${user.fullName}</td>
                                        <td>${user.email}</td>
                                        <td>${user.DOB}</td>
                                        <td>${user.address.city}</td>
                                        <td>${user.address.street}</td>
                                        <td>${user.address.postcode}</td>
                                        <td>${user.gender}</td>
                                    </tr>
                                </c:if>
                            </c:if>
                        </c:forEach>
                    </table>
                </div>
            </c:if>
        </details>
        <details ${sessionScope.totalTurnover != null ? 'open' : ''}>
            <summary>Turnover</summary>
                <form action="${pageContext.request.contextPath}/turnover" method="post">
                    <label>NHS Only
                        <input type="checkbox" name="nhsOnly">
                    </label>
                    <label>
                        Date to generate turnover since
                        <input type="date" max="${requestScope.minimumDate}" name="date">
                    </label>
                    <br/>
                    <br/>
                    <button id="pinkButtonAlternative">Generate Turnover stats</button>
                </form>
            <c:if test="${sessionScope.totalTurnover != null}">
                <p>Total turnover for period since ${sessionScope.startPeriod}: £${sessionScope.totalTurnover}</p>
            </c:if>
        </details>
        <details>
            <summary>Change appointment lengths and charges</summary>
            <p>Current Slot Length: ${requestScope.slotPrices.slotSize} minutes</p>
            <p>Current nurse cost: £${requestScope.slotPrices.nurseCost}</p>
            <p>Current doctor rate: £${requestScope.slotPrices.doctorCost}</p>
            <p>Current max slot length: ${requestScope.slotPrices.maxSlotSize} minutes</p>
            <form action="${pageContext.request.contextPath}/slotprice" method="post">
                <label>New slot length:
                    <input type="number" name="newSlotInterval" required>
                </label>
                <label>New max appointment length:
                    <input type="number" name="newMaxSlotLength" required>
                </label>
                <label>
                    New Nurse Rate
                    <input type="number" name="newNurseCost" required>
                </label>
                <label>
                    New Doctor Rate
                    <input type="number" name="newDoctorCost" required>
                </label>
                <button id="pinkButtonAlternative">Update appointment config</button>
            </form>
        </details>
        <details>
            <summary>Recreate Tables</summary>
            <p>Warning, this action will reset all databases</p>
            <form method="post" action="${pageContext.request.contextPath}/slotprice">
                <input type="hidden" name="action" value="recreate-tables">
                <button id="pinkButtonAlternative">Recreate Tables</button>
            </form>
        </details>
    </div>
</div>
</body>
</html>