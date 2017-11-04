
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>


<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">

<link rel='stylesheet prefetch'
	href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.min.css'>
<link rel='stylesheet prefetch'
	href='https://fonts.googleapis.com/css?family=Signika:700,400'
	rel='stylesheet' type='text/css'>
<style media="screen" type="text/css">
h3 {
	color: white;
}

label {
	display: block;
	position: relative;
}

label span {
	font-weight: bold;
	position: absolute;
	left: 3px;
}

label input, label textarea, label select {
	margin-left: 150px;
	background-color: white;
}

.mom {
	width: 100%; /* Try setting this to 400px or something */
	display: table;
	border: 1px solid #444444;
	background-color: red;
}

.child {
	display: table-cell;
}

.childinner {
	margin-left: 25px;
	/* Decorative .. */
	background-color: #cccccc;
	min-height: 40px;
}

.child:first-child .childinner {
	margin-left: 0;
}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>EMAG</title>

<div class="mom">
	<div class="child">
		<a href="${pageContext.request.contextPath}/index"> <img
			src="${pageContext.request.contextPath}/img/emag.jpg" alt="Logo">
		</a>
	</div>
	<div class="child">
		<form action="${pageContext.request.contextPath}/searchProduct"
			method="get">
			<input class="navi" type="text" name="productName"
				placeholder="Serch for product..."
				style="height: 45px; width: 300px"> <input class="navi"
				type="submit" name="submit" value="Search"
				style="height: 45px; width: 150px">
		</form>
	</div>

	<div class="child">
		<h3 class="welcome">Welcome ${sessionScope.user.name }</h3>
	</div>
</div>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>
	<div class="header">


		<form class="navi" action="${pageContext.request.contextPath}/about">
			<input type="submit" value="About Us">
		</form>
		<form class="navi" action="${pageContext.request.contextPath}/index">
			<input type="submit" value="Products on sale">
		</form>
		<c:if test="${sessionScope.user != null}">
			<form class="navi" action="${pageContext.request.contextPath}/orders">
				<input type="submit" value="View My orders">
			</form>
		</c:if>
		<form class="navi"
			action="${pageContext.request.contextPath}/categories" method="GET">
			<input type="submit" value="View Categories">
		</form>
		<c:if test="${sessionScope.user == null}">
			<form class="navi"
				action="${pageContext.request.contextPath}/loginPage" method="GET">
				<input type="submit" value="Go To Login Page">
			</form>
		</c:if>

		<form class="navi" action="${pageContext.request.contextPath}/contact">
			<input type="submit" value="Contacts">
		</form>
		<form class="navi" action="${pageContext.request.contextPath}/main"
			method="GET">
			<input type="submit" value="Main">
		</form>
		<c:if test="${sessionScope.user != null}">
			<form class="navi" action="${pageContext.request.contextPath}/logout"
				method="GET">
				<input type="submit" value="Logout">
			</form>
		</c:if>
		<c:if test="${sessionScope.user.isAdmin == true}">
			<form class="navi"
				action="${pageContext.request.contextPath}/admin/productManagement"
				method="GET">
				<input type="submit" value="Product Management">
			</form>
		</c:if>

		<c:if test="${sessionScope.user != null}">
			<form class="navi"
				action="${pageContext.request.contextPath}/shopping/shop"
				method="GET">
				<input type="submit" value="Let's go shopping!!!">
			</form>
		</c:if>

		<!--  	<c:if test="${sessionScope.user != null}"> -->
		<form class="navi"
			action="${pageContext.request.contextPath}/shopping/confirmOrder"
			method="GET">
			<input type="submit" value="My cart">
		</form>
		<!-- 		</c:if> -->

		<br> <br> <br>
		<c:if test="${ sessionScope.newUser != null }">
			<h5 style="color: lime;"center";">New account registered ${ sessionScope.newUser.name}
				. Congratulations!</h5>

		</c:if>
		<h3 style="color: lime;"center";">${requestScope.SubscrMsg }</h3>
		<h3 style="color: lime;"center";">${requestScope.errorMsg }</h3>
		<h3 style="color: lime;"center";">${requestScope.error}</h3>
		<br>

	</div>
</body>
</html>