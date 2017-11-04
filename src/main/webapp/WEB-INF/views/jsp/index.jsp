
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Emag Final Project</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">

<link rel='stylesheet prefetch'
	href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.min.css'>
<link rel='stylesheet prefetch'
	href='https://fonts.googleapis.com/css?family=Signika:700,400'
	rel='stylesheet' type='text/css'>

  <link rel="stylesheet" href="css/css/style.css">   
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

</head>

<body>
	<jsp:include page="header.jsp"></jsp:include>
	


	<h3 align="center"; >Take advantage of hot offers, the prices were
		never so attractive !</h3>


	<div class="wrap">
		<ul class="grid grid-sm-2 grid-lg-4">
			<li>
				<div class="cat-item">
					<div class="item-header">
						<a href="${pageContext.request.contextPath}/admin/show?id=1"
							class="item-img"> <img src="img/Cisco.jpg" alt="" />
						</a>
						<!-- <a href="" class="item-img-zoom btn btn-secondary btn-circle"
								title="Zoom In"> <span class="fa fa-search-plus"></span>
							</a> -->
					</div>
					<div class="item-body">
						<h4 class="item-title">Sony Headphones</h4>
						<div class="item-size">24 Pack</div>
						<div class="item-rating rating">
							<ul class="item-stars rating-stars">
								<li><span class="fa fa-star"></span></li>
								<li><span class="fa fa-star"></span></li>
								<li><span class="fa fa-star"></span></li>
								<li><span class="fa fa-star-half-o"></span></li>
								<li><span class="fa fa-star-o"></span></li>
							</ul>
							<span class="item-rating-no rating-no small">300</span>
						</div>
						<div class="item-price h4">30 лв.</div>
					</div>
					<a href=${pageContext.request.contextPath}/loginPage
						" class="btn btn-primary btn-expanded">Добави в количка</a>
				</div>
			</li>
			<li>
				<div class="cat-item">
					<div class="item-header">
						<a
							href="${pageContext.request.contextPath}/admin/show?id=2"
							class="item-img"> <img src="img/Dell.jpg" alt="" />
						</a>
						<!-- <a href="" class="item-img-zoom btn btn-secondary btn-circle"
								title="Zoom In"> <span class="fa fa-search-plus"></span> 
							</a> -->
					</div>
					<div class="item-body">
						<h4 class="item-title">Dell laptop</h4>
						<div class="item-size">24 Pack</div>
						<div class="item-rating rating">
							<ul class="item-stars rating-stars">
								<li><span class="fa fa-star"></span></li>
								<li><span class="fa fa-star"></span></li>
								<li><span class="fa fa-star"></span></li>
								<li><span class="fa fa-star-half-o"></span></li>
								<li><span class="fa fa-star-o"></span></li>
							</ul>
							<span class="item-rating-no rating-no small">300</span>
						</div>
						<div class="item-price h4">$900 лв.</div>
					</div>
						<a href=${pageContext.request.contextPath}/loginPage
						" class="btn btn-primary btn-expanded">Добави в количка</a>
				</div>
			</li>
			<li>
				<div class="cat-item">
					<div class="item-header">
						<a
							href="${pageContext.request.contextPath}/admin/show?id=3"
							class="item-img"> <img src="img/Logitech1.jpg" alt="" />
						</a>
						<!--	<a href="" class="item-img-zoom btn btn-secondary btn-circle"
										title="Zoom In"> <span class="fa fa-search-plus"></span>
									</a> -->
					</div>
					<div class="item-body">
						<h4 class="item-title">Logitech PC mouse</h4>
						<div class="item-size">24 Pack</div>
						<div class="item-rating rating">
							<ul class="item-stars rating-stars">
								<li><span class="fa fa-star"></span></li>
								<li><span class="fa fa-star"></span></li>
								<li><span class="fa fa-star"></span></li>
								<li><span class="fa fa-star-half-o"></span></li>
								<li><span class="fa fa-star-o"></span></li>
							</ul>
							<span class="item-rating-no rating-no small">300</span>
						</div>
						<div class="item-price h4">20 лв.</div>
					</div>
					<a href=${pageContext.request.contextPath}/loginPage
						" class="btn btn-primary btn-expanded">Добави в количка</a>
				</div>
			</li>
			<li>
				<div class="cat-item">
					<div class="item-header">
						<a
							href="${pageContext.request.contextPath}/admin/show?id=4"
							class="item-img"> <img src="img/Elysee.jpg" alt="" />
						</a>
						<!-- <a href="" class="item-img-zoom btn btn-secondary btn-circle"
								title="Zoom In"> <span class="fa fa-search-plus"></span>
							</a> -->
					</div>
					<div class="item-body">
						<h4 class="item-title">Original Casio Watch</h4>
						<div class="item-size">24 Pack</div>
						<div class="item-rating rating">
							<ul class="item-stars rating-stars">
								<li><span class="fa fa-star"></span></li>
								<li><span class="fa fa-star"></span></li>
								<li><span class="fa fa-star"></span></li>
								<li><span class="fa fa-star-half-o"></span></li>
								<li><span class="fa fa-star-o"></span></li>
							</ul>
							<span class="item-rating-no rating-no small">300</span>
						</div>
						<div class="item-price h4">100 лв.</div>
					</div>
					<a href=${pageContext.request.contextPath}/loginPage
						" class="btn btn-primary btn-expanded">Добави в количка</a>
				</div>
			</li>
			<li>
				<div class="cat-item">
					<div class="item-header">
						<a
							href="${pageContext.request.contextPath}/admin/show?id=5"
							class="item-img"> <img src="img/Hermes.jpg" alt="" />
						</a>
						<!-- <a href="" class="item-img-zoom btn btn-secondary btn-circle"
								title="Zoom In"> <span class="fa fa-search-plus"></span>
							</a> -->
					</div>
					<div class="item-body">
						<h4 class="item-title">Hermes Watch</h4>
						<div class="item-size">24 Pack</div>
						<div class="item-rating rating">
							<ul class="item-stars rating-stars">
								<li><span class="fa fa-star"></span></li>
								<li><span class="fa fa-star"></span></li>
								<li><span class="fa fa-star"></span></li>
								<li><span class="fa fa-star-half-o"></span></li>
								<li><span class="fa fa-star-o"></span></li>
							</ul>
							<span class="item-rating-no rating-no small">300</span>
						</div>
						<div class="item-price h4">890 лв.</div>
					</div>
					<a href=${pageContext.request.contextPath}/loginPage
						" class="btn btn-primary btn-expanded">Добави в количка</a>
				</div>
			</li>
			<li>
				<div class="cat-item">
					<div class="item-header">
						<a
							href="${pageContext.request.contextPath}/admin/show?id=6"
							class="item-img"> <img src="img/Swatch.jpg" alt="" />
						</a>
						<!-- <a href="" class="item-img-zoom btn btn-secondary btn-circle"
										title="Zoom In"> <span class="fa fa-search-plus"></span>
									</a> -->
					</div>
					<div class="item-body">
						<h4 class="item-title">Swatch Watch</h4>
						<div class="item-size">24 Pack</div>
						<div class="item-rating rating">
							<ul class="item-stars rating-stars">
								<li><span class="fa fa-star"></span></li>
								<li><span class="fa fa-star"></span></li>
								<li><span class="fa fa-star"></span></li>
								<li><span class="fa fa-star-half-o"></span></li>
								<li><span class="fa fa-star-o"></span></li>
							</ul>
							<span class="item-rating-no rating-no small">300</span>
						</div>
						<div class="item-price h4">500 лвю</div>
					</div>
					<a href=${pageContext.request.contextPath}/loginPage
						" class="btn btn-primary btn-expanded">Добави в количка</a>
				</div>
			</li>
			<li>
				<div class="cat-item">
					<div class="item-header">
						<a
							href="${pageContext.request.contextPath}/admin/show?id=7"
							class="item-img"> <img src="img/Casio.jpg" alt="" />
						</a>
						<!-- <a href="" class="item-img-zoom btn btn-secondary btn-circle"
										title="Zoom In"> <span class="fa fa-search-plus"></span>
									</a> -->
					</div>
					<div class="item-body">
						<h4 class="item-title">Casio Watch</h4>
						<div class="item-size">24 Pack</div>
						<div class="item-rating rating">
							<ul class="item-stars rating-stars">
								<li><span class="fa fa-star"></span></li>
								<li><span class="fa fa-star"></span></li>
								<li><span class="fa fa-star"></span></li>
								<li><span class="fa fa-star-half-o"></span></li>
								<li><span class="fa fa-star-o"></span></li>
							</ul>
							<span class="item-rating-no rating-no small">300</span>
						</div>
						<div class="item-price h4">250 лв.</div>
					</div>
					<a href=${pageContext.request.contextPath}/loginPage
						" class="btn btn-primary btn-expanded">Добави в количка</a>
				</div>
			</li>

			<li>
				<div class="cat-item">
					<div class="item-header">
						<a
							href="${pageContext.request.contextPath}/admin/show?id=8"
							class="item-img"> <img src="img/Citizen.jpg" alt="" />
						</a>
						<!-- <a href="" class="item-img-zoom btn btn-secondary btn-circle"
										title="Zoom In"> <span class="fa fa-search-plus"></span>
									</a> -->
					</div>
					<div class="item-body">
						<h4 class="item-title">Citizen Watch</h4>
						<div class="item-size">24 Pack</div>
						<div class="item-rating rating">
							<ul class="item-stars rating-stars">
								<li><span class="fa fa-star"></span></li>
								<li><span class="fa fa-star"></span></li>
								<li><span class="fa fa-star"></span></li>
								<li><span class="fa fa-star-half-o"></span></li>
								<li><span class="fa fa-star-o"></span></li>
							</ul>
							<span class="item-rating-no rating-no small">300</span>
						</div>
						<div class="item-price h4">200 лв.</div>
					</div>
					<a href="${pageContext.request.contextPath}/loginPage"
						 class="btn btn-primary btn-expanded">Добави в количка</a>
				</div>
			</li>
		</ul>

		<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>