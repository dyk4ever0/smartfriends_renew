package dwsws.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwsws.outputdata.bmkSelectDO;
import dwsws.repository.contentsRuleGuideRepository;

/**
 * admin - 컨텐츠관리 - 가이드관리 - 스마트워크Rule 관련 서비스
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
	contentsRuleGuideRepository contentsRuleGuideRepository;
	
 * @author 김예린
 *
 */
@Service
public class contentsRuleGuideService {

	@Autowired
	contentsRuleGuideRepository contentsRuleGuideRepository;
	
	/**
	 * 규칙에 속하는 북마크의 왼쪽 select 박스에 들어갈 도구 목록
	 * 
	 * @return list : 왼쪽 select 박스에 들어갈 도구 목록
	 */
	@Transactional
	public List<String> selecttoolList(){
		List<String> list = contentsRuleGuideRepository.selecttoolList();
		return list;
	}
	
	/**
	 * 규칙에 속하는 북마크의 오른쪽 select 박스에 들어갈 도구 목록(도구별 북마크 리스트)
	 * 
	 * @return 북마크의 오른쪽 select 박스에 들어갈 도구 목록
	 */
	@Transactional
	public Map<String,Object> selectBmkList2Tool(){
		List<String> list = contentsRuleGuideRepository.selecttoolList();
		Map<String,Object> map = new HashMap<String,Object>();
		
		for(int i=0; i<list.size(); i++) {
			
			List<bmkSelectDO> bmkList = contentsRuleGuideRepository.selectBmkList(list.get(i));
			map.put(list.get(i), bmkList);
			
		}
		return map;

	}
	
	/**
	 * 스마트워크Rule 동영상의 제목 수정
	 * 
	 * @param title : 수정할 제목
	 * @param userindex
	 * @return
	 */
	@Transactional
	public int updateVideoTitle4Rule(String title, String userindex) {
		int result = contentsRuleGuideRepository.updateVideoTitle4Rule(title, userindex);
		return result;
	}
	
	/**
	 * 스마트워크Rule 동영상의 한 줄 설명 수정
	 * 
	 * @param onedesc : 수정할 한 줄 설명
	 * @param userindex
	 * @return
	 */
	@Transactional
	public int updateOneDesc(String onedesc, String userindex) {
		int result = contentsRuleGuideRepository.updateOneDesc(onedesc, userindex);
		return result;
	}
	
	/**
	 * 스마트워크Rule 동영상의 상세 설명 수정
	 * 
	 * @param comments : 수정할 내용
	 * @param userindex
	 * @return
	 */
	@Transactional
	public int updateComments4Rule(String comments, String userindex) {
		int result = contentsRuleGuideRepository.updateComments4Rule(comments, userindex);
		return result;
	}
	
	/**
	 * 스마트워크Rule 동영상의 url 수정
	 * 
	 * @param youtubeurl : 수정할 url
	 * @param userindex
	 * @return
	 */
	@Transactional
	public int updateUrl4Rule(String youtubeurl, String userindex) {
		int result = contentsRuleGuideRepository.updateUrl4Rule(youtubeurl, userindex);
		return result;
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
	@Transactional
	public int updateRuleNameAndDesc(String ruleName, String comments, String userindex, int ruleNum ) {
		int result = contentsRuleGuideRepository.updateRuleNameAndDesc(ruleName, comments, userindex, ruleNum);
		return result;
	}
	
	/**
	 * rule테이블에서 특정 번호의 룰 삭제 
	 * 업무계획수립, 업무시작알림에 임의로 순서대로 1, 2, 3 번호 부여
	 * 
	 * @param ruleNum : 규칙번호
	 * @return
	 */
	@Transactional
	public int deleteRule(int ruleNum) {
		int result = contentsRuleGuideRepository.deleteRule(ruleNum);
		return result;
	}
	
	/**
	 * 특정 Rule에 bmk 추가
	 *  
	 * @param addBmkList4Rule : 추가할 북마크 리스트
	 * @param userindex
	 * @param ruleNum : 규칙번호
	 * @return
	 */
	@Transactional
	public int insertBmk4SpecificRule( ArrayList<String> addBmkList4Rule, String userindex, int ruleNum) {
		int result = 0;
		for(String i : addBmkList4Rule) {
			result += contentsRuleGuideRepository.insertBmk4SpecificRule(ruleNum, Integer.parseInt(i), userindex);
		}
		return result;
	}
	
	/**
	 * 스마트워크Rule 규칙 삭제
	 * 
	 * @param deleteSmartRule : 삭제할 규칙번호 배열
	 * @return
	 */
	@Transactional
	public int deleteSmartRule(ArrayList<Integer> deleteSmartRule) {
		int result = 0;
		for(Integer i :deleteSmartRule ) {
			result += contentsRuleGuideRepository.deleteSmartRule(i);
		}
		return result;
	}

	/**
	 * 스마트워크Rule 규칙 삽입
	 * 
	 * @param param : 추가 할 규칙 번호
	 * @param userindex
	 * @return
	 */
	@Transactional
	public int insertNewRule(Object param, String userindex) {
		int result = contentsRuleGuideRepository.insertNewRule(param, userindex);
		return result;
	}
}
