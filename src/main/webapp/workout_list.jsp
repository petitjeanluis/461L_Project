<%@ page import="Database.*" %>
<%@ page import="java.util.ArrayList" %>
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
              <li><a href="index.jsp">Home</a></li>
              <li class="active"><a href="#">Your Workouts</a></li>
              <li><a href="workout_build.jsp">Build Workout</a></li>
              <li><a href="workout.jsp">Workout</a></li>
            </ul>
            <div class="nav navbar-nav navbar-right">
                <button class="btn navbar-btn">Register</button>
                <button class="btn navbar-btn login-btn">Login</button>
            </div>
          </div>
        </nav>
        
        <div class="row">
  			<div class="col-xs-6">
  			  	<h1 align="center">Your workouts.</h1>
  			</div>
  			<div class="col-xs-6">
			  	<h1 align="center">Tailored for you.</h1>
  			</div>
		</div>
		
		<div class ="row">
			<div class ="col-xs-6">
				<div class="panel panel-default">
  					<div class="panel-body" align="left">Personal workout 1</div>
  					<div class="panel-body" align="left">Personal workout 2</div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
				</div>
			</div>
			<div class ="col-xs-6">
				<div class="panel panel-default">
  					<div class="panel-body" align="left">Recommended workout 1</div>
  					<div class="panel-body" align="left">Recommended workout 2</div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
  					<div class="panel-body"></div>
				</div>
			</div>
		</div>

    </body>
</html>
