<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/css/styless.css"
	type="text/css"></link>
<jsp:include page="header.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Emag final project</title>
</head>



<c:if test="${ requestScope.error == null }">
	<h2>Welcome, please login:</h2>
	<br>
</c:if>
<c:if test="${ requestScope.error != null }">
	<h1 style="color: red">Sorry, username or password missmatch.
		Reason: ${requestScope.error }</h1>
</c:if>

<c:if test="${ sessionScope.user != null }">
	<c:redirect url="main"></c:redirect>
	<h1 style="color: black">You are already logged in.</h1>

</c:if>
<div class="container">

	<form action="login" method="post">
		<label><span>Email:</span> <input type="text" name="user"
			placeholder="Enter email" size="35" style="width: 193px"></label> <br>
		<label><span>Password:</span><input type="password"
			placeholder="Enter Password" name="pass" style="width: 193px"></label>
		<br> <br> <label><input type="submit" value="Login">
		</label>
	</form>
</div>
Don`t have an account yet? Please register
<a href="registerPage">here</a>
<br>

<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>