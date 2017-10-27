<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<title>Insert title here</title>

</head>
<body>
	<div class="footer">
		<div class="content">
			<div class="container">
				<div class="login-page">
					<div class="dreamcrub">
						<ul class="breadcrumbs">
							<li class="home"><a href="index" title="Go to Home Page">Home</a>&nbsp;
								<span>&gt;</span></li>
							<li class="women"><a href="loginPage" title="Why not login?">Login</a>&nbsp;
								</form></li>
						</ul>
						<ul class="previous">
							<li><a href="index">Back to Home Page</a></li>
						</ul>
						<div class="clearfix"></div>
					</div>
					<div class="account_grid">
						<div class="col-md-6 login-left wow fadeInLeft"
							data-wow-delay="0.4s">
							<h2>NEW CUSTOMERS</h2>
							<p>
								By creating an account with our store, you will be able to move
								through the checkout process faster, <br> view and track
								your orders in your account and more.
							</p>
							<a class="acount-btn" href="registerPage">Create an Account</a>
						</div>
						<div class="col-md-6 login-right wow fadeInRight"
							data-wow-delay="0.4s">
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

							<h2>Password Recovery</h2>
							</style>
							<p>Please Enter the email address associated to your account.</p>
							<p>${message}</p>
							<div class="container">
								<form action="send" method="post">
									<div>
										<span><strong>Email Address:</strong></span> <input type="text"
											name="email" placeholder="Enter email" size="35"
											style="width: 193px"> <input type="submit"
											value="Send" style="height:45px; width: 150px" />
									</div>


								</form>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
		</div>
</body>
</html>