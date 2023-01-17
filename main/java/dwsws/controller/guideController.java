package dwsws.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dwsws.dto.digitalDTO;
import dwsws.outputdata.GuideinfoDO;
import dwsws.outputdata.digitalBoardDO;
import dwsws.service.commonService;
import dwsws.service.digitalService;
import dwsws.service.guideService;
import dwsws.service.priorKnowService;
import dwsws.service.toolGuideService;
import net.sf.json.JSONArray;

@Controller
/**가이드페이지 관련 컨트롤러
 * 
 * - guide 메소드 : 가이드 메인페이지(스마트워크Rule)
 * - toolGuide 메소드 :  도구가이드 페이지
 * - priorKnow 메소드 :  보안 페이지
 * - list 메소드 : 디지털창 페이지
 * 
 * 	@Autowired
	guideService guideService;// 스마트워크 rule
	@Autowired
	commonService commonService;// 즐겨찾기목록
	@Autowired
	toolGuideService toolGuideService;// 도구가이드
	@Autowired
	priorKnowService priorKnowService;// 보안
	@Autowired
	digitalService digitalService; // 디지털창
 * 
 * 페이지에 필요한 모든 데이터를 model에 담아서 jsp에 전송
 * guide-lnb와 관련된 데이터도 같이 보냄 : guide-lnb에는 각 카테고리(도구가이드, 보안..)에 해당하는 컨텐츠 리스트(Zoom, Lineworks..) 데이터 필요
 * desktop-version/include-pages/guide-lnb.jsp에 guide-lnb 위치
 * 
 * @author 김예린
 *
 */
public class guideController {

	@Autowired
	guideService guideService;// 스마트워크 rule
	@Autowired
	commonService commonService;// 즐겨찾기목록
	@Autowired
	toolGuideService toolGuideService;// 도구가이드
	@Autowired
	priorKnowService priorKnowService;// 보안
	@Autowired
	digitalService digitalService; // 디지털창
	
