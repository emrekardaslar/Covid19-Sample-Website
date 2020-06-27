package servlet.session;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CreateUserServlet")
public class CreateUserServlet extends AuthorizeServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isAdmin(request, response))
			return;
		
		request.setAttribute("user", "");
		request.setAttribute("pwd1", "");
		request.setAttribute("pwd2", "");
		request.setAttribute("is_admin", "0");
		request.getRequestDispatcher("/WEB-INF/usercreate.jsp").include(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		if (!isAdmin(request, response))
			return;
		
		String user = request.getParameter("user");
		String pwd1 = request.getParameter("pwd1");
		String pwd2 = request.getParameter("pwd2");
		String isadmin = request.getParameter("is_admin");
		int is_admin = isadmin != null && isadmin.equals("1") ? 1 : 0;
				
		HttpSession session = request.getSession();
		UserDao userdao = new UserDao();
		String option = request.getParameter("option");
		
		if (option==null) {
			session.setAttribute("error", "No action specified");
			response.sendRedirect("UserServlet");
		}
		else if (option.equals("Cancel")) {
			response.sendRedirect("UserServlet");
		}
		else if (option.equals("Create")) {
			try {
				userdao.checkUser(user);
				userdao.createUser(user, pwd1, pwd2, is_admin);
				session.setAttribute("success", "User created successfully.");
				response.sendRedirect("UserServlet");
			}
			catch(Exception e) {
				request.setAttribute("user", user);
				request.setAttribute("pwd1", pwd1);
				request.setAttribute("pwd2", pwd2);
				request.setAttribute("is_admin", is_admin);
				session.setAttribute("error",e.getMessage());
				request.getRequestDispatcher("/WEB-INF/usercreate.jsp").include(request, response);
			}
		}
	}

}
