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

public class TogglePrivacyServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		//window.relocation so its in the get method
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
        Storage storage = Storage.getInstance();
        Client c = storage.loadClientSync(user);
        
        c.toggleAllowSharingTrue();
        
        storage.saveClientSync(user, c);
        
        resp.sendRedirect("index.jsp");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
	}
}
