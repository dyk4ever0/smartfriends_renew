package dwsws.dto;

import java.util.Date;

public class smartworkRuleDTO {
	private int idx;
	private String title;
	private String comments;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
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
		return "smartworkRuleDTO [idx=" + idx + ", title=" + title + ", comments=" + comments + ", updateuserindex="
				+ updateuserindex + ", updatedate=" + updatedate + ", entryuserindex=" + entryuserindex + ", entrydate="
				+ entrydate + "]";
	}
	
	
	
}
