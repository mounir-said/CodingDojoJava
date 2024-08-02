<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title>Create Ninja</title>
</head>
<body style="margin:10vw;">
    <h1>Create a Ninja</h1>
    <form:form action="${pageContext.request.contextPath}/ninjas" method="post" modelAttribute="ninja">
        <div class="form-group">
            <form:label path="firstName">First Name:</form:label>
            <form:errors path="firstName" class="text-danger"/>
            <form:input path="firstName" class="form-control" style="width:250px;"/>
        </div>
        <div class="form-group">
            <form:label path="lastName">Last Name:</form:label>
            <form:errors path="lastName" class="text-danger"/>
            <form:input path="lastName" class="form-control" style="width:250px;"/>
        </div>
        <div class="form-group">
            <form:label path="age">Age:</form:label>
            <form:errors path="age" class="text-danger"/>
            <form:input path="age" class="form-control" style="width:250px;"/>
        </div>
        <div class="form-group">
            <form:label path="dojo.id">Dojo:</form:label>
            <form:errors path="dojo.id" class="text-danger"/>
            <form:select path="dojo.id" class="form-control" style="width:250px;">
                <form:option value="" label="Select a dojo"/>
                <form:options items="${dojos}" itemValue="id" itemLabel="name"/>
            </form:select>
        </div>
        <button type="submit" class="btn btn-primary">Create</button>
    </form:form>
</body>
</html>
