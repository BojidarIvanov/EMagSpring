<%@ page language="java" contentType="text/html; charset = UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Order Confirmation</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/css/styless.css"
	type="text/css"></link>
</head>
<body>
	<h3 style="color: lime;"center";">${requestScope.result }</h3>

	<center>
		<h3>Order Confirmation</h3>
	</center>
	<div align="center">
		<center>
			<button type="button" name="back" onclick="history.back()">back</button>

			<!--  	<a href="${pageContext.request.contextPath}/shopping/shop">Back</a> -->
		</center>

		<form action="placeOrder" method="post">
			<table class="products" border="1" cellpadding="5">
				<tr>
					<th>Qty</th>
					<th>Id</th>
					<th>Product name</th>
					<th>Availability</th>
					<th>Price</th>
				</tr>
				<c:set var='ind' value='1' />
				<c:forEach var="item" items="${items}">
					<tr>
						<td><input type="number" name="num-${ind}" min="1" max="99"
							step="1" value="${item.qty}" size="3" /></td>
						<td><input type="text" name="id-${ind}" value="${item.id}"
							size="4" readonly /></td>
						<td><input style="text-align: center;" name="prod-${ind}"
							type="text" value="${item.product}" readonly /></td>
						<td><input style="text-align: center;" name="cat-${ind}"
							type="text" value="${item.category}" readonly /></td>
						<td><input type="text" name="price-${ind}"
							value="${item.price}" size="7" readonly /></td>
				<!--  	<td><form action="${pageContext.request.contextPath}/shopping/deleteEntry"
								method="POST">
								<input type="hidden" value="${item.id}" name="productId" />
								<input type="submit" value="Delete entry">
							</form></td>-->	
					</tr>
					<c:set var='ind' value='${ind + 1}' />
				</c:forEach>
			</table>
			<input type="hidden" value="${items.size()}" name="rowCount" />
			<p>
			<div class="total">
				<span>Total: $</span>
				<fmt:formatNumber value="${total}" />
			</div>
			<c:set var="totalSum" value="${total}" />

			<c:if
				test="${totalSum.unscaledValue() > 0 && sessionScope.user != null }">
				<p>
					<input type="submit" value=" Confirm order " />
				</p>
			</c:if>
			<c:if test="${sessionScope.user == null }">
				<p>
				<h3>Please login to be able to make an order.</h3>
				</p>
			</c:if>
			<c:if test="${totalSum.unscaledValue() == 0 }">
				<p>
				<h4>No items were chosen, nothing to confirm.</h4>
				</p>
			</c:if>
		</form>
		<center>
			<!--	<a href="${pageContext.request.contextPath}/index">Back</a> -->
			<button type="button" name="back" onclick="history.back()">back</button>

		</center>

	</div>
</body>
</html>
