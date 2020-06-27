package servlet.session;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet{	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// get request parameters for userID and password
		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");
		// number of matches in DB for this user and pwd
		String username = "";
		boolean isAdmin = false;
		HttpSession session = request.getSession();
		Connection con = null;
		PreparedStatement st = null;
		
		try {
			con = DatabaseConnection.initializeDatabase();

			st = con.prepareStatement("SELECT username, isAdmin FROM CovidUser WHERE username=? AND pwd=?");
			st.setString(1, user); 
			st.setString(2, pwd);
			// process the results
		    ResultSet rs = st.executeQuery();
		    if (rs.next()) {
		    	username = rs.getString("username");
		    	isAdmin = (rs.getInt("isAdmin") == 1) ? true : false;
		    }
		    
		    rs.close();
            st.close(); 
            con.close();
            
            if (username.equals(user) && !username.equals("") && !pwd.equals("")) {
    			session.setAttribute("user", user);
    			session.setAttribute("isAdmin", isAdmin);
    			request.getRequestDispatcher("/WEB-INF/main.jsp").forward(request,response);
            }          
                            
            else {
    			session.setAttribute("error", "Either user name or password is wrong.");
    			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);
            }
        }
		
		catch(Exception e) {
			System.out.println("Error on database connection");					
			session.setAttribute("error", "Error on database connection.");
			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);
		}
	}
}
