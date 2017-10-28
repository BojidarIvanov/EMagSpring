
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html>
<html>
<head>
<title>Create new user</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/css/styless.css"
	type="text/css"></link>
</head>
<body>
	<sql:setDataSource var="myDS" driver="com.mysql.jdbc.Driver"
		url="jdbc:mysql://localhost:3306/emag_final_project" user="Ivan"
		password="Koparan2525" />

	<sql:query var="listStuff" dataSource="${myDS}">
	SELECT * FROM products where product_id = ${param.id};
    </sql:query>
	<p>
		<c:if test="${ sessionScope.user.isAdmin == true }">
			<a href="${pageContext.request.contextPath}/admin/productManagement">Back</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="${pageContext.request.contextPath}/admin/edit?id=${param.id}">Edit</a>
		</c:if>
		<c:if
			test="${ sessionScope.user == null || sessionScope.user.isAdmin == false}">
			<a href="${pageContext.request.contextPath}/categories">Back</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		</c:if>

	</p>
	<div>
		<fieldset>

			<legend class="legend">Display product</legend>
			<table>
				<tbody>
					<tr style="background: white;">
						<td><label for="id">Id:</label></td>
						<td><input id="id" name="id" style="border: none;"
							value="${param.id}" type="text" readonly /></td>
					</tr>
					<tr style="background: white;">
						<td><label for="product">Product:</label></td>
						<td><input id="product" name="product" style="border: none;"
							value="${listStuff.rows[0].name}" type="text" readonly /></td>
					</tr>
					<tr style="background: white;">
						<td><label for="category">Category:</label></td>
						<td><input id="category" name="category"
							style="border: none;" value="${listStuff.rows[0].category_id}"
							type="text" readonly /></td>
					</tr>

					<tr style="background: white;">
						<td><label for="price">Price:</label></td>
						<td><input id="price" name="price" style="border: none;"
							value="${listStuff.rows[0].price}" type="text" readonly /></td>
					</tr>

					<tr style="background: white;">
						<td><label for="brand">Brand:</label></td>
						<td><input id="brand" name="brand" style="border: none;"
							value="${listStuff.rows[0].brand_id}" type="text" readonly /></td>
					</tr>

					<tr style="background: white;">
						<td><label for="availableProducts">Available
								products:</label></td>
						<td><input id="availableProducts" name="availableProducts"
							style="border: none;"
							value="${listStuff.rows[0].available_products}" type="text"
							readonly /></td>
					</tr>


					<tr style="background: white;">
						<td><label for="displayImage">Display image: </label></td>
						<td><input type="image" src="${listStuff.rows[0].image_url}"
							id="displayImage" name="displayImage" style="border: none;"
							value="${listStuff.rows[0].image_url}" type="text" readonly /></td>
					</tr>

				</tbody>
			</table>
		</fieldset>
	</div>
	<p>
		<c:if test="${ sessionScope.user.isAdmin == true }">
			<a href="${pageContext.request.contextPath}/admin/productManagement">Back</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="${pageContext.request.contextPath}/admin/edit?id=${param.id}">Edit</a>
		</c:if>
		<c:if
			test="${ sessionScope.user == null || sessionScope.user.isAdmin == false}">
			<a href="${pageContext.request.contextPath}/categories">Back</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		</c:if>

	</p>
</body>
</html>