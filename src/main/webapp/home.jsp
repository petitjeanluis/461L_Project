<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Database.*" %>

<!DOCTYPE html>

<%UserService userService = UserServiceFactory.getUserService();%>

<html>
    <head>
        <title>Welcome</title>
        <link rel="stylesheet" href="style/style2.css" type="text/css">
    </head>
    <body>
        <div class="heading">
            <span class="title1">Simply Stronger</span>
            <span class="title2">the new you.</span>
            <a style= "text-decoration:none;" href= "<%= userService.createLoginURL(request.getRequestURI()) %>">
	            <button class="register">
	                Log in to the future.
	            </button>
            </a>
        </div>
    </body>
</html>