	/**
	 * error handling functionality added due to the erase of intercepter about accessment to guide page
	 * try-catch문 생성 이유 : 로그인 정보가 없을 때 가이드 페이지를 보여주기 위해서(session 관련)
	 * Modification made on 21.01.27 by 박승수
	 * 가이드페이지-스마트워크Rule 페이지 관련(가이드페이지 메인)
	 * @RequestMapping(path = "/guide") 
	 * 
	 * @param model
	 * @param session
	 * @param device
	 * 
	 * @return ModelMap에 담은 데이터
	 * @return PC인 경우 desktop-version/guide/rule
	 * @return 모바일인 경우 mobile-version/guide/rule
	 * @throws JsonProcessingException
	 */
	@RequestMapping(path = "/guide") // guide page
	public String guide(ModelMap model, HttpSession session, Device device) throws JsonProcessingException {
		// regarding to smartworkRule
		
		//error handling added due to the erase of intercepter about guide page
		try {
			// smartworkRule
			List<String> ruleList = guideService.selectRuleList();
			model.addAttribute("ruleList", ruleList);

			// regarding to smartworkRule fullversoin video url
			String url = guideService.selectFullVersionUrl();
			model.addAttribute("fullUrl", url);

			// toolguide list for guide-Inb
			List<String> toolGuideList = toolGuideService.selectToolguideList();
			ObjectMapper mapper = new ObjectMapper();
			String json_toolGuideList = mapper.writeValueAsString(toolGuideList);
			model.addAttribute("toolGuideList", json_toolGuideList);
			
			// priorKnow list for guide-Inb
			List<String> priorKnowList = priorKnowService.selectPriorKnowList();
			String json_priorKnowList = mapper.writeValueAsString(priorKnowList);
			model.addAttribute("priorKnowList", json_priorKnowList);
			
			//digital list for guide-lnb
			List<String> digitalList = digitalService.selectDigitalList();
			String json_digitalList = mapper.writeValueAsString(digitalList);
			model.addAttribute("digitalList", json_digitalList);

			// 한줄설명
			String oneDesc = guideService.selectOneDesc();
			model.addAttribute("oneDesc", oneDesc);

			// 상세설명
			String ruleDesc = guideService.selectRuleDesc();
			model.addAttribute("ruleDesc", ruleDesc);

			// 영상제목
			String videoTitle = guideService.selectVideoTitle();
			model.addAttribute("videoTitle", videoTitle);

			//category 목록
			List<String> categoryList = guideService.selectCategoryList();
			String json_categoryList = mapper.writeValueAsString(categoryList);
			model.addAttribute("categoryList",json_categoryList);
			
			// regarding to bookmark index only among userfavoriteList
			// 북마크를 출력할 때 사용자의 즐겨찾기에 이미 포함된 경우, 즐겨찾기의 별표가 색칠되어서 출력되어야하기 때문에 사용자별 즐겨찾기 목록 필요
			// 사용자의 즐겨찾기 idx 리스트와 북마크 idx를 비교하는 방식
			String userindex = session.getAttribute("userindex").toString(); // session에서 userindex가져오기
			List<Integer> bmkidx = commonService.selectBmkidxList(userindex);// only bookmark idx among favorite list
			model.addAttribute("bmkidx", bmkidx);
			
		} catch(NullPointerException e) {
			//add dummy bmkidx to avoid non-appending error on rule.jsp
			model.addAttribute("bmkidx", new Vector());
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("non-nullpointerexception error occured");
			e.printStackTrace();
		}
		
		if (device.isMobile()) {
			return "mobile-version/guide/rule";
		} else {
			return "desktop-version/guide/rule";
		}

	}
	/**
	 * error handling functionality added due to the erase of intercepter about accessment to toolguide page
	 * try-catch문 생성 이유 : 로그인 정보가 없을 때 가이드 페이지를 보여주기 위해서(session 관련)
	 * Modification made on 21.01.27 by 박승수, 김예린
	 * 가이드페이지-도구 가이드 관련
	 * @RequestMapping(path = "/toolguide")
	 * 
	 * @param model
	 * @param session
	 * @param start : 동영상 시작 시간
	 * @param tool : 도구가이드 컨텐츠 ex)Zoom, Lineworks..
	 * @param device
	 * 
	 * @return ModelMap에 담은 데이터
	 * @return PC인 경우 desktop-version/guide/tool
	 * @return 모바일인 경우 mobile-version/guide/tool
	 * 
	 * @throws JsonProcessingException
	 */
	@RequestMapping(path = "/toolguide") // 도구가이드(zoom, lineworks)
	public String toolGuide(ModelMap model, HttpSession session,
			@RequestParam(name = "start", required = false, defaultValue = "0") int start,
			@RequestParam(name = "tool", required = true) String tool, Device device) throws JsonProcessingException {
		
		try {
			ObjectMapper mapper = new ObjectMapper();

			// tool, comments, youtubeurl
			GuideinfoDO guideInfo = toolGuideService.selectGuideInfo(tool);
			String json_guideInfo = mapper.writeValueAsString(guideInfo);
			model.addAttribute("guideInfo", json_guideInfo);

			// section List
			List<String> sectionList = toolGuideService.selectSectionList(tool);
			String json_sectionList = mapper.writeValueAsString(sectionList);
			model.addAttribute("sectionList", json_sectionList);

			// section list num
			int sectionNum = toolGuideService.selectSectionListNum(tool);
			model.addAttribute("num", sectionNum);

			// regarding to bookmark list
			List<String> bmkList = toolGuideService.selectBookmarkList(tool);
			model.addAttribute("bmkList", bmkList);
			
			// toolguide list for guide-Inb
			List<String> toolGuideList = toolGuideService.selectToolguideList();
			String json_toolGuideList = mapper.writeValueAsString(toolGuideList);
			model.addAttribute("toolGuideList", json_toolGuideList);

			// video start time
			model.addAttribute("start", start);

			// priorKnow list for guide-Inb
			List<String> priorKnowList = priorKnowService.selectPriorKnowList();
			String json_priorKnowList = mapper.writeValueAsString(priorKnowList);
			model.addAttribute("priorKnowList", json_priorKnowList);
			
			//digital list for guide-lnb
			List<String> digitalList = digitalService.selectDigitalList();
			String json_digitalList = mapper.writeValueAsString(digitalList);
			model.addAttribute("digitalList", json_digitalList);
			
			//category 목록
			List<String> categoryList = guideService.selectCategoryList();
			String json_categoryList = mapper.writeValueAsString(categoryList);
			model.addAttribute("categoryList",json_categoryList);
			
			//도구가이드에 해당하는 contents 목록
			List<String> contentsList = guideService.selectContentsList("도구 가이드");
			String json_contentsList = mapper.writeValueAsString(contentsList);
			model.addAttribute("contentsList", json_contentsList);
			
			// regarding to bookmark index only among userfavoriteList
			String userindex = session.getAttribute("userindex").toString(); // session에서 userindex가져오기
			List<Integer> bmkidx = commonService.selectBmkidxList(userindex);// only bookmark idx among favorite list
			model.addAttribute("bmkidx", bmkidx);
		} catch(NullPointerException e) {
			model.addAttribute("bmkidx", new Vector());
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("non-nullpointerexception error occured");
			e.printStackTrace();
		}
		
		
		if (device.isMobile()) {
			
			return "mobile-version/guide/tool";
		} else {
			
			return "desktop-version/guide/tool";
		}
	}
	/**
	 * 가이드페이지-보안관련
		by 김예린
		try-catch문 생성 이유 : 로그인 정보가 없을 때 가이드 페이지를 보여주기 위해서(session 관련)
		@RequestMapping(path = "/priorKnow") 
		
	 * @param model
	 * @param session
	 * @param start : 동영상 시작 시간
	 * @param tool : 보안 컨텐츠 ex)VPN, BearDoc..
	 * @param device
	 * @param second : 베어독(BearDoc)에서 두번째 영상인지 판별
	 * 
	 * @return ModelMap에 담은 데이터
	 * @return PC인 경우 desktop-version/guide/etc
	 * @return 모바일인 경우 mobile-version/guide/etc
	 * @return 베어독 PC인 경우 desktop-version/guide/BearDoc
	 * @return 베어독 모바일인 경우 mobile-version/guide/BearDoc
	 * @throws JsonProcessingException
	 * 
	 */
	@RequestMapping(path = "/priorKnow") // 보안
	public String priorKnow(ModelMap model, HttpSession session,
			@RequestParam(name = "start", required = false, defaultValue = "0") int start,
			@RequestParam(name = "tool", required = true) String tool, 
			@RequestParam(name = "second", required = false, defaultValue = "false") boolean second, Device device) throws JsonProcessingException {
		
		try {
			ObjectMapper mapper = new ObjectMapper();

			// priorKnow list for guide-Inb
			List<String> priorKnowList = priorKnowService.selectPriorKnowList();
			String json_priorKnowList = mapper.writeValueAsString(priorKnowList);
			model.addAttribute("priorKnowList", json_priorKnowList);

			// toolguide list for guide-Inb
			List<String> toolGuideList = toolGuideService.selectToolguideList();
			String json_toolGuideList = mapper.writeValueAsString(toolGuideList);
			model.addAttribute("toolGuideList", json_toolGuideList);
			
			//digital list for guide-lnb
			List<String> digitalList = digitalService.selectDigitalList();
			String json_digitalList = mapper.writeValueAsString(digitalList);
			model.addAttribute("digitalList", json_digitalList);

			// tool, comments, youtubeurl
			GuideinfoDO guideInfo = toolGuideService.selectGuideInfo(tool);

			//here should be checked
			String json_guideInfo = mapper.writeValueAsString(guideInfo);
			model.addAttribute("guideInfo", json_guideInfo);

			// section List
			List<String> sectionList = toolGuideService.selectSectionList(tool);
			String json_sectionList = mapper.writeValueAsString(sectionList);
			model.addAttribute("sectionList", json_sectionList);

			// section list num
			int sectionNum = toolGuideService.selectSectionListNum(tool);
			model.addAttribute("num", sectionNum);

			// regarding to bookmark list
			List<String> bmkList = toolGuideService.selectBookmarkList(tool);
			model.addAttribute("bmkList", bmkList);

			// video start time
			model.addAttribute("start", start);
			
			//category 목록
			List<String> categoryList = guideService.selectCategoryList();
			String json_categoryList = mapper.writeValueAsString(categoryList);
			model.addAttribute("categoryList",json_categoryList);
			
			//도구가이드에 해당하는 contents 목록
			List<String> contentsList = guideService.selectContentsList("보안");
			String json_contentsList = mapper.writeValueAsString(contentsList);
			model.addAttribute("contentsList", json_contentsList);
			
			//두번째 영상을 재생시켜야 할 경우 true return
			if(second == true) {
				model.addAttribute("second", true);
			}else {
				model.addAttribute("second", false);
			}
			
			// regarding to bookmark index only among userfavoriteList
			String userindex = session.getAttribute("userindex").toString(); // session에서 userindex가져오기
			List<Integer> bmkidx = commonService.selectBmkidxList(userindex);// only bookmark idx among favorite list
			model.addAttribute("bmkidx", bmkidx);
			
		} catch(NullPointerException e) {
			model.addAttribute("bmkidx", new Vector());
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("non-nullpointerexception occured");
			e.printStackTrace();
		}
		
		
		//베어독인 경우
		if(tool.equals("BearDoc")) {
			if (device.isMobile()) {
				
				return "mobile-version/guide/BearDoc";
			} else {
				
				return "desktop-version/guide/BearDoc";
			}
		}
		
		if (device.isMobile()) {
			
			return "mobile-version/guide/etc";
		} else {
			
			return "desktop-version/guide/etc";
		}
	}
	
