<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/style.css">
<title>Admin Dashboard</title>
</head>
<body>

<h1>Welcome, ${currentUser.firstName}</h1>
<form id="logoutForm" method="POST" action="/logout">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="submit" value="Logout!" />
</form>

<table>
    <thead> 
    	<tr>
    		<th>Name</th>
    		<th>Email</th>
    		<th>Action</th>
    	</tr>
    </thead>
    <tbody>
    	<c:forEach var="user" items="${users}">
    	<c:if test = "${!user.roles.get(0).name.contains('ROLE_SUPER_ADMIN')}">
	        <tr>
				<td>${user.firstName} ${user.lastName}</td>
				<td>${user.email}</td>
				<c:if test = "${currentUser.roles.get(0).name.contains('ROLE_SUPER_ADMIN')}">
			        <c:if test = "${user.roles.get(0).name.contains('ROLE_USER')}">
			        	<td><a href="/delete/${user.id}">Delete</a> <a href="/admin/${user.id}">Make Admin</a></td>
			    	</c:if>
			    	<c:if test = "${user.roles.get(0).name.contains('ROLE_ADMIN')}">
			        	<td><a href="/delete/${user.id}">Delete</a></td>
			    	</c:if>
			    </c:if>
			    <c:if test = "${currentUser.roles.get(0).name.contains('ROLE_ADMIN')}">        
			        <c:if test = "${user.roles.get(0).name.contains('ROLE_USER')}">
			        	<td><a href="/admin/${user.id}">Make Admin</a></td>
			    	</c:if>
			    	<c:if test = "${user.roles.get(0).name.contains('ROLE_ADMIN')}">
			        	<td>Admin</td>
			    	</c:if>
			    </c:if>
			</tr>
	    </c:if>	
	</c:forEach>
    </tbody>
</table>

</body>
</html>