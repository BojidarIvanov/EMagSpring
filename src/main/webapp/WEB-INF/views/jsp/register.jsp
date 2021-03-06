<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
}
</style>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/css/styless.css"
	type="text/css"></link>

<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<jsp:include page="header.jsp"></jsp:include>


<c:if test="${ requestScope.error != null }">
	<h1 style="color: red">Sorry, registration unsuccessful. Reason:
		${requestScope.error}</h1>
</c:if>
<c:if test="${ requestScope.user == null }">
	<h1>Welcome, please register an account</h1>
<h2 style="color: brown">Please provide valid name. It cannot be changed after registration.</h2>

	<form action="${pageContext.request.contextPath}/register" method="post">
		<fieldset>
			<legend>Personal information:</legend>
			<label><span>Full name </span> <input type="text" name="name" placeholder = "real name please"
				required minlength="2" maxlength="50"></label> <br> <label><span>Email</span> <input
				type="text" name="email" required minlength="4" maxlength="50" > </label><br> <label><span>Year
					of birth</span> <input type="number" name="DOB" min= "1900" max= "2017" minlength="4" maxlength="4" required placeholder = "only year four digits"> <br></label> <br>
			<label><span>Phone</span><input type="number" name="phone" minlength="5" maxlenght="20" required> 
			</label><br> <label><span>Address</span><input type="text"
				name="address" required  minlength="3" maxlength="50"> </label><br> <label><span>Password</span><input
				type="password" name="pass" required  minlength="8" maxlength="50"> </label><br> <label><span>Confirm
					password</span><input type="password" name="pass2" required minlength="8" maxlength="50"> </label><br>
			<input type="submit" value="Register"><br>
		</fieldset>
	</form>
</c:if>
Already have an account? Please login:
<a href="loginPage">here</a>
<br>
<!-- <a href="index" title="Go to Home Page">Home</a>   -->
<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>