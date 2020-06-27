package servlet.session;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/UserServlet")
public class UserServlet extends AuthorizeServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isAdmin(request, response))
			return;
		
		UserDao userdao = new UserDao();
		ArrayList<User> userList = userdao.getAll();		
		request.setAttribute("userList", userList);
		request.setAttribute("search_text", "");
		request.getRequestDispatcher("/WEB-INF/user.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isAdmin(request, response))
			return;
		
		UserDao userdao = new UserDao();
		String search_text = request.getParameter("search_text");
		ArrayList<User> userList;
		
		if (search_text == null || search_text.isEmpty()) {
			userList = userdao.getAll();
		}
		else {
			userList = userdao.getFiltered(search_text);
		}
		request.setAttribute("userList", userList);
		request.setAttribute("search_text", search_text);
		request.getRequestDispatcher("/WEB-INF/user.jsp").forward(request, response);
	}
	
}
