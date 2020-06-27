package servlet.session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	protected static Connection initializeDatabase() //Initialize database 
	        throws SQLException, ClassNotFoundException 
	{ 
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","username","password");
		
		System.out.println("Connection established "+con); 

		return con; 
	} 
}
