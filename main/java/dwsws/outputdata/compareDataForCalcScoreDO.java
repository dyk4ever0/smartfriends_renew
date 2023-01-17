package dwsws.outputdata;

import java.util.Date;

public class compareDataForCalcScoreDO {
	
	private int questionidx;
	private int bmkidx;
	private int secidx;
	private int toolidx;
	private String question;
	private byte answer;
	private String updateuserindex;
	private Date updatedate;
	private String entryuserindex;
	private Date entrydate;
	public int getQuestionidx() {
		return questionidx;
	}
	public void setQuestionidx(int questionidx) {
		this.questionidx = questionidx;
	}
	public int getBmkidx() {
		return bmkidx;
	}
	public void setBmkidx(int bmkidx) {
		this.bmkidx = bmkidx;
	}
	public int getSecidx() {
		return secidx;
	}
	public void setSecidx(int secidx) {
		this.secidx = secidx;
	}
	public int getToolidx() {
		return toolidx;
	}
	public void setToolidx(int toolidx) {
		this.toolidx = toolidx;
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
		return "compareDataForCalcScoreDO [questionidx=" + questionidx + ", bmkidx=" + bmkidx + ", secidx=" + secidx
				+ ", toolidx=" + toolidx + ", question=" + question + ", answer=" + answer + ", updateuserindex="
				+ updateuserindex + ", updatedate=" + updatedate + ", entryuserindex=" + entryuserindex + ", entrydate="
				+ entrydate + "] \n";
	}
	
}
