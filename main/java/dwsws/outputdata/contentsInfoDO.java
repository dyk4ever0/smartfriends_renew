package dwsws.outputdata;

public class contentsInfoDO {
	private String title;
	private String comments;
	private String youtubeurl;
	private String section;
	private int idx;
	
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
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	@Override
	public String toString() {
		return "contentsInfoDO [title=" + title + ", comments=" + comments + ", youtubeurl=" + youtubeurl + ", section="
				+ section + ", idx=" + idx + "]";
	}

	
	
}