	/**
	 * 가이드페이지-보안-베어독(BearDoc) 관련
	 * 베어독 part2(두번째 영상)의 URL을 가져오는 함수
	 * 자동화를 위해 파라미터로 입력되는 도구의 URL을 가져오도록 구현
	 * by 김예린
	 * @GetMapping(path = "/secondUrl")
	   @ResponseBody
	   
	 * @param tool : 두번째 영상의 URL을 가져오고 싶은 도구명 ex)BearDoc
	 * 
	 * @return url : 두번재 영상의 URL 리턴
	 */
	// BearDoc second youtube url
	@GetMapping(path = "/secondUrl")
	@ResponseBody
	public String secondUrl(@RequestParam String tool) {
		String url = priorKnowService.selectUrl2(tool);
		
		return url;
	}
	
	/**
	 * 가이드페이지-디지털 창 관련 
	 * by 김예린
	 * @RequestMapping("/digital")
	 * 
	 * @param model
	 * @param session
	 * @param title
	 * @param device
	 * 
	 * @return ModelMap에 담은 데이터
	 * @return PC인 경우 desktop-version/guide/digital
	 * @return 모바일인 경우 mobile-version/digital/digital
	 * @throws Exception
	 * 
	 */
	@RequestMapping("/digital")
	public String list(ModelMap model, HttpSession session, @RequestParam(name = "title", required = true) String title, Device device) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		// priorKnow list for guide-Inb
		List<String> priorKnowList = priorKnowService.selectPriorKnowList();
		String json_priorKnowList = mapper.writeValueAsString(priorKnowList);
		model.addAttribute("priorKnowList", json_priorKnowList);

