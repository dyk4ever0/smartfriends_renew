package dwsws.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import dwsws.outputdata.SmartworkRuleDO;

/**
 * 가이드페이지 관련 레퍼지토리
 * 
 * - selectRuleList : 스마트워크Rule 관련 데이터 - 업무계획수립 : 업무계획을 수립하여 일과를 계획합니다..
 * - selectRuleNum : 스마트워크룰 갯수
 * - selectFullVersionUrl : 스마트워크룰 동영상 url
 * - selectOneDesc : 스마트워크룰 한 줄 설명
 * - selectRuleDesc : 스마트워크룰 상세 설명
 * - selectVideoTitle : 스마트워크룰 영상제목
 * - selectContentsList : 특정 카테고리에 해당하는 툴 목록(for 모바일)
 * - selectCategoryList : category 목록(for 모바일)
 * 
 * @Autowired
	SqlSessionTemplate sqlSession;
 * 
 * @author 김예린
 *
 */
@Repository
public class guideRepository {
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	/**
	 * 스마트워크Rule 관련 데이터 - 업무계획수립 : 업무계획을 수립하여 일과를 계획합니다..
	 * 
	 * @param num : 규칙번호
	 * @return 특정 규칙번호와 관련된 스마트워크Rule 데이터
	 */
	public List<SmartworkRuleDO> selectRuleList(int num){//smartworkRule
		return sqlSession.selectList("selectRule", num);
	}
	
	/**
	 * 스마트워크룰 갯수
	 * 
	 * @return 스마트워크룰 갯수
	 */
	public int selectRuleNum() {//smartworkRule num
		return sqlSession.selectOne("selectRuleNum");
	}
	
	/**
	 * 스마트워크룰 동영상 url
	 * 
	 * @return 스마트워크룰 동영상 url
	 */
	public String selectFullVersionUrl() {
		return sqlSession.selectOne("fullVersion");
	}
		
	/**
	 *  스마트워크룰 한 줄 설명
	 * 
	 * @return  스마트워크룰 한 줄 설명
	 */
	public String selectOneDesc() {
		return sqlSession.selectOne("oneDesc");
	}
	
	/**
	 * 스마트워크룰 상세 설명
	 * 
	 * @return 스마트워크룰 상세 설명
	 */
	public String selectRuleDesc() {
		return sqlSession.selectOne("ruleDesc");
	}
	/**
	 * 스마트워크룰 영상제목
	 * 
	 * @return 스마트워크룰 영상제목
	 */
	public String selectVideoTitle() {
		return sqlSession.selectOne("selectVideoTitle");
	}
	/**
	 *  특정 카테고리에 해당하는 툴 목록(for 모바일)
	 * 
	 * @param category  :카테고리명
	 * @return
	 */
	public List<String> selectContentsList(String category){
		return sqlSession.selectList("contentsList", category);
	}
	/**
	 * category 목록(for 모바일)
	 * 
	 * @return
	 */
	public List<String> selectCategoryList(){
		return sqlSession.selectList("categoryList");
	}
}
