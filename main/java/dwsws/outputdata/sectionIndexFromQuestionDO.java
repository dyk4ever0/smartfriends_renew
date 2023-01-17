package dwsws.outputdata;

public class sectionIndexFromQuestionDO {
	private int idx;
	private int bmksection;
	private byte recentflag;
	public byte getRecentflag() {
		return recentflag;
	}
	public void setRecentflag(byte recentflag) {
		this.recentflag = recentflag;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getBmksection() {
		return bmksection;
	}
	public void setBmksection(int bmksection) {
		this.bmksection = bmksection;
	}
	@Override
	public String toString() {
		return "sectionIndexFromQuestionDO [idx=" + idx + ", bmksection=" + bmksection + ", recentflag=" + recentflag
				+ "] \n";
	}
}
