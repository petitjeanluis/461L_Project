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

	}

	/*
	 * Builds a workout, redirects client to do that workout and saves the workout
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
        Storage storage = Storage.getInstance();
        Client client = storage.loadClientSync(user);
        
        client.resetSets();
        
        int numOfExercises = Integer.parseInt(req.getParameter("numOfExercises"));
        String workoutName = req.getParameter("workoutName");
        
        //handles no input in workout name
        if(workoutName.equals("")) {
        	workoutName = "CustomWorkout" + client.getCustomWorkouts().size();
        }
        
        //builds exercise list
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        String base = "Exercise";
        for(int i = 0; i < numOfExercises; i++) {
        	Exercise e = storage.getExerciseFromName(req.getParameter(base + Integer.toString(i)));
        	exercises.add(e);
        }
        
        //build workout
        Workout customWorkout = new Workout(workoutName, exercises);
        client.addCustomWorkout(customWorkout);
        
        //set the newlymade workout for client to do
        client.setCurrentWorkout(customWorkout);
        client.setCurrentExerciseIndex(0);
        
        storage.saveClientSync(user, client);
        
        resp.sendRedirect("/workout.jsp");
	}
}
