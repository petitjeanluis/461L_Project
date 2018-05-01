package Servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import Database.*;

public class WorkoutServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
        Storage storage = Storage.getInstance();
        Client c = storage.loadClient(user);
        
        String exerciseName = req.getParameter("name");
        Exercise currentExercise = storage.getExercise(exerciseName);
        
        int reps = Integer.parseInt(req.getParameter("reps"));
        int weight = Integer.parseInt(req.getParameter("weight"));
        int set = Integer.parseInt(req.getParameter("set"));
        
        System.out.println("WorkoutServlet: weight" + weight + " reps " + 
        		+ reps + " exerciseName " + exerciseName);
        
        System.out.println("WorkoutServlet" + set);
        
        DataPoint d = new DataPoint(weight, reps, set, new Date());
        
        c.updateExerciseData(currentExercise, d);
        
        if(set == currentExercise.getStartingSets()) {
        	c.getCurrentWorkout().setCurrentExerciseIndex(c.getCurrentWorkout().getCurrentExerciseIndex()+1);
        }
        
        storage.saveClient(c);
        
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("Data Successfully Stored");
	}
	
}