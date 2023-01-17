package dwsws.outputdata;

import java.util.Arrays;

public class bulletChartFormatDO {
	String toolname;
	int orgToolscore;
	int totalOrgNumOnTooltable;
	int[] rankingRangeScore;
	int toolMinScore;
	int[] medianAndMaximum;
	public String getToolname() {
		return toolname;
	}
	public void setToolname(String toolname) {
		this.toolname = toolname;
	}
	public int getOrgToolscore() {
		return orgToolscore;
	}
	public void setOrgToolscore(int orgToolscore) {
		this.orgToolscore = orgToolscore;
	}
	public int getTotalOrgNumOnTooltable() {
		return totalOrgNumOnTooltable;
	}
	public void setTotalOrgNumOnTooltable(int totalOrgNumOnTooltable) {
		this.totalOrgNumOnTooltable = totalOrgNumOnTooltable;
	}
	public int[] getRankingRangeScore() {
		return rankingRangeScore;
	}
	public void setRankingRangeScore(int[] rankingRangeScore) {
		this.rankingRangeScore = rankingRangeScore;
	}
	public int getToolMinScore() {
		return toolMinScore;
	}
	public void setToolMinScore(int toolMinScore) {
		this.toolMinScore = toolMinScore;
	}
	public int[] getMedianAndMaximum() {
		return medianAndMaximum;
	}
	public void setMedianAndMaximum(int[] medianAndMaximum) {
		this.medianAndMaximum = medianAndMaximum;
	}
	@Override
	public String toString() {
		return "bulletChartFormatDO [toolname=" + toolname + ", orgToolscore=" + orgToolscore
				+ ", totalOrgNumOnTooltable=" + totalOrgNumOnTooltable + ", rankingRangeScore="
				+ Arrays.toString(rankingRangeScore) + ", toolMinScore=" + toolMinScore + ", medianAndMaximum="
				+ Arrays.toString(medianAndMaximum) + "] \n";
	}
}
