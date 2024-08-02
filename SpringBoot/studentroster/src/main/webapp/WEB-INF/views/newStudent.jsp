<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>New Student</title>
    <link rel="stylesheet" href="/css/style.css"> 
</head>
<body>
	<h1>New Student</h1>
	<p><a href="/">Dashboard</a></p>
	<form:form action="/students/new" modelAttribute="student" method="post">
		<div>
			<form:errors path="name"/>
			<form:label path="name" for="name">Name:</form:label>
			<form:input path="name" type="text"/>
		</div>
		<div>
			<form:select path="dorm" class="input">
			    <c:forEach var="dorm" items="${dorms}">
			    	<option value="${dorm.id}">${dorm.name}</option>
			    </c:forEach>
			</form:select>
		</div>
		<div>
			<input value="Add" type="submit"/>
		</div>
	</form:form>
</body>
</html>