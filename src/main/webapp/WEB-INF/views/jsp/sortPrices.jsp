<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/css/styless.css"
	type="text/css"></link>
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/css/styless.css"
	type="text/css"></link>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>

	<div align="center">
		<a href="sortProducts?sort=desc"><button>Price high to
				low</button></a> <a href="sortProducts?sort=asc"><button>Price low
				to high</button></a>
	</div>
	<div>
		<center>
			<table class="products" border="1" cellpadding="5">

				<tr>
					<th>Product Name</th>
					<th>Product Price</th>
					<th>Product Quantity</th>
				</tr>
				<c:forEach items="${ sessionScope.products }" var="product">
					<tr>
						<td>${product.getName()}</td>
						<td>${product.getPrice()}</td>
						<td>${product.getQuantity()}</td>
						<td><img
							src="${pageContext.request.contextPath}/admin/getImage/${product.getProductID()}"
							height="80" width="80"></td>
						<td><a
							href="${pageContext.request.contextPath}/admin/show?id=${product.getProductID()}">Show</a></td>
					</tr>
				</c:forEach>
			</table>
		</center>
	</div>
<!-- 	<jsp:include page="footer.jsp"></jsp:include> -->
</body>
</html>