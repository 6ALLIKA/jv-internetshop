<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<h2>Registration</h2>

<h4 style="color: crimson">${message}</h4>

<form method="post" action="${pageContext.request.contextPath}registration">
    <table border="1">
        <tr>
            <td>Name:</td>
            <td><label>
                <input type="text" name="name" value="<%=(request.getParameter("name") != null)
        ? request.getParameter("name") : ""%>">
            </label></td>
        </tr>
        <tr>
            <td>Login:</td>
            <td><label>
                <input type="text" name="login" value="<%=(request.getParameter("login") != null)
        ? request.getParameter("login") : ""%>">
            </label></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><label>
                <input type="password" name="pass">
            </label></td>
        </tr>
        <tr>
            <td>Repeat password:</td>
            <td><label>
                <input type="password" name="rep-pass">
            </label></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <button type="submit">Register</button>
            </td>
        </tr>
    </table>
</form>
<button onclick="document.location='/index'">Main page</button>

</body>
</html>
