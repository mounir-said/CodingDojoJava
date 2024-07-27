<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.codingdojo.FruityLoops.models.Item" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Fruit Store</title>
    <style>
        .fruit-name {
            color: black;
        }
        .fruit-name.g {
            color: orange;
        }
    </style>
</head>
<body>
    <h1>Fruit Store</h1>
    <ul>
        <c:forEach var="fruit" items="${fruits}">
            <li>
                <span class="fruit-name ${fruit.name.startsWith('G') ? 'g' : ''}">${fruit.name}</span>: ${fruit.price}
            </li>
        </c:forEach>
    </ul>
</body>
</html>
