package servlet.session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
	public ArrayList<User> getAll() {
		ArrayList <User> userList = new ArrayList<User>();	//Gets all data from database, creates user objects and returns list of those user objects
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DatabaseConnection.initializeDatabase();
			st = con.prepareStatement("SELECT * FROM CovidUser ORDER BY username");
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
		    	User user = new User();
		    	user.setName(rs.getString("username"));
		    	user.setPwd(rs.getString("pwd"));
		    	user.setIsAdmin(rs.getInt("isAdmin"));
		    	userList.add(user);
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
		
		return userList;
	}
	
	public ArrayList<User> getFiltered(String search_text) { //Searches Users By Country username
		ArrayList <User> userList = new ArrayList<User>();	
		Connection con = null;
		PreparedStatement st = null;

		try {
			con = DatabaseConnection.initializeDatabase();
			st = con.prepareStatement("SELECT * FROM CovidUser WHERE username LIKE '%' || ? || '%' ORDER BY username");
			st.setString(1, search_text);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
		    	User user = new User();
		    	user.setName(rs.getString("username"));
		    	user.setPwd(rs.getString("pwd"));
		    	user.setIsAdmin(rs.getInt("isAdmin"));
		    	userList.add(user);
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
		
		return userList;
	}

	
	public User getUser(String name) {
		Connection con = null;
		PreparedStatement st = null;
		User user = null;

		try {
			con = DatabaseConnection.initializeDatabase();
			st = con.prepareStatement("SELECT username, pwd, isAdmin FROM CovidUser WHERE username =?");
			st.setString(1, name);
			ResultSet rs = st.executeQuery();
			
			if (rs.next()) {
				user = new User();
		    	user.setName(rs.getString("username"));
		    	user.setPwd(rs.getString("pwd"));
		    	user.setIsAdmin(rs.getInt("isAdmin"));
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
		
		return user;
	}
	
	public boolean deleteUser(String userToDelete) {
		Connection con = null;
		PreparedStatement st = null;
		boolean result=false;
		int recordnum=0;

		try {
			con = DatabaseConnection.initializeDatabase();
			st = con.prepareStatement("DELETE FROM CovidUser WHERE username = ?");
			st.setString(1, userToDelete);
			recordnum=st.executeUpdate();
			if (recordnum == 1)
				result=true;
		}
		catch(Exception e) {
			System.out.println("Error during deleting the record ("+userToDelete+")");
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
	
	public void saveUser(String userToSave, User user) throws Exception{

		if (user.getName().isEmpty()) {
			throw new Exception("Username cannot be empty");
		}
		
		if (user.getPwd().isEmpty()) {
			throw new Exception("Password cannot be empty");
		}
				
		Connection con = DatabaseConnection.initializeDatabase();
		PreparedStatement st = con.prepareStatement("UPDATE CovidUser SET username=?, pwd=?, isadmin=? WHERE username = ?");
		
		st.setString(1, user.getName());
		st.setString(2, user.getPwd());
		st.setInt(3, user.getIsAdmin());
		st.setString(4, userToSave);
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
	
	public void checkUser(String username, String userToEdit) throws Exception{
		if (username.toLowerCase().contentEquals("admin"))
			throw new Exception("Username cannot be admin");		
		
		int count = 0;
		Connection con = DatabaseConnection.initializeDatabase();
		PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM CovidUser WHERE username = ?");
		st.setString(1, username);
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
	
		if (count != 0 && !username.equals(userToEdit))
			throw new Exception("Username already exists.");	
	}
	
	public void checkUser(String username) throws Exception{
		if (username.toLowerCase().contentEquals("admin"))
			throw new Exception("Username cannot be admin");		
		
		int count = 0;
		Connection con = DatabaseConnection.initializeDatabase();
		PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM CovidUser WHERE username = ?");
		st.setString(1, username);
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
			throw new Exception("Username already exists.");	
	}
	
	public void createUser(String user, String pwd1, String pwd2, int is_admin) throws Exception {
		if (user.isEmpty()) {
			throw new Exception("Username cannot be empty");
		}
		
		if (pwd1.isEmpty()) {
			throw new Exception("Password cannot be empty");
		}

		if (!pwd1.equals(pwd2)) {
			throw new Exception("Passwords do not match");
		}
		
		Connection con = DatabaseConnection.initializeDatabase();
		PreparedStatement st = con.prepareStatement("INSERT INTO CovidUser (username,pwd,isAdmin) VALUES (?,?,?)");
		st.setString(1, user);
		st.setString(2, pwd1);
		st.setInt(3, is_admin);
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
