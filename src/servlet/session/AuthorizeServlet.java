package servlet.session;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthorizeServlet extends HttpServlet{	
	private static final long serialVersionUID = 1L;
	
	protected boolean isAdmin(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		boolean roleIsAdmin = (boolean)session.getAttribute("isAdmin");
		
		if (!roleIsAdmin) {
			System.out.println("not admin");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return false;
		}
		
		return true;
	}

}
