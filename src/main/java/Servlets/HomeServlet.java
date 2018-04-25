package Servlets;

import java.io.IOException;
import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class HomeServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		//System.out.println("home");
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
        if(user == null) {
        	resp.sendRedirect("home.html");
        	//resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
        } else {
        	//System.out.println("found a user");
        	resp.sendRedirect("index.jsp");
        }
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
	}
}