package dwsws.outputdata;

public class UserFavoriteList {
	private String userindex;
	private int bookmark;
	private String comments;
	private String title;
	private int timemoment;
	private String category;
	private int section;
	
	public int getSection() {
		return section;
	}
	public void setSection(int section) {
		this.section = section;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getTimemoment() {
		return timemoment;
	}
	public void setTimemoment(int timemoment) {
		this.timemoment = timemoment;
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
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "UserFavoriteList [userindex=" + userindex + ", bookmark=" + bookmark + ", comments=" + comments
				+ ", title=" + title + ", timemoment=" + timemoment + ", category=" + category + ", section=" + section
				+ "]";
	}
	


	
	
}
