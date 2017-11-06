
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
.wrapper {
	display: grid;
	grid-template-columns: repeat(3, 1fr);
	grid-gap: 10px;
	grid-auto-rows: minmax(100px, auto);
	padding-left:0cm;
}
</style>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/css/styless.css"
	type="text/css"></link>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>
	<jsp:include page="header.jsp"></jsp:include>
	<c:if test="${ sessionScope.newUser != null }">
		<tr>
			<td>You have successfully registered an account ${ sessionScope.newUser.name}
				. It is time to enjoy our services!</td>
		</tr>
	</c:if>
	<h1 style="color: red">${error}</h1>


	<div class="wrapper">
		<c:forEach items="${applicationScope.products.values()}" var="product">
			<div class="padding">
		<center>		<a
					href="${pageContext.request.contextPath}/admin/show?id=${product.productID}">
					<img
					src="${pageContext.request.contextPath}/admin/getImage/${product.productID}"
					height="180" width="200" alt="" />
					<p></p>
						<span> "${product.name}" </span>

					 <span> ${product.price} лв </span> 
				</a></center>
			</div> 

		</c:forEach>
	</div>
	<!-- <jsp:include page="footer.jsp"></jsp:include> -->
</body>
</html>