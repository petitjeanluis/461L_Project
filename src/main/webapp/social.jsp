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
	
	ArrayList<String> friendEmailList = c.getFriendsEmails();
	ArrayList<String> globalEmailList = storage.getAllClientsEmails();
%>
<html lang="en">
    <header>
		<jsp:include page="header.jsp"/>
		<link rel="stylesheet" href="style/social_style.css">
        <script src="js/social.js"></script>
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
              <%if(c.getCurrentWorkout() != null) { %>
              <li><a href="workout.jsp">Current Workout</a></li>
              <%} %>
              <li><a href="map.jsp">Find A Gym</a></li>
              <li class="active"><a href="#">Get Your Friends' Workouts</a></li>
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
                <div class="col-xs-6">
                    <h2>Followers</h2>
                    <div class="box">
                        <ul id="friend-list">
             			<%
             				for(String email : friendEmailList) {
             					%><li><%=email%></li><%
             				}
             			%>
                        </ul>
                    </div>
                </div>
                <div class="col-xs-6">
                    <h2>Start Following</h2>
                    <div class="box">
                        <ul id="follow-list">
                        <%
                            int id = 0;
             				for(String email : globalEmailList) {
             					%><li id="user<%=id%>"><%=email%></li><%
   								id++;
             				}
             			%>
                        </ul>
                    </div>
                    <input type="text" id="follow-search">
                    <button type="button" name="add-follower-btn" onclick="addFollower()">Add</button>
                </div>
            </div>
            <div class="row">
                <h2>Followers' Workouts</h2>
                <table>
                    <thead>
                        <th>User</th>
                        <th>Workout Name</th>
                    </thead>
                    <tbody id="workout-table-body">
                   	<%
                   		id = 0;
                   		ArrayList<String> workoutNameList;
                    	for(String email : friendEmailList) {
                    		workoutNameList = storage.getFriendsWorkoutNamesFromEmail(email);
                    		for(String workoutName : workoutNameList) {
                    			%>
                        		<tr id="row<%=id%>">
                        			<td><%=email%></td>
                        			<td><%=workoutName%></td>
                        		</tr>
                        		<%	
                        		id++;
                    		}
                    	}		
                   	%>
                    </tbody>
                </table>
                <button type="button" name="add-workout-btn" onclick="addWorkout()">Add Workout</button>
            </div>
        </div>
    </body>
</html>
