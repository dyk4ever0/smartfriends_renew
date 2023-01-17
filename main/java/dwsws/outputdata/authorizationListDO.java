package dwsws.outputdata;

public class authorizationListDO {
	String userindex;
	String username;
	String companyname;
	String orgname;
	String avatar;
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getUserindex() {
		return userindex;
	}
	public void setUserindex(String userindex) {
		this.userindex = userindex;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	@Override
	public String toString() {
		return "authorizationListDO [userindex=" + userindex + ", username=" + username + ", companyname=" + companyname
				+ ", orgname=" + orgname + ", avatar=" + avatar + "] \n";
	}
}
