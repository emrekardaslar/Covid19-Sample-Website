package servlet.session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GlobalStatisticsDao {
	public GlobalStatistics get() {
		GlobalStatistics globalStatistics = new GlobalStatistics();	//Gets all data from database, creates user objects and returns list of those user objects
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DatabaseConnection.initializeDatabase();
			st = con.prepareStatement("SELECT * FROM globalstatistics");
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				globalStatistics.setTotalTests(rs.getInt("totaltests"));
				globalStatistics.setTotalCases(rs.getInt("totalcases"));
				globalStatistics.setTotalMortality(rs.getInt("totalmortality"));
				globalStatistics.setTotalRecovered(rs.getInt("totalrecovered"));
		    }
		}
		catch(Exception e) {
			System.out.println("Connection failed");
		}
		
		try {
			st.close();
		} 
		catch (SQLException e) {
		}
		
		try {
			con.close();
		} 
		catch (SQLException e) {
		}

		return globalStatistics;
	}

}
