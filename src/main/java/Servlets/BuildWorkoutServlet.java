package Servlets;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import Database.Exercise;
import Database.Workout;

public class BuildWorkoutServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
	}
	
	public ArrayList<Exercise> getAllExercises(){
		return null;
	}
	
	public Workout createWorkout(ArrayList<Exercise> exerciseList) {
		return null;
				
	}
}
