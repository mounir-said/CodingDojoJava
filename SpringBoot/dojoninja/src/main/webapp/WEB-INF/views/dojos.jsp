<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title>Dojo List</title>
</head>
<body style="margin:10vw;">
    <h1>All Dojos</h1>
    <ul class="list-group">
        <c:forEach var="dojo" items="${dojos}">
            <li class="list-group-item">
                <a href="${pageContext.request.contextPath}/dojos/${dojo.id}" class="btn btn-link">${dojo.name}</a>
            </li>
        </c:forEach>
    </ul>
</body>
</html>
