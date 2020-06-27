package servlet.session;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CovidDailyDao {
	
	public ArrayList<CovidDaily> getAll() {
		ArrayList <CovidDaily> covidDailyList = new ArrayList<CovidDaily>();			
		Connection con = null;
		PreparedStatement st = null;
		
		try {
			con = DatabaseConnection.initializeDatabase();
			st = con.prepareStatement("SELECT CovidDaily.*, Country.name AS cname FROM CovidDaily, Country WHERE CovidDaily.cid = Country.id ORDER BY CovidDaily.cdate DESC, Country.name");
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				CovidDaily covidDaily = new CovidDaily();
				covidDaily.setCid(rs.getInt("cid"));
				covidDaily.setCname(rs.getString("cname"));
				covidDaily.setCdate(rs.getDate("cdate"));
				covidDaily.setTests(rs.getInt("tests"));
				covidDaily.setCases(rs.getInt("cases"));
				covidDaily.setMortality(rs.getInt("mortality"));
				covidDaily.setRecovered(rs.getInt("recovered"));
				
				covidDailyList.add(covidDaily);
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
		
		return covidDailyList;
	}
	
	public CovidDaily getDailyData(String cidStr, String dateStr) {					
		Connection con = null;
		PreparedStatement st = null;
		CovidDaily covidDaily = new CovidDaily();	
		int cid = -1; //***
		try {
			cid = Integer.parseInt(cidStr);
		}
		catch (Exception e) {
			System.out.println(cid); //***
			return covidDaily;
		}
		
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.setLenient(false); 
			df.parse(dateStr);
		}
		catch (Exception e) {
			System.out.println("date error"); //***
			return covidDaily;
		}
		
		Date cdate = Date.valueOf(dateStr);
		
		try {	
			con = DatabaseConnection.initializeDatabase();
			st = con.prepareStatement("SELECT * FROM CovidDaily WHERE cdate = ? AND cid = ?");
			st.setDate(1, cdate);
			st.setInt(2, cid);
			
			ResultSet rs = st.executeQuery();
			System.out.println("executed query"); //***
			
			if (rs.next()) {
				System.out.println("found some results"); //***
				covidDaily.setCid(rs.getInt("cid"));
				covidDaily.setCdate(rs.getDate("cdate"));
				covidDaily.setTests(rs.getInt("tests"));
				covidDaily.setCases(rs.getInt("cases"));
				covidDaily.setMortality(rs.getInt("mortality"));
				covidDaily.setRecovered(rs.getInt("recovered"));
		    }
		}
		catch(Exception e) {
			
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
		
		return covidDaily;
	}

	
	public ArrayList<CovidDaily> getFiltered(String search_date1, String search_date2, String cidStr, boolean Descending) throws Exception { //Searches Covid Daily Data By Country Name
		ArrayList <CovidDaily> covidDailyList = new ArrayList<CovidDaily>();			
		Connection con = null;
		PreparedStatement st = null;
		int cid;
		
		Date cdate1 = Date.valueOf("2000-01-01");
		Date cdate2 = Date.valueOf("2000-01-01");
		

		try {
			cid = Integer.parseInt(cidStr);
		}
		catch (Exception e) {
			throw new Exception("A valid Country must be specified");
		}
		
		if (search_date1 != null && !search_date1.equals("")) {
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				df.setLenient(false); 
				df.parse(search_date1);
			}
			catch (Exception e) {
				throw new Exception("A valid date must be specified");
			}
			cdate1 = Date.valueOf(search_date1);
		}
		
		if (search_date2 != null && !search_date2.equals("")) {
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				df.setLenient(false); 
				df.parse(search_date2);
			}
			catch (Exception e) {
				throw new Exception("A valid date must be specified");
			}
			cdate2 = Date.valueOf(search_date2);
		}

		int paramId = 1;
		
		try {
			con = DatabaseConnection.initializeDatabase();
			String qry = "SELECT CovidDaily.*, Country.name AS cname FROM CovidDaily, Country WHERE CovidDaily.cid = Country.id ";
			
			if (search_date1 != null && !search_date1.equals("")) {
				qry += " AND cdate >= ? ";
			}
			if (search_date2 != null && !search_date2.equals("")) {
				qry += " AND cdate <= ? ";
			}
			qry += " AND CovidDaily.cid = ?";
			if (Descending)
				qry += " ORDER BY CovidDaily.cdate DESC";
			else
				qry += " ORDER BY CovidDaily.cdate ASC";
			
			st = con.prepareStatement(qry);
			if (search_date1 != null && !search_date1.equals("")) {
				st.setDate(paramId, cdate1);
				paramId++;
			}
			if (search_date2 != null && !search_date2.equals("")) {
				st.setDate(paramId, cdate2);
				paramId++;
			}
			st.setInt(paramId, cid);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				CovidDaily covidDaily = new CovidDaily();
				covidDaily.setCid(rs.getInt("cid"));
				covidDaily.setCname(rs.getString("cname"));
				covidDaily.setCdate(rs.getDate("cdate"));
				covidDaily.setTests(rs.getInt("tests"));
				covidDaily.setCases(rs.getInt("cases"));
				covidDaily.setMortality(rs.getInt("mortality"));
				covidDaily.setRecovered(rs.getInt("recovered"));
				
				covidDailyList.add(covidDaily);
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
		
		return covidDailyList;
	}
	
	public boolean deleteCovidDaily(int recordToDelete, Date cdate) {
		Connection con = null;
		PreparedStatement st = null;
		boolean result=false;
		int recordnum=0;
		try {
			con = DatabaseConnection.initializeDatabase();
			st = con.prepareStatement("DELETE FROM CovidDaily WHERE cid = ? AND cdate= ?");
			st.setInt(1, recordToDelete);
			st.setDate(2, cdate);
			recordnum=st.executeUpdate();
			
			if (recordnum == 1)
				result=true;
		}
		catch(Exception e) {
			System.out.println("Error during deleting the record ("+recordToDelete+")");
			System.out.println(e.getMessage());
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
		return result;
	}
		
	public void checkDailyData(String dateStr, String cidStr) throws Exception{					
		int count = 0;		
		int cid;
		try {
			cid = Integer.parseInt(cidStr);
		}
		catch (Exception e) {
			throw new Exception("A valid Country must be specified");
		}
		
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.setLenient(false); 
			df.parse(dateStr);
		}
		catch (Exception e) {
			throw new Exception("A valid date must be specified");
		}
		
		Date cdate = Date.valueOf(dateStr);
		
		
		Connection con = DatabaseConnection.initializeDatabase();
		PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM CovidDaily WHERE cdate = ? AND cid= ?");
		st.setDate(1, cdate);
		st.setInt(2, cid);
		
		ResultSet rs = st.executeQuery();
		
		if (rs.next()) {
			count = rs.getInt(1);
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
		
		if (count != 0)
			throw new Exception("Covid Daily data already exist for specified date and country");	
	}
	
	
	public void checkDailyData(String cidStr, String cdateStr, String originalCidStr, String originalCdateStr) throws Exception{					
		int count = 0;		
		int cid;
		try {
			cid = Integer.parseInt(cidStr);
		}
		catch (Exception e) {
			throw new Exception("A valid Country must be specified");
		}
		
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.setLenient(false); 
			df.parse(cdateStr);
		}
		catch (Exception e) {
			throw new Exception("A valid date must be specified");
		}
		
		Date cdate = Date.valueOf(cdateStr);
		
		
		Connection con = DatabaseConnection.initializeDatabase();
		PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM CovidDaily WHERE cdate = ? AND cid= ?");
		st.setDate(1, cdate);
		st.setInt(2, cid);
		
		ResultSet rs = st.executeQuery();
		
		if (rs.next()) {
			count = rs.getInt(1);
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
		
		if (count != 0 && (!cidStr.equals(originalCidStr) || !cdateStr.equals(originalCdateStr)))
			throw new Exception("Covid Daily data already exist for specified date and country");	
	}
	
	public void createDailyData(String dateStr, String cidStr, String testsStr, String casesStr, String mortalityStr, String recoveredStr) throws Exception {		
		int cid;
		int tests;
		int cases;
		int mortality;
		int recovered;
		
		try {
			cid = Integer.parseInt(cidStr);
		}
		catch (Exception e) {
			throw new Exception("A valid Country must be specified");
		}
		
		try {
			tests = Integer.parseInt(testsStr);
		}
		catch (Exception e) {
			throw new Exception("A valid number of tests must be specified");
		}
		if (tests<0)
			throw new Exception("Number of tests must be positive number");
		
		try {
			cases = Integer.parseInt(casesStr);
		}
		catch (Exception e) {
			throw new Exception("A valid number of cases must be specified");
		}
		if (cases<0)
			throw new Exception("Number of cases must be positive number");
		
		try {
			mortality = Integer.parseInt(mortalityStr);
		}
		catch (Exception e) {
			throw new Exception("A valid number of mortalities must be specified");
		}
		if (mortality<0)
			throw new Exception("Number of mortalities must be positive number");
		
		try {
			recovered = Integer.parseInt(recoveredStr);
		}
		catch (Exception e) {
			throw new Exception("A valid number of recovered people must be specified");
		}
		if (recovered<0)
			throw new Exception("Number of recovered people must be positive number");
			
		Date cdate = Date.valueOf(dateStr);					
		Connection con = DatabaseConnection.initializeDatabase();
		PreparedStatement st = con.prepareStatement("INSERT INTO CovidDaily (cid, cdate,tests,cases,mortality,recovered) VALUES(?,?,?,?,?,?)");
		st.setInt(1, cid);
		st.setDate(2, cdate);
		st.setInt(3,tests);
		st.setInt(4, cases);
		st.setInt(5, mortality);
		st.setInt(6, recovered);
		st.executeUpdate();
		
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
	}	
	
	public void saveCovidDaily(String cidToSave, String dateToSave, String cidStr, String dateStr, String testsStr, String casesStr, String mortalityStr, String recoveredStr) throws Exception{
		int cidOriginal;	
		try {
			cidOriginal = Integer.parseInt(cidToSave);
		}
		catch (Exception e) {
			throw new Exception("A valid Country must be specified");
		}
		
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.setLenient(false); 
			df.parse(dateToSave);
		}
		catch (Exception e) {
			throw new Exception("A valid date must be specified");
		}
		
		Date cdateOriginal = Date.valueOf(dateToSave);	
		
		int cid;
		int tests;
		int cases;
		int mortality;
		int recovered;
		
		try {
			cid = Integer.parseInt(cidStr);
		}
		catch (Exception e) {
			throw new Exception("A valid Country must be specified");
		}
		
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.setLenient(false); 
			df.parse(dateStr);
		}
		catch (Exception e) {
			throw new Exception("A valid date must be specified");
		}
		
		Date cdate = Date.valueOf(dateStr);
		
		try {
			tests = Integer.parseInt(testsStr);
		}
		catch (Exception e) {
			throw new Exception("A valid number of tests must be specified");
		}
		if (tests<0)
			throw new Exception("Number of tests must be positive number");
		
		try {
			cases = Integer.parseInt(casesStr);
		}
		catch (Exception e) {
			throw new Exception("A valid number of cases must be specified");
		}
		if (cases<0)
			throw new Exception("Number of cases must be positive number");
		
		try {
			mortality = Integer.parseInt(mortalityStr);
		}
		catch (Exception e) {
			throw new Exception("A valid number of mortalities must be specified");
		}
		if (mortality<0)
			throw new Exception("Number of mortalities must be positive number");
		
		try {
			recovered = Integer.parseInt(recoveredStr);
		}
		catch (Exception e) {
			throw new Exception("A valid number of recovered people must be specified");
		}
		if (recovered<0)
			throw new Exception("Number of recovered people must be positive number");
		
		
		
		Connection con = DatabaseConnection.initializeDatabase();
		PreparedStatement st = con.prepareStatement("UPDATE CovidDaily SET cid = ?, cdate = ?, tests = ?, cases = ?, mortality = ?, recovered = ? WHERE cid = ? AND cdate = ?");
		
		st.setInt(1, cid);
		st.setDate(2, cdate);
		st.setInt(3, tests);
		st.setInt(4, cases);
		st.setInt(5, mortality);
		st.setInt(6, recovered);			
		st.setInt(7, cidOriginal);
		st.setDate(8, cdateOriginal);
		
		st.executeUpdate();
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
	}
}
