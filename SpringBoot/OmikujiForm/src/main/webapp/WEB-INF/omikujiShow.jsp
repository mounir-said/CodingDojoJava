<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Omikuji Fortune</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>
    <h1>Your Fortune</h1>
    <p>Hello, ${name}!</p>
    <p>Your fortune message is: ${message}</p>
    <a href="/omikuji">Back to Omikuji Form</a>
</body>
</html>
