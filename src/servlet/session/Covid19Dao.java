package servlet.session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;

public class Covid19Dao {
	public void createDatabase() throws Exception {
		String[] locales = Locale.getISOCountries();
		ArrayList<String> countries = new ArrayList<String>();
		
		Locale locale;
		for (String countryCode : locales) {
			locale = new Locale("en_US", countryCode);
			countries.add(locale.getDisplayCountry(new Locale("en", "US")));
		}
		
		Connection con = null;
		PreparedStatement st = null;
		Statement stmt = null;
		try {
			con = DatabaseConnection.initializeDatabase();
			
			// Create CovidUser table and fill it with one user named 'admin'
			System.out.println("Creating table CovidUser...");
			st = con.prepareStatement(
				"CREATE TABLE CovidUser(" + 
				"    username VARCHAR2(50) PRIMARY KEY," + 
				"    pwd VARCHAR2(50) NOT NULL," + 
				"    isAdmin NUMBER(1) DEFAULT 0 NOT NULL" + 
				")"
			);
			st.executeUpdate();
			System.out.println("Table CovidUser was created.");
			System.out.println("Inserting admin account...");
			// Create admin account with username "admin and password "admin"
			st = con.prepareStatement(
				"INSERT INTO CovidUser(username, pwd, isAdmin) VALUES(?, ?, ?)"
			);
			st.setString(1, "admin");
			st.setString(2, "admin");
			st.setInt(3, 1);
			st.executeUpdate();
			System.out.println("admin account was inserted.");
			
			// Create Country table and fill it with country names and default statistics as 0.
			System.out.println("Creating table Country...");
			st = con.prepareStatement(
				"CREATE TABLE Country (" + 
				"    id NUMERIC PRIMARY KEY," + 
				"    name VARCHAR2(60) NOT NULL," + 
				"    totaltests NUMERIC DEFAULT 0 NOT NULL," + 
				"    totalcases NUMERIC DEFAULT 0 NOT NULL," + 
				"    totalmortality NUMERIC DEFAULT 0 NOT NULL," + 
				"    totalrecovered NUMERIC DEFAULT 0 NOT NULL" + 
				")"
			);
			st.executeUpdate();
			System.out.println("Table Country was created.");
			System.out.println("Inserting countries...");
			// Create countries with 0 statistics
			for (int i=0; i < countries.size(); i++) {
				st = con.prepareStatement(
					"INSERT INTO Country(id, name) VALUES(?, ?)"
				);
				st.setInt(1, i+1);
				st.setString(2, countries.get(i));
				st.executeUpdate();
			}
			System.out.println("countries was inserted.");
			
			// Create CovidDaily table
			System.out.println("Creating table CovidDaily...");
			st = con.prepareStatement(
				"CREATE TABLE CovidDaily (" + 
				"    cid NUMERIC," + 
				"    cdate DATE," + 
				"    tests NUMERIC DEFAULT 0 NOT NULL," + 
				"    cases NUMERIC DEFAULT 0 NOT NULL," + 
				"    mortality NUMERIC DEFAULT 0 NOT NULL," + 
				"    recovered NUMERIC DEFAULT 0 NOT NULL," + 
				"    PRIMARY KEY (cid,cdate)," + 
				"    FOREIGN KEY (cid) REFERENCES Country" + 
				")"
			);
			st.executeUpdate();
			System.out.println("Table CovidDaily was created.");
			
			// Create GlobalStatistics table
			System.out.println("Creating table GlobalStatistics...");
			st = con.prepareStatement(
				"CREATE TABLE GlobalStatistics(" + 
				"    totaltests NUMERIC DEFAULT 0 NOT NULL," + 
				"    totalcases NUMERIC DEFAULT 0 NOT NULL," + 
				"    totalmortality NUMERIC DEFAULT 0 NOT NULL," + 
				"    totalrecovered NUMERIC DEFAULT 0 NOT NULL" + 
				")"
			);
			st.executeUpdate();
			System.out.println("Table GlobalStatistics was created.");
			System.out.println("Inserting default statistics...");
			st = con.prepareStatement(
				"INSERT INTO GlobalStatistics VALUES(DEFAULT,DEFAULT,DEFAULT,DEFAULT)"
			);
			st.executeUpdate();
			System.out.println("Default statistics was inserted.");
			
			stmt = con.createStatement(); 
			
			// Create triggers 
			System.out.println("Creating trigger coviddaily_after_insert...");
			stmt.execute(
				"CREATE OR REPLACE TRIGGER coviddaily_after_insert " + 
				"AFTER INSERT " + 
				"   ON coviddaily " + 
				"   FOR EACH ROW " + 
				"BEGIN " + 
				"   UPDATE country SET totaltests = totaltests + :new.tests WHERE country.id = :new.cid; " + 
				"   UPDATE country SET totalcases = totalcases + :new.cases WHERE country.id = :new.cid; " + 
				"   UPDATE country SET totalmortality = totalmortality + :new.mortality WHERE country.id = :new.cid; " + 
				"   UPDATE country SET totalrecovered = totalrecovered + :new.recovered WHERE country.id = :new.cid; " + 
				"    " + 
				"   UPDATE globalstatistics SET totaltests = totaltests + :new.tests; " + 
				"   UPDATE globalstatistics SET totalcases = totalcases + :new.cases; " + 
				"   UPDATE globalstatistics SET totalmortality = totalmortality + :new.mortality; " + 
				"   UPDATE globalstatistics SET totalrecovered = totalrecovered + :new.recovered; " + 
				"END;"
			);
			System.out.println("Trigger coviddaily_after_insert was created.");
			
			System.out.println("Creating trigger coviddaily_after_delete...");
			stmt.execute(
				"CREATE OR REPLACE TRIGGER coviddaily_after_delete " + 
				"AFTER DELETE " + 
				"   ON coviddaily " + 
				"   FOR EACH ROW " + 
				"BEGIN " + 
				"   UPDATE country SET totaltests = totaltests - :old.tests WHERE country.id = :old.cid; " + 
				"   UPDATE country SET totalcases = totalcases - :old.cases WHERE country.id = :old.cid; " + 
				"   UPDATE country SET totalmortality = totalmortality - :old.mortality WHERE country.id = :old.cid; " + 
				"   UPDATE country SET totalrecovered = totalrecovered - :old.recovered WHERE country.id = :old.cid; " + 
				"    " + 
				"   UPDATE globalstatistics SET totaltests = totaltests - :old.tests; " + 
				"   UPDATE globalstatistics SET totalcases = totalcases - :old.cases; " + 
				"   UPDATE globalstatistics SET totalmortality = totalmortality - :old.mortality; " + 
				"   UPDATE globalstatistics SET totalrecovered = totalrecovered - :old.recovered; " + 
				"END;"
			);
			System.out.println("Trigger coviddaily_after_delete was created.");
			
			System.out.println("Creating trigger coviddaily_after_update...");
			stmt.execute(
				"CREATE OR REPLACE TRIGGER coviddaily_after_update " + 
				"AFTER UPDATE " + 
				"   ON coviddaily " + 
				"   FOR EACH ROW " + 
				"BEGIN " + 
				"   UPDATE country SET totaltests = totaltests + :new.tests - :old.tests WHERE country.id = :new.cid; " + 
				"   UPDATE country SET totalcases = totalcases + :new.cases - :old.cases WHERE country.id = :new.cid; " + 
				"   UPDATE country SET totalmortality = totalmortality + :new.mortality - :old.mortality WHERE country.id = :new.cid; " + 
				"   UPDATE country SET totalrecovered = totalrecovered + :new.recovered - :old.recovered WHERE country.id = :new.cid; " + 
				"    " + 
				"   UPDATE globalstatistics SET totaltests = totaltests + :new.tests - :old.tests; " + 
				"   UPDATE globalstatistics SET totalcases = totalcases + :new.cases - :old.cases; " + 
				"   UPDATE globalstatistics SET totalmortality = totalmortality + :new.mortality - :old.mortality; " + 
				"   UPDATE globalstatistics SET totalrecovered = totalrecovered + :new.recovered - :old.recovered; " + 
				"END;"
			);
			System.out.println("Trigger coviddaily_after_update was created.");
			
		}
		catch(Exception e) {
			System.out.println("Database schema couldn't be created");
			throw new Exception(e.getMessage());
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
	}
	
	public void dropDatabase() throws Exception {
		Connection con = null;
		PreparedStatement st = null;
		
		try {
			con = DatabaseConnection.initializeDatabase();
			
			// Drop CovidUser table
			System.out.println("Dropping table CovidUser...");
			st = con.prepareStatement("DROP TABLE CovidUser");
			st.executeUpdate();
			System.out.println("Table CovidUser was dropped.");

			// Drop CovidDaily table
			System.out.println("Dropping table CovidDaily...");
			st = con.prepareStatement("DROP TABLE CovidDaily");
			st.executeUpdate();
			System.out.println("Table CovidDaily was dropped.");

			// Drop Country table
			System.out.println("Dropping table Country...");
			st = con.prepareStatement("DROP TABLE Country");
			st.executeUpdate();
			System.out.println("Table Country was dropped.");

			// Drop GlobalStatistics table
			System.out.println("Dropping table GlobalStatistics...");
			st = con.prepareStatement("DROP TABLE GlobalStatistics");
			st.executeUpdate();
			System.out.println("Table GlobalStatistics was dropped.");
			
			st.close();
			con.close();
		}
		
		catch(Exception e) {
			System.out.println("Database schema couldn't be dropped");
			throw new Exception("Database schema couldn't be dropped");
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
	}
	
	public void resetDatabase() throws Exception {
		Connection con = null;
		PreparedStatement st = null;

		try {
			con = DatabaseConnection.initializeDatabase();
			
			// Drop CovidUser table
			System.out.println("Resetting table CovidUser...");
			System.out.println("Deleting table CovidUser...");
			st = con.prepareStatement("DELETE FROM CovidUser");
			st.executeUpdate();
			System.out.println("Table CovidUser was deleted...");

			System.out.println("Inserting admin account...");
			// Create admin account with username "admin and password "admin"
			st = con.prepareStatement(
				"INSERT INTO CovidUser(username, pwd, isAdmin) VALUES(?, ?, ?)"
			);
			st.setString(1, "admin");
			st.setString(2, "admin");
			st.setInt(3, 1);
			st.executeUpdate();
			System.out.println("admin account was inserted.");
			System.out.println("Table CovidUser was reset.");

			// Reset CovidDaily table
			System.out.println("Resetting table CovidDaily...");
			System.out.println("Deleting table CovidDaily...");
			st = con.prepareStatement("DELETE FROM CovidDaily");
			st.executeUpdate();
			System.out.println("Table CovidDaily was deleted...");
			System.out.println("Table CovidDaily was reset.");

			st.close();
			con.close();
		}
		catch(Exception e) {
			System.out.println("Database schema couldn't be reset");
			throw new Exception("Database schema couldn't be reset");
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
	}	
}
