<%@ page language = "java" contentType = "text/html; charset = UTF-8" pageEncoding = "UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>	
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>					  
<html>
  <head>
    <title>Order Confirmation</title>
    <link rel = "stylesheet" href = "style.css" type = "text/css"></link>
  </head>
  <body>
    <center><h3>Order Confirmation</h3></center>
    <div align="center">
      <form action = "placeOrder" method = "post">
	<table class = "products" border = "1" cellpadding = "5">
          <tr>
	    <th>Qty</th>
	    <th>Id</th>
            <th>Product</th>
            <th>Category</th>
	    <th>Price</th>
          </tr>
	  <c:set var = 'ind' value = '1'/>
          <c:forEach var = "item" items = "${items}"> 
            <tr>
	      <td><input type = "number"   name ="num-${ind}" min ="0" max ="99" step ="1"
			  value="${item.qty}" size ="3"/></td>
              <td><input type = "text"     name = "id-${ind}"
			  value = "${item.id}" size = "4" readonly/></td>
              <td><input style = "text-align:center;" name = "prod-${ind}" type = "text"
			  value = "${item.name}" readonly/></td>
              <td><input style = "text-align:center;" name = "cat-${ind}" type = "text"
			  value = "${item.available_products}" readonly/></td>
	      <td><input type = "text"                name = "price-${ind}"
			  value = "${item.price}" size = "7" readonly/></td>
            </tr>
	    <c:set var = 'ind' value = '${ind + 1}'/>
          </c:forEach>
	</table>
	<input type	 = "hidden" value = "${items.size()}" name = "rowCount"/>
	<p>
	  <div class = "total">
	    <span>Total:  </span><fmt:formatNumber type = 'currency' value = "${total}"/>
	  </div>
	  <p><input type = "submit" value = " Confirm order "/></p>
      </form>
    </div>
  </body>
</html>
