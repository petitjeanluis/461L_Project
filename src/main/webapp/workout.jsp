<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Database.*"%>
<!DOCTYPE html>
<%
//pull workout data from db
UserService userService = UserServiceFactory.getUserService();
User user = userService.getCurrentUser();                   		 

if(user == null) {
	System.out.println("workout.jsp: test");
	response.sendRedirect(userService.createLoginURL(request.getRequestURI()));
}

Storage storage = Storage.getInstance();
//System.out.println(user);
Client client =  storage.loadClient(user);
System.out.println("workout.jsp: clientName " + client.getUser().getEmail());

//will be future code
Workout workout = client.getCurrentWorkout();
System.out.println("workout.jsp: currentWorkout " + workout.getWorkoutName());
//code for testing
//Workout workout = Storage.getInstance().getAllWorkouts().get(0);

int numExercises = workout.getNumOfExercises();// from db, dummy limited to 3 max
int currentExercise = workout.getCurrentExerciseIndex();
String workoutName = workout.getWorkoutName();// add name of workout to GUI
%>
<html lang="en">
    <header>
		<jsp:include page="header.jsp"/>
		<link rel="stylesheet" href="style/workout_style.css">
		<script src="js/workout.js"></script>
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
              <li><a href="workout_build.jsp">Build Workout</a></li>
            </ul>
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
                        <%System.out.println("workout.jsp: exercise: " + workout.getExercises().toString());%>
                          <img src="/img/<%=workout.getExerciseNum(currentExercise).getName() %>-1.jpg" alt="Workout 1" width= "600" height="500" id="image1" class="images">
                        </div>

                        <div class="item">
                          <img src="/img/<%=workout.getExerciseNum(currentExercise).getName() %>-2.jpg" alt="Workout 2" width= "600" height="500" id="image2" class="images">
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
String in = "in";// this opens accordion
int id  = 0;
                    		  
for(int i = 0; i < numExercises; i++){
	Exercise exercise = workout.getExerciseNum(i);
	//System.out.println(exercise.getName());
	String name = exercise.getName();
	String description = exercise.getDescription();
	int setCount = 3;
	//System.out.println(client);
	int repCount = client.getReps(exercise);
	int repWeight = client.getWeight(exercise);
%>
						<div class="panel-group" id="accordion">
	                      <div class="panel panel-default">
	                        <div class="panel-heading">
	                          <h4 class="panel-title">
	                            <a data-toggle="collapse" data-parent="#accordion" href="#collapse<%=i%>" onclick="updateCollapse('<%=name%>')"><%=name%></a>
	                          </h4>
	                        </div>
	                        <div id="collapse<%=i%>" class="panel-collapse collapse <%=in%>">
	                            <div class="panel-body">
	                                <div class="subtitle">
	                                    Set 1
	                                </div>
	                                <div class="shifters">
	                                    <div class="left-shifter">
	                                        <div class="arrow">
	                                            <a href="#"><i class="fa fa-arrow-up"></i></a>
	                                        </div>
	                                        <input id="repField<%=id%>" type="text" name="reps" value="<%=repCount%>" readonly="true" ondblclick="this.readOnly='';" size="3" maxlength="3" pattern="\d*">
	                                        <div class="arrow">
	                                            <a href="#"><i class="fa fa-arrow-down"></i></a>
	                                        </div>
	                                    </div>
	                                    <div class="right-shifter">
	                                        <div class="arrow">
	                                            <a href="#"><i class="fa fa-arrow-up"></i></a>
	                                        </div>
	                                        <input id="weightField<%=id%>" type="text" name="weight" value="<%=repWeight%>" readonly="true" ondblclick="this.readOnly='';" size="3" maxlength="3" pattern="\d*">
	                                        <div class="arrow">
	                                            <a href="#"><i class="fa fa-arrow-down"></i></a>
	                                        </div>
	                                    </div>
	                                </div>
	                                <div class="exercise-footer">
	                                    <div class="timer">
	
	                                    </div>
	                                    <button class="rest-btn" type="button" name="rest">Rest</button>
	                                    <button class="next-btn" type="button" name="next" onclick="ajax_update('<%=name%>', document.getElementById('repField<%=id%>').value, document.getElementById('weightField<%=id%>').value)">Next</button>
	                                </div>
	                            </div>
	                        	</div>
                      		</div>
	                    </div>
<%
in = "";
id++;}%>                  
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
