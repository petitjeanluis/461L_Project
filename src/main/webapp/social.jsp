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
              <a class="navbar-brand" href="index.jsp">Basically Fit</a>
            </div>
            <ul class="nav navbar-nav">
              <li><a href="index.jsp">Home</a></li>
              <li><a href="workout_list.jsp">Your Workouts</a></li>
              <li><a href="workout_build.jsp">Build Workout</a></li>
              <li><a href="map.jsp">Find A Gym</a></li>
              <li class="active"><a href="#">Friends</a></li>
              <%if(c.getCurrentWorkout() != null) { %>
              <li><a href="workout.jsp">Current Workout</a></li>
              <%} %>
            </ul>
            <div class="nav navbar-nav navbar-right">  	
                <a href= "/logoutservlet">
                	<button class="btn navbar-btn login-btn">Logout</button>
                </a>
            </div>
          </div>
        </nav>
        <div class="container">
            <div class="row">
                <div class="col-xs-5">
                    <div class="box left-box">
                        <h2 align="center">Friends</h2>
                        <ul id="friend-list">
             			<%
             				for(String email : friendEmailList) {
             					%><li><%=email%></li><%
             				}
             			%>
                        </ul>
                    </div>
                </div>
                <div class="col-xs-2">
                    <div class="btn-wrapper">
                        <button type="button" class="btn" id="add-follower-btn" onclick="addFollower()"><span id="left-arrow" class="fa fa-arrow-left"><span><span id="btn-text"></span> Follow</button>
                    </div>
                </div>
                <div class="col-xs-5">
                    <div class="box right-box">
                        <h2 align="center">Add Friends</h2>
                        <ul id="follow-list">
                        <%
                            int id = 0;
             				for(String email : globalEmailList) {
             					%><li id="user<%=id%>"><%=email%></li><%
   								id++;
             				}
             			%>
                        </ul>
                        <input type="text" id="follow-search" onkeyup="searchFilter()" placeholder="Search users...">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="workouts-box">
                    <h2>Friends' Workouts</h2>
                    <table>
                        <thead>
                            <th width="50%">User</th>
                            <th width="50%">Workout Name</th>
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
                        			<td width="50%"><%=email%></td>
                        			<td width="50%"><%=workoutName%></td>
                        		</tr>
                        		<%	
                        		id++;
                    		}
                    	}		
                   	%>
                    	</tbody>
                    </table>
                    <button type="button"class="btn" onclick="addWorkout()">Add Workout</button>
                </div>
            </div>
        </div>
        <form id="add-friend-form" action="/addfriendservlet" method="post" hidden>
        </form>
        <form id="add-friend-workout-form" action="/addfriendsworkoutservlet" method="post" hidden>
        </form>
    </body>
</html>
