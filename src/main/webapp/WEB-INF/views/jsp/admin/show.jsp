<%@ page import="com.emag.model.ProductPojo"%>
<%@ page import="com.emag.model.CategoryPojo"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ page import="java.util.TreeMap"%>

<!DOCTYPE html>
<html>
<head>
<title>Create new user</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/css/styless.css"
	type="text/css"></link>
</head>
<body>

	<%
		ProductPojo p = null;
		String productName = "";
		TreeMap<Integer, ProductPojo> prod = (TreeMap<Integer, ProductPojo>) application.getAttribute("products");
		p = prod.get(Integer.parseInt(request.getParameter("id")));
		productName = p.getName();
	%>

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
							value="<%=productName%>" type="text" readonly /></td>
					</tr>
					<tr style="background: white;">
						<td><label for="category">Category:</label></td>
						<td><input id="category" name="category"
							style="border: none;" value="<%=p.getCategory().getName()%>"
							type="text" readonly /></td>
					</tr>

					<tr style="background: white;">
						<td><label for="price">Price:</label></td>
						<td><input id="price" name="price" style="border: none;"
							value=" <%=p.getPrice()%>" type="text" readonly /></td>
					</tr>

					<tr style="background: white;">
						<td><label for="brand">Brand:</label></td>
						<td><input id="brand" name="brand" style="border: none;"
							value=<%=p.getBrand().getName()%> type="text" readonly /></td>
					</tr>

					<tr style="background: white;">
						<td><label for="availableProducts">Available
								products:</label></td>
						<td><input id="availableProducts" name="availableProducts"
							style="border: none;" value="<%=p.getQuantity()%>" type="text"
							readonly /></td>
					</tr>

					<tr style="background: white;">
						<td><label for="description">Description:</label></td>
						<td><label> <%=p.getDescription()%></label></td>
					</tr>

					<tr>
						<td><img
							src="${pageContext.request.contextPath}/admin/getImage/<%=p.getProductID()%>"
							height="150" width="150"></td>
					</tr>

				</tbody>
			</table>
		</fieldset>
	</div>


	<c:if test="${ sessionScope.user == null}">
		<h3>Please log in to be able to buy goods.</h3>
	</c:if>


	<p>
		<c:if test="${ sessionScope.user.isAdmin == true }">
			<a href="${pageContext.request.contextPath}/admin/productManagement">Back</a>&nbsp;&nbsp;|&nbsp;&nbsp;
			<a
				href="${pageContext.request.contextPath}/admin/edit?id=${param.id}">Edit</a>
		</c:if>

		<c:if
			test="${ sessionScope.user == null || sessionScope.user.isAdmin == false}">
			<a href="${pageContext.request.contextPath}/categories">Back</a> &nbsp;&nbsp;|&nbsp;&nbsp;
		</c:if>

		
	</p>
</body>
</html>