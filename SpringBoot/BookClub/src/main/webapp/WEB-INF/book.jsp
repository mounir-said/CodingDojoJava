<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/main.css"> <!-- change to match your file/naming structure -->
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>

	<div class="container">
		<h1><c:out value="${book.title}"/></h1>
		
		<h3>Books from everyone shelves:</h3>
		<a href="/home">back to shelves</a>
		
		<div class="container">
			<h4>Here are <c:out value="${book.user.userName}"/>'s thoughts:</h4>
			<hr />
			
			<p><c:out value="${book.thoughts}"/></p>
			<hr />
		
		</div>
		
	</div>

</body>
</html>