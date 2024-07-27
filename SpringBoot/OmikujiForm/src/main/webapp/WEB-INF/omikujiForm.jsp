<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Omikuji Form</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>
    <h1>Omikuji Fortune</h1>
    <form action="/omikuji" method="post">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required><br>
        <label for="message">Fortune Message:</label>
        <input type="text" id="message" name="message" required><br>
        <button type="submit">Send</button>
    </form>
</body>
</html>
