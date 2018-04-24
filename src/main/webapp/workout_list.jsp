<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="Database.*" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="en">
    <header>
		<jsp:include page="header.jsp"/>
	</header>
    <body>
        <nav class="navbar navbar-inverse">
          <div class="container-fluid">
            <div class="navbar-header">
              <a class="navbar-brand" href="index.jsp">WorkoutMaker</a>
            </div>
            <ul class="nav navbar-nav">
              <li><a href="index.jsp">Home</a></li>
              <li class="active"><a href="#">Your Workouts</a></li>
              <li><a href="workout_build.jsp">Build Workout</a></li>
              <li><a href="workout.jsp">Workout</a></li>
            </ul>
          </div>
        </nav>
 
		<div class="container">
			<div class="row">
	  			<div class="col-xs-6">
	  			  	<h1 align="center">Your workouts.</h1>
	  			</div>
	  			<div class="col-xs-6">
				  	<h1 align="center">Standard Workouts.</h1>
	  			</div>
			</div>
			<div class ="row">
<%
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();                   		 

	if(user == null) {
		System.out.println("no user found");
		response.sendRedirect(userService.createLoginURL(request.getRequestURI()));
	}

	Storage storage = Storage.getInstance();
	//System.out.println(user);
	Client client =  storage.loadClient(user);
	ArrayList<Workout> customWorkouts = client.getCustomWorkouts();
	ArrayList<Workout> standardWorkouts = storage.getAllWorkouts();
	int id;
				%>
				<div class="col-xs-6">
                    <div class="box">
                        <ul class="workout-list" id="my-list">
                        <%
                        	id = 0;
                            for(Workout e: customWorkouts) {
                        		%><li id="excercise<%=id%>" class="abs"><%=e.getWorkoutName()%></li><%
                        		id++;
                            }
                        %>
                        </ul>
                    </div>
                </div>
                <div class="col-xs-6">
                    <div class="box">
                        <ul class="workout-list" id="standard-list">
                        <%
							id = 0;
                            for(Workout e: standardWorkouts) {
                        		%><li id="excercise<%=id%>" class ="abs"><%=e.getWorkoutName()%></li><%
                        		id++;
                            }
                        %>
                        </ul>
                    </div>
                </div>
			</div>
		</div>
    </body>
</html>
