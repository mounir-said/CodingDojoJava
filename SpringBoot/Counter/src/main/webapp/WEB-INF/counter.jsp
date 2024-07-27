<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Counter</title>
</head>
<body>
    <h1>Counter Page</h1>
    <p>Number of visits: ${count}</p>
    <a href="/">Go to Home</a>
    <a href="/increment-by-2">Increment by 2</a>
    <a href="/reset">Reset Counter</a>
</body>
</html>
