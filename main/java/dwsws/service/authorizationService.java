package dwsws.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwsws.dto.userAuthDTO;
import dwsws.outputdata.authorizationListDO;
import dwsws.repository.authorizationRepository;

/**
 * admin - 사용자 권한관리 페이지
 * 
 * - checkUserAuth : 한 인원의 auth 내역을 return.
 * - getAuthUserListByAuthtype : 권한 종류에 따라, 해당 권한을 가진 인원들의 목록을 return.
 * - insertNewAuthUserData : 새로운 인원에게 권한을 부여함.
 * - deleteUserAuth : 권한을 가지고 있는 기존 사용자의 권한을 회수함.
 * - callSearchedResult : 사용자 이름을 통해 user_info 테이블을 검색한 결과를 return.
 * 
 * @Autowired
	private authorizationRepository authRepository;
	
 * @author 박승수 
 *
 */
@Service
public class authorizationService {
	@Autowired
	private authorizationRepository authRepository;
	
	/**
	 * 한 인원의 auth 내역을 return.
	 * 
	 * @Transactional
	 * 
	 * @param userindex auth내역을 알고싶은 사용자의 index값 (user_info 테이블에서, 사용자에 대 가지는 고유값)
	 * @return userAuthDTO user_auth 테이블의 내용을 가지고 있는 데이터 객체 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public userAuthDTO checkUserAuth(String userindex) throws Exception{
		userAuthDTO userAuthResult = new userAuthDTO();
		try {
			userAuthResult = authRepository.callOneUserAuth(userindex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userAuthResult;
	}
	
	/**
	 * 권한 종류에 따라, 해당 권한을 가진 인원들의 목록을 return.
	 * 
	 * @Transactional
	 * 
	 * @param authtype 검색하고자 하는 권한 종류 
	 * @return List<authorizationListDO> 검색한 권한을 가지고 있는 사용자들의 목록 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public List<authorizationListDO> getAuthUserListByAuthtype(String authtype) throws Exception {
		List<authorizationListDO> userAuthList = new Vector();
		try {
			userAuthList = authRepository.callAuthUserList(authtype);
		} catch(SQLException e) {
			userAuthList = null;
			System.out.println("SQL Exception occured while calling callAuthUserList \n casted to null");
		} catch(Exception e) {
			System.out.println("non sql exception occured");
			e.printStackTrace();
		}
		return userAuthList;
	}
	
	/**
	 * 새로운 인원에게 권한을 부여함.
	 * 
	 * @Transactional
	 * 
	 * @param map [userindex, authtype, loginuserindex]를 가지고 있는 객체 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public void insertNewAuthUserData(HashMap map) throws Exception {
		try {
			authRepository.insertNewAuthData(map);
		} catch(SQLException e) {
			System.out.println("failed to insert auth due to the SQL Exception");
		} catch(Exception e) {
			System.out.println("failed to insert auth due to the other type of Exception");
		}
	}
	
	/**
	 * delete user's auth
	 * @param authtype auth type(EDITAUTH or DATAAUTH) that want to change state of.
	 * @param userindex user's index want to deregister the auth
	 * @param loginuserindex user that deregister target user's auth
	 */
	@Transactional
	public void deleteUserAuth(String authtype, String userindex, String loginuserindex) throws Exception {
		try {
			authRepository.deregisterUserAuth(authtype, userindex, loginuserindex);
		} catch(SQLException e) {
			System.out.println("failed to delete auth due to the SQL Exception");
		} catch(Exception e) {
			System.out.println("failed to delete auth due to the other type of Exception");
		}
	}
	
	/**
	 * returns searched result list
	 * @param username a string that the name of target including.
	 * @return List<?> authDO list
	 */
	@Transactional
	public List<authorizationListDO> callSearchedResult(String username) throws Exception {
		try {
			return authRepository.searchedUserList(username);
		} catch(SQLException e) {
			System.out.println("SQL Exception occured on callSearchedResult");
			return null;
		} catch(Exception e) {
			System.out.println("Non_SQL Exception occured on callSearchedResult");
			return null;
		}
	}
}
