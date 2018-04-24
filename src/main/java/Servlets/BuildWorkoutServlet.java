package Servlets;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import Database.*;

public class BuildWorkoutServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        Storage storage = Storage.getInstance();
        Client client = storage.loadClient(user);
        
        int numOfExercises = Integer.parseInt(req.getParameter("numOfExercises"));
        String workoutName = req.getParameter("workoutName");
        
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        String base = "Exercise";
        for(int i = 0; i < numOfExercises; i++) {
        	Exercise e = storage.getExercise(req.getParameter(base + Integer.toString(i)));
        	exercises.add(e);
        }
        
        Workout customWorkout = new Workout(workoutName, exercises);
        client.addCustomWorkout(customWorkout);
        client.setCurrentWorkout(customWorkout);
        
        storage.saveClient(client);
        
        resp.sendRedirect("/workout.jsp");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        Storage storage = Storage.getInstance();
        Client client = storage.loadClient(user);
        
        int numOfExercises = Integer.parseInt(req.getParameter("numOfExercises"));
        String workoutName = req.getParameter("workoutName");
        
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        String base = "Exercise";
        for(int i = 0; i < numOfExercises; i++) {
        	Exercise e = storage.getExercise(req.getParameter(base + Integer.toString(i)));
        	exercises.add(e);
        }
        
        Workout customWorkout = new Workout(workoutName, exercises);
        client.addCustomWorkout(customWorkout);
        client.setCurrentWorkout(customWorkout);
        
        storage.saveClient(client);
        
        resp.sendRedirect("/workout.jsp");
	}
}
