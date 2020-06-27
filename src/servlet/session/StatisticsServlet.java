package servlet.session;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/StatisticsServlet")
public class StatisticsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GlobalStatisticsDao globalStatisticsDao = new GlobalStatisticsDao();
		CountryDao countryDao = new CountryDao();
		
		GlobalStatistics globalStatistics = globalStatisticsDao.get(); 
		ArrayList<Country> countryList = countryDao.getAll();
		
		request.setAttribute("globalStatistics", globalStatistics);
		request.setAttribute("countryList", countryList);
		request.setAttribute("search_text", "");
		request.getRequestDispatcher("/WEB-INF/statistics.jsp").forward(request, response);
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GlobalStatisticsDao globalStatisticsDao = new GlobalStatisticsDao();
		ArrayList<Country> countryList;
		CountryDao countryDao = new CountryDao();
		GlobalStatistics globalStatistics = globalStatisticsDao.get();
		String search_text = request.getParameter("search_text");
		
		if (search_text == null || search_text.isEmpty()) {
			countryList = countryDao.getAll();
		}
		else {
			countryList = countryDao.getFiltered(search_text);
		}
		request.setAttribute("countryList", countryList);
		request.setAttribute("globalStatistics", globalStatistics);
		request.setAttribute("search_text", search_text);
		request.getRequestDispatcher("/WEB-INF/statistics.jsp").forward(request, response);
	}
}
