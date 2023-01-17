package dwsws.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwsws.controller.loginSSOchk;
import dwsws.dto.userInfoDTO;
import dwsws.repository.loginRepository;

/**
 * system - 로그인 및 SSO 처리
 * 
 * - userInfoExistCheck : 제출된 사용자 정보가 스마트워크 프렌즈의 DB에 존재하는지 확인함. 
 * 
 * @Autowired
	private loginRepository repository;
	
 * @author 박승수 
 *
 */
@Service
public class loginService {
	@Autowired
	private loginRepository repository;
	
	/**
	 * 제출된 사용자 정보가 스마트워크 프렌즈의 DB에 존재하는지 확인함. 
	 * id값과 pw값이 둘 다 제출된 경우 호출되는 함수 
	 * @param id 제출된 id 값 
	 * @param pw 제출된 비밀번호 값 
	 * @return userInfoDTO DB에 제출된 값과 일치하는 사용자 정보가 있는 경우, 해당 사용자의 정보를 담은 객체 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public userInfoDTO userInfoExistCheck(String id, String pw) throws Exception {
		return repository.userInfoExistCheck(id);
	}
	
	/**
	 * 제출된 사용자 정보가 스마트워크 프렌즈의 DB에 존재하는지 확인함. 
	 * id값만 제출된 경우 호출되는 함수 
	 * @param id 제출된 id 값 
	 * @return userInfoDTO DB에 제출된 값과 일치하는 사용자 정보가 있는 경우, 해당 사용자의 정보를 담은 객체 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public userInfoDTO userInfoExistCheck(String id) throws SQLException {
		userInfoDTO loginTrialUserInfo = repository.userInfoExistCheck(id);
		return loginTrialUserInfo;
	}
}