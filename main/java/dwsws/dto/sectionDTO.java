package dwsws.dto;

import java.util.Date;

public class sectionDTO {
	private int idx;
	private String comments;
	private int contents;
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
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getContents() {
		return contents;
	}
	public void setContents(int contents) {
		this.contents = contents;
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
		return "sectionDTO [idx=" + idx + ", comments=" + comments + ", contents=" + contents + ", updateuserindex="
				+ updateuserindex + ", updatedate=" + updatedate + ", entryuserindex=" + entryuserindex + ", entrydate="
				+ entrydate + "]";
	}
	
	
	
	
	
}
