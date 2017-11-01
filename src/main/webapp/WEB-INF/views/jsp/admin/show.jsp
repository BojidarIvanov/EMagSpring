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
<<<<<<< HEAD
	<%--CHANGE PASSWORD HERE--%>
	<sql:setDataSource var="myDS" driver="com.mysql.jdbc.Driver"
		url="jdbc:mysql://localhost:3306/emag_final_project" user="root"
		password="admin" />

	<sql:query var="listStuff" dataSource="${myDS}">
	SELECT * FROM products WHERE product_id =  ${param.id};
	
    </sql:query>
	<%
		System.out.println("${param.id}");
	%>
=======

>>>>>>> 0fe559ae3c5bd97767f4c3af9f50d0ff42dcbd99
	<%
		ProductPojo p = null;
		String productName = "";
		TreeMap<Integer, ProductPojo> prod = (TreeMap<Integer, ProductPojo>) application.getAttribute("products");
		p = prod.get(Integer.parseInt(request.getParameter("id")));
		productName = p.getName();
	%>

	<button type="button" name="back" onclick="history.back()">back</button>
	<p>
		<c:if test="${ sessionScope.user.isAdmin == true }">
			<!-- 		<a href="${pageContext.request.contextPath}/admin/productManagement">Back</a>&nbsp;&nbsp;|&nbsp;&nbsp; -->
			<a
				href="${pageContext.request.contextPath}/admin/edit?id=${param.id}">Edit</a>
		</c:if>
		<c:if
			test="${ sessionScope.user == null || sessionScope.user.isAdmin == false}">
		<!-- 	<a href="${pageContext.request.contextPath}/categories">Back</a>&nbsp;&nbsp;|&nbsp;&nbsp; -->
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
<<<<<<< HEAD
						<td><label for="displayImage">Display image: </label></td>
						<td><input type="image" src="${p.imageURL}" id="displayImage"
							name="displayImage" style="border: none;"
							value="<%=p.getImageURL()%>" type="text" readonly /></td>
=======
						<td><label for="description">Description:</label></td>
						<td><label> <%=p.getDescription()%></label></td>
					</tr>

					<tr>
						<td><img
							src="${pageContext.request.contextPath}/admin/getImage/<%=p.getProductID()%>"
							height="150" width="150"></td>
>>>>>>> 0fe559ae3c5bd97767f4c3af9f50d0ff42dcbd99
					</tr>

				</tbody>
			</table>
		</fieldset>
	</div>


<<<<<<< HEAD

	<%--BOZHIDAR ADDING RAVIEW--%>
	<br>
	<jsp:include page="../comment2.jsp"></jsp:include>
	
=======
	<c:if test="${ sessionScope.user == null}">
		<h3>Please log in to be able to buy goods.</h3>
	</c:if>


>>>>>>> 0fe559ae3c5bd97767f4c3af9f50d0ff42dcbd99
	<p>
		<button type="button" name="back" onclick="history.back()">back</button>
		<c:if test="${ sessionScope.user.isAdmin == true }">
			<!--  	<a href="${pageContext.request.contextPath}/admin/productManagement">Back</a>&nbsp;&nbsp;|&nbsp;&nbsp; -->
			<a
				href="${pageContext.request.contextPath}/admin/edit?id=${param.id}">Edit</a>
		</c:if>

		<c:if
			test="${ sessionScope.user == null || sessionScope.user.isAdmin == false}">
			<!-- 		<a href="${pageContext.request.contextPath}/categories">Back</a> &nbsp;&nbsp;|&nbsp;&nbsp;  -->
		</c:if>


	</p>
</body>
</html>