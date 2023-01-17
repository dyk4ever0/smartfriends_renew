package dwsws.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dwsws.service.contentsRuleGuideService;
import dwsws.service.guideService;
import dwsws.service.contents_admin_lnbService;

@Controller
/**
 * admin - 컨텐츠관리 - 가이드관리 - 스마트워크Rule 관련 컨트롤러
 * 
 * - ruleContents 메소드 : 기존 스마트워크Rule 데이터를 출력하는 메소드
 * - submitSet 메소드 : 스마트워크Rule 데이터 수정 후 제출버튼을 눌렀을 경우 실행되는 함수. 백엔드로 데이터 넘겨줘서 저장.
 * - callContentsList 메소드 : 컨텐츠 관리 페이지의 lnb 데이터 호출
 * 
 * @Autowired
	guideService guideService;//스마트워크 rule
	
	@Autowired
	contentsRuleGuideService contentsRuleGuideService;//도구 목록 및 북마크리스트
	
	@Autowired
	contents_admin_lnbService contents_admin_lnbService; // contents_admin_lnb
	
	
 * 
 * @author 김예린
 *
 */
public class rule_modifyController {

	@Autowired
	guideService guideService;//스마트워크 rule
	
	@Autowired
	contentsRuleGuideService contentsRuleGuideService;//도구 목록 및 북마크리스트
	
	@Autowired
	contents_admin_lnbService contents_admin_lnbService; // contents_admin_lnb
	
	//스마트워크rule 수정
	/** 
	 * 기존 스마트워크Rule 데이터를 출력하는 메소드
	 * 
	 * @RequestMapping(path = "/contents")
	 * 
	 * @param model
	 * 
	 * @return ModelMap에 담은 데이터
	 * @return desktop-version/admin/guide-manage/rule-modify
	 * 
	 * @throws JsonProcessingException
	 * 
	 * @author 김예린
	 */
	@RequestMapping(path = "/contents")
	public String ruleContents(ModelMap model) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		List<String> ruleList = guideService.selectRuleList();
		model.addAttribute("ruleList", ruleList);//smartworkRule
		
		
		//regarding to smartworkRule fullversoin video url
		String url = guideService.selectFullVersionUrl();
		String json_url = mapper.writeValueAsString(url);
		model.addAttribute("fullUrl", json_url);
		
		 //한줄설명
		 String oneDesc = guideService.selectOneDesc();
		 String json_oneDesc = mapper.writeValueAsString(oneDesc);
		 model.addAttribute("oneDesc", json_oneDesc);
		 
		 //상세설명
		 String ruleDesc = guideService.selectRuleDesc();
		 String json_ruleDesc = mapper.writeValueAsString(ruleDesc);
		 model.addAttribute("ruleDesc", json_ruleDesc);
		 
		//영상제목
		 String videoTitle = guideService.selectVideoTitle();
		 String json_videoTitle = mapper.writeValueAsString(videoTitle);
		 model.addAttribute("videoTitle", json_videoTitle);
		
		 
		 //도구리스트
		 //규칙과 관련된 북마크 select 박스를 위해서 필요
		 List<String> toolList = contentsRuleGuideService.selecttoolList();
		 String json_toolList = mapper.writeValueAsString(toolList);
		 model.addAttribute("toolList",json_toolList );
		 
		//도구별 북마크 리스트 
		//규칙과 관련된 북마크 select 박스를 위해서 필요
		 Map<String,Object> bmkMap = contentsRuleGuideService.selectBmkList2Tool();
		 String json_bmkMap = mapper.writeValueAsString(bmkMap);
		 model.addAttribute("bmkMap", json_bmkMap);
	 
