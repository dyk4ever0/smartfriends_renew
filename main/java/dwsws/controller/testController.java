package dwsws.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import dwsws.service.testService;

@Controller
public class testController {

	@Autowired
	testService testService;

	@RequestMapping(path = "/test")
	public String test(ModelMap model) {
	/*	
		List<categoryDTO> list = testService.selectList(); 
		List<contentsDTO> list2 = testService.selectContentsList(); 
		List<favoriteListDTO> list3 = testService.selectFavoriteList();
		List<questionListDTO> list4 = testService.selectQuestionList();
		List<testResultDTO> list5 = testService.selectResultList(); 
		List<ruleThirdDTO> list6 = testService.selectRuleList();
		List<sectionDTO> list7 = testService.selectSectionList();
		List<smartworkRuleDTO> list8 = testService.selectSmartworkList();
		List<testOfferToolDTO> list9 = testService.selectToolList();
		List<userInfoDTO> list10 = testService.selectUserList();
		
		
		System.out.println(list); 
		System.out.println(list2);
		System.out.println(list3);
		System.out.println(list4);
		System.out.println(list5); 
		System.out.println(list6);
		System.out.println(list7);
		System.out.println(list8);
		System.out.println(list9);
		System.out.println(list10);
		
	*/
		
		/*
		//insert test
		userInfoDTO user = new userInfoDTO();
		user.setUserindex("1375232036047");
		user.setAvatar("/share/proxy/alfresco/api/node/workspace/SpacesStore/bc22c44a-6d16-47cf-baaf-1d48e0a9ee74/content/thumbnails/avatar?c=force");
		user.setCompanycode("00001");
		user.setCompanyname("idsTrust");
		user.setDutycode(2065);
		user.setOrgcode("01282");
		user.setOrgname("IT자산관리팀");
		user.setUserid("minjh");
		user.setUsername("민종혁");
		
		testService.insertUserinfo(user);
		*/
		//사용자별 즐겨찾기 목록 호출
		List<UserFavoriteList> list = testService.selectUserFavoriteList("0000320083111415387");
		//System.out.println(list);
		model.addAttribute("favoriteList", list);
		return "test";
	}

	@RequestMapping(path = "/guidepage")
	public String guides(ModelMap model, HttpSession session) throws JsonProcessingException {
		List<SmartworkRuleDO> list = testService.selectBookmarkList();
		
		//List<UserFavoriteList> favoritelist = testService.selectUserFavoriteList("0000320083111413609"); //즐겨찾기 하고싶은 사람의 userindex입력
		
		//session에서 userindex가져오기
		String userindex = session.getAttribute("userindex").toString();
		List<UserFavoriteList> favoritelist = testService.selectUserFavoriteList(userindex); //즐겨찾기 하고싶은 사람의 userindex입력하여 즐겨찾기 리스트를 받음
		
		
		//데이터를 json형태로 변환
		ObjectMapper mapper = new ObjectMapper();
		String json_favoritelist = mapper.writeValueAsString(favoritelist);
		
		
		model.addAttribute("bookmark", list); //스마트워크룰 :소통/협업
		model.addAttribute("json_favoritelist",json_favoritelist );//사용자별 즐겨찾기 목록
		
		return "htmljspexample";
	}

}
