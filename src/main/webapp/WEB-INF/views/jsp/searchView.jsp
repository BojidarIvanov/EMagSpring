
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/css/styless.css"
	type="text/css"></link>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<jsp:include page="header.jsp"></jsp:include>
	<center>
		<c:if test="${productExists}">
			<table border="1">
				<tr>

					<th>Product Name</th>
					<th>Product Price</th>

				</tr>
				<c:forEach items="${ requestScope.matchingProducts }" 	var="productEntry">
					<tr>
						<center>
						<td>${productEntry.value.getName()}</td>
						<td>${productEntry.value.getPrice()}</td>
                      	</center>
					<td><img
						src="${pageContext.request.contextPath}/admin/getImage/${productEntry.key}"
						height="80" width="80"></td>

					<td><a
						href="${pageContext.request.contextPath}/admin/show?id=${productEntry.key}">Show</a></td>
				</tr></c:forEach>
			</table>
		</c:if>
		<c:if test="${not productExists}">
			<h1 style="color: Tomato;">No Product found</h1>
		</c:if>
		</tr>

	</center>
	<c:if test="${productExists}">
		<td><a
			href="${pageContext.request.contextPath}/admin/show?id=${productEntry.value.getProductID()}">Show</a></td>

	</c:if>


	</center>

	<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>