<!DOCTYPE html>
<html lang="en">
	<header>
		<jsp:include page="header.jsp"/>
		<link rel="stylesheet" href="style/build_style.css">
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
                        <input class="form-control" type="text" placeholder="Search exercises...">
                    </div>
                </div>
                <div class="col-xs-6">
                    <div class="search">
                        <span class="fa fa-search"></span>
                        <input class="form-control" type="text" placeholder="Search workouts...">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-6">
                    <div class="box">
                        <ul class="workout-list">
                            <li>Workout Item</li>
                        </ul>
                    </div>
                </div>
                <div class="col-xs-6">
                    <div class="box">
                        <ul class="workout-list">
                            <li>Workout Item</li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="row">
                <a href="https://www.gogle.com"><i class="fa fa-arrow-right center-arrow"></i></a>
                <button class="create-btn" type="submit" name="button">Create</button>
            </div>
		</div>
    </body>
</html>
