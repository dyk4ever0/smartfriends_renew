package dwsws.repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dwsws.outputdata.recentTestDataOnMyinfoDO;
import dwsws.outputdata.sectionIndexFromQuestionDO;

/**
 * user - 내 정보 페이지
 * 
 * - getNumOfRankingTable : user_ranking table에 있는 데이터의 개수를 return.
 * - getRankingByUserindex : 사용자의 등수를 return.
 * - getNumOfUserInfoTable : user_info 테이블에 있는 데이터의 개수를 return.
 * - getRecentTrialnumByToolname : 가장 최근 진단 데이터의 회차를 return. 
 * - getRankingByToolnameFromRecentData : recent_test_result table로부터 등수를 return.
 * - getRecentDataByLimitationNum : 최근 진단 데이터를 주어진 개수만큼 return.
 * - getSectionCommentsByToolname : 도구에 대한 section의 comments를 return.
 * - getBmkSectionByQuestionList : 섹션별 점수 계산을 위해, bookmark table로부터 section의 index를 return.
 * - getUserindexFromTestresultByToolname : 진단 제공 도구에 대해, 진단에 참여한 인원의 이름을 return.
 * 
 * @Autowired
	private SqlSessionTemplate sqlSession;
	
 * @author 박승수 
 *
 */
@Repository
public class myinfoRepository {
	@Autowired
	private SqlSessionTemplate sqlSession;

	/**
	 * user_ranking table에 있는 데이터의 개수를 return.
	 * 
	 * @return int user_ranking에 있는 데이터의 개수 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public int getNumOfRankingTable() throws SQLException {
		return sqlSession.selectOne("getNumOfRankingData");
	}
	
	/**
	 * 사용자의 등수를 return.
	 * 
	 * @param userindex 등수를 얻고자 하는 사용자의 고유 값 
	 * @return int 사용자의 등수 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public int getRankingByUserindex(String userindex) throws SQLException {
		return sqlSession.selectOne("getRankingByUserindex", userindex);
	}
	
	/**
	 * user_info 테이블에 있는 데이터의 개수를 return.
	 * @return int 전체 사원 수 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public int getNumOfUserInfoTable() throws SQLException {
		return sqlSession.selectOne("getNumOfAllPeople");
	}
	
	/**
	 * 가장 최근 진단 데이터의 회차를 return. 
	 * @param toolname 최근 진단 데이터를 호출하고자 하는 도구 이름 
	 * @param userindex 누구에 대한 진단 데이터를 호출하고자하는지에 대한, 사용자의 고유 값 
	 * @return int 최근 진단 데이터의 회차 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public int getRecentTrialnumByToolname(String toolname, String userindex) throws SQLException {
		HashMap map = new HashMap();
		map.put("toolname", toolname);
		map.put("userindex", userindex);
		return sqlSession.selectOne("getRecentTrialNumByUserindexForToolname", map);
	}
	
	/**
	 * recent_test_result table로부터 등수를 return.
	 * 
	 * @param toolname 어느 도구에 대해 등수를 받을 것인지에 대한 도구 이름 
	 * @param userindex 누구의 등수를 받을 것인지에 대한, 사용자 고유 값 
	 * @return int 사용자의 등수 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public int getRankingByToolnameFromRecentData(String toolname, String userindex) throws SQLException {
		HashMap map = new HashMap();
		map.put("toolname", toolname);
		map.put("userindex", userindex);
		return sqlSession.selectOne("getRankingFromRecentResultByUserindex", map);
	}
	
	/**
	 * 최근 진단 데이터를 주어진 개수만큼 return.
	 * 
	 * @param toolname 진단 데이터를 받아올 도구의 이름 
	 * @param userindex 진단 데이터를 받아올 사용자의 고유 값 
	 * @param limitation 몇 개의 최근 진단 데이터를 받을 것인지에 대한 변수 
	 * @return List<recentTestDataOnMyinfoDO> 최근 진단 데이터를 가지고 있는 객체의 list 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public List<recentTestDataOnMyinfoDO> getRecentDataByLimitationNum(String toolname, String userindex, int limitation) throws SQLException {
		HashMap map = new HashMap();
		map.put("toolname", toolname);
		map.put("userindex", userindex);
		map.put("limitation", limitation);
		return sqlSession.selectList("getRecentTestResultByToolnameWithLimitnum", map);
	}
	
	/**
	 * 도구에 대한 section의 comments를 return.
	 * 
	 * @param toolname section의 comments를 얻고자 하는 도구 이름 
	 * @return List<String> comments를 담고 있는 객체의 list 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public List<String> getSectionCommentsByToolname(String toolname) throws SQLException {
		return sqlSession.selectList("getSectionCommentsByToolname", toolname);
	}
	
	/**
	 * 섹션별 점수 계산을 위해, bookmark table로부터 section의 index를 return.
	 * 
	 * @param toolname 어느 도구와 연결된 section의 index를 받을지에 대한, 도구 이름 
	 * @param userindex 진단 문항과 실제 진단 실시 일자의 비교를 위해, 사용자의 최근 진단 결과를 불러오기 위한 사용자 고유 값 
	 * @return List<sectionIndexFromQuestionDO> section의 정보를 담고 있는 객체의 list 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public List<sectionIndexFromQuestionDO> getBmkSectionByQuestionList(String toolname, String userindex) throws SQLException {
		HashMap map = new HashMap();
		map.put("toolname", toolname);
		map.put("userindex", userindex);
		return sqlSession.selectList("getSectionIndexByToolQuestionList", map);
	}
	
	/**
	 * 진단 제공 도구에 대해, 진단에 참여한 인원의 이름을 return.
	 * 
	 * @param toolname 어느 도구에 대한 참여인원을 구할 것인지의, 도구 이름 
	 * @return List<String> 진단에 참여한 인원들의 정보가 담긴 객체의 List
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public List<String> getUserindexFromTestresultByToolname(String toolname) throws SQLException {
		return sqlSession.selectList("getUserindexFromTestresult", toolname);
	}
}
