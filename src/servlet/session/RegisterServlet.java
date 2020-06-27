package servlet.session;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String user = request.getParameter("user");
		String pwd1 = request.getParameter("pwd1");
		String pwd2 = request.getParameter("pwd2");
		HttpSession session = request.getSession();
		UserDao userdao = new UserDao();	
		
		try {
			userdao.checkUser(user);
			userdao.createUser(user, pwd1, pwd2, 0); //0 means not admin
			session.setAttribute("success", "User created successfully.");
			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);
		}

		catch(Exception e) {
			session.setAttribute("error",e.getMessage());
			request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request,response);
		}
	}
}
