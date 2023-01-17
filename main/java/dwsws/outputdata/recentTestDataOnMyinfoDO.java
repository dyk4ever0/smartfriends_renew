package dwsws.outputdata;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class recentTestDataOnMyinfoDO {
	private int trialnum;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date entrydate;
	private int totalscore;
	private String skill;
	public int getTrialnum() {
		return trialnum;
	}
	public void setTrialnum(int trialnum) {
		this.trialnum = trialnum;
	}
	public Date getEntrydate() {
		return entrydate;
	}
	public void setEntrydate(Date entrydate) {
		this.entrydate = entrydate;
	}
	public int getTotalscore() {
		return totalscore;
	}
	public void setTotalscore(int totalscore) {
		this.totalscore = totalscore;
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	@Override
	public String toString() {
		return "recentTestDataOnMyinfoDO [trialnum=" + trialnum + ", entrydate=" + entrydate + ", totalscore="
				+ totalscore + ", skill=" + skill + "] \n";
	}
}
