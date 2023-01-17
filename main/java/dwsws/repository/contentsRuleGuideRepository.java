package dwsws.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import dwsws.outputdata.bmkSelectDO;

/**
 * admin - 컨텐츠관리 - 가이드관리 - 스마트워크Rule 관련 레퍼지토리
 * 
 * - selecttoolList : 규칙에 속하는 북마크의 왼쪽 select 박스에 들어갈 도구 목록
 * - selectBmkList2Tool : 규칙에 속하는 북마크의 오른쪽 select 박스에 들어갈 도구 목록(도구별 북마크 리스트)
 * - updateVideoTitle4Rule : 스마트워크Rule 동영상의 제목 수정
 * - updateOneDesc : 스마트워크Rule 동영상의 한 줄 설명 수정 
 * - updateComments4Rule : 스마트워크Rule 동영상의 상세 설명 수정
 * - updateUrl4Rule : 스마트워크Rule 동영상의 url 수정
 * - updateRuleNameAndDesc : 스마트워크Rule 관련 규칙명, 규칙설명 수정
 * - deleteRule : rule테이블에서 특정 번호의 룰 삭제 
 * - insertBmk4SpecificRule : 특정 Rule에 bmk 추가 
 * - deleteSmartRule : 스마트워크Rule 규칙 삭제
 * - insertNewRule : 스마트워크Rule 규칙 삽입
 * 
 * @Autowired
	SqlSessionTemplate sqlSession;
 * 
 * @author 김예린
 *
 */
@Repository
public class contentsRuleGuideRepository {
	@Autowired
	SqlSessionTemplate sqlSession;
	
	/**
	 * 규칙에 속하는 북마크의 왼쪽 select 박스에 들어갈 도구 목록
	 * 
	 * @return list : 왼쪽 select 박스에 들어갈 도구 목록
	 */
	public List<String> selecttoolList(){
		return sqlSession.selectList("toolList4Contents");
	}
	
	/**
	 * 규칙에 속하는 북마크의 오른쪽 select 박스에 들어갈 도구 목록(도구별 북마크 리스트)
	 * 
	 * @param tool : 도구명(Lineworks, Zoom..)
	 * 
	 * @return 북마크의 오른쪽 select 박스에 들어갈 도구 목록
	 */
	public List<bmkSelectDO> selectBmkList(String tool){
		return sqlSession.selectList("bmkListAboutTool", tool);
	}
	
	/**
	 * 스마트워크Rule 동영상의 제목 수정
	 * 
	 * @param title : 수정할 제목
	 * @param userindex
	 * @return
	 */
	public int updateVideoTitle4Rule(String title, String userindex) {
		Map<String, Object> param = new HashMap<>();
		param.put("title", title);
		param.put("userindex", userindex);
		return sqlSession.update("updateVideoTitle4Rule", param);
	}
	
	/**
	 * 스마트워크Rule 동영상의 한 줄 설명 수정
	 * 
	 * @param onedesc : 수정할 한 줄 설명
	 * @param userindex
	 * @return
	 */
	public int updateOneDesc(String onedesc, String userindex) {
		Map<String, Object> param = new HashMap<>();
		param.put("onedesc", onedesc);
		param.put("userindex", userindex);
		return sqlSession.update("updateOneDesc", param);
	}
	
	/**
	 * 스마트워크Rule 동영상의 상세 설명 수정
	 * 
	 * @param comments : 수정할 내용
	 * @param userindex
	 * @return
	 */
	public int updateComments4Rule(String comments, String userindex) {
		Map<String, Object> param = new HashMap<>();
		param.put("comments", comments);
		param.put("userindex", userindex);
		return sqlSession.update("updateComments4Rule", param);
	}
	
	/**
	 * 스마트워크Rule 동영상의 url 수정
	 * 
	 * @param youtubeurl : 수정할 url
	 * @param userindex
	 * @return
	 */
	public int updateUrl4Rule(String youtubeurl, String userindex) {
		Map<String, Object> param = new HashMap<>();
		param.put("youtubeurl", youtubeurl);
		param.put("userindex", userindex);
		return sqlSession.update("updateUrl4Rule", param);
	}
	
	/**
	 * 스마트워크Rule 관련 규칙명, 규칙설명 수정
	 * 
	 * @param ruleName : 규칙명
	 * @param comments : 규칙설명
	 * @param userindex
	 * @param ruleNum  :규칙번호
	 * @return
	 */
	public int updateRuleNameAndDesc(String ruleName, String comments, String userindex, int ruleNum ) {
		Map<String, Object> param = new HashMap<>();
		param.put("ruleName", ruleName);
		param.put("comments", comments);
		param.put("userindex", userindex);
		param.put("ruleNum", ruleNum);
		return sqlSession.update("updateRuleNameAndDesc", param);
	}
	
	/**
	 * rule테이블에서 특정 번호의 룰 삭제 
	 * 업무계획수립, 업무시작알림에 임의로 순서대로 1, 2, 3 번호 부여
	 * 
	 * @param ruleNum : 규칙번호
	 * @return
	 */
	public int deleteRule(int ruleNum) {
		return sqlSession.delete("deleteRule", ruleNum);
	}
	
	/**
	 * 특정 Rule에 bmk 추가
	 * 
	 * @param ruleNum : 규칙번호
	 * @param bmk : 북마크 idx
	 * @param userindex
	 * @return
	 */
	public int insertBmk4SpecificRule(int ruleNum, int bmk, String userindex) {
		Map<String, Object> param = new HashMap<>();
		param.put("ruleNum", ruleNum);
		param.put("bmk", bmk);
		param.put("entryuserindex", userindex);
		return sqlSession.insert("insertBmk4SpecificRule", param);
	}
	
	/**
	 * 스마트워크Rule 규칙 삭제
	 * @param ruleNum : 규칙번호
	 * @return
	 */
	public int deleteSmartRule(int ruleNum) {
		return sqlSession.delete("deleteSmartRule", ruleNum);
	}
	
	/**
	 * 스마트워크Rule 규칙 삽입
	 * @param param : 추가 할 규칙 번호
	 * @param userindex
	 * @return
	 */
	public int insertNewRule(Object param, String userindex ) {
		Map<String, Object> mapParam = new HashMap<>();
		mapParam.put("rule", Integer.parseInt((String) ((HashMap)param).get("num")));
		mapParam.put("title",((HashMap)param).get("title").toString() );
		mapParam.put("comments", ((HashMap)param).get("comments").toString());
		mapParam.put("userindex", userindex);
		
		return sqlSession.insert("insertNewRule", mapParam) ;
	}
	
}
