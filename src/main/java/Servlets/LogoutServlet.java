package Servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class LogoutServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
        if(user == null) {
        	//logged out - redirecting to home page
        	resp.sendRedirect("home.jsp");
        } else {
        	//log user out - will reconnect to here and then redirect to the homepage
        	resp.sendRedirect(userService.createLogoutURL(req.getRequestURI()));
        }
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
	}
}
