<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/style.css">
<title>New Question</title>
</head>
<body>

<h1>What is your question?</h1>
<p><a href="/">Dashboard</a></p>
<hr>
<p class="error">${error}</p>

<div>
	<form:form action="/questions/new" modelAttribute="question" class="form" method="post">
	
		<div>
		 	<form:errors path="text" class="error"/>
			<form:label for="text" path="text">Question:</form:label>
			<form:textarea rows="4" path="text"/>
		</div>
		
		<div>
			<label for="listOfTags">Tags:</label>
			<input class="input" name="listOfTags" id="listOfTags"/>
		</div>
		
		<div class="form-row">
			<input type="submit" value="Submit"/>
		</div>
		
	</form:form>
</div>

</body>
</html>