package dwsws.dto;

import java.util.Date;

public class testOfferToolDTO {
	private int idx;
	private String tool;
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
	public String getTool() {
		return tool;
	}
	public void setTool(String tool) {
		this.tool = tool;
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
		return "testOfferToolDTO [idx=" + idx + ", tool=" + tool + ", updateuserindex=" + updateuserindex
				+ ", updatedate=" + updatedate + ", entryuserindex=" + entryuserindex + ", entrydate=" + entrydate
				+ "]";
	}
	
	
	
	
}
