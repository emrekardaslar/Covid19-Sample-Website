package servlet.session;

import java.sql.Date;

public class CovidDaily {
	private int cid;
	private String cname;
	private Date cdate;
	private int tests=0;
	private int cases=0;
	private int mortality=0;
	private int recovered=0;
	
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public Date getCdate() {
		return cdate;
	}
	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
	public int getTests() {
		return tests;
	}
	public void setTests(int tests) {
		this.tests = tests;
	}
	public int getCases() {
		return cases;
	}
	public void setCases(int cases) {
		this.cases = cases;
	}
	public int getMortality() {
		return mortality;
	}
	public void setMortality(int mortality) {
		this.mortality = mortality;
	}
	public int getRecovered() {
		return recovered;
	}
	public void setRecovered(int recovered) {
		this.recovered = recovered;
	}
}
