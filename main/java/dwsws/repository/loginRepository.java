package dwsws.repository;

import java.sql.SQLException;
import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dwsws.dto.userInfoDTO;

/**
 * system - 로그인 시스템
 * 
 * - userInfoExistCheck : 사용자의 정보가 DB에 있는지 확인 후 return. 
 * - getPassword : 사용자의 비밀번호를 return. 
 * 
 * @Autowired
	private SqlSessionTemplate sqlSession;
	
 * @author 박승수 
 *
 */
@Repository
public class loginRepository {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	/**
	 * 사용자의 정보가 DB에 있는지 확인 후 return. 
	 * @param id 로그인 창에 사용자가 입력한 ID 값 
	 * @param pw 로그인 창에 사용자가 입력한 PW 값 
	 * @return userInfoDTO 제공된 id, pw와 일치하는 사용자의 데이터를 담고 있는 객체 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public userInfoDTO userInfoExistCheck(String id, String pw) throws SQLException{
		HashMap map = new HashMap();
		map.put("id", id);
		map.put("pw", pw);
		return sqlSession.selectOne("userInfoSelectWithPw", map);
	}
	
	/**
	 * 사용자의 정보가 DB에 있는지 확인 후 return. 
	 * @param id 로그인 창에 사용자가 입력한 ID 값 
	 * @return userInfoDTO 제공된 id와 일치하는 사용자의 데이터를 담고 있는 객체 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public userInfoDTO userInfoExistCheck(String id) throws SQLException{
		return sqlSession.selectOne("userInfoSelect", id);
	}
	
	public String getPassword(String id) throws SQLException {
		return sqlSession.selectOne("getPassword", id);
	}
}
