<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title>Expense Details</title>
</head>
<body style="margin:10vw;">
    <h1>Expense Details</h1>
    <table class="table table-striped table-bordered">
        <tbody>
            <tr>
                <th>Name:</th>
                <td><c:out value="${expense.name}"/></td>
            </tr>
            <tr>
                <th>Vendor:</th>
                <td><c:out value="${expense.vendor}"/></td>
            </tr>
            <tr>
                <th>Amount:</th>
                <td><c:out value="${expense.amount}"/></td>
            </tr>
            <tr>
                <th>Description:</th>
                <td><c:out value="${expense.description}"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <a href="${pageContext.request.contextPath}/expenses/edit/${expense.id}" class="btn btn-primary">Edit</a>
                    <a href="${pageContext.request.contextPath}/expenses/delete/${expense.id}" class="btn btn-danger">Delete</a>
                </td>
            </tr>
        </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/expenses" class="btn btn-secondary">Back to List</a>
</body>
</html>
