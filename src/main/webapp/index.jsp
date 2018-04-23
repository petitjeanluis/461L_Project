<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="Database.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<%UserService userService = UserServiceFactory.getUserService(); 
User user = userService.getCurrentUser();
Storage storage = Storage.getInstance();
Client c = storage.loadClient(user);
c.populateFakeData();
%>

<html lang="en">
    <header>
		<jsp:include page="header.jsp"/>
		<script src="js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="js/canvasChart.js" type="text/javascript"></script>
		<script type="text/javascript">
			<%
			String exerciseName = request.getParameter("exerciseName");
			ExerciseData data = c.getData(exerciseName);
			if(c != null && data != null && data.getDataPoints().size() > 3) {%>
			$(document).ready(function() {
				var chart = {
					title: "<%=data.getExerciseName()%>",
					xLabel: 'Times Exercised',
					yLabel: 'Amount of Weight',
					labelFont: '19pt Arial',
					dataPointFont: '10pt Arial',
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
			
			<%} else {
				%> <h4>You need to do more exercises in order for us to create your progress graph</h4> <%
			}%>
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
              <li><a href="workout.jsp">Workout</a></li>
            </ul>
            <div class="nav navbar-nav navbar-right">
                <button class="btn navbar-btn">Register</button>
                <a href= "<%=userService.createLoginURL(request.getRequestURI()) %>">
                	<button class="btn navbar-btn login-btn">Login</button>
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
					<div class = "row">
						<canvas id="canvas" style="margin: auto; display: inline-block" width="1000" height="400"></canvas>
					</div>
					<div class = "row">
						<%
						int count = 0;
						ArrayList<ExerciseData> exerciseData = c.getExerciseData();
						for(ExerciseData e: exerciseData) {
							if(e.getDataPoints().size() > 5) {
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
									if(e.getDataPoints().size() > 3) {
										%><option value="<%=e.getExerciseName() %>">
										<%
									}
								}
								%>
							</datalist>
							<input type="submit">
						</form>
						<br><br>
						<%}%>
					</div>
					<div class ="row">
						<div class="col-xs-6">
							<div class="panel panel-default" id="setup">
			  					<div class="panel-text">Setup Workout</div>
							</div>
						</div>	
						<div class="col-xs-6">				
							<div class="panel panel-default" id="start">
			  					<div class="panel-text">Start Workout</div>
							</div>
						</div>
					</div> <%
					
					}%>
        </div>
    </body>
</html>
