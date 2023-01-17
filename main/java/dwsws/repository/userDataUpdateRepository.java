package dwsws.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dwsws.dto.userInfoDTO;

@Repository
public class userDataUpdateRepository {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	/**
	 * 한 사용자의 정보를 삽입함 
	 * @param user 베어월드 API 서버로부터 반환받은 한 사용자의 정보 
	 * 
	 * @author 박승수 
	 */
	public void insertUserData(userInfoDTO user) {
		sqlSession.insert("insertUser", user);
	}
	
	/**
	 * 한 사용자의 정보를 삭제함 
	 * 
	 * @author 박승수 
	 */
	public void deleteAllUserData() {
		sqlSession.delete("deleteAllUser");
	}
	
	/**
	 * 사용자 정보의 nullity를 확인함 
	 * @return int userindex가 Null이 아닌 row의 개수 
	 * 
	 * @author 박승수 
	 */
	public int checkUserInfoNull() {
		int result = sqlSession.selectOne("checkUserInfoNull");
		return result;
	}
	
	/**
	 * favorite List의 nullity를 확인함 
	 * @return int favorite list table의 index가 Null이 아닌 row의 갯수 
	 * 
	 * @author 박승수 
	 */
	public int checkFavoriteNull() {
		int result = sqlSession.selectOne("checkFavoriteNull");
		return result;
	}
	
	/**
	 * 즐겨찾기 목록을 back up 함
	 * @author 박승수 
	 */
	public void backupFavoriteData() {
		sqlSession.insert("moveFavoriteListToTempTable");
		sqlSession.delete("flushAllDataOfFavoriteList");
	}
	
	/**
	 * 즐겨찾기 목록을 복원함 
	 * @author 박승수 
	 */
	public void restoreFavoriteData() {
		sqlSession.insert("moveTempToFavoriteList");
		sqlSession.delete("flushAllDataOfFavoriteListTemp");
	}
}
