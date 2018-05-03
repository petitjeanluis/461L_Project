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

if(user == null) {
	response.sendRedirect(userService.createLoginURL(request.getRequestURI()));
	return;
}

Storage storage = Storage.getInstance();
Client c = storage.loadClient(user);
%>

<html lang="en">
    <header>
		<jsp:include page="header.jsp"/>
		<link rel="stylesheet" href="style/index_style.css">
		<script src="js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="js/canvasChart.js" type="text/javascript"></script>
		<script src="js/index.js" type="text/javascript"></script>
		<script type="text/javascript">
			<%
			String exerciseName = request.getParameter("exerciseName");
			ExerciseData data = c.getData(exerciseName);
			
			if(c != null && data != null && data.getDataPoints().size() >= 3) {%>
			$(document).ready(function() {
				var chart = {
					title: "<%=data.getExerciseName()%>",
					xLabel: '',
					yLabel: 'Weight',
					labelFont: '19pt Roboto Slab',
					dataPointFont: '10pt Roboto Slab',
					renderTypes: [CanvasChart.renderType.lines, CanvasChart.renderType.points],
					dataPoints: [
						<% 
					ArrayList<DataPoint> d = data.getDataPoints();
					DataPoint value;
					SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-dd");
					
					//used if the client has a lot of datapoints
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
              <li><a href="map.jsp">Find A Gym</a></li>
              <li><a href="social.jsp">Friends</a></li>
              <%if(c.getCurrentWorkout() != null) { %>
              <li><a href="workout.jsp">Current Workout</a></li>
              <%} %>
            </ul>
            <div class="nav navbar-nav navbar-right">  	
                <a href= "/logoutservlet">
                	<button class="btn login-btn navbar-btn">Logout</button>
                </a>
            </div>
          </div>
        </nav>
        <div class="container">
			<div class ="row first-row">
				<%      
					if(user == null) {
						%><br><br><h1 align="center">You need to login to continue!</h1><br>
						<a href= "<%=userService.createLoginURL(request.getRequestURI()) %>">
                			<button class="btn navbar-btn login-btn">Login</button>
                		</a>
                	<%
                	} else {
		        	%>
						<%if(c.getCurrentWorkout() != null) { %>
						<div class="col-xs-4">
							<div class="panel panel-default button-animation" id="setup" onclick="location.href='/workout_list.jsp'" style="width: 50%; margin-left:auto;margin-right:-90px">
			  					<div class="panel-text">Setup Workout</div>
							</div>
						</div>
						<div class="col-xs-4">
							<div class="panel panel-default button-animation" id="resume" onclick="location.href='/workout.jsp'" style="width: 50%; margin:auto;">
			  					<div class="panel-text">Resume Workout</div>
							</div>
						</div>
						<div class="col-xs-4">
							<div class="panel panel-default button-animation" id="build" onclick="location.href='/workout_build.jsp'" style="width: 50%; margin-left:-90px">
			  					<div class="panel-text">Build Workout</div>
							</div>
						</div>
						<%} else { %>
						<div class="col-xs-6">
							<div class="panel panel-default button-animation" id="start" onclick="location.href='/workout_list.jsp'" style="width: 50%; margin-left:auto;margin-right:10px">
			  					<div class="panel-text">Setup Workout</div> 
							</div>
						</div>
						<div class="col-xs-6">
							<div class="panel panel-default button-animation" id="build" onclick="location.href='/workout_build.jsp'" style="width: 50%; margin-left:10px;">
			  					<div class="panel-text">Build Workout</div>
							</div>
						</div>
						<%} %>
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
						//counts how many dataPoints are >= 3 to determine if graph, etc... is displayed
						int count = 0;
						ArrayList<ExerciseData> exerciseData = c.getExerciseData();
						
						for(ExerciseData e: exerciseData) {
							if(e.getDataPoints().size() >= 3) {
								count++;
							}
						}
						
						if(count > 0) {%>
						<h5 align = "center" class="datalist-title">Select Exercise To Plot</h5>
						<form action = "/index.jsp" align = "center">
						<%
						String val = exerciseData.get(0).getExerciseName();
						%>
							<input list="exercises" name="exerciseName" value="<%=val%>">
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
							<input class="btn update-btn"type="submit" value="Update">
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
					<%}}
	
					}%>
				<div class="row share-row">
					<%if(c.getAllowSharing()){%>
					<h4>You have allowed workout sharing! Your friends can now use your workouts!</h4>
					<%} else { %>
					<h4>Allow Workout Sharing: </h4>
					<button class="btn share-btn" onclick="togglePrivacy()">Activate</button>
					<%} %>
				</div>
        </div>
    </body>
</html>
