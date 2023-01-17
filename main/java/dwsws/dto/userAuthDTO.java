package dwsws.dto;

import java.util.Date;

public class userAuthDTO {
	String userindex;
	byte editauth;
	byte dataauth;
	String updateuserindex;
	Date updatedate;
	String entryuserindex;
	Date entrydate;
	public String getUserindex() {
		return userindex;
	}
	public void setUserindex(String userindex) {
		this.userindex = userindex;
	}
	public byte getEditauth() {
		return editauth;
	}
	public void setEditauth(byte editauth) {
		this.editauth = editauth;
	}
	public byte getDataauth() {
		return dataauth;
	}
	public void setDataauth(byte dataauth) {
		this.dataauth = dataauth;
	}
	public String getUpdateuserindex() {
		return updateuserindex;
	}
	public void setUpdateuserindex(String updateuserindex) {
		this.updateuserindex = updateuserindex;
	}
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	public String getEntryuserindex() {
		return entryuserindex;
	}
	public void setEntryuserindex(String entryuserindex) {
		this.entryuserindex = entryuserindex;
	}
	public Date getEntrydate() {
		return entrydate;
	}
	public void setEntrydate(Date entrydate) {
		this.entrydate = entrydate;
	}
	@Override
	public String toString() {
		return "userAuthDTO [userindex=" + userindex + ", editauth=" + editauth + ", dataauth=" + dataauth
				+ ", updateuserindex=" + updateuserindex + ", updatedate=" + updatedate + ", entryuserindex="
				+ entryuserindex + ", entrydate=" + entrydate + "] \n";
	}
	
	
}
