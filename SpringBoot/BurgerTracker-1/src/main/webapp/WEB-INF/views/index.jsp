<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Burger Tracker</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Burger Tracker</h1>
    <table>
        <thead>
            <tr>
                <th>Burger Name</th>
                <th>Restaurant</th>
                <th>Rating (out of 5)</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="burger" items="${burgers}">
                <tr>
                    <td>${burger.name}</td>
                    <td>${burger.restaurant}</td>
                    <td>${burger.rating}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <h2>Add a Burger:</h2>
    <form:form action="/burgers" method="post" modelAttribute="burger">
        <form:errors path="*" cssClass="error" element="div"/>
        <div>
            <form:label path="name">Burger Name:</form:label>
            <form:input path="name" />
            <form:errors path="name" cssClass="error"/>
        </div>
        <div>
            <form:label path="restaurant">Restaurant:</form:label>
            <form:input path="restaurant" />
            <form:errors path="restaurant" cssClass="error"/>
        </div>
        <div>
            <form:label path="rating">Rating:</form:label>
            <form:input path="rating" />
            <form:errors path="rating" cssClass="error"/>
        </div>
        <div>
            <form:label path="notes">Notes:</form:label>
            <form:textarea path="notes" />
            <form:errors path="notes" cssClass="error"/>
        </div>
        <div>
            <button type="submit">Submit</button>
        </div>
    </form:form>
</body>
</html>
