<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<script type="text/javascript">
	
		function postComment() {
			var request = new XMLHttpRequest();
			request.onreadystatechange = function() {
				//when response is received
				if (this.readyState == 4 && this.status == 200) {
					
					var commenttxt = document.getElementById("commentdesc").value;
					var table = document.getElementById("commentstable");

					// Create an empty <tr> element and add it to the 1st position of the table:
					var row = table.insertRow(0);//<tr></tr>

					// Insert new cells (<td> elements) at the 1st and 2nd position of the "new" <tr> element:
					var cell1 = row.insertCell(0);//<td></td>

					// Add some text to the new cells:
					cell1.innerHTML = commenttxt;
					//append first child to table of comments with the value
				}
				else
				if (this.readyState == 4 && this.status == 401) {
					alert("Sorry, you must log in to post a comment");
				}
					
			}
			request.open("put", "comment/save", true);
			request.send();
		}
	
		function handleLike(){
			var button = document.getElementById("likebutton");
			var title = button.innerHTML;
			if(title == "Like"){
				likeVideo();
			}
			else{
				unlikeVideo();
			}
		}
	
		function likeVideo() {
			var request = new XMLHttpRequest();
			request.onreadystatechange = function() {
				//when response is received
				if (this.readyState == 4 && this.status == 200) {
					var button = document.getElementById("likebutton");
					button.innerHTML = "Unlike";
					button.style.background='red';
				}
				else
				if (this.readyState == 4 && this.status == 401) {
					alert("Sorry, you must log in to like this video!");
				}
					
			}
			request.open("post", "like", true);
			request.send();
		}
		
		function unlikeVideo() {
			var request = new XMLHttpRequest();
			request.onreadystatechange = function() {
				//when response is received
				if (this.readyState == 4 && this.status == 200) {
					var button = document.getElementById("likebutton");
					button.innerHTML = "Like";
					button.style.background='green';
				}
				else
					if (this.readyState == 4 && this.status == 401) {
						alert("Sorry, you must log in to like this video!");
					}
			}
			request.open("post", "unlike", true);
			request.send();
		}
	</script>
</head>
<body>

<video width="400" controls loop autoplay>
  <source src="http://techslides.com/demos/sample-videos/small.mp4" type="video/mp4">
  <source src="mov_bbb.ogg" type="video/ogg">
  Your browser does not support HTML5 video.
</video>
<br>
<button style="background-color: green" id="likebutton" onclick="handleLike()">Like</button>
<br><br>
Post your comment<br><br>
<textarea id="commentdesc" rows="3" ></textarea><br>
<button onclick="postComment()">Submit Comment</button>
<table border="1" id="commentstable">
	<tr>
		<td>Ebasi qkiq klip</td>
	</tr>
	<tr>
		<td>Mnogo silno demo</td>
	</tr>
	<tr>
		<td>Napravo mi otvori ochite!</td>
	</tr>
</table>
</body>
</html>