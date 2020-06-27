package servlet.session;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/CreateCovidDailyServlet")
public class CreateCovidDailyServlet extends AuthorizeServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isAdmin(request, response))
			return;
		
		CountryDao countryDao = new CountryDao();
		ArrayList<Country> countryList = countryDao.getAll();
		request.setAttribute("countryList", countryList);
		
		if (countryList.size()>0) {
			request.setAttribute("cid", countryList.get(0).getId());
		}
		request.setAttribute("cdate", "");
		request.setAttribute("tests", "0");
		request.setAttribute("cases", "0");
		request.setAttribute("mortality", "0");
		request.setAttribute("recovered", "0");
		request.getRequestDispatcher("/WEB-INF/coviddailycreate.jsp").include(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		if (!isAdmin(request, response))
			return;
									
		HttpSession session = request.getSession();
		CovidDailyDao covidDailyDao = new CovidDailyDao();
		CountryDao countryDao = new CountryDao();
		String option = request.getParameter("option");
		
		ArrayList<Country> countryList = countryDao.getAll();
		
		
		if (option==null) {
			session.setAttribute("error", "No action specified");
			response.sendRedirect("CovidDailyServlet");
		}
		else if (option.equals("Cancel")) {
			response.sendRedirect("CovidDailyServlet");
		}
		
		else if (option.equals("Create")) {	
			String dateStr = request.getParameter("cdate");
			String cid = request.getParameter("cid");
			String tests = request.getParameter("tests");
			String cases = request.getParameter("cases");
			String mortality = request.getParameter("mortality");
			String recovered = request.getParameter("recovered");

			
			request.setAttribute("countryList", countryList);
			request.setAttribute("cid", cid);
			request.setAttribute("cdate", dateStr);
			request.setAttribute("tests", tests);
			request.setAttribute("cases", cases);
			request.setAttribute("mortality", mortality);
			request.setAttribute("recovered", recovered);

			
			try {	
				covidDailyDao.checkDailyData(dateStr,cid);
				covidDailyDao.createDailyData(dateStr,cid,tests,cases,mortality,recovered);
				session.setAttribute("success", "Covid Daily Data created successfully.");
				response.sendRedirect("CovidDailyServlet");
			}

			catch(Exception e) {			
				session.setAttribute("error", e.getMessage());
				request.getRequestDispatcher("/WEB-INF/coviddailycreate.jsp").include(request, response);
			}		
		}
	}
}
