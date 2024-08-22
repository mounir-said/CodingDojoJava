<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/style.css">
<title>View Product</title>
</head>
<body>

<h1>${product.name}</h1>
<p><a href="/">Home</a></p>
<hr>

<h3>Categories:</h3>
<ul>
	<c:forEach var="category" items="${assignedCategories}">
		<li><c:out value="${category.name}"></c:out></li>
	</c:forEach>
</ul>
<hr>
<form action="/products/${id}" method="post">
	<h4>Add Category:</h4>				
	<select name="categoryId" id="categoryId" class="input">
	    <c:forEach var="category" items="${unassignedCategories}">
	    	<option value="${category.id}">${category.name}</option>
	    </c:forEach>
	</select>
	<input class="input" class="button" type="submit" value="Add"/>
</form>
</body>
</html>