		 return "desktop-version/admin/guide-manage/rule-modify";
	}
	
	
	/**
	 * 제출버튼 눌렀을 경우 프론트에서 변경된 데이터를 map에 담아서 전송
	 * Ajax로 데이터를 받음
	 * 
	 * 받은 map데이터를 newmap으로 받아서 사용
	 * key를 이용해서 경우에 따라 다른 메소드 적용
	 * 
	 * 영상제목 수정, 상세설명 수정은 특정 부분만 변경하나, 북마크가 수정된 경우 특수하게 처리
	 * 북마크가 속한 규칙을 알아내서 해당 규칙과 관련된 북마크를 모두 삭제하고 북마크를 새로 삽입
	 * 위와 같이 적용한 이유 : 북마크가 변경되 경우 기존 북마크와 변경된 북마크를 대조하는 과정이 필요한데, 구현하기 어려워서 위와 같은 방법을 채택
	 * 
	 * 새로운 스마트워크 룰에 대한 북마크 추가는 스마트워크 룰이 추가된 이후에 삽입되어야 하기 때문에 keys2를 생성하여 분리
	 * 
	 * @PostMapping(path = "/submitSet4rule")
	   @ResponseBody
	 * 
	 * @param map
	 * @param session
	 * 
	 * @author 김예린
	 */
	@PostMapping(path = "/submitSet4rule")
	@ResponseBody
	public void submitSet(@RequestBody HashMap map, HttpSession session) {
		HashMap newmap = map;
		
		String userindex = session.getAttribute("userindex").toString(); //session에서 userindex가져오기
	
		Iterator<String> keys = newmap.keySet().iterator();
		
		while(keys.hasNext()) {
			String key = keys.next();
			if(key.equals("title")) {
				//영장제목이 수정된 경우
				String title = (String) newmap.get("title");
				contentsRuleGuideService.updateVideoTitle4Rule(title, userindex);
				
			}else if(key.equals("oneDesc")) {
				//한 줄 설명이 수정된 경우
				String oneDesc = (String) newmap.get("oneDesc");
				contentsRuleGuideService.updateOneDesc(oneDesc, userindex);
				
			}else if(key.equals("ruleDesc")) {
				//상세 설명이 수정된 경우
				String ruleDesc = (String) newmap.get("ruleDesc");
				contentsRuleGuideService.updateComments4Rule(ruleDesc, userindex);
				
			}else if(key.equals("url")) {
				//url이 수정된 경우
				String url = (String) newmap.get("url");
				contentsRuleGuideService.updateUrl4Rule(url, userindex);
				
			}else if(key.contains("forRule")) {
				//규칙명, 규칙설명이 수정된 경우
				int ruleNum = Integer.parseInt(key.split("forRule")[0].split("_")[1]);
				String comments = ((HashMap)newmap.get(key)).get("comments").toString();
				String ruleName = ((HashMap)newmap.get(key)).get("title").toString();
				
				contentsRuleGuideService.updateRuleNameAndDesc(ruleName, comments, userindex, ruleNum);
				
			}else if(key.contains("forBmk")) {
				//북마크가 수정된 경우
				//몇번째 규칙인지 추출
				int ruleNum = Integer.parseInt(key.split("forBmk")[0].split("_")[1]);
				
				//특정 번째 규칙 삭제
				contentsRuleGuideService.deleteRule(ruleNum);
			
				//추가할 북마크 리스트
				ArrayList<String> addBmkList4Rule = (ArrayList<String>)newmap.get(key);
				contentsRuleGuideService.insertBmk4SpecificRule(addBmkList4Rule, userindex, ruleNum);
			
				
			}else if(key.equals("deleteRule")) {
				//규칙을 삭제한 경우
				//삭제할 북마크 리스트
				ArrayList<Integer> deleteSmartRule = (ArrayList<Integer>)newmap.get(key);
				contentsRuleGuideService.deleteSmartRule(deleteSmartRule);
				
			}else if(key.contains("addNewRule")) {
				contentsRuleGuideService.insertNewRule(newmap.get(key), userindex);
				
			}
		}
		
		//새로운 스마트워크 룰에 대한 북마크 추가는 스마트워크 룰이 추가된 이후에 삽입되어야 하기 때문에 keys2를 생성하여 분리
		Iterator<String> keys2 = newmap.keySet().iterator();
		while(keys2.hasNext()) {
			String key = keys2.next();
			if(key.contains("newBmk4newRule")) {
				//새로운 스마트워크룰에 대한 북마크 추가
				ArrayList<String> addBmkList4Rule = (ArrayList<String>)newmap.get(key);
				contentsRuleGuideService.insertBmk4SpecificRule(addBmkList4Rule, userindex, Integer.parseInt(key.split("_")[1]));
			}
		}
	}

	
	
	/**
	 * contents-lnb에 삽입될 컨텐츠 목록(Zoom, Lineworks..) 리턴
	 * 스마트워크룰과 디지털창 관련 컨텐츠는 제외
	 * 생성이유 : 컨텐츠관리 - 가이드관리탭 에서 컨텐츠 추가 시 컨텐츠 목록이 업데이트되기 때문
	 * 
	 * 컨텐츠관리 - 가이드관리 탭에서만 사용
	 * 
	 * @PostMapping(path = "/callContentsList")
	   @ResponseBody
	   
	 * @return list
	 * 
	 * @author 김예린
	 */
	//contents_admin_lnb의 contents 리스트 호출
	@PostMapping(path = "/callContentsList")
	@ResponseBody
	public List<String> callContentsList(){
		List<String> list = contents_admin_lnbService.contentsList4admin();
		return list;
	}
}
