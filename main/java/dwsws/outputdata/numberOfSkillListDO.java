package dwsws.outputdata;

public class numberOfSkillListDO {
	private int veryhigh;
	private int high;
	private int normal;
	private int low;
	private int verylow;
	
	public int getVeryhigh() {
		return veryhigh;
	}
	public void setVeryhigh(int veryhigh) {
		this.veryhigh = veryhigh;
	}
	public int getHigh() {
		return high;
	}
	public void setHigh(int high) {
		this.high = high;
	}
	public int getNormal() {
		return normal;
	}
	public void setNormal(int normal) {
		this.normal = normal;
	}
	public int getLow() {
		return low;
	}
	public void setLow(int low) {
		this.low = low;
	}
	public int getVerylow() {
		return verylow;
	}
	public void setVerylow(int verylow) {
		this.verylow = verylow;
	}
	@Override
	public String toString() {
		return "numberOfSkillListDO [veryhigh=" + veryhigh + ", high=" + high + ", normal=" + normal + ", low=" + low
				+ ", verylow=" + verylow + "] \n";
	}
}
