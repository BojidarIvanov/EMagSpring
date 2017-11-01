<%@ page import="com.emag.model.CategoryPojo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>


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
						<a href="sortCategories?sort=${category.key}"><button
								size="35" style="width: 150px">${(category.value).name}</button></a>
					</c:if>
				</div>
			</c:forEach>
		</c:if>
	</div>
	<br>
	<div align="center">
		<%
			int catId = 0;
			Object category = request.getAttribute("specificCategory");
			if (category != null) {
				catId = ((CategoryPojo) category).getCategoryID();
			}
		%>
		<a href="sortProducts?sort=desc&cat=<%=catId%>"><button size="35"
				style="width: 150px">Price high to low</button></a> <a
			href="sortProducts?sort=asc&cat=<%=catId%>"><button size="35"
				style="width: 150px">Price low to high</button></a>
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
						<td class="cent">${ product.name }</td>
						<td class="cent">${ product.category.name}</td>
						<td class="cent">${ product.quantity }</td>
						<td class="cent">${ product.price }</td>
						<td><img
							src="${pageContext.request.contextPath}/admin/getImage/${product.productID}"
							height="80" width="80"></td>
						<td><a
							href="${pageContext.request.contextPath}/admin/show?id=${product.productID}">Show</a></td>

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
						<td><img
							src="${pageContext.request.contextPath}/admin/getImage/${product.productID}"
							height="80" width="80"></td>
						<td><a
							href="${pageContext.request.contextPath}/admin/show?id=${product.productID}">Show</a></td>

					</tr>
				</c:if>
			</c:forEach>
		</table>
	</div>
	<!--  	<jsp:include page="footer.jsp"></jsp:include> -->
</body>
</html>