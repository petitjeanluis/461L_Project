package Servlets;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import Database.Client;
import Database.Storage;
import Database.Workout;

public class WorkoutListServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
        Storage storage = Storage.getInstance();
        Client c = storage.loadClient(user);
        
        String workoutName = req.getParameter("workoutName");
        Workout currentWorkout = c.getWorkoutFromName(workoutName);
        c.setCurrentWorkout(currentWorkout);
        
        storage.saveClient(c);
        
        resp.sendRedirect("/workout.jsp");
	}
}