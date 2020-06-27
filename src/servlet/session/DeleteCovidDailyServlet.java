package servlet.session;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DeleteCovidDailyServlet")
public class DeleteCovidDailyServlet extends AuthorizeServlet  {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isAdmin(request, response))
			return;
		
		CovidDailyDao covidDailyDao = new CovidDailyDao();
		HttpSession session = request.getSession();		
		int dataToDelete = Integer.parseInt(request.getParameter("cid"));
		Date cdate = Date.valueOf(request.getParameter("cdate"));
		boolean success = false;
		
		success = covidDailyDao.deleteCovidDaily(dataToDelete,cdate); 
		

		if (success) {
			System.out.println("Country with cid: " + dataToDelete + " deleted!!!");
			session.setAttribute("success", "Daily data deleted successfully.");
		}
		else {
			session.setAttribute("error", "Daily data  cannot be deleted.");
		}
							
		response.sendRedirect("CovidDailyServlet");
	}

}
