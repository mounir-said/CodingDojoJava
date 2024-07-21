<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Receipt</title>
</head>
<body>
    <h1>Purchase Receipt</h1>
    <p>Customer:<c:out value="${name}" /></p>
    <p>Item: <c:out value="${itemName}" /></p>
    <p>Price: $<c:out value="${price}" /></p>
    <p>Description: <c:out value="${description}" /></p>
    <p>Vendor: <c:out value="${vendor}" /></p>
</body>
</html>
