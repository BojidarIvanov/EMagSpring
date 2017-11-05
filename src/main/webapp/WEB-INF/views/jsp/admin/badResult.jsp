<!DOCTYPE html>					  
<html>
<head>
<title>Emag</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/css/styless.css"
	type="text/css"></link></head>
<body>
	<jsp:include page="../header.jsp"></jsp:include>

  <div class  = "badResult">
    <center>${result}</center>

    <center><a href = "${pageContext.request.contextPath}/admin/productManagement">Back</a></center>                

  </div>
</body>
</html>