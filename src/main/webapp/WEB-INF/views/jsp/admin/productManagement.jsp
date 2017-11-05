
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
<title>The products in stock</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/css/styless.css"
	type="text/css"></link>
</head>
<body>
	<c:if
		test="${sessionScope.user == null || sessionScope.user.isAdmin == false}">
		<c:redirect url="loginPage"></c:redirect>
	</c:if>

	<jsp:include page="../header.jsp"></jsp:include>


	<center>
		<h3>Products in store</h3>
	</center>
	<div align="center">
		<p>
			<a href="${pageContext.request.contextPath}/admin/create">Create
				a new product</a>
		</p>
	<!--	<p>
			<a href="${pageContext.request.contextPath}/index">Back</a>
		</p>  -->
	
		<table class="products" border="1" cellpadding="5">
			<tr>
				<th>Id</th>
				<th>Product</th>
				<th>Category</th>
				<th>Brand</th>
				<th>Availability</th>
				<th>Price</th>
				<th>Image</th>

			</tr>
			<c:forEach var="item" items="${products.values()}">
				<c:if test="${ item.quantity > 0}">
					<tr>
						<td class="rght"><c:out value="${item.productID}" /></td>
						<td class="cent"><c:out value="${item.name}" /></td>
						<td class="cent"><c:out value="${item.category.name}" /></td>
						<td class="cent"><c:out value="${item.brand.name}" /></td>
						<td class="cent"><c:out value="${item.quantity}" /></td>
						<td class="cent"><c:out value="$${item.price}" /></td>
						<td><img
							src="${pageContext.request.contextPath}/admin/getImage/${item.productID}"
							height="80" width="80"></td>
						<td><a
							href="${pageContext.request.contextPath}/admin/show?id=${item.productID}">Show</a></td>
						<td><a
							href="${pageContext.request.contextPath}/admin/edit?id=${item.productID}">Edit</a></td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
	
		<p>
			<a href="${pageContext.request.contextPath}/admin/create">Create
				a new product</a>
		</p>
		<!-- <p>
			<a href="${pageContext.request.contextPath}/index">Back</a>
		</p>  -->
	</div>
</body>
</html>