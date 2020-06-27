package servlet.session;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet("/CovidDailyServlet")
public class CovidDailyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		CountryDao countryDao = new CountryDao();
		ArrayList<Country> countryList = countryDao.getAll();	
		

		CovidDailyDao covidDailyDao = new CovidDailyDao();
		ArrayList<CovidDaily> covidDailyList = covidDailyDao.getAll();	
		
		if (countryList.size()>0) {
			request.setAttribute("cid", countryList.get(0).getId());
		}
		
		request.setAttribute("countryList", countryList);
		request.setAttribute("covidDailyList", covidDailyList);
		request.setAttribute("search_date1", "");
		request.setAttribute("search_date2", "");
		request.getRequestDispatcher("/WEB-INF/coviddaily.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		CountryDao countryDao = new CountryDao();
		ArrayList<Country> countryList = countryDao.getAll();
		CovidDailyDao covidDailyDao = new CovidDailyDao();
		String search_date1 = request.getParameter("search_date1");
		String search_date2 = request.getParameter("search_date2");
		ArrayList<CovidDaily> covidDailyList = new ArrayList<CovidDaily>();	
		String cid = request.getParameter("cid");
		
		request.setAttribute("countryList", countryList);
		request.setAttribute("search_date1", search_date1);
		request.setAttribute("search_date2", search_date2);
		request.setAttribute("cid", cid);
		
		try {
			covidDailyList = covidDailyDao.getFiltered(search_date1, search_date2, cid, true);
			request.setAttribute("covidDailyList", covidDailyList);
			request.getRequestDispatcher("/WEB-INF/coviddaily.jsp").include(request, response);
		}
		catch(Exception e) {
			request.setAttribute("covidDailyList", covidDailyList);
			session.setAttribute("error",e.getMessage());
			request.getRequestDispatcher("/WEB-INF/coviddaily.jsp").include(request, response);
		}

	}
	
}
