<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>UserID:${id} orders</h2>

<table border="1">
    <tr>
        <th>OrderID</th>
        <th>Details</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.id * 3}"/>
            </td>
            <td>
                <button onclick="document.location='/orders/details?id=${order.id}'">Details</button>
            </td>
            <td>
                <button onclick="document.location='delete?id=${order.id}'">Delete</button>
            </td>
        </tr>
    </c:forEach>
</table>
<button onclick="document.location='/index'">Main page</button>
<button onclick="document.location='/orders/all'">All orders</button>
</body>
</html>
