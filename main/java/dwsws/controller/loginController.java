package dwsws.controller;

import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import dwsws.controller.loginSSOchk.StringEncrypter;
import dwsws.dto.userInfoDTO;
import dwsws.service.loginService;


/**
 * - loginpage : 로그인 페이지로 redirect
 * - notLogined : 로그인이 필요한 페이지에 사용자가 접근을 시도하는 경우, 로그인 페이지로 이동시키는 함수
 * - login : 로그인 창에서 [로그인] 버튼 클릭 시 실행되는 로그인 인증 / 처리를 위한 함수
 * - logout : 로그아웃을 위해, 세션의 정보를 지우는 함수
 * - sessionData : 로그인 된 사용자의 정보를 호출하는 함수 
 * 
 * @Autowired
	loginService login;
	@Autowired
	loginSSOchk ssochk;
 *	
 * @author 박승수 
 *
 */
@Controller
public class loginController {
	@Autowired
	loginService login;
	@Autowired
	loginSSOchk ssochk;

	/**
	 * 로그인 페이지로 redirect
	 * 
	 * @RequestMapping(path = "/login")
	 * 
	 * @param modelMap
	 * @return String 로그인 페이지에서 보여줄 .jsp 파일 위치 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	@RequestMapping(path = "/login")
	public String loginpage(ModelMap modelMap) throws SQLException {
		return "mobile-version/login";
	}

	/**
	 * 로그인이 필요한 페이지에 사용자가 접근을 시도하는 경우, 로그인 페이지로 이동시키는 함수
	 * 
	 * @GetMapping(path = "/loginRequired")
	 * 
	 * @param device 사용자가 접속한 기기가 PC인지 MOBILE인지 구분하는 library 객체 
	 * @return String 로그인 페이지에서 보여줄 .jsp 파일 위치 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@GetMapping(path = "/loginRequired")
	public String notLogined(Device device) throws Exception {
		if (device.isMobile()) {
			return "mobile-version/login/error";
		} else {
			return "desktop-version/login/error";
		}
	}

	/**
	 * 로그인 창에서 [로그인] 버튼 클릭 시 실행되는 로그인 인증 / 처리를 위한 함수
	 * 
	 * @PostMapping(path = "/loginAuth")
	 * @ResponseBody
	
	 * @param id 로그인 창에서 사용자가 입력한 id 값 
	 * @param session 사용자 정보가 담겨있는 세션 데이터 
	 * @return userInfoDTO 로그인을 시도한 유저의 데이터가 담겨있는 객체 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@PostMapping(path = "/loginAuth")
	@ResponseBody
	public userInfoDTO login(@RequestParam(name = "id", required = true) String id, HttpSession session)
			throws Exception {

		// 기존 세션이 남아있으면 기존값 제거
		if (session.getAttribute("username") != null) {
			session.removeAttribute("username");
			session.removeAttribute("userindex");
			session.removeAttribute("userorgname");
			session.removeAttribute("userimage");
			session.removeAttribute("usercompanyname");
		}

		// 로그인 처리하는 부분

		// temp pwd implementing code
		userInfoDTO loginTrialUserInfo = new userInfoDTO();
			loginTrialUserInfo = login.userInfoExistCheck(id);

		if (loginTrialUserInfo == null) { // 로그인 정보가 없는 경우
			return null;
		} else {// 로그인 정보가 존재하는 경우

			// 세션에 username과 userindex 저장
			session.setAttribute("username", loginTrialUserInfo.getUsername());
			session.setAttribute("userindex", loginTrialUserInfo.getUserindex());
			session.setAttribute("userorgname", loginTrialUserInfo.getOrgname());
			session.setAttribute("userimage", loginTrialUserInfo.getAvatar());
			session.setAttribute("usercompanyname", loginTrialUserInfo.getCompanyname());

			return loginTrialUserInfo;
		}
	}

	/**
	 * 로그아웃을 위해, 세션의 정보를 지우는 함수
	 * 
	 * @RequestMapping(path = "/logout")
	 * 
	 * @param session 사용자 정보를 담고 있는 세션 데이터 
	 * @return String 로그아웃 이후 보여줄 로그인 페이지 .jsp 파일 위치 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@RequestMapping(path = "/logout")
	public String logout(HttpSession session) throws Exception {
		session.removeAttribute("username");
		session.removeAttribute("userindex");
		session.removeAttribute("userorgname");
		session.removeAttribute("userimage");
		session.removeAttribute("usercompanyname");

		return "mobile-version/login";
	}

	/**
	 * 로그인 된 사용자의 정보를 호출하는 함수 
	 * 
	 * @GetMapping(path = "/callSessionData", produces = "application/json;charset=utf8")
	 * @ResponseBody
	
	 * @param session 사용자 정보를 담고 있는 세션 데이터 
	 * @return jsonTypeString 세션에 담기는 사용자 정보를 담은 Map 객체가 json으로 변환됨. 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@GetMapping(path = "/callSessionData", produces = "application/json;charset=utf8")
	@ResponseBody
	public String sessionData(HttpSession session) throws Exception {
		HashMap sessionDataElements = new HashMap();
		try {
			sessionDataElements.put("username", session.getAttribute("username").toString());
			sessionDataElements.put("userindex", session.getAttribute("userindex").toString());
			sessionDataElements.put("userorgname", session.getAttribute("userorgname").toString());
			sessionDataElements.put("usercompanyname", session.getAttribute("usercompanyname").toString());
			sessionDataElements.put("userimage", session.getAttribute("userimage").toString());
			// usercompanyname calling occurs nullpointer exception
		} catch (NullPointerException e) {
			System.out.println("userimage - nullity error occured on calling session data");
			e.printStackTrace();
			sessionDataElements.put("userimage", null);
		} catch(Exception e) {
			System.out.println("non userimage - nullity error occured on calling session data");
		}

		ObjectMapper mapper = new ObjectMapper();
		String json_sessionDataElements = mapper.writeValueAsString(sessionDataElements);

		return json_sessionDataElements;
	}

}