		// toolguide list for guide-Inb
		List<String> toolGuideList = toolGuideService.selectToolguideList();
		String json_toolGuideList = mapper.writeValueAsString(toolGuideList);
		model.addAttribute("toolGuideList", json_toolGuideList);

		//digital list for guide-lnb
		List<String> digitalList = digitalService.selectDigitalList();
		String json_digitalList = mapper.writeValueAsString(digitalList);
		model.addAttribute("digitalList", json_digitalList);
		
		//category 목록
		List<String> categoryList = guideService.selectCategoryList();
		String json_categoryList = mapper.writeValueAsString(categoryList);
		model.addAttribute("categoryList",json_categoryList);
		
		// @RequestParam으로 받은 title은 영어로 되어있고, 데이터베이스에는 한글명으로 저장되어있기 때문에 영어 -> 한글로 변환하는 작업
		String digitalTitle = null;
		
		if(title.equals("BearWorld")) {
			digitalTitle = "베아월드";
		}
		else if(title.equals("SimpleTool")) {
			digitalTitle = "간편도구";
		}else {
			digitalTitle = title;
		}
		
		//digital contents list 반환		
		List<digitalBoardDO> list = digitalService.digitalListAll(digitalTitle); // 제목, 썸네일
		List<digitalDTO> digitalContentslList = new ArrayList<>(list.size());

		JSONArray jsonArray = new JSONArray();
		
		for(int i = 0; i < list.size(); i++) {
			List<String> digitalImgs = digitalService.digitalImgs(list.get(i).getIdx()); // 이미지 리스트
			
			digitalContentslList.add(new digitalDTO(list.get(i).getIdx(), jsonArray.fromObject(digitalImgs), 
					list.get(i).getTitle(), list.get(i).getComments()));
		}
		
		model.addAttribute("digitalContentsList", jsonArray.fromObject(digitalContentslList));
		
		if (device.isMobile()) {
			
			return "mobile-version/digital/digital";
		} else {
			
			return "desktop-version/guide/digital";
		}
	}
}