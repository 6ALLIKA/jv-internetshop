<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h2>Login to your account</h2>
<h4 style="color: crimson">${message}</h4>
<form method="post" action="${pageContext.request.contextPath}login">
    <table border="1">
        <tr>
            <td>Login:</td>
            <td><input type="text" name="login"></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="pass"></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <button type="submit">Login</button>
            </td>
        </tr>
    </table>
</form>
<button onclick="document.location='/users/registration'">Register</button>
<button onclick="document.location='/index'">Main page</button>

</body>
</html>
