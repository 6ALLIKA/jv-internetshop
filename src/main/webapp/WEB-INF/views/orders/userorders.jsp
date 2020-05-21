<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All orders</title>
</head>
<body>
<h2>History of my orders</h2>

<table border="1">
    <tr>
        <th>OrderID</th>
        <th>Details</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.id}"/>
            </td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}details">
                <button type="submit" name="id" value="${order.id}">Details</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<button onclick="document.location='/index'">Main page</button>
</body>
</html>
