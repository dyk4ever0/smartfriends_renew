package dwsws.outputdata;

public class bmkSelectDO {
	private int idx;
	private String comments;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	@Override
	public String toString() {
		return "bmkSelectDO [idx=" + idx + ", comments=" + comments + "]";
	}
	
	
}
