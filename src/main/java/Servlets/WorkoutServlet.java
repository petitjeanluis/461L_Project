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
        Client c = storage.loadClientSync(user);
        
        String exerciseName = req.getParameter("name");
        int reps = Integer.parseInt(req.getParameter("reps"));
        int weight = Integer.parseInt(req.getParameter("weight"));
        
        Exercise currentExercise = storage.getExerciseFromName(exerciseName);
        DataPoint d = new DataPoint(weight, reps, c.getSet(currentExercise), new Date());
        
        c.updateExerciseData(currentExercise, d);
        
        storage.saveClientSync(user, c);
        
        //response that the ajax can receive to confirm successful completion
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("Data Successfully Stored");
	}
}