<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>Insert title here</title>
</head>
<body>
	
	<script type="text/javascript">
	
	function postComment() {
		var request = new XMLHttpRequest();
		request.onreadystatechange = function() {
			//when response is received
			if (this.readyState == 4 && this.status == 200) {				
				var commenttxt = document.getElementById("commentdesc").value;
				var table = document.getElementById("commentstable");
				var row = table.insertRow(0);
				var cell1 = row.insertCell(0);
				cell1.innerHTML = commenttxt;
				//append first child to table of comments with the value
			}
			else
			if (this.readyState == 4 && this.status == 401) {
				alert("Sorry, you must log in to post a comment");
			}
				
		}
		request.open("put", "comment", true);
		request.send();
	}
	</script>
	<c:if test="${ sessionScope.user != null }">
		
		<textarea id="commentdesc" rows="3" ></textarea><br>
		<button onclick="postComment()">Submit Comment</button>
		<table border="1" id="commentstable">
			<c:forEach items="${ requestScope.reviews}" var="reviewEntry">
						<td>${productEntry.key}</td>
						<td>${productEntry.value.getReviewText()}</td>
					</tr>
					</c:forEach>
	</c:if>
		<c:if test="${ sessionScope.user == null }">
			<h4>To post a review please log in first</h4>
	</c:if>
	
</table>
</body>
</html>