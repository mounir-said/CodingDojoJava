<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title>Create Dojo</title>
</head>
<body style="margin:10vw;">
    <h1>Create a Dojo</h1>
    <form:form action="${pageContext.request.contextPath}/dojos" method="post" modelAttribute="dojo">
        <div class="form-group">
            <form:label path="name">Name:</form:label>
            <form:errors path="name" class="text-danger"/>
            <form:input path="name" class="form-control" style="width:250px;"/>
        </div>
        <button type="submit" class="btn btn-primary">Create</button>
    </form:form>
</body>
</html>
