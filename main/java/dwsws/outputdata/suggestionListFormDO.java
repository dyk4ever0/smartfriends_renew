package dwsws.outputdata;

public class suggestionListFormDO {
	private int idx;
	private int toolindex;
	private int secindex;
	private int bmkindex;
	private String category;
	private String toolname;
	private String secname;
	private String bmkcomment;
	private String bmktime;
	private String bmkurl;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getToolindex() {
		return toolindex;
	}
	public void setToolindex(int toolindex) {
		this.toolindex = toolindex;
	}
	public int getSecindex() {
		return secindex;
	}
	public void setSecindex(int secindex) {
		this.secindex = secindex;
	}
	public int getBmkindex() {
		return bmkindex;
	}
	public void setBmkindex(int bmkindex) {
		this.bmkindex = bmkindex;
	}
	public String getToolname() {
		return toolname;
	}
	public void setToolname(String toolname) {
		this.toolname = toolname;
	}
	public String getSecname() {
		return secname;
	}
	public void setSecname(String secname) {
		this.secname = secname;
	}
	public String getBmkcomment() {
		return bmkcomment;
	}
	public void setBmkcomment(String bmkcomment) {
		this.bmkcomment = bmkcomment;
	}
	public String getBmktime() {
		return bmktime;
	}
	public void setBmktime(String bmktime) {
		this.bmktime = bmktime;
	}
	public String getBmkurl() {
		return bmkurl;
	}
	public void setBmkurl(String bmkurl) {
		this.bmkurl = bmkurl;
	}
	@Override
	public String toString() {
		return "suggestionListFormDO [idx=" + idx + ", toolindex=" + toolindex + ", secindex=" + secindex
				+ ", bmkindex=" + bmkindex + ", category=" + category + ", toolname=" + toolname + ", secname="
				+ secname + ", bmkcomment=" + bmkcomment + ", bmktime=" + bmktime + ", bmkurl=" + bmkurl + "] \n";
	}
	
	
}
