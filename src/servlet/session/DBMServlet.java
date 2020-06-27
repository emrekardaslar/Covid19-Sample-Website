package servlet.session;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DBMServlet")
public class DBMServlet extends AuthorizeServlet{
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isAdmin(request, response))
			return;
		
		request.getRequestDispatcher("/WEB-INF/dbm.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isAdmin(request, response))
			return;
		HttpSession session = request.getSession();
		Covid19Dao covid19Dao = new Covid19Dao();
		String option = request.getParameter("option");
		
		if (option.equals("Create Database")) {
			try {
				covid19Dao.createDatabase();
				session.setAttribute("success", "Database schemas created successfully");
			} catch (Exception e) {
				session.setAttribute("error", e.getMessage());
			}
			response.sendRedirect("DBMServlet");
		}
		else if (option.equals("Drop Database")) {
			try {
				covid19Dao.dropDatabase();
				session.setAttribute("success", "Database schemas dropped successfully");
			} catch (Exception e) {
				session.setAttribute("error", e.getMessage());
			}
			response.sendRedirect("DBMServlet");
		}
		else if (option.equals("Reset Database")) {
			try {
				covid19Dao.resetDatabase();
				session.setAttribute("success", "Database reset successfully");
			} catch (Exception e) {
				session.setAttribute("error", e.getMessage());
			}
			response.sendRedirect("DBMServlet");
		}
		else {
			session.setAttribute("error", "No action specified");
			response.sendRedirect("DBMServlet");
		}
	}
}
