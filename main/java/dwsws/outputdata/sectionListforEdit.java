package dwsws.outputdata;

public class sectionListforEdit {
	private int sectionIdx;
	private String comments;
	
	public int getSectionIdx() {
		return sectionIdx;
	}
	public void setSectionIdx(int sectionIdx) {
		this.sectionIdx = sectionIdx;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	@Override
	public String toString() {
		return "sectionListforEdit [sectionIdx=" + sectionIdx + ", comments=" + comments + "]";
	}
	
	
}
