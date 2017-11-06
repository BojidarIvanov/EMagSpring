<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>Insert title here</title>
</head>
<body>

	<c:if test="${ sessionScope.user != null }">
		<form action="${pageContext.request.contextPath}/saveReview"
			method="POST">
			<h3>Please provide rating for the product:</h3>
			<input type="number" placeholder=" 0 to 10" value="0" required
				min="0" max="10" name="rating" style="height: 30px; width: 100px">

			<div class="contact-right">
				<textarea id="input3" onkeyup="auto_grow(this)" name="message"
					rows="5" cols="15" placeholder="Post a review" maxlength="400"
					required></textarea>
			</div>
			<input type="hidden" value="${param.id}" name="productId" /> <input
				type="submit" value="SUBMIT">
		</form>
	</c:if>
	<table border="1" id="commentstable" align="center">
		<c:set var="avgRating" value="${0}" />

		<c:forEach var="r" items="${reviews[param.id]}">
			<c:set var="avgRating" value="${avgRating + r.reviewingRating}" />
		</c:forEach>

		<h3 style="color: red; text-align: center"> ${ param.error } </h3>
		<c:set var="hashSet" value="${reviews[param.id]}" />
		<c:set var="avgRating" value="${avgRating / hashSet.size() }" />
		<c:if test="${avgRating != 'NaN'}">
			<p>
			 <h3 align="center">Average rating:
			<fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${avgRating}"/> 
			</h3>
			</p>
		</c:if>
		
		<c:if test="${avgRating == 'NaN'}">
				<h3 align="center">No reviews yet, please be the first to review and rate the
					product.</h3>
			</c:if>
		<tr>
			<th>User id</th>
			<th>Review body</th>
			<th>Rating</th>
		</tr>
		<c:forEach items="${reviews[param.id]}" var="review">
			
			<tr>
				<td align="center" ><c:out value="${review.reviewerID}" /></td>
				<td><c:out value="${review.reviewText}" /></td>
				<td align="center"><c:out value="${review.reviewingRating}" /></td>
				<c:if test="${ user.customerID == review.reviewerID || user.isAdmin }">
					<td><form
							action="${pageContext.request.contextPath}/deleteReview"
							method="post">
							<input type="hidden" value="${review.reviewID}" name="reviewId" />
							<input type="hidden" value="${param.id}" name="productId" />

							<button type="submit" name="button" value="Delete">Delete</button>
						</form>
				</c:if>
			</tr>
		</c:forEach>

		<c:if test="${ sessionScope.user == null }">
			<h4>To post a review please log in first</h4>
		</c:if>
	</table>

</body>
</html>