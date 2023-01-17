package dwsws.dto;

import java.util.Date;

public class questionListDTO {
	private int idx;
	private String question;
	private byte answer;
	private int toolidx;
	private int bmkidx;
	private String updateuserindex;
	private Date updatedate;
	private String entryuserindex;
	private Date entrydate;
	
	public int getToolidx() {
		return toolidx;
	}
	public void setToolidx(int toolidx) {
		this.toolidx = toolidx;
	}
	public int getBmkidx() {
		return bmkidx;
	}
	public void setBmkidx(int bmkidx) {
		this.bmkidx = bmkidx;
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
		return "questionListDTO [idx=" + idx + ", question=" + question + ", answer=" + answer + ", toolidx=" + toolidx
				+ ", bmkidx=" + bmkidx + ", updateuserindex=" + updateuserindex + ", updatedate=" + updatedate
				+ ", entryuserindex=" + entryuserindex + ", entrydate=" + entrydate + "] \n";
	}
	
	
	
}
