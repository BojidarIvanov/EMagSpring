
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<!-- Custom Theme files -->
<link rel="stylesheet" type="text/css" href="css/css/style.scss">
<!-- Custom Theme files -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords"
	content="Eshop Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>EMAG</title>
<link rel="stylesheet" type="text/css" href="css/styles.css">

</head>
<!-- <a href="index"> <img src="img/emag.jpg"> -->

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
		<c:if test="${sessionScope.user == null}">
			<form class="navi" action="loginPage" method="get">
				<input type="submit" value="Go To Login Page">
			</form>
		</c:if>
		
		<form class="navi" action="contact">
			<input type="submit" value="Contacts">
		</form>
			
		
		<form class = "navi" action="searchProduct" method = "get">
				<input  class= "navi"  type="text" name="productName" value = "Serch for product">
				<input class = "navi" type="submit" name="submit" value="Search">
		</form>
		
		
		<c:if test="${sessionScope.user != null}">
			<form class="navi" action="logout" method="post">
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
				action="${pageContext.request.contextPath}/shopping/shop" method="post">
				<input type="submit" value="Let's go shopping!!!">
			</form>
		</c:if>


		<h3 class="welcome">Welcome, ${sessionScope.user.name }</h3>
	</div>
</body>
</html>