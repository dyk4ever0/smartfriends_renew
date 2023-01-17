package dwsws.dto;

import java.util.Date;

public class ruleDTO {
	private int idx;
	private int rule;
	private int bmk;
	private String updateuserindex;
	private Date updatedate;
	private String entryuserindex;
	private Date entrydate;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getRule() {
		return rule;
	}
	public void setRule(int rule) {
		this.rule = rule;
	}
	public int getBmk() {
		return bmk;
	}
	public void setBmk(int bmk) {
		this.bmk = bmk;
	}
	public String getUpdateuserindex() {
		return updateuserindex;
	}
	public void setUpdateuserindex(String updateuserindex) {
		this.updateuserindex = updateuserindex;
	}
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	public String getEntryuserindex() {
		return entryuserindex;
	}
	public void setEntryuserindex(String entryuserindex) {
		this.entryuserindex = entryuserindex;
	}
	public Date getEntrydate() {
		return entrydate;
	}
	public void setEntrydate(Date entrydate) {
		this.entrydate = entrydate;
	}
	@Override
	public String toString() {
		return "ruleDTO [idx=" + idx + ", rule=" + rule + ", bmk=" + bmk + ", updateuserindex=" + updateuserindex
				+ ", updatedate=" + updatedate + ", entryuserindex=" + entryuserindex + ", entrydate=" + entrydate
				+ "]";
	}

	
	
	
	
	
}
