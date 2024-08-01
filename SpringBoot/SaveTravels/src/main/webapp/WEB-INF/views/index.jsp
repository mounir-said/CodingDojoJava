<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title>Expense Tracker</title>
</head>
<body style="margin:10vw;">
    <h1>Expense Tracker</h1>
    <table class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>Name</th>
                <th>Vendor</th>
                <th>Amount</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="expense" items="${expenses}">
                <tr>
                    <td><c:out value="${expense.name}"/></td>
                    <td><c:out value="${expense.vendor}"/></td>
                    <td><c:out value="${expense.amount}"/></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/expenses/details/${expense.id}" class="btn btn-info">View</a>
                        <a href="${pageContext.request.contextPath}/expenses/edit/${expense.id}" class="btn btn-primary">Edit</a>
                        <a href="${pageContext.request.contextPath}/expenses/delete/${expense.id}" class="btn btn-danger">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <br>
    <h2>Add new expense</h2>
    <form:form action="/expenses" method="post" modelAttribute="expense">
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
            <input type="submit" value="Submit"/>
        </div>
    </form:form>
</body>
</html>
