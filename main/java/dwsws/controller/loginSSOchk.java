package dwsws.controller;

import static dwsws.config.dataSourceDBConnectInfo.encrypterInitializer;
import static dwsws.config.dataSourceDBConnectInfo.encrypterKey;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import dwsws.dto.userInfoDTO;
import dwsws.service.loginService;

/**
 * - class StringEncrypter : 베어월드에서 암호화되어 보내지는 사용자 정보를 해독하는 encrypter/decrypter
 * - loginSSOCheck : 베어월드에서 전송된 암호화된 사용자 정보를 스마트워크 프렌즈의 사용자 정보와 대조하여 로그인 처리 혹은 거부하는 함수 
 * @author 박승수 
 */
@Controller
public class loginSSOchk {

	@Autowired
	loginService login;
//	loginController loginManager;
	

	public class StringEncrypter {
		private Cipher rijndael;
		private SecretKeySpec key;
		private IvParameterSpec initalVector;

		public StringEncrypter() throws Exception {
			String key = encrypterKey;
			String initialVector = encrypterInitializer;

			if (key == null || key.equals(""))
				throw new Exception("The key can not be null or an empty string.");

			if (initialVector == null || initialVector.equals(""))
				throw new Exception("The initial vector can not be null or an empty string.");

			this.rijndael = Cipher.getInstance("AES/CBC/PKCS5Padding");

			MessageDigest md5 = MessageDigest.getInstance("MD5");
			this.key = new SecretKeySpec(md5.digest(key.getBytes("UTF8")), "AES");
			this.initalVector = new IvParameterSpec(md5.digest(initialVector.getBytes("UTF8")));
		}

		public String encrypt(String value) throws Exception {
			if (value == null)
				value = "";

			this.rijndael.init(Cipher.ENCRYPT_MODE, this.key, this.initalVector);
			byte[] utf8Value = value.getBytes("UTF8");
			byte[] encryptedValue = this.rijndael.doFinal(utf8Value);

			return Base64.encodeBase64String(encryptedValue);
		}

		public String decrypt(String value) throws Exception {
			if (value == null || value.equals(""))
				throw new Exception("The cipher string can not be null or an empty string.");

			this.rijndael.init(Cipher.DECRYPT_MODE, this.key, this.initalVector);

			byte[] encryptedValue = Base64.decodeBase64(value);
			byte[] decryptedValue = this.rijndael.doFinal(encryptedValue);
			return new String(decryptedValue, "UTF8");
		}
	}
	
	/**
	 * 베어월드에서 전송된 암호화된 사용자 정보를 스마트워크 프렌즈의 사용자 정보와 대조하여 로그인 처리 혹은 거부하는 함수
	 * 
	 * @RequestMapping(path = "/SSOCheck")
	 * 
	 * @param request 베어월드에서 전송해준 암호화된 사용자 정
	 * @param response
	 * @param modelMap
	 * @param session 사용자 정보를 저장할 (로그인 처리 할) 세션 데이
	 * @return String 로그인 인증 이후 보여줄 메인 페이지의 .jsp 파일 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@RequestMapping(path = "/SSOCheck")
	public String loginSSOCheck(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			HttpSession session) throws Exception {
		//get userid from bearworld.
		String userid = request.getParameter("userid");
		System.out.println("userid : " + userid + " \n");
		StringEncrypter encryptedId = new StringEncrypter();
		
		//decrypt userid
		String decryptedUserId = encryptedId.decrypt(userid);
		System.out.println("bearworld return user ID : " + decryptedUserId + " \n");
		
		//check whether decrypted userid exists in dwsws user db.
		//loginManager.login(userid, session);
		
		// 로그인 처리하는 부분
		userInfoDTO loginTrialUserInfo = login.userInfoExistCheck(decryptedUserId);
		if (loginTrialUserInfo == null) { // 로그인 정보가 없는 경우
			return "redirect:/login";
		
		} else {// 로그인 정보가 존재하는 경우

			// 세션에 username과 userindex 저장
			session.setAttribute("username", loginTrialUserInfo.getUsername());
			session.setAttribute("userindex", loginTrialUserInfo.getUserindex());
			session.setAttribute("userorgname", loginTrialUserInfo.getOrgname());
			session.setAttribute("userimage", loginTrialUserInfo.getAvatar());
			session.setAttribute("usercompanyname", loginTrialUserInfo.getCompanyname());

			
			//if exist, redirect to guide.
			//because there is intercepter before approach to guide, it's fine to directly approach to guide.
			return "redirect:/main";
		}
	}
}
