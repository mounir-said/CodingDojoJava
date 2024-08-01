<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title>Edit Expense</title>
</head>
<body style="margin:10vw;">
    <h1>Edit Expense</h1>
    <form:form action="/expenses/edit" method="post" modelAttribute="expense">
        <div>
            <form:label path="name">Expense Name:</form:label><br>
            <form:errors path="name" class="text-danger"/>
            <form:input path="name" style="width:250px;"/>
        </div>
        <div>
            <form:label path="vendor">Vendor Name:</form:label><br>
            <form:errors path="vendor" class="text-danger"/>
            <form:input path="vendor" style="width:250px;"/>
        </div>
        <div>
            <form:label path="amount">Amount:</form:label><br>
            <form:errors path="amount" class="text-danger"/>
            <form:input path="amount" style="width:250px;"/>
        </div>
        <div>
            <form:label path="description">Description:</form:label><br>
            <form:errors path="description" class="text-danger"/>
            <form:textarea path="description" rows="3" style="width:250px;"/>
        </div>
        <div>
            <input type="submit" value="Update"/>
        </div>
    </form:form>
</body>
</html>
