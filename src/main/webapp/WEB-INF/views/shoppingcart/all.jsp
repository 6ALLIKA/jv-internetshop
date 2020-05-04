<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping cart</title>
</head>
<body>
<h1>Products in shopping cart</h1>
<table border="1">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Price</th>
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
                <button onclick="document.location='/shoppingcart/products/delete?id=${product.id}'">Delete</button>
            </td>
        </tr>
    </c:forEach>
    <tr>
        <td>
            <button onclick="document.location='/orders/create'">Create order</button>
        </td>
    </tr>
</table>
    <button onclick="document.location='/index'">Main page</button>
</body>
</html>
