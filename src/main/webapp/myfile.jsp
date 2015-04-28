<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<sql:query var="rs" dataSource="jdbc/testdb">
    select Login, Password, Role, FullName, Email, MobilePhone from user
</sql:query>

<html>
<head>
    <title></title>
</head>
<body>
<h1>Hello ${name}</h1>

<table>
    <tr>
        <td>Login</td>
        <td>Password</td>
        <td>Role</td>
        <td>FullName</td>
        <td>Email</td>
        <td>MobilePhone</td>
    </tr>
    <c:forEach var="row" items="${rs.rows}">
        <tr>
            <td>${row.Login}</td>
            <td>${row.Password}</td>
            <td>${row.Role}</td>
            <td>${row.FullName}</td>
            <td>${row.Email}</td>
            <td>${row.MobilePhone}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
