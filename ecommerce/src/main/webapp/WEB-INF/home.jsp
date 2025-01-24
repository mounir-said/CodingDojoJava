<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/style.css">
<title>Dashboard</title>
</head>
<body>

<p>Welcome, ${user.firstName}</p>
<form id="logoutForm" method="POST" action="/logout">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="submit" value="Logout!" />
</form>

<p>First Name: ${user.firstName}</p>
<p>Last Name: ${user.lastName}</p>
<p>Email: ${user.email}</p>
<p>Signup Date: <fmt:formatDate pattern = "MMMM dd, y" value = "${user.createdAt}" /></p>
<p>Last Sign in: <fmt:formatDate pattern = "MMMM dd, y" value = "${user.lastLogin}" /></p>

</body>
</html>