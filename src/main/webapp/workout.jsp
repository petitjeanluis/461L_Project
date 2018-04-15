<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Database.*"%>
<!DOCTYPE html>
<html lang="en">
    <jsp:include page="header.jsp"/>
    <body>
        <nav class="navbar navbar-inverse">
          <div class="container-fluid">
            <div class="navbar-header">
              <a class="navbar-brand" href="index.jsp">WorkoutMaker</a>
            </div>
            <ul class="nav navbar-nav">
              <li><a href="index.jsp">Home</a></li>
              <li><a href="workout_list.jsp">Your Workouts</a></li>
              <li><a href="workout_build.jsp">Build Workout</a></li>
              <li class="active"><a href="#">Workout</a></li>
            </ul>
            <div class="nav navbar-nav navbar-right">
                <button class="btn navbar-btn">Register</button>
                <button class="btn navbar-btn login-btn">Login</button>
            </div>
          </div>
        </nav>
        <div class="container">
            <div class="row">
                <div class="col-xs-6">
                    <div id="myCarousel" class="carousel slide" data-ride="carousel">
                      <!-- Indicators -->
                      <ol class="carousel-indicators">
                        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                        <li data-target="#myCarousel" data-slide-to="1"></li>
                        <li data-target="#myCarousel" data-slide-to="2"></li>
                      </ol>

                      <!-- Wrapper for slides -->
                      <div class="carousel-inner">
                        <div class="item active">
                          <img src="/img/pic_0.jpg" alt="Wokrout 1" id="image1">
                        </div>

                        <div class="item">
                          <img src="/img/pic_0.jpg" alt="Wokrout 2" id="image2">
                        </div>
                      </div>

                      <!-- Left and right controls -->
                      <a class="left carousel-control" href="#myCarousel" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left"></span>
                        <span class="sr-only">Previous</span>
                      </a>
                      <a class="right carousel-control" href="#myCarousel" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right"></span>
                        <span class="sr-only">Next</span>
                      </a>
                    </div>
                </div>
                <div class="col-xs-6">
                    <div class="panel-group" id="accordion">
<%
//pull workout data from db
UserService userService = UserServiceFactory.getUserService();
User user = userService.getCurrentUser();                   		 

if(user == null) {
	
}

Storage storage = Storage.getInstance();
Client client =  storage.loadClient(user);
Workout workout = client.getCurrentWorkout();


int numExercises = workout.getNumOfExercises();// from db, dummy limited to 3 max
int currentExercise = workout.getCurrentExerciseIndex();
String workoutName = workout.getWorkoutName();// add name of workout to GUI
String in = "in";// this opens accordion
                    		  
for(int i = 0; i < numExercises; i++){
	Exercise exercise = workout.getExerciseNum(i);
	String name = exercise.getName();
	String description = exercise.getDescription();
	int setCount = 3;
	int repCount = client.getReps(exercise);
	int repWeight = 0;
%>
						<div class="panel panel-default">
						    <div class="panel-heading">
								<h4 class="panel-title">
						        	<a data-toggle="collapse" data-parent="#accordion" href="#collapse<%=i%>" onclick="updateColapse(<%=i%>)"><%=name%></a>
						      	</h4>
						    </div>
						    <div id="collapse<%=i%>" class="panel-collapse collapse <%=in%>">
						      	<div class="panel-body">
						      		<div class="row">
						           		<%=description%>
						           	</div>
						           	<div class="row">
						           		Set 1
						           	</div>
						           	<div class="row">
						           		Reps: <%=repCount%>   Weight: <%=repWeight%>lbs
						           	</div>
						           	<div class="row">
						           		<button>Start</button>
						           	</div>
						      	</div>
							</div>
						</div>
<%
in = "";}%>                  
                    </div>
                </div>
            </div>
        </div>
        <script src="js/workout.js"></script>
    </body>
</html>
