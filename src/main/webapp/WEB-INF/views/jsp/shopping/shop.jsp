<%@ page language="java" contentType="text/html; charset = UTF-8"
	pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>
<head>
<title>Emag shop ordering process</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/css/styless.css" type="text/css"></link>
</head>
<body>

	<sql:setDataSource var="myDS" driver="com.mysql.jdbc.Driver"
		url="jdbc:mysql://localhost:3306/emag_final_project" user="Ivan"
		password="Koparan2525" />

	<sql:query var="listStuff" dataSource="${myDS}"> 
      SELECT * FROM products ORDER BY product_id;
    </sql:query>

	<center>
		<h3>Emag shop ordering process</h3>
	</center>

	<div align="center">
		<p>
			<a href="${pageContext.request.contextPath}/index">Back</a>
		</p>
		<form action="${pageContext.request.contextPath}/shopping/handleOrder"
			method="post">
			<table class="products" border="1" cellpadding="5">
				<tr>
					<th>Buy</th>
					<th>Qty</th>
					<th>Id</th>
					<th>Product</th>
					<th>Availability</th>
					<th>Price</th>
				</tr>
				<c:set var='ind' value='1' scope='page' />
				<c:forEach var="item" items="${listStuff.rows}">
					<tr>
						<td><input type="checkbox" name="check-${ind}" value="${ind}" /></td>
						<td><input type="number" name="num-${ind}" min="0"
							max="${item.available_products}" step="1" value="0" size="3" /></td>
						<td><input type="text" name="id-${ind}"
							value="${item.product_id}" size="10" readonly /></td>
						<td><input style="text-align: center;" name="prod-${ind}"
							type="text" value="${item.name}" readonly /></td>
						<td><input style="text-align: center;" name="cat-${ind}"
							type="text" value="${item.available_products}" readonly /></td>
						<td><input type="text" name="price-${ind}"
							value="${item.price}" size="7" readonly /></td>
					</tr>
					<c:set var='ind' value='${ind + 1}' />
				</c:forEach>
				<input type="hidden" value="${listStuff.rowCount}" name="rowCount" />
			</table>
			<p>
				<input type="submit" value=" Submit order " />
		</form>
		<p>
			<a href="${pageContext.request.contextPath}/index">Back</a>
		</p>
	</div>
</body>
</html>
