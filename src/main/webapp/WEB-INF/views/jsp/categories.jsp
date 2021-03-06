<%@ page import="com.emag.model.CategoryPojo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/css/styless.css"
	type="text/css"></link>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<style>
.wrapper {
	display: grid;
	grid-template-columns: repeat(3, 1fr);
	grid-gap: 10px;
	grid-auto-rows: minmax(100px, auto);
	padding-left: 0cm;
}

img.button:hover p.text {
	color: rgb(0, 255, 200);
}

p {
	font-weight: 300;
}
</style>



</head>

<body>

	<jsp:include page="header.jsp"></jsp:include>


	<h2>Please select category:</h2>
	<div class="mom">
		<c:if test="${ requestScope.specificCategory == null }">
			<c:forEach items="${ applicationScope.categories }" var="category">
				<div class="child">
					<a href="sortCategories?sort=${category.key}"><button size="35"
							style="width: 150px">${(category.value).name}</button></a>
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
						<div class="image">
							<a href="sortCategories?sort=${category.key}"><button
									size="35" style="width: 150px">${(category.value).name}</button></a>
						</div>
					</c:if>
				</div>
			</c:forEach>
		</c:if>
	</div>
	<br>

	<%
		int catId = 0;
		Object category = request.getAttribute("specificCategory");
		if (category != null) {
			catId = ((CategoryPojo) category).getCategoryID();
		}
	%>
	<center>
		<a href="sortProducts?sort=desc&cat=<%=catId%>"><button size="35"
				style="width: 150px">Price high to low</button></a> <a
			href="sortProducts?sort=asc&cat=<%=catId%>"><button size="35"
				style="width: 150px">Price low to high</button></a>
		<table class="products" border="1" cellpadding="5">
			</center>

			<div class="wrapper">
				<c:forEach items="${applicationScope.products.values()}"
					var="product">
					<c:if
						test="${ requestScope.specificCategory == null && product.quantity > 0}">
						<div>
							<a
								href="${pageContext.request.contextPath}/admin/show?id=${product.productID}">
								<img class='button'
								src="${pageContext.request.contextPath}/admin/getImage/${product.productID}"
								height="180" width="180" alt="" />
								<p class='text'><p>
							
									<span> "${product.name}" </span> <span> ${product.price}
										лв </span></a>
						</div>
					</c:if>
				</c:forEach>
			</div>

			<div class="wrapper">
				<c:forEach items="${applicationScope.products.values()}"
					var="product">
					<c:if
						test="${ requestScope.specificCategory != null && product.category.categoryID == requestScope.specificCategory.categoryID && product.quantity > 0 }">

						<div>
							<a
								href="${pageContext.request.contextPath}/admin/show?id=${product.productID}">
								<img class='button'
								src="${pageContext.request.contextPath}/admin/getImage/${product.productID}"
								height="180" width="180" alt="" />
								<p class='text'>
								<p>
									<span> "${product.name}" </span> <span> ${product.price}
										лв </span>
							</a>
						</div>
					</c:if>
				</c:forEach>
			</div>

		</table>
</body>
</html>