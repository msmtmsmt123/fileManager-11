<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.Reader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.util.Properties" %>
<%@ page import="FileServlets.UploadServlet" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2/14/2018
  Time: 12:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Home page</title>
    <link href="Style.css" rel="stylesheet" type="text/css">


</head>
<body>
<div id="container">

    <div id="header">

        <H3>Hello <%=session.getAttribute("user'sName")%>
        </H3>
        Welcome EGS training's webpage

        <form action="LogoutServlet" method="post">
            <input type="submit" value="Logout" name="Logout">
        </form>

    </div>

    <div id="content">

        <%
            String folderPath = UploadServlet.getPath() + session.getAttribute("username");
            File folder = new File(folderPath);
            String[] fileNames = folder.list();
        %>

        <form action="DownloadZipServlet" method="get">

            <table style="width:70%">

                <% if (fileNames!=null && fileNames.length>0){
                    for (String name : fileNames) { %>

                <tr>
                    <td><input type="checkbox" name="selectedFiles" value="<%=name%>"></td>
                    <td><%=name%></td>
                    <td><a href="DownloadServlet?fname=<%=name%>"> download </a></td>
                    <td><a href="DeleteServlet?fname=<%=name%>"> delete </a></td>
                </tr>

                <% } } %>

            </table>

            <input type="submit" value="Zip and download">

        </form>

        <form method="POST" action="UploadServlet" enctype="multipart/form-data" id="uploadForm">
            <input type="file" name="file" id="file"/>
            <input type="submit" value="Upload" name="upload" id="upload"/>
        </form>

    </div>

</div>

</body>
</html>
