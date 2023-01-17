package dwsws.dto;

import java.util.List;

import net.sf.json.JSONArray;

public class digitalDTO {
	private int idx;
	private JSONArray route;
	private String title; //베아월드
	private String comment; //베아월드 제목

	
	public digitalDTO(int idx, JSONArray route, String title, String comment) {
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
	public JSONArray getRoute() {
		return route;
	}
	public void setRoute(JSONArray route) {
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
	
}
