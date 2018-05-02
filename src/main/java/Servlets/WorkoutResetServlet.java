package Servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import Database.*;

public class WorkoutResetServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		System.out.println("workoutresetServlet reached");
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
        Storage storage = Storage.getInstance();
        Client c = storage.loadClientSync(user);
        
        c.resetSets();
        
        c.setCurrentWorkout(null);
        c.setCurrentExerciseIndex(-1);
        
        storage.saveClientSync(user, c);
        
        resp.sendRedirect("index.jsp");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		System.out.println("workoutresetServlet reached");
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
        Storage storage = Storage.getInstance();
        Client c = storage.loadClientSync(user);
        
        c.resetSets();
        
        c.setCurrentWorkout(null);
        c.setCurrentExerciseIndex(-1);
        
        storage.saveClientSync(user, c);
        
        resp.sendRedirect("index.jsp");
	}
	
}