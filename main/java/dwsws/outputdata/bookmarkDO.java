package dwsws.outputdata;

public class bookmarkDO {
	private int idx;
	private String section;
	private int timemoment;
	private String bookmark;
	private String youtubeurl;
	private int bmkidx;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public int getTimemoment() {
		return timemoment;
	}
	public void setTimemoment(int timemoment) {
		this.timemoment = timemoment;
	}
	public String getBookmark() {
		return bookmark;
	}
	public void setBookmark(String bookmark) {
		this.bookmark = bookmark;
	}
	public String getYoutubeurl() {
		return youtubeurl;
	}
	public void setYoutubeurl(String youtubeurl) {
		this.youtubeurl = youtubeurl;
	}
	public int getBmkidx() {
		return bmkidx;
	}
	public void setBmkidx(int bmkidx) {
		this.bmkidx = bmkidx;
	}
	@Override
	public String toString() {
		return "bookmarkDO [idx=" + idx + ", section=" + section + ", timemoment=" + timemoment + ", bookmark="
				+ bookmark + ", youtubeurl=" + youtubeurl + ", bmkidx=" + bmkidx + "]";
	}
	

	
}
