package dwsws.repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dwsws.dto.userAuthDTO;
import dwsws.outputdata.authorizationListDO;

@Repository
public class authorizationRepository {
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	/**
	 * only calls auth data for one person who try to access
	 * @param userindex user's index want to query
	 * @return data and edit auth flag
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	public userAuthDTO callOneUserAuth(String userindex) throws Exception {
		return sqlSession.selectOne("oneUserAuthCall", userindex);
	}
	
	/**
	 * calls users that have auth about edit or data.
	 * @param authtype type of auth want to query (data or edit)
	 * @throws SQLException exception occurs on sqlSession
	 * @return object[userindex, username, companyname, orgname]
	 * 
	 * @author 박승수 
	 */
	public List<authorizationListDO> callAuthUserList(String authtype) throws SQLException {
		return sqlSession.selectList("callAuthorizedPeople", authtype);
	}
	
	/**
	 * update or insert new auth data
	 * @param userindex user's index 'll get auth
	 * @param username user's name 'll get auth
	 * @param companyname user's companyname
	 * @param orgname user's orgname
	 * @param avatar user's picture url from groupware server
	 * @param loginuserindex currently login user's index who gives auth
	 * @param authtype which auth code want to give.
	 * 
	 * @author 박승수 
	 */
	public void insertNewAuthData(HashMap map) throws SQLException {
		sqlSession.insert("registerAuth", map);
	}
	
	/**
	 * delete selected user's auth
	 * @param authtype auth type(EDITAUTH or DATAAUTH) that want to change state of.
	 * @param userindex user's index want to deregister the auth
	 * @param loginuserindex user that deregister target user's auth
	 * 
	 * @author 박승수 
	 */
	public void deregisterUserAuth(String authtype, String userindex, String loginuserindex) throws SQLException {
		HashMap map = new HashMap();
		map.put("authtype", authtype);
		map.put("userindex", userindex);
		map.put("loginuserindex", loginuserindex);
		sqlSession.update("deregisterAuth", map);
	}
	
	/**
	 * returns searched result
	 * @param username string that result including
	 * @return List returned list about searched
	 * 
	 * @author 박승수 
	 */
	public List<authorizationListDO> searchedUserList(String username) throws SQLException {
		return sqlSession.selectList("callPeopleBySearchedName", username);
	}
}
