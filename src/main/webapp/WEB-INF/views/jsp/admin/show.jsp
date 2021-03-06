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
	href="${pageContext.request.contextPath}/css/styles.css">
<jsp:include page="../header.jsp"></jsp:include>

</head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/css/styless.css"
	type="text/css"></link>
<body>

	<%
		ProductPojo p = null;
		String productName = "";
		TreeMap<Integer, ProductPojo> prod = (TreeMap<Integer, ProductPojo>) application.getAttribute("products");
		p = prod.get(Integer.parseInt(request.getParameter("id")));
		productName = p.getName();
	%>

	<!-- 	<button type="button" name="back" onclick="history.back()">back</button> -->
	<p>
		<c:if test="${ sessionScope.user.isAdmin == true }">
			<!-- 		<a href="${pageContext.request.contextPath}/admin/productManagement">Back</a>&nbsp;&nbsp;|&nbsp;&nbsp; -->
			<center>
				<a
					href="${pageContext.request.contextPath}/admin/edit?id=${param.id}">Edit</a>
			</center>
		</c:if>
		<c:if
			test="${ sessionScope.user == null || sessionScope.user.isAdmin == false}">
			<!-- 	<a href="${pageContext.request.contextPath}/categories">Back</a>&nbsp;&nbsp;|&nbsp;&nbsp; -->
		</c:if>
	</p>


	<center>
		<div>
			<fieldset>

				<legend class="legend">Display product</legend>

				<table style="width: 100px">

					<tbody>
						<tr style="background: white;">
							<td><label for="id">Id:</label></td>
							<td><input id="id" name="id" style="border: none;"
								value="${param.id}" type="text" readonly /></td>
						</tr>
						<tr style="background: white;">
							<td><label for="product">Product:</label></td>
							<td><input id="product" name="product"
								"
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


						<div>

							<form
								action="${pageContext.request.contextPath}/shopping/handleOrder"
								method="post">
								<table class="products" border="1" cellpadding="5"
									style="width: 100px">
									<c:set var='ind' value='1' scope='page' />
									<c:forEach var="item" items="1">
										<tr>
											<input type="hidden" name="check-${ind}" value="${ind}" />
											<td>Qty<input type="number" name="num-${ind}" min="1"
												max=<%=p.getQuantity()%> step="1" value="0" size="3"
												style="width: 50px" /></td>
											<input type="hidden" name="id-${ind}"
												value=<%=p.getProductID()%> size="10" readonly />
											<input style="text-align: center;" name="prod-${ind}"
												type="hidden" value=<%=productName%> readonly />
											<input style="text-align: center;" name="cat-${ind}"
												type="hidden" value=<%=p.getQuantity()%> readonly />
											<input type="hidden" name="price-${ind}"
												value=<%=p.getPrice()%> size="7" readonly />
										</tr>
										<tr>
											<c:set var='ind' value='${ind + 1}' />
									</c:forEach>
									<input type="hidden" value="1" name="rowCount" />
								</table>
								<p>
									<input type="submit" value=" Add to cart " style="width: 150px" ; />
								</p>
								<c:if test="sessionScope.user == null }">
									<h3>Please login to be able to order goods.</h3>
								</c:if>
							</form>

						</div>

					</tbody>
				</table>
			</fieldset>
		</div>
	</center>
	<c:if test="${ sessionScope.user.isAdmin == true }">
		<!--  	<a href="${pageContext.request.contextPath}/admin/productManagement">Back</a>&nbsp;&nbsp;|&nbsp;&nbsp; -->
		<center>
		<p>
			<a
				href="${pageContext.request.contextPath}/admin/edit?id=${param.id}">Edit</a> </p>
		</center>
	</c:if>

	<jsp:include page="../comment2.jsp"></jsp:include>

	<br>
	<p>
		<!-- 	<button type="button" name="back" onclick="history.back()">back</button> -->


		<c:if
			test="${ sessionScope.user == null || sessionScope.user.isAdmin == false}">
			<!-- 		<a href="${pageContext.request.contextPath}/categories">Back</a> &nbsp;&nbsp;|&nbsp;&nbsp;  -->
		</c:if>

	</p>
	<jsp:include page="../footer.jsp"></jsp:include>

</body>
</html>