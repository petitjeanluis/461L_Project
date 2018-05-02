package Servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import Database.Client;
import Database.Storage;

public class UpdateSetServlet extends HttpServlet {
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
        int currentSet = Integer.parseInt(req.getParameter("set"));
        
        c.updateSetForExercise(storage.getExercise(exerciseName), currentSet);
        
        storage.saveClient(c);
        System.out.println("UpdateSetServlet + set: " + currentSet);
	}
	
}