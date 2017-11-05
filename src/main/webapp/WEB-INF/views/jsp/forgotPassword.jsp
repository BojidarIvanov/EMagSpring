<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/css/styless.css"
	type="text/css"></link>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Forgot password</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>

	<div class="container">
		<div class="row">
			<c:if test="${sessionScope.user == null }">
				<h1 class="lead">
					Please enter your Email <br>
				</h1>

				<form action="${pageContext.request.contextPath}/forgotPassword" method="POST">
					<div>
						<h4 style="color: black;">
							Email: <input id="input2" type="text"
								placeholder="enter your email" name="email"
								style="height: 30px; width: 300px" required="required">
						</h4>
					</div>
					<input class="btn" type="submit" value="Submit"></br>
				</form>
			</c:if>
			<c:if test="${sessionScope.user != null }">
				<jsp:forward page="${pageContext.request.contextPath}/index"></jsp:forward>
			</c:if>
		</div>
	</div>

	<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>