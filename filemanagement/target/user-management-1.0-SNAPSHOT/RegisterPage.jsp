<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2/14/2018
  Time: 3:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Registration</title>
    <link href="Style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="container">
    <div id="content">

        <b>Registration is free</b> <br>
        <form action="RegisterServlet" method="post">
            <input type="text" name="nameRegPage" placeholder="Name..."> <br>
            <input type="text" name="emailRegPage" placeholder="Email..."> <br>
            <input type="text" name="usernameRegPage" placeholder="Login..."> <br>
            <input type="password" name="passRegPage" placeholder="Password..."> <br>
            <input type="submit" value="Register">
        </form>

        <br>
        <a href="LoginPage.jsp"> Go to Login page </a>
    </div>
</div>

</body>
</html>
