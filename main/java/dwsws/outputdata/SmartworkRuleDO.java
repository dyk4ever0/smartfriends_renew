package dwsws.outputdata;

public class SmartworkRuleDO {
	
	private int bidx;
	private String title;
	private String comments;
	private String bkcomments;
	private String tool;
	private int timemoment;
	private String youtubeurl;
	public int getBidx() {
		return bidx;
	}
	public void setBidx(int bidx) {
		this.bidx = bidx;
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
	public String getBkcomments() {
		return bkcomments;
	}
	public void setBkcomments(String bkcomments) {
		this.bkcomments = bkcomments;
	}
	public String getTool() {
		return tool;
	}
	public void setTool(String tool) {
		this.tool = tool;
	}
	public int getTimemoment() {
		return timemoment;
	}
	public void setTimemoment(int timemoment) {
		this.timemoment = timemoment;
	}
	public String getYoutubeurl() {
		return youtubeurl;
	}
	public void setYoutubeurl(String youtubeurl) {
		this.youtubeurl = youtubeurl;
	}
	@Override
	public String toString() {
		return "SmartworkRuleDO [bidx=" + bidx + ", title=" + title + ", comments=" + comments + ", bkcomments="
				+ bkcomments + ", tool=" + tool + ", timemoment=" + timemoment + ", youtubeurl=" + youtubeurl + "]";
	}
	
	
}
