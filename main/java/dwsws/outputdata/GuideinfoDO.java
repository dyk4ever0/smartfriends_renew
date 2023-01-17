package dwsws.outputdata;

public class GuideinfoDO {

	private String title;
	private String comments;
	private String youtubeurl;
	
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
	@Override
	public String toString() {
		return "GuideinfoDO [title=" + title + ", comments=" + comments + ", youtubeurl=" + youtubeurl + "]";
	}
	

	
	
	
}
