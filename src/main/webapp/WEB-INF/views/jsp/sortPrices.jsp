
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/css/styless.css"
	type="text/css"></link>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<h2>Please select category:</h2>
	<div align="center">
				<div class="mom">
			<c:if test="${ requestScope.specificCategory == null }">
				<c:forEach items="${ applicationScope.categories }" var="category">
					<div class="child">
						<a href="sortCategories?sort=${category.key}"><button
								size="35" style="width: 150px">${(category.value).name}</button></a>
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
							<a href="sortCategories?sort=${category.key}"><button
									size="35" style="width: 150px">${(category.value).name}</button></a>
						</c:if>
					</div>
				</c:forEach>
			</c:if>
		</div>
		<a
			href="${pageContext.request.contextPath}/sortProducts?sort=desc&cat=${categoryId}"><button>Price
				high to low</button></a> <a
			href="${pageContext.request.contextPath}/sortProducts?sort=asc&cat=${categoryId}"><button>Price
				low to high</button></a>

		<div>
			<center>
				<table class="products" border="1" cellpadding="5">

					<tr>
						<th>Name</th>
						<th>Category</th>
						<th>Price</th>
					</tr>
					<c:forEach items="${ products }" var="product">
						<tr>
				
							<td class="cent">${product.name}</td>
							<td class="cent">${product.category.name}</td>
							<td class="cent">${product.price}</td>
							<td><img
								src="${pageContext.request.contextPath}/admin/getImage/${product.productID}"
								height="80" width="80"></td>
							<td><a
								href="${pageContext.request.contextPath}/admin/show?id=${product.productID}">Show</a></td>
						</tr>
					</c:forEach>
				</table>
			</center>
		</div>
		<!-- 	<jsp:include page="footer.jsp"></jsp:include> -->
</body>
</html>