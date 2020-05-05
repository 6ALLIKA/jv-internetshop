<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All orders</title>
</head>
<body>
<h2>All orders page</h2>
<h4>Search orders by user id</h4>
<form method="post" action="${pageContext.request.contextPath}byUser">
    <table border="1">
        <tr>
            <td>UserID:</td>
            <td><input type="number" name="id"></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <button type="submit">Search</button>
            </td>
        </tr>
    </table>
</form>

<table border="1">
    <tr>
        <th>OrderID</th>
        <th>UserName</th>
        <th>UserID</th>
        <th>Details</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.id}"/>
            </td>
            <td>
                <c:out value="${order.user.name}"/>
            </td>
            <td>
                <c:out value="${order.user.id}"/>
            </td>
            <td>
                <button onclick="document.location='details?id=${order.id}'">Look inside</button>
            </td>
            <td>
                <button onclick="document.location='delete?id=${order.id}'">Delete</button>
            </td>
        </tr>
    </c:forEach>
</table>
<button onclick="document.location='/index'">Main page</button>
</body>
</html>
