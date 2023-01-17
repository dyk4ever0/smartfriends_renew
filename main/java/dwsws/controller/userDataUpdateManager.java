package dwsws.controller;

import static dwsws.config.dataSourceDBConnectInfo.GroupWareTokenGetURL;
import static dwsws.config.dataSourceDBConnectInfo.GroupWareUserInfoGetURL;
import static dwsws.config.dataSourceDBConnectInfo.userInfoAPIId;
import static dwsws.config.dataSourceDBConnectInfo.userInfoAPIPassword;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.core.util.IOUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dwsws.dto.userInfoDTO;
import dwsws.service.userDataUpdateService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * system - 베어월드 인사정보 연동
 * 
 * - getToken : 베어월드 API 서버로부터 인사정보 획득을 위한 token값을 받아옴 
 * - getUserList : 받아온 token값을 통해 인사정보를 받아옴 
 * - userInfoDBSync : 받아온 인사정보를 스마트워크 프렌즈의 DB에 insert함. 
 * 
 * @Autowired
	userDataUpdateService userupdate;
	
 * @author 박승수 
 *
 */
@Controller
public class userDataUpdateManager {

	@Autowired
	userDataUpdateService userupdate;

	/**
	 * 베어월드 API 서버로부터 인사정보 획득을 위한 token값을 받아옴 
	 * 
	 * @return String 서버로부터 받아온 token값.
	 * 
	 * @author 박승수 
	 */
	public String getToken() {
		String ticket = "";

		try {
			URL ticketURL = new URL(GroupWareTokenGetURL + userInfoAPIId + "&pw=" + userInfoAPIPassword);
			BufferedReader outerConnection = new BufferedReader(new InputStreamReader(ticketURL.openStream()));
			StringBuffer returnedTicket = new StringBuffer();
			String inputLine;
			while ((inputLine = outerConnection.readLine()) != null) {
				returnedTicket.append(inputLine);
			}
			Document returnXML = new SAXBuilder().build(new StringReader(returnedTicket.toString()));
			Element temp = returnXML.getRootElement();
			ticket = temp.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticket;
	}

	/**
	 * 받아온 token값을 통해 인사정보를 받아옴 
	 * 
	 * @param ticket 베어월드 API 서버로부터 수령한 token값 
	 * @return List<userInfoDTO> 베어월드 서버로부터 받은 개인 인사정보의 List
	 * 
	 * @author 박승수 
	 */
	public List<userInfoDTO> getUserList(String ticket) {
		List<userInfoDTO> resultUserList = new ArrayList<userInfoDTO>();

		try {
			// Preparation for setting up connection to Groupware API server
			URL userListURL = new URL(GroupWareUserInfoGetURL + ticket);
			HttpURLConnection userListConnection = (HttpURLConnection) userListURL.openConnection();
			userListConnection.setRequestMethod("GET");
			userListConnection.setRequestProperty("Content-Type", "text/json");

			// URL Connection Error check
			if (userListConnection.getResponseCode() != 200) {
				throw new Exception(
						"Connection Failed : Http error code as below \n" + userListConnection.getResponseCode());
			}

			// Make returned JSON type variable
			BufferedReader returnedDataReader = new BufferedReader(
					new InputStreamReader((userListConnection.getInputStream()), "UTF-8"));
			String returnedString = IOUtils.toString(returnedDataReader);
			JSONArray returnedJsonResult = (JSONArray) JSONSerializer.toJSON(returnedString);

			// Refine returned JSON data and returns List type data so that can be insert
			// into DB
			for (int i = 0; i < returnedJsonResult.length(); i++) {
				JSONObject returnedJsonObject = returnedJsonResult.getJSONObject(i);
				userInfoDTO userInfo = new userInfoDTO();

				// only get Primary job info
				if (((String) returnedJsonObject.get("PRIMARY_YN")).equals("Y") == true) {
					userInfo.setUserindex((String) returnedJsonObject.get("USERINDEX"));
					if (returnedJsonObject.has("AVATAR"))
						userInfo.setAvatar((String) returnedJsonObject.get("AVATAR"));
					userInfo.setUsername((String) returnedJsonObject.get("USER_NAME"));
					userInfo.setCompanycode((String) returnedJsonObject.get("COMPANY_CODE"));
					userInfo.setCompanyname((String) returnedJsonObject.get("COMPANY_NAME"));
					userInfo.setOrgcode((String) returnedJsonObject.get("ORG_CODE"));
					userInfo.setOrgname((String) returnedJsonObject.get("ORG_NAME"));
					userInfo.setUserid((String) returnedJsonObject.get("USERID"));
					userInfo.setPassword((String) returnedJsonObject.getString("PWD"));
					userInfo.setDutycode((Integer) returnedJsonObject.get("DUTY_CODE"));
					userInfo.setEntryuserindex("System_Sync");
					userInfo.setUpdateuserindex("System_Sync");

					resultUserList.add(userInfo);
				}
			}

			userListConnection.disconnect();
			returnedDataReader.close();
			
			System.out.println("User Update Done");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultUserList;
	}

	/**
	 * 받아온 인사정보를 스마트워크 프렌즈의 DB에 insert함. 
	 * 
	 * @author 박승수 
	 */
	@RequestMapping(path = "/syncuserdata")
	public void userInfoDBSync() {
		try {
			String ticket = getToken();
			List<userInfoDTO> groupwareUserData = getUserList(ticket);
			String result = userupdate.insertUserData(groupwareUserData);
			System.out.println("User Data Update by Daily Scheduler successfully done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
