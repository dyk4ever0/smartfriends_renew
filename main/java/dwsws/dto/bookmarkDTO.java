package dwsws.dto;

import java.util.Date;

public class bookmarkDTO {
	private int idx;
	private Date timemoment;
	private String comments;
	private int section;
	private int smartworkrule;
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
	public Date getTimemoment() {
		return timemoment;
	}
	public void setTimemoment(Date timemoment) {
		this.timemoment = timemoment;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getSection() {
		return section;
	}
	public void setSection(int section) {
		this.section = section;
	}
	public int getSmartworkrule() {
		return smartworkrule;
	}
	public void setSmartworkrule(int smartworkrule) {
		this.smartworkrule = smartworkrule;
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
		return "bookmarkDTO [idx=" + idx + ", timemoment=" + timemoment + ", comments=" + comments + ", section="
				+ section + ", smartworkrule=" + smartworkrule + ", updateuserindex=" + updateuserindex
				+ ", updatedate=" + updatedate + ", entryuserindex=" + entryuserindex + ", entrydate=" + entrydate
				+ "]";
	}
	
	
	

	
	
}
