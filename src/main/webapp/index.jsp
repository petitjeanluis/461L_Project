<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="Database.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<%
UserService userService = UserServiceFactory.getUserService(); 
User user = userService.getCurrentUser();
Storage storage = Storage.getInstance();
Client c = storage.loadClient(user);
%>

<html lang="en">
    <header>
		<jsp:include page="header.jsp"/>
		<link rel="stylesheet" href="style/style.css">
		<link rel="stylesheet" href="style/index_style.css">
		<script src="js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="js/canvasChart.js" type="text/javascript"></script>
		<script type="text/javascript">
			<%
			String exerciseName = request.getParameter("exerciseName");
			ExerciseData data = c.getData(exerciseName);
			if(c != null && data != null && data.getDataPoints().size() >= 3) {%>
			$(document).ready(function() {
				var chart = {
					title: "<%=data.getExerciseName()%>",
					xLabel: '',
					yLabel: 'Amount of Weight',
					labelFont: '19pt Roboto Slab',
					dataPointFont: '10pt Roboto Slab',
					renderTypes: [CanvasChart.renderType.lines, CanvasChart.renderType.points],
					dataPoints: [
						<% 
					ArrayList<DataPoint> d = data.getDataPoints();
					DataPoint value;
					SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-dd");
					int divisor = d.size()/15;
					for(int i = 0; i < d.size() -1 ; i = i + 1 + divisor) {
						value = d.get(i);
						%>{ x: '<%=dateFormat.format(value.getDate()) %>', y: <%=value.getWeight()%> },
						<%
					}
					value = d.get(d.size()-1);
					%>{ x: '<%=dateFormat.format(value.getDate())%>', y: <%=value.getWeight()%> }]
				};
				CanvasChart.render('canvas', chart);
			});	
			
			<%} else {%> 
				<h4>You need to do more exercises in order for us to create your progress graph</h4>
			<%}%>
		</script> 
	</header>
    <body>
        <nav class="navbar navbar-inverse">
          <div class="container-fluid">
            <div class="navbar-header">
              <a class="navbar-brand" href="index.jsp">WorkoutMaker</a>
            </div>
            <ul class="nav navbar-nav">
              <li class="active"><a href="#">Home</a></li>
              <li><a href="workout_list.jsp">Your Workouts</a></li>
              <li><a href="workout_build.jsp">Build Workout</a></li>
              <%if(c.getCurrentWorkout() != null) { %>
              <li><a href="workout.jsp">Current Workout</a></li>
              <%} %>
              <li><a href="map.jsp">Find A Gym</a></li>
              <li><a href="social.jsp">Get Your Friends' Workouts</a></li>
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
	  			<div class="col-xs-4">
	  			</div>
	  			<div class="col-xs-4">
	  				<h1 align="center">Simply Stronger</h1>
	  				<h5 align="center">the new way to get fit.</h5>
	  			</div>
	  			<div class="col-xs-4">
	  			</div>
			</div>
			
			<div class ="row">
				<%      
					if(user == null) {
						%><br><br><h1 align="center">You need to login to continue!</h1><br>
						<a href= "<%=userService.createLoginURL(request.getRequestURI()) %>">
                			<button class="btn navbar-btn login-btn">Login</button>
                		</a>
                	<%
                	} else {
		        	%>
					</div>
					<!--  This is going to be the graph of progress -->
					<%
					if(c != null && data != null && data.getDataPoints().size() >= 3) {%>
					<div class = "row" >
						<br>
						<canvas id="canvas" width="1000" height="400"></canvas>
					</div>
					<div class = "row">
						<%
						int count = 0;
						ArrayList<ExerciseData> exerciseData = c.getExerciseData();
						for(ExerciseData e: exerciseData) {
							if(e.getDataPoints().size() >= 3) {
								count++;
							}
						}
						if(count > 0) {%>
						<h5 align = "center">Select other exercises from the dropdown menu to see your other graphs</h5>
						<form action = "/index.jsp" align = "center">
							<input list="exercises" name="exerciseName">
							<datalist id="exercises">
								<%
								for(ExerciseData e: exerciseData) {
									if(e.getDataPoints().size() >= 3) {
										%><option value="<%=e.getExerciseName() %>">
										<%
									}
								}
								%>
							</datalist>
							<input type="submit">
						</form>
						<br>
						<%}%>
					</div>
					<%} else { 
						if(exerciseName == null) {%>
					<div>
						<h4 align="center">You don't have enough data points to graph</h4>
					</div>
						<% } else { %>
					<div>
						<h4 align="center">You don't have enough data points to graph for <%=exerciseName %></h4>
					</div>
					<%}}%>
					<div class ="row" >
						<%if(c.getCurrentWorkout() != null) { %>
						<div class="col-xs-4" onclick="location.href='/workout_list.jsp'">
						<%} else { %>
						<div class="col-xs-6" onclick="location.href='/workout_list.jsp'">
						<%} %>
							<div class="panel panel-default text-center button-animation" id="setup">
			  					<div class="panel-text">Setup Workout</div>
							</div>
						</div>	
						<%if(c.getCurrentWorkout() != null) { %>
						<div class="col-xs-4" onclick="location.href='/workout.jsp'">				
							<div class="panel panel-default text-center button-animation" id="setup">
			  					<div class="panel-text">Resume Workout</div>
							</div>
						</div><%} %>
						<%if(c.getCurrentWorkout() != null) { %>
						<div class="col-xs-4" onclick="location.href='/workout_build.jsp'">
						<%} else { %>
						<div class="col-xs-6" onclick="location.href='/workout_build.jsp'">
						<%} %>			
							<div class="panel panel-default text-center button-animation" id="start">
			  					<div class="panel-text">Build Workout</div>
							</div>
						</div>
					</div> <%
					
					}%>
        </div>
    </body>
</html>
