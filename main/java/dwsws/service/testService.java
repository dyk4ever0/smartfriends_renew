package dwsws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import dwsws.dao.testDAO;
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
import dwsws.repository.TestRepository;

@Service
//@Transactional

public class testService{
	
	@Autowired
	TestRepository repository;
	
	@Transactional
	public List<categoryDTO> selectList()  {
		List<categoryDTO> list = repository.selectCategoryList();
		return list;
	}

	@Transactional
	public List<SmartworkRuleDO> selectBookmarkList() {
		List<SmartworkRuleDO> list = repository.selectBookmarkList();
		return list;
	}
	
	@Transactional  //contents 
	public List<contentsDTO> selectContentsList(){
		List<contentsDTO> list = repository.selectContentsList();
		return list;
	}
	
	@Transactional //favoritelist 
	public List<favoriteListDTO> selectFavoriteList(){
		List<favoriteListDTO> list = repository.selectFavoriteList();
		return list;
	}
	@Transactional // questionlist 
	public List<questionListDTO> selectQuestionList(){
		List<questionListDTO> list = repository.selectQuestionList();
		return list;
	}
	@Transactional // testresult 
	public List<testResultDTO> selectResultList(){
		List<testResultDTO> list = repository.selectResultList();
		return list;
	}
	
	@Transactional // rulelist 
	public List<ruleDTO> selectRuleList(){
		List<ruleDTO> list = repository.selectRuleList();
		return list;
	}
	
	
	@Transactional // section
	public List<sectionDTO> selectSectionList(){
		List<sectionDTO> list = repository.selectSectionList();
		return list;
	}
	
	@Transactional // smartworkRule
	public List<smartworkRuleDTO> selectSmartworkList(){
		List<smartworkRuleDTO> list = repository.selectSmartworkList();
		return list;
	}
	
	@Transactional // tool
	public List<testOfferToolDTO> selectToolList(){
		List<testOfferToolDTO> list = repository.selectToolList();
		return list;
	}
	@Transactional // userinfo
	public List<userInfoDTO> selectUserList(){
		List<userInfoDTO> list = repository.selectUserList();
		return list;
	}
	
	@Transactional //사용자별 즐겨찾기 목록 호출
	public List<UserFavoriteList> selectUserFavoriteList(String userindex) {
		List<UserFavoriteList> list = repository.selectUserFavoriteList(userindex);
		return list;
	}
	
	
}
