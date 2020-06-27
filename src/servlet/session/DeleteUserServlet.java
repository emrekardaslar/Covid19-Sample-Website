package servlet.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends AuthorizeServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isAdmin(request, response))
			return;
		
		UserDao userdao = new UserDao();
		HttpSession session = request.getSession();		
		String userToDelete = request.getParameter("name");
		boolean success = false;
		
		if (userToDelete != null) {
			success = userdao.deleteUser(userToDelete); 
		}

		if (success) {
			System.out.println("User " + userToDelete + " deleted!!!");
			session.setAttribute("success", "User deleted successfully.");
		}
		else {
			session.setAttribute("error", "User cannot be deleted.");
		}
							
		response.sendRedirect("UserServlet");
	}
	
}
