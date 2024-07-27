<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ninja Gold Game</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>
    <h1>Ninja Gold Game</h1>
    <p>You have ${gold} gold</p>

    <form action="/process" method="post">
        <button type="submit" name="place" value="farm">Visit Farm</button>
        <button type="submit" name="place" value="cave">Explore Cave</button>
        <button type="submit" name="place" value="house">Visit House</button>
        <button type="submit" name="place" value="quest">Go on Quest</button>
        <button type="submit" name="place" value="spa">Visit Spa</button>
    </form>

    <form action="/reset" method="post">
        <button type="submit">Reset Game</button>
    </form>

    <h2>Activity Log</h2>
    <ul>
        <c:forEach var="activity" items="${activities}">
            <li><c:out value="${activity}"/></li>
        </c:forEach>
    </ul>
</body>
</html>

