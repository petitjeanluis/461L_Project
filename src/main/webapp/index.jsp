<!DOCTYPE html>
<html lang="en">
    <jsp:include page="header.jsp"/>
    <body>
        <nav class="navbar navbar-inverse">
          <div class="container-fluid">
            <div class="navbar-header">
              <a class="navbar-brand" href="index.html">WorkoutMaker</a>
            </div>
            <ul class="nav navbar-nav">
              <li class="active"><a href="#">Home</a></li>
              <li><a href="workout_list.jsp">Your Workouts</a></li>
              <li><a href="workout_build.jsp">Build Workout</a></li>
              <li><a href="workout.jsp">Workout</a></li>
            </ul>
            <div class="nav navbar-nav navbar-right">
                <button class="btn navbar-btn">Register</button>
                <button class="btn navbar-btn login-btn">Login</button>
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
			</div>
        </div>
    </body>
</html>
