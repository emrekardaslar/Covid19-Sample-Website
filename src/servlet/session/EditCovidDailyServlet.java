package servlet.session;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/EditCovidDailyServlet")
public class EditCovidDailyServlet extends AuthorizeServlet  {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isAdmin(request, response))
			return;
		
		HttpSession session = request.getSession();
		CovidDailyDao covidDailyDao = new CovidDailyDao();	
		CountryDao countryDao = new CountryDao();
		ArrayList<Country> countryList = countryDao.getAll();
		request.setAttribute("countryList", countryList);
		
		String cidToEdit = request.getParameter("cid");
		String dateToEdit = request.getParameter("cdate");
		
		if (cidToEdit != null && dateToEdit != null) {
			 CovidDaily covidDaily = covidDailyDao.getDailyData(cidToEdit,dateToEdit);
			 
			if (covidDaily!=null) {
				request.setAttribute("cid",covidDaily.getCid());
				request.setAttribute("cdate", covidDaily.getCdate());
				request.setAttribute("tests", covidDaily.getTests());
				request.setAttribute("cases", covidDaily.getCases());
				request.setAttribute("mortality", covidDaily.getMortality());
				request.setAttribute("recovered", covidDaily.getRecovered());
				
				
				request.setAttribute("cidToEdit",cidToEdit);
				request.setAttribute("dateToEdit",dateToEdit);
				request.getRequestDispatcher("/WEB-INF/coviddailyedit.jsp").include(request, response);
			}
			else {
				session.setAttribute("error", "Cannot locate covid daily data");
				response.sendRedirect("CovidDailyServlet");
			}
		}
		else {
			session.setAttribute("error", "You must specify the country and the date to be edited");
			response.sendRedirect("CovidDailyServlet");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isAdmin(request, response))
			return;
		
		HttpSession session = request.getSession();
		CovidDailyDao covidDailyDao = new CovidDailyDao();	
		CountryDao countryDao = new CountryDao();
		ArrayList<Country> countryList = countryDao.getAll();
		request.setAttribute("countryList", countryList);
		String option = request.getParameter("option");
		String cidToEdit = request.getParameter("country_id");
		String dateToEdit = request.getParameter("country_date");
		String cid = request.getParameter("cid");
		String cdate = request.getParameter("cdate");
		String tests = request.getParameter("tests");
		String cases = request.getParameter("cases");
		String mortality = request.getParameter("mortality");
		String recovered = request.getParameter("recovered");
		
		request.setAttribute("cid", cid);
		request.setAttribute("cdate", cdate);
		request.setAttribute("tests", tests);
		request.setAttribute("cases", cases);
		request.setAttribute("mortality", mortality);
		request.setAttribute("recovered", recovered);
		request.setAttribute("cidToEdit",cidToEdit);
		request.setAttribute("dateToEdit",dateToEdit);

		if (option==null) {
			session.setAttribute("error", "No action specified");
			response.sendRedirect("CovidDailyServlet");
		}
		else if (option.equals("Cancel")) {
			response.sendRedirect("CovidDailyServlet");
		}
		
		else if (option.equals("Save")) {
			try {								
				covidDailyDao.checkDailyData(request.getParameter("cid"),request.getParameter("cdate"), cidToEdit, dateToEdit);
				covidDailyDao.saveCovidDaily(cidToEdit, dateToEdit, cid, cdate, tests, cases, mortality, recovered);
				session.setAttribute("success", "Covid Daily Data saved successfully");
				response.sendRedirect("CovidDailyServlet");
			}		
			
			catch(Exception e) {
				session.setAttribute("error", e.getMessage());	
				request.getRequestDispatcher("/WEB-INF/coviddailyedit.jsp").include(request, response);
			}	
		}
	}
}
