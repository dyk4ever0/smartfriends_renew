package dwsws.dto;

import java.util.Date;

public class favoriteListDTO {
	private int idx;
	private String userindex;
	private int bookmark;
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
	public String getUserindex() {
		return userindex;
	}
	public void setUserindex(String userindex) {
		this.userindex = userindex;
	}
	public int getBookmark() {
		return bookmark;
	}
	public void setBookmark(int bookmark) {
		this.bookmark = bookmark;
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
		return "favoriteListDTO [idx=" + idx + ", userindex=" + userindex + ", bookmark=" + bookmark
				+ ", updateuserindex=" + updateuserindex + ", updatedate=" + updatedate + ", entryuserindex="
				+ entryuserindex + ", entrydate=" + entrydate + "]";
	}
	
	
	
	
	
	
	
}
