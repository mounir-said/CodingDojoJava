<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><c:out value="${dorm.name}"/></title>
    <link rel="stylesheet" href="/css/style.css"> 
</head>
<body>
	<h1><c:out value="${dorm.name}"/> Students</h1>
	<p><a href="/">Dashboard</a></p>
	<table>
		<thead>
			<tr>
				<th>Student</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="student" items="${students}">
				<tr>
					<td><c:out value="${student.name}"/></td>
					<td><a href="/students/remove/${student.id}"><c:out value="Remove"/></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>