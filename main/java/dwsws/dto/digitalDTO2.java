package dwsws.dto;

public class digitalDTO2 {
	private int idx;
	private String route;
	private String title; //베아월드
	private String comment; //베아월드 제목
	
	public digitalDTO2(int idx, String route, String title, String comment) {
		this.idx = idx;
		this.route = route;
		this.comment = comment;
		this.title = title;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "digitalDTO2 [idx=" + idx + ", route=" + route + ", title=" + title + ", comment=" + comment + "]";
	}

	

}
