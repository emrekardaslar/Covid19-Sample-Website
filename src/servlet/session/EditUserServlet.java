package servlet.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/EditUserServlet")
public class EditUserServlet extends AuthorizeServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isAdmin(request, response))
			return;
		
		HttpSession session = request.getSession();
		UserDao userdao = new UserDao();	
		String userToEdit = request.getParameter("name");
		
		if (userToEdit != null) {
			User user = userdao.getUser(userToEdit);
			if (user!=null) {
				request.setAttribute("user", user);
				request.setAttribute("userToEdit",userToEdit);
				request.getRequestDispatcher("/WEB-INF/useredit.jsp").include(request, response);
			}
			else {
				session.setAttribute("error", "Cannot locate user");
				response.sendRedirect("UserServlet");
			}
		}
		else {
			session.setAttribute("error", "You must specify the user to be edited");
			response.sendRedirect("UserServlet");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isAdmin(request, response))
			return;
		
		HttpSession session = request.getSession();
		UserDao userdao = new UserDao();	
		String userToEdit = request.getParameter("name");
		String option = request.getParameter("option");
		
		if (option==null) {
			session.setAttribute("error", "No action specified");
			response.sendRedirect("UserServlet");
		}
		else if (option.equals("Cancel")) {
			response.sendRedirect("UserServlet");
		}
		
		else if (option.equals("Save")) {
			User user = new User();
			user.setName(request.getParameter("user"));
			user.setPwd(request.getParameter("pwd"));
			String is_admin = request.getParameter("is_admin");
			user.setIsAdmin(is_admin != null && is_admin.equals("1") ? 1 : 0);
			
			//System.out.println(userToEdit);
			//System.out.println(request.getParameter("user"));
			
			try {								
				userdao.checkUser(request.getParameter("user"),userToEdit);
				userdao.saveUser(userToEdit, user);
				session.setAttribute("success", "User saved successfully");
				response.sendRedirect("UserServlet");
			}		
			
			catch(Exception e) {
				session.setAttribute("error", e.getMessage());	
				request.setAttribute("user", user);
				request.setAttribute("userToEdit",userToEdit);
				request.getRequestDispatcher("/WEB-INF/useredit.jsp").include(request, response);
			}
		}
	}
}
