<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2/14/2018
  Time: 12:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login page</title>
    <link href="Style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="container">
    <div id="header">

        <form action="LoginServlet" method="post">
            <input type="text" placeholder="Username..." name="usernameForm">
            <input type="password" placeholder="Password..." name="passForm">
            <input type="submit" value="Login" name="Login">
        </form>

        Don't have an account ?

        <form action="RegisterPage.jsp" method="get">
            <input type="submit" value="Register a new account">
        </form>
    </div>

</div>
</body>
</html>
