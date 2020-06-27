package servlet.session;

public class User {
	private String name;
	private String pwd;
	private int isAdmin;
	
	public User() {
		this.name="";
		this.pwd="";
		this.isAdmin=0;
	}
	
	public User(String name, String pwd, int isAdmin) {
		super();
		this.name = name;
		this.pwd = pwd;
		this.isAdmin = isAdmin;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}
	

}
