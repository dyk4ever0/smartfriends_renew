package dwsws.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import dwsws.dto.userAuthDTO;
import dwsws.outputdata.authorizationListDO;
import dwsws.service.authorizationService;

/**
 * admin - 사용자 권한관리 페이지
 * 
 * - checkUserAuthentication : 현재 로그인된 사용자 계정에 부여된 권한 상태값을 return
 * - callAuthUserListByAuthType : 권한 종류에 따라, 해당 권한을 가진 사용자 목록을 return
 * - insertUserAuth : 특정 유저에 대해 권한을 부여함.
 * - deleteUserAuth : 특정 유저에 대해 권한을 회수함.
 * - returnSearchedUser : 특정 유저의 이름을 검색하여 authDO에 맞는 형식을 return
 * - linkToAuthManagement : 유저 권한 관리 페이지로 redirect
 * 
 * @Autowired
	authorizationService edit;
	
 * @author 박승수
 *
 */
@Controller
public class authorizationController {
	@Autowired
	authorizationService edit;

	/**
	 * 현재 로그인된 사용자 계정에 부여된 권한 상태값을 return
	 * 
	 * @RequestMapping(path = "/authCheck")
	 * @ResponseBody
	 *
	 * @param session 세션에 저장된 사용자 정보를 활
	 * @return jsonTypeString userAuthDTO가 json으로 변환됨.
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@RequestMapping(path = "/authCheck")
	@ResponseBody
	public String checkUserAuthentication(HttpSession session) throws Exception {
		String userindex = session.getAttribute("userindex").toString();
		userAuthDTO currentUserAuthInfo = new userAuthDTO();
		if (!userindex.equals("")) {
			currentUserAuthInfo = edit.checkUserAuth(userindex);
		}
		ObjectMapper mapper = new ObjectMapper();
		String json_currentUserAuthInfo = mapper.writeValueAsString(currentUserAuthInfo);
		return json_currentUserAuthInfo;
	}

	/**
	 * 권한 종류에 따라, 해당 권한을 가진 사용자 목록을 return
	 * @param authtype editauth 혹은 dataauth
	 * 
	 * @GetMapping(path = "/authList", produces = "application/json;charset=utf8")
	 * @ResponseBody
	 * 
	 * @return jsonTypeString authorizationListDO가 json으로 변환됨.
	 * @throws Exception
	 * 
	 * @author 박승수
	 */
	@GetMapping(path = "/authList", produces = "application/json;charset=utf8")
	@ResponseBody
	public String callAuthUserListByAuthType(@RequestParam(name = "authtype", required = true) String authtype)
			throws Exception {
		List<authorizationListDO> authuserResult = new Vector();
		try {
			authuserResult = edit.getAuthUserListByAuthtype(authtype);
		} catch(SQLException e) {
			System.out.println("SQL Exception occured while calling getAuthUserListByAuthtype on service. \n casted to null");
		} catch(Exception e) {
			System.out.println("Non SQL Exception occured _ casted to null");
		}
		//cast result into json type
		ObjectMapper mapper = new ObjectMapper();
		String json_authuserResult = mapper.writeValueAsString(authuserResult);

		return json_authuserResult;
	}
	
	/**
	 * 특정 유저에 대해 권한을 부여함.
	 * 
	 * @PostMapping(path = "/newAuthInsert")
	 * @ResponseBody
	
	 * @param newInsertion 새로 부여하고자 하는 사용자 목록과 권한 종류를 포함
	 * @param session 현재 로그인된 사용자의 정보 ( 권한 부여자 저장을 위한 정보 )
	 * @return String success 혹은 fail
	 * @throws Exception
	 * 
	 * @author 박승수
	 */
	@PostMapping(path = "/newAuthInsert")
	@ResponseBody
	public String insertUserAuth(@RequestBody newUserWrapper newInsertion, HttpSession session) throws Exception {
		try {
			for(int index = 0; index < newInsertion.newUserList.size(); index++) {
				HashMap map = new HashMap();
				map.put("userindex", newInsertion.newUserList.get(index).userindex);
				map.put("authtype", newInsertion.authtype);
				map.put("loginuserindex", session.getAttribute("userindex").toString());
				edit.insertNewAuthUserData(map);
			}
		} catch(SQLException e) {
			System.out.println("SQL Exception occured _ on \"newAuthInsert\"");
			return "fail";
		} catch(Exception e) {
			System.out.println("Non_SQL Exception occured _ on \"newAuthInsert\"");
			return "fail";
		}
		return "success";
	}
	
	/**
	 * 특정 유저에 대해 권한을 회수함.
	 * 
	 * @PostMapping(path = "/authDelete")
	 * @ResponseBody
	 * 
	 * @param deletionUser 권한을 회수할 사용자 정보와 권한 종
	 * @param session 현재 로그인된 사용자의 정보 ( 권한 회수자 저장을 위한 정보 )
	 * @return String success 혹은 fail
	 * @throws Exception
	 * 
	 * @author 박승수
	 */
	@PostMapping(path = "/authDelete")
	@ResponseBody
	public String deleteUserAuth(@RequestBody newUserWrapper deletionUser, HttpSession session) throws Exception {
		try {
			edit.deleteUserAuth(deletionUser.authtype, deletionUser.newUserList.get(0).userindex, session.getAttribute("userindex").toString());
		} catch(SQLException e) {
			System.out.println("SQL Exception occured _ on \"authDelete\"");
			return "fail";
		} catch(Exception e) {
			System.out.println("Non_SQL Exception occured _ on \"authDelete\"");
			return "fail";
		}
		return "success";
	}
	
	/**
	 * 특정 유저의 이름을 검색하여 authDO에 맞는 형식을 return
	 * 
	 * @GetMapping(path = "/searchUser", produces = "application/json;charset=utf8")
	 * @ResponseBody
	
	 * @param username 검색할 사용자의 이름
	 * @return jsonTypeString 검색 결과로 등장한 authorizationListDO가 json으로 변환됨.
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@GetMapping(path = "/searchUser", produces = "application/json;charset=utf8")
	@ResponseBody
	public String returnSearchedUser(@RequestParam(name = "username", required=true)String username) throws Exception {
		List<authorizationListDO> searchedResult = new Vector();
		try {
			searchedResult = edit.callSearchedResult(username);
		} catch(SQLException e) {
			System.out.println("SQL Exception occured while calling callSearchedResult on service. \n casted to null");
		} catch(Exception e) {
			System.out.println("Non SQL Exception occured _ casted to null");
		}
		//cast result into json type
		ObjectMapper mapper = new ObjectMapper();
		String json_searchedResult = mapper.writeValueAsString(searchedResult);

		return json_searchedResult;
	}
	
	/**
	 * 유저 권한 관리 페이지로 redirect
	 * 
	 * @RequestMapping(path="/authManagement")
	 * 
	 * @return String 유저 권한 관리 페이지에서 보여줄 .jsp 파일 위치
	 * 
	 * @author 박승수 
	 */
	@RequestMapping(path="/authManagement")
	public String linkToAuthmanagement() {
		return "desktop-version/admin/auth-setting";
	}
	
	static class insertionUserData {
		public String userindex;
		public String username;
		public String companyname;
		public String orgname;
		public String avatar;
	}
	static class newUserWrapper {
		public List<insertionUserData> newUserList;
		public String authtype;
	}
}
