<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>


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

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<jsp:include page="header.jsp"></jsp:include>
<c:if test="${ user == null }">
	<c:redirect url="loginPage"></c:redirect>
</c:if>

<c:if test="${ user != null }">
	<h1>You are free to change some of your registration details:</h1>
	
<h1 style="color: red">${error}</h1>

	<form action="${pageContext.request.contextPath}/register"
		method="post">
		<fieldset>
			<legend>Personal information:</legend>
			<label><span>Full name </span> <input type="text" name="name"
				value="${user.name}" required readonly></label> <br> <label>
				<span>Email</span> <input type="text" name="email" required
				value="${user.email}">
			</label><br> <label><span>Year of birth</span>
			
			
			   <c:set var = "string1" value = "${user.dateOfBirth}"/>
      <c:set var = "string2" value = "${fn:substring(string1, 0, 4)}" />
			
			 <input
				type="text" name="DOB" min="1900" max="2017" value = "${string2}" required>
				<br></label> <br> <label><span>Phone</span><input
				type="text" name="phone" required value="${user.phone}"> </label><br>
			<label><span>Address</span> <input type="text" name="address"
				required value="${user.address}"> </label><br> <label><input
				type="hidden" value="${user.email}" name="oldEmail" /></label> <br> <input
				type="submit" value="Update info"><br>
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