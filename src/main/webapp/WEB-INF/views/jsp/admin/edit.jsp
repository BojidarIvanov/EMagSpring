<%@ page import="com.emag.model.ProductPojo"%>
<%@ page import="com.emag.model.CategoryPojo"%>
<%@ page import="java.util.TreeMap"%>
<%@ page import="java.util.Iterator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Create new user</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/css/styless.css"
	type="text/css"></link>
</head>
<body>

	<c:if
		test="${sessionScope.user == null || sessionScope.user.isAdmin == false}">
		<c:redirect url="login"></c:redirect>
	</c:if>

	<%
		ProductPojo p = null;
		String productName = "";
		TreeMap<Integer, ProductPojo> prod = (TreeMap<Integer, ProductPojo>) application.getAttribute("products");
		p = prod.get(Integer.parseInt(request.getParameter("id")));
		productName = p.getName();
	%>
	


	<div>
		<form action="uploadPicture" method="post"
			enctype="multipart/form-data">
			<input type="file" name="picture"> <input type="hidden"
				value='${param.id}' name="id" /> <input type="submit">
		</form>
	</div>
	<div>
		<fieldset>
			<legend class="legend">Edit product</legend>
			<form action="${pageContext.request.contextPath}/dataVerifier"
				method="post">
				<table>
					<font face="Britannic Bold" size="4" style="color: red;">${errorMsg}</font>
					<tbody>
						<tr style="background: white;">
							<td><label for="id">Id:</label></td>
							<td><input id="id" name="id" value='${param.id}'
								type="number" min="1" readonly /></td>
						</tr>
						<tr style="background: white;">
							<td><label for="product">Product name:</label></td>
							<td><input id="product" value="<%=productName%>" name="name"
								type="text" minlength="3" required /></td>
						</tr>

						<tr style="background: white;">
							<td><label for="category">Category id:</label></td>
							<td><input id="categoryId"
								value=<%=p.getCategory().getCategoryID()%> name="categoryId"
								type="number" type="number" step="1" min="1" /></td>
						</tr>

						<tr style="background: white;">
							<td><label for="price">Price:</label></td>
							<td><input id="price" value=<%= p.getPrice() %> name="price"
								value="${p.price}" type="number" step="0.01" min="0" /></td>
						</tr>

						<tr style="background: white;">
							<td><label for="description">Description:</label></td>
							<td><input id="description" value="<%=p.getDescription()%>"
								name="description" type="text" minlength="3" required /></td>
						</tr>

						<tr style="background: white;">
							<td><label for="brand">Brand id:</label></td>
							<td><input id="brandId"
								value=<%=p.getBrand().getBrandID()%> name="brandId"
								type="number" step="1" min="1" /></td>
						</tr>

						<tr style="background: white;">
							<td><label for="availability">Available quantity:</label></td>
							<td><input id="availability" value=<%=p.getQuantity()%>
								name="availability" type="number" step="1" min="0" /></td>
						</tr>

						<tr style="background: white;">
							<td><label for="discount">Discount:</label></td>
							<td><input id="discount" value="0" name="discountPercent"
								type="number" step="0.01" min="0" max="99" /></td>
						</tr>

						<tr style="background: white;">
							<td><label for="image">Image url:</label></td>
							<td><input id="imageURL" value="<%=p.getImageURL()%> "
								name="imageUrl" type="text" /></td>
						</tr>
						<tr>
							<td><img
								src="${pageContext.request.contextPath}/admin/getImage/<%=p.getProductID()%>"
								height="125" width="125"></td>
						</tr>
					</tbody>
				</table>
				<p>
					<input type="submit" value=" Save edits " />
				</p>
				<p>
					<a
						href="${pageContext.request.contextPath}/admin/productManagement">Back</a>
				</p>
			</form>
		</fieldset>
	</div>

	<form action="${pageContext.request.contextPath}/sendMail"
		method="POST">
		<div class="contact-left">
			<h3 style="color: brown">
				Please provide text of the promo mail to be sent to subscribed
				customers. <br>The first sentence with the name of the customer
				will be generated programmatically.
			</h3>
			<input type="text" value="Subject" required name="subject"
				style="width: 300px">
		</div>
		<div class="contact-right">
			<textarea placeholder="Dear  + fullName + " required maxlength="1000"
				name="promoContent"></textarea>
		</div>
		<div class="clearfix"></div>
		<input type="hidden" value="promo" name="task" /> <input
			type="submit" value="SUBMIT">
	</form>
</body>
</html>
