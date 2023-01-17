package dwsws.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import dwsws.repository.guideRepository;

/**
 * 가이드페이지 관련 서비스
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
	guideRepository guideRepository;
 * 
 * @author 김예린
 *
 */
@Service
public class guideService {
	@Autowired
	guideRepository guideRepository;
	
	/**
	 *  스마트워크Rule 관련 데이터 - 업무계획수립 : 업무계획을 수립하여 일과를 계획합니다..
	 * 
	 * @return 스마트워크Rule 관련 데이터
	 * @throws JsonProcessingException
	 */
	@Transactional
	public List<String> selectRuleList() throws JsonProcessingException{
		List<String> arrayList = new ArrayList<>(); //list for smartworkRule
		ObjectMapper mapper = new ObjectMapper(); //for json format
		
		int num = guideRepository.selectRuleNum(); // rule num
		for(int i=1; i<=num; i++) { //add smartworkRule to list as json format
			arrayList.add(mapper.writeValueAsString(guideRepository.selectRuleList(i)));
		}
		
		return arrayList;
	}
	
	/**
	 * 스마트워크룰 갯수
	 * 
	 * @return 스마트워크룰 갯수
	 */
	@Transactional
	public int selectRuleNum() {
		int num = guideRepository.selectRuleNum();
		return num;
	}
	
	/**
	 * 스마트워크룰 동영상 url
	 * 
	 * @return 스마트워크룰 동영상 url
	 */
	@Transactional
	public String selectFullVersionUrl() {//smartworkRule fullversoin video url
		String url = guideRepository.selectFullVersionUrl();
		return url;
	}

	/**
	 *  스마트워크룰 한 줄 설명
	 * 
	 * @return  스마트워크룰 한 줄 설명
	 */
	@Transactional
	public String selectOneDesc() {
		String oneDesc = guideRepository.selectOneDesc();
		return oneDesc;
	}
	
	/**
	 * 스마트워크룰 상세 설명
	 * 
	 * @return 스마트워크룰 상세 설명
	 */
	@Transactional
	public String selectRuleDesc() {
		String ruleDesc = guideRepository.selectRuleDesc();
		return ruleDesc;
	}
	
	/**
	 * 스마트워크룰 영상제목
	 * 
	 * @return 스마트워크룰 영상제목
	 */
	@Transactional
	public String selectVideoTitle() {
		String videoTitle = guideRepository.selectVideoTitle();
		return videoTitle;
	}
	
	/**
	 *  특정 카테고리에 해당하는 툴 목록(for 모바일)
	 * 
	 * @param category  :카테고리명
	 * @return
	 */
	@Transactional
	public List<String> selectContentsList(String category){
		List<String> list = guideRepository.selectContentsList(category);
		return list;
	}
	
	/**
	 * category 목록(for 모바일)
	 * 
	 * @return
	 */
	@Transactional
	public List<String> selectCategoryList(){
		List<String> list = guideRepository.selectCategoryList();
		return list;
	}
}
