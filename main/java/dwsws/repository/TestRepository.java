package dwsws.repository;


import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dwsws.dto.categoryDTO;
import dwsws.dto.contentsDTO;
import dwsws.dto.favoriteListDTO;
import dwsws.dto.questionListDTO;
import dwsws.dto.ruleDTO;
import dwsws.dto.sectionDTO;
import dwsws.dto.smartworkRuleDTO;
import dwsws.dto.testOfferToolDTO;
import dwsws.dto.testResultDTO;
import dwsws.dto.userInfoDTO;
import dwsws.outputdata.SmartworkRuleDO;
import dwsws.outputdata.UserFavoriteList;

@Repository
public class TestRepository {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public List<categoryDTO> selectCategoryList() { 
		return sqlSession.selectList("selectCategoryList");
	}

	public List<SmartworkRuleDO> selectBookmarkList() {
		return sqlSession.selectList("selectBookmarkList");
	}
	
	public List<contentsDTO> selectContentsList(){
		return sqlSession.selectList("selectContentsList");
	}
	public List<favoriteListDTO> selectFavoriteList(){
		return sqlSession.selectList("selectFavoriteList");
	}
	
	public List<questionListDTO> selectQuestionList(){
		return sqlSession.selectList("selectQuestionList");
	}
	
	public List<testResultDTO> selectResultList(){
		return sqlSession.selectList("selectTestresultList");
	}
	public List<ruleDTO> selectRuleList(){
		return sqlSession.selectList("selectRule3");
	}
	
	public List<sectionDTO> selectSectionList(){
		return sqlSession.selectList("selectSection");
	}
	public List<smartworkRuleDTO> selectSmartworkList(){
		return sqlSession.selectList("selectSmartworkRule");
	}
	public List<testOfferToolDTO> selectToolList(){
		return sqlSession.selectList("selectTestoffertool");
	}
	public List<userInfoDTO> selectUserList(){
		return sqlSession.selectList("selectUserinfo");
	}
	
	// 사용자별 즐겨찾기 목록 호출
	public List<UserFavoriteList> selectUserFavoriteList(String userindex){
		return sqlSession.selectList("userFavoriteList", userindex);
	}
	
}
