<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../includes/header.jsp"></jsp:include>
<title>List of all products</title>
<h1 class="h2-responsive text-center my-5"><strong>List of all products</strong></h1>
<div class="container">
    <table id="dtBasicExample" class="mt-5 table table-striped table-bordered" cellspacing="0" width="100%">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Buy</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <c:out value="${product.getId()}"/>
                </td>
                <td>
                    <c:out value="${product.getName()}"/>
                </td>
                <td>
                    <c:out value="${product.getPrice()}"/>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/shoppingcart/products/add?id=${product.getId()}"
                       type="button"  class="btn btn-info btn-sm">Buy
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<jsp:include page="../includes/footer.jsp"></jsp:include>
