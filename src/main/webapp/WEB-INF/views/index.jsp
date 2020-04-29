<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1><a href="${pageContext.request.contextPath}/"><img src="https://logoeps.com/wp-content/uploads/2014/01/39023-
amazon-pay-card-logo-icon-vector-icon-vector-eps.png"></a></h1>

<h2>Admin panel</h2>
<button onclick="document.location='users/all'">All users</button>
<button onclick="document.location='/products/add'">Add product</button>
<button onclick="document.location='/orders/all'">All orders</button>

<h2>Injectors</h2>
<button onclick="document.location='/index/injectusers'">Add users</button>
<button onclick="document.location='/index/injectproducts'">Add products</button>

<h2>User panel</h2>
<button onclick="document.location='/registration'">Registration</button>
<button onclick="document.location='/products/all'">All products</button>
<button onclick="document.location='/shoppingcart'">ShoppingCart</button>

</body>
</html>
