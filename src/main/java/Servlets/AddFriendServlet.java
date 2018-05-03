package Servlets;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import Database.*;

public class AddFriendServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
        Storage storage = Storage.getInstance();
        Client client = storage.loadClientSync(user);
        
        String email = req.getParameter("email");
        client.addFriend(email);
        
        storage.saveClientSync(user, client);
        
        resp.sendRedirect("social.jsp");
	}
}