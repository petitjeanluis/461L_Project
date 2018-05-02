<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="Database.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html lang="en">
    <header>
		<jsp:include page="header.jsp"/>
		<link rel="stylesheet" href="style/social_style.css">
        <script src="js/social.js"></script>
	</header>
    <body>
    	<nav class="navbar navbar-inverse">
          <div class="container-fluid">
            <div class="navbar-header">
              <a class="navbar-brand" href="index.html">WorkoutMaker</a>
            </div>
            <ul class="nav navbar-nav">
              <li><a href="index.html">Home</a></li>
              <li><a href="workout_list.html">Your Workouts</a></li>
              <li><a href="workout_build.html">Build Workout</a></li>
              <li class="active"><a href="#">Social</a></li>
            </ul>
            <div class="nav navbar-nav navbar-right">
                <button class="btn navbar-btn login-btn">Login</button>
            </div>
          </div>
        </nav>
        <div class="container">
            <div class="row">
                <div class="col-xs-6">
                    <h2>Followers</h2>
                    <div class="box">
                        <ul id="friend-list">
                            <li>Name 1</li>
                            <li>Name 2</li>
                            <li>Name 3</li>
                        </ul>
                    </div>
                </div>
                <div class="col-xs-6">
                    <h2>Start Following</h2>
                    <div class="box">
                        <ul id="follow-list">
                            <li id="user0">Name 0</li>
                            <li id="user1">Name 1</li>
                            <li id="user2">Name 2</li>
                            <li id="user3">Name 3</li>
                            <li id="user4">Name 4</li>
                            <li id="user5">Name 5</li>
                            <li id="user6">Name 6</li>
                            <li id="user7">Name 7</li>
                            <li id="user8">Name 8</li>
                        </ul>
                    </div>
                    <input type="text" id="follow-search">
                    <button type="button" name="add-follower-btn" onclick="addFollower()">Add</button>
                </div>
            </div>
            <div class="row">
                <h2>Followers' Workouts</h2>
                <table>
                    <thead>
                        <th>User</th>
                        <th>Workout Name</th>
                    </thead>
                    <tbody id="workout-table-body">
                        <tr id="row0">
                            <td>A name</td>
                            <td>A workout name</td>
                        </tr>
                        <tr id="row1">
                            <td>A name</td>
                            <td>A workout name</td>
                        </tr>
                    </tbody>
                </table>
                <button type="button" name="add-workout-btn" onclick="sendWorkout()">Add Workout</button>
            </div>
        </div>
    </body>
</html>
