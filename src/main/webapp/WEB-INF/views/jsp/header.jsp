
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- Custom Theme files -->
<!-- <link rel="stylesheet" type="text/css" href="css/css/style.css"> -->
<link rel="stylesheet" type="text/css" href="header.css">

<!-- Custom Theme files -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords"
	content="Eshop Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />

<head>
<style media="screen" type="text/css">
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
		<a href="index"> <img src="img/emag.jpg" alt="Logo">
		</a>
	</div>
	<div class="child">
		<form action="searchProduct" method="get">
			<input class="navi" type="text" name="productName"
				placeholder="Serch for product..."
				style="height: 45px; width: 300px"> <input class="navi"
				type="submit" name="submit" value="Search"
				style="height: 45px; width: 150px">
		</form>
	</div>
	<div class="child">
		<h3 class="welcome">Welcome, ${sessionScope.user.name }</h3>
	</div>
</div>
<link rel="stylesheet" type="text/css" href="css/styles.css">
</head>

<body>
	<div class="header">
		<form class="navi" action="about">
			<input type="submit" value="About Us">
		</form>
		<form class="navi" action="index">
			<input type="submit" value="Products on sale">
		</form>
		<c:if test="${sessionScope.user != null}">
			<form class="navi" action="orders">
				<input type="submit" value="View My orders">
			</form>
		</c:if>
		<form class="navi" action="categories" method="GET">
			<input type="submit" value="View Categories">
		</form>
		<c:if test="${sessionScope.user == null}">
			<form class="navi" action="loginPage" method="GET">
				<input type="submit" value="Go To Login Page">
			</form>
		</c:if>

		<form class="navi" action="contact">
			<input type="submit" value="Contacts">
		</form>

		<c:if test="${sessionScope.user != null}">
			<form class="navi" action="logout" method="GET">
				<input type="submit" value="Logout">
			</form>
		</c:if>
		<c:if test="${sessionScope.user.isAdmin == true}">
			<form class="navi"
				action="${pageContext.request.contextPath}/admin/productManagement"
				method="get">
				<input type="submit" value="Product Management">
			</form>
		</c:if>

		<c:if test="${sessionScope.user != null}">
			<form class="navi"
				action="${pageContext.request.contextPath}/shopping/shop"
				method="post">
				<input type="submit" value="Let's go shopping!!!">
			</form>
		</c:if>


	</div>
</body>
</html>