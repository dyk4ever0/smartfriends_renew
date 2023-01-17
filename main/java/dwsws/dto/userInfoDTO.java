package dwsws.dto;

import java.util.Date;

public class userInfoDTO {
	private String userindex;
	private String avatar;
	private String username;
	private String companycode;
	private String companyname;
	private String orgcode;
	private String orgname;
	private String userid;
	private String password;
	private int dutycode;
	private Date entrydate;
	private String entryuserindex;
	private Date updatedate;
	private String updateuserindex;
	public String getUserindex() {
		return userindex;
	}
	public void setUserindex(String userindex) {
		this.userindex = userindex;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCompanycode() {
		return companycode;
	}
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getOrgcode() {
		return orgcode;
	}
	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getDutycode() {
		return dutycode;
	}
	public void setDutycode(int dutycode) {
		this.dutycode = dutycode;
	}
	public Date getEntrydate() {
		return entrydate;
	}
	public void setEntrydate(Date entrydate) {
		this.entrydate = entrydate;
	}
	public String getEntryuserindex() {
		return entryuserindex;
	}
	public void setEntryuserindex(String entryuserindex) {
		this.entryuserindex = entryuserindex;
	}
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	public String getUpdateuserindex() {
		return updateuserindex;
	}
	public void setUpdateuserindex(String updateuserindex) {
		this.updateuserindex = updateuserindex;
	}
	@Override
	public String toString() {
		return "userInfoDTO [userindex=" + userindex + ", avatar=" + avatar + ", username=" + username
				+ ", companycode=" + companycode + ", companyname=" + companyname + ", orgcode=" + orgcode
				+ ", orgname=" + orgname + ", userid=" + userid + ", password=" + password + ", dutycode=" + dutycode
				+ ", entrydate=" + entrydate + ", entryuserindex=" + entryuserindex + ", updatedate=" + updatedate
				+ ", updateuserindex=" + updateuserindex + "] \n";
	}
	
	
	
}
