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
                <button type="submit">Добавить</button>
            </td>
        </tr>
    </table>
</form>
<button onclick="document.location='/index'">На главную</button>
</body>
</html>
