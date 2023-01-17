package dwsws.outputdata;

import java.sql.Date;

public class questionManagementListDO {
	private int idx;
	private String question;
	private byte answer;
	private int toolidx;
	private String toolname;
	private int secidx;
	private String seccomment;
	private int bmkidx;
	private String bmkcomment;
	private String updateuserindex;
	private Date updatedate;
	private String entryuserindex;
	private Date entrydate;
	
	public int getSecidx() {
		return secidx;
	}
	public void setSecidx(int secidx) {
		this.secidx = secidx;
	}
	public String getSeccomment() {
		return seccomment;
	}
	public void setSeccomment(String seccomment) {
		this.seccomment = seccomment;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public byte getAnswer() {
		return answer;
	}
	public void setAnswer(byte answer) {
		this.answer = answer;
	}
	public int getToolidx() {
		return toolidx;
	}
	public void setToolidx(int toolidx) {
		this.toolidx = toolidx;
	}
	public String getToolname() {
		return toolname;
	}
	public void setToolname(String toolname) {
		this.toolname = toolname;
	}
	public int getBmkidx() {
		return bmkidx;
	}
	public void setBmkidx(int bmkidx) {
		this.bmkidx = bmkidx;
	}
	public String getBmkcomment() {
		return bmkcomment;
	}
	public void setBmkcomment(String bmkcomment) {
		this.bmkcomment = bmkcomment;
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
		return "questionManagementListDO [idx=" + idx + ", question=" + question + ", answer=" + answer + ", toolidx="
				+ toolidx + ", toolname=" + toolname + ", secidx=" + secidx + ", seccomment=" + seccomment + ", bmkidx="
				+ bmkidx + ", bmkcomment=" + bmkcomment + ", updateuserindex=" + updateuserindex + ", updatedate="
				+ updatedate + ", entryuserindex=" + entryuserindex + ", entrydate=" + entrydate + "]\n";
	}
	
}
