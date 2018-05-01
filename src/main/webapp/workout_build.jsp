<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Database.*" %>
<%
UserService userService = UserServiceFactory.getUserService(); 
User user = userService.getCurrentUser();
Storage storage = Storage.getInstance();
Client c = storage.loadClient(user);
%>
<!DOCTYPE html>
<html lang="en">
	<header>
		<jsp:include page="header.jsp"/>
		<link rel="stylesheet" href="style/build_style.css">
		<script src="js/build.js"></script>
	</header>
    <body>
        <nav class="navbar navbar-inverse">
          <div class="container-fluid">
            <div class="navbar-header">
              <a class="navbar-brand" href="index.jsp">WorkoutMaker</a>
            </div>
            <ul class="nav navbar-nav">
              <li><a href="index.jsp">Home</a></li>
              <li><a href="workout_list.jsp">Your Workouts</a></li>
              <li class="active"><a href="#">Build Workout</a></li>
            </ul>
            <div class="nav navbar-nav navbar-right">  	
                <a href= "/logoutservlet">
                	<button class="btn navbar-btn">Logout</button>
                </a>
            </div>
          </div>
        </nav>
		
		<div class="container">
            <div class="row">
                <div class="col-xs-6">
                    <h1>Exercises</h1>
                </div>
                <div class="col-xs-6">
                    <h1>Workout</h1>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-6">
                    <div class="search">
                        <span class="fa fa-search"></span>
                        <input class="form-control" type="text" id="myInput" onkeyup="searchFilter()" placeholder="Search exercises...">
                    </div>
                </div>
                <div class="col-xs-6">
                    <div class="workout-name">
                        <input class="form-control" type="text" id="workoutNombre" placeholder="Workout name...">
                    </div>
                    <button class="create-btn" onclick="create()">Create</button>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-6">
                    <div class="box">
                        <ul class="workout-list" id="exercise-list">
                        <%
                        	ArrayList<Exercise> exercises = storage.getAllExercises();
                        int id = 0;
                            for(Exercise e: exercises) {
                        		%><li id="exercise<%=id%>"><%=e.getName()%></li><%
                        		id++;
                            }
                        %>
                        </ul>
                    </div>
                </div>
                <div class="col-xs-6">
                    <div class="box">
                        <ul class="workout-list" id="create-list">
                        </ul>
                    </div>
                </div>
            </div>
            <div class="row">
                <a onclick="removeItem()"><i class="fa fa-arrow-left left-arrow"></i></a>
                <a onclick="addItem()"><i class="fa fa-arrow-right right-arrow"></i></a>
                
            </div>
		</div>
		<form id="exercise-form" action="/buildworkoutservlet" method="post" hidden>
        </form>
    </body>
</html>
