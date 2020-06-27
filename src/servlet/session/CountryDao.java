package servlet.session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CountryDao {
	public ArrayList<Country> getAll() {
		ArrayList <Country> countryList = new ArrayList<Country>();
		Connection con = null;
		PreparedStatement st = null;
		
		try {
			con = DatabaseConnection.initializeDatabase();
			st = con.prepareStatement("SELECT * FROM Country ORDER BY name");
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Country country = new Country();
				country.setId(rs.getInt("id"));
				country.setName(rs.getString("name"));
				country.setTotalTests(rs.getInt("totaltests"));
				country.setTotalCases(rs.getInt("totalcases"));
				country.setTotalMortality(rs.getInt("totalmortality"));
				country.setTotalRecovered(rs.getInt("totalrecovered"));
		    	
		    	countryList.add(country);
		    }
		}
		catch(Exception  e) {
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
		
		return countryList;
	}
	
	
	public ArrayList<Country> getFiltered(String search_text) {
		ArrayList <Country> countryList = new ArrayList<Country>();
		Connection con = null;
		PreparedStatement st = null;
		
		try {
			con = DatabaseConnection.initializeDatabase();
			st = con.prepareStatement("SELECT * FROM Country WHERE UPPER(Country.name) LIKE '%' || UPPER(?) || '%' ORDER BY name");
			st.setString(1, search_text);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Country country = new Country();
				country.setId(rs.getInt("id"));
				country.setName(rs.getString("name"));
				country.setTotalTests(rs.getInt("totaltests"));
				country.setTotalCases(rs.getInt("totalcases"));
				country.setTotalMortality(rs.getInt("totalmortality"));
				country.setTotalRecovered(rs.getInt("totalrecovered"));
		    	
		    	countryList.add(country);
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
		return countryList;
	} 
	

}
