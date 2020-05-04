<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New product</title>
</head>
<body>
<h1>Put product details below</h1>

<form method="post" action="${pageContext.request.contextPath}add">
    <table border="1">
        <tr>
            <td>Name:</td>
            <td><input type="text" name="name" ></td>
        </tr>
        <tr>
            <td>Price:</td>
            <td><input type="text" name="price"></td>
        </tr>
        <tr>
        <tr>
            <td></td>
            <td>
                <button type="submit">Add product</button>
            </td>
        </tr>
    </table>
</form>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>
                <c:out value="${product.id}"/>
            </td>
            <td>
                <c:out value="${product.name}"/>
            </td>
            <td>
                <c:out value="${product.price}"/>
            </td>
            <td>
                <button onclick="document.location='delete?id=${product.id}'">Delete</button>
            </td>
        </tr>
    </c:forEach>
</table>
<button onclick="document.location='/index'">Main page</button>
</body>
</html>
