package dwsws.dto;

import java.util.Date;

public class contentsDTO {
	private int idx;
	private String title;
	private String comments;
	private String youtubeurl;
	private int category;
	private int toolimg;
	private String updateuserindex;
	private Date updatedate;
	private String entryuserindex;
	private Date entrydate;
	private String onedesc;
	
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
	public String getYoutubeurl() {
		return youtubeurl;
	}
	public void setYoutubeurl(String youtubeurl) {
		this.youtubeurl = youtubeurl;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public int getToolimg() {
		return toolimg;
	}
	public void setToolimg(int toolimg) {
		this.toolimg = toolimg;
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
	public String getOnedesc() {
		return onedesc;
	}
	public void setOnedesc(String onedesc) {
		this.onedesc = onedesc;
	}
	@Override
	public String toString() {
		return "contentsDTO [idx=" + idx + ", title=" + title + ", comments=" + comments + ", youtubeurl=" + youtubeurl
				+ ", category=" + category + ", toolimg=" + toolimg + ", updateuserindex=" + updateuserindex
				+ ", updatedate=" + updatedate + ", entryuserindex=" + entryuserindex + ", entrydate=" + entrydate
				+ ", onedesc=" + onedesc + "]";
	}

	
	
}
