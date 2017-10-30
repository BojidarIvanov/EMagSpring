<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style media="screen" type="text/css">
label {
	display: block;
	position: relative;
}

label span {
	font-weight: bold;
	position: absolute;
	left: 3px;
}

label input, label textarea, label select {
	margin-left: 150px;
	background-color: white;
}

.mom {
	width: 100%; /* Try setting this to 400px or something */
	display: table;
	border: 1px solid #444444;
	background-color: red;
}

.child {
	display: table-cell;
}

.childinner {
	margin-left: 25px;
	/* Decorative .. */
	background-color: #cccccc;
	min-height: 40px;
}

.child:first-child .childinner {
	margin-left: 0;
}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/css/styless.css"
	type="text/css"></link>

</head>

<body>

	<jsp:include page="header.jsp"></jsp:include>

	<h2>Please select category:</h2>
	<div class="mom">
		<c:if test="${ requestScope.specificCategory == null }">
			<c:forEach items="${ applicationScope.categories }" var="category">
				<div class="child">
					<a href="sortCategories?sort=${category.key}"><button>${(category.value).name}</button></a>
				</div>
			</c:forEach>
		</c:if>

		<c:if test="${ requestScope.specificCategory != null }">
			<h2>Subcategories of ${ requestScope.specificCategory.name }:</h2>
			<br>
			<c:forEach items="${ applicationScope.categories }" var="category">
				<div class="child">
					<c:if
						test="${ category.value.parentCategory.categoryID == requestScope.specificCategory.categoryID }">
						<a href="sortCategories?sort=${category.key}"><button>${(category.value).name}</button></a>
					</c:if>
				</div>
			</c:forEach>
		</c:if>
	</div>
	<br>
	<div align="center">
		<a href="sortProducts?sort=desc"><button>Price high to low</button></a>
		<a href="sortProducts?sort=asc"><button>Price low to high</button></a>
		<table class="products" border="1" cellpadding="5">
			<tr>
				<th>Id</th>
				<th>Name</th>
				<th>Category</th>
				<th>Quantity</th>
				<th>Price</th>
			</tr>
			<c:forEach items="${applicationScope.products.values()}"
				var="product">
				<c:if
					test="${ requestScope.specificCategory == null && product.quantity > 0}">
					<tr>
						<td class="cent">${ product.productID }</td>
						<td class="cent"><c:out value="${ product.name }"></c:out></td>
						<td class="cent"><c:out value="${ product.category.name}"></c:out></td>
						<td class="cent"><c:out value="${ product.quantity }"></c:out></td>
						<td class="cent"><c:out value="${ product.price }"></c:out></td>
	                    <td><a href="${pageContext.request.contextPath}/admin/show?id=${product.productID}">Show</a></td>
						
					</tr>
				</c:if>
			</c:forEach>


			<c:forEach items="${applicationScope.products.values()}"
				var="product">
				<c:if
					test="${ requestScope.specificCategory != null && product.category.categoryID == requestScope.specificCategory.categoryID && product.quantity > 0 }">

					<tr>
						<td class="cent"><c:out value="${ product.productID }"></c:out></td>
						<td class="cent"><c:out value="${ product.name }"></c:out></td>
						<td class="cent"><c:out value="${ product.category.name}"></c:out></td>
						<td class="cent"><c:out value="${ product.quantity }"></c:out></td>
						<td class="cent"><c:out value="${ product.price }"></c:out></td>
						<td><a href="${pageContext.request.contextPath}/admin/show?id=${product.productID}">Show</a></td>
					</tr>
				</c:if>
			</c:forEach>


		</table>
		<!--  	<jsp:include page="footer.jsp"></jsp:include> -->
</body>
</html>