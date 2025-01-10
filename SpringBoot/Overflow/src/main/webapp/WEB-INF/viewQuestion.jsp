<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/style.css">
<title>Question</title>
</head>
<body>

<h1>${question.text}</h1>
<p><a href="/">Dashboard</a></p>
<h3 class="in-line">
Tags:
<c:forEach var="tag" items="${question.tags}">
	<button type="button"><c:out value="${tag.subject}"></c:out></button>
</c:forEach>
</h3>

<div class="answerContainer">
	<div class="flex">
		<h3>Answers:</h3>
		<c:forEach var="answer" items="${question.answers}">
			<ul>
				<li><c:out value="${answer.text}"></c:out></li>
			</ul>	
		</c:forEach>
	</div>
	
	<div class="flex">
		<form:form action="/questions/${question.id}/answers/new" modelAttribute="answer" class="form" method="post">
		
			<div>
			 	<form:errors path="text" class="error"/>
				<form:label for="text" path="text">Answer:</form:label>
				<form:textarea rows="4" path="text"/>
			</div>
			
			<div class="form-row">
				<input type="submit" value="Answer it!"/>
			</div>
			
		</form:form>
	</div>
</div>

</body>
</html>