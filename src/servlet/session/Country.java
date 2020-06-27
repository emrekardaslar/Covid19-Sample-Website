package servlet.session;

public class Country {
	private int id;
	private String name;
	private int totalTests=0;
	private int totalCases=0;
	private int totalMortality=0;
	private int totalRecovered=0;
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTotalTests() {
		return totalTests;
	}
	public void setTotalTests(int totalTests) {
		this.totalTests = totalTests;
	}
	public int getTotalCases() {
		return totalCases;
	}
	public void setTotalCases(int totalCases) {
		this.totalCases = totalCases;
	}
	public int getTotalMortality() {
		return totalMortality;
	}
	public void setTotalMortality(int totalMortality) {
		this.totalMortality = totalMortality;
	}
	public int getTotalRecovered() {
		return totalRecovered;
	}
	public void setTotalRecovered(int totalRecovered) {
		this.totalRecovered = totalRecovered;
	}	
	
}
