<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="Database.*" %>
<!DOCTYPE html>
<%UserService userService = UserServiceFactory.getUserService(); 
User user = userService.getCurrentUser();
Storage storage = Storage.getInstance();
Client c = storage.loadClient(user);
%>
<script src="Scripts/jquery-1.6.min.js" type="text/javascript"></script>
<script src="Scripts/canvasChart.js" type="text/javascript"></script>
<script type="text/javascript">
	<%if(c != null) {%>
	$(document).ready(function() {
		var chart = {
			title: "<%=c.getFirstExerciseDataSet()%>",
			xLabel: "Times Exercised",
			yLabel: "Amount of Weight",
			labelFont: "19pt Arial",
			dataPointFont: "10pt Arial",
			renderTypes: [CanvasChart.renderType.lines, CanvasChart.renderType.point]
		}
	})	
	<%}%>
</script>    

<html lang="en">

    <jsp:include page="header.jsp"/>
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
                			<button top=50%; class="btn navbar-btn login-btn">Login</button>
                		</a>
                	<%
                	} else {
		        	%>
					</div>
					<!--  This is going to be the graph of progress -->
					<div class = "row">
						<canvas id="canvas" width="800" height="600"></canvas>
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
