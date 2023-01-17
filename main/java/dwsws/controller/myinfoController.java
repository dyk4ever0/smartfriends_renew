package dwsws.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import dwsws.outputdata.suggestionListFormDO;
import dwsws.service.diagnoseService;
import dwsws.service.myinfoService;

/**
 * user - 사용자 개인 페이지 (마이페이지)
 * 
 * - getUniversalMypageInfo : 그래프 및 정답/오답노트를 제외한, 내정보 페이지를 출력하는데 필요한 모든 데이터를 return
 * - getMoreStudyingList : 정답노트에 필요한 데이터를 return
 * - getDataOfAnalysisByToolForTestData : 그래프를 제외한, 도구별 현황을 출력하는데 필요한 종합 정보를 return
 * - getDataOfAnalysisByToolForGraph : 도구별 현황을 출력하는데 필요한 그래프의 data를 return
 * - getTestOfferToolsList : 진단을 제공하는 도구들의 이름을 List로 return 
 * - linkToMyinfoPage : 내정보 페이지로 redirect
 * 
 * @Autowired
	myinfoService myinfo;
	@Autowired
	diagnoseService diagnose;
	
 * @author 박승수 
 *
 */
@Controller
public class myinfoController {
	@Autowired
	myinfoService myinfo;
	@Autowired
	diagnoseService diagnose;

	/**
	 * 그래프 및 정답/오답노트를 제외한, 내정보 페이지를 출력하는데 필요한 모든 데이터를 return
	 * 
	 * @GetMapping(path = "/getUniversalMypageInfo", produces = "application/json;charset=utf8")
	 * @ResponseBody
	
	 * @param session 사용자 정보를 담고 있는 세션 데이터 
	 * @return jsonTypeString 모든 도구의 진단 정보를 아우르는 결과값을 가진 Map객체가 json으로 변환됨.
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@GetMapping(path = "/getUniversalMypageInfo", produces = "application/json;charset=utf8")
	@ResponseBody
	public String getUniversalMypageInfo(HttpSession session) throws Exception {
		String userindex = session.getAttribute("userindex").toString();
		HashMap universalInfo = myinfo.getUniversalMypageInfoByUserindex(userindex);

		ObjectMapper mapper = new ObjectMapper();
		String json_universalInfo = mapper.writeValueAsString(universalInfo);

		return json_universalInfo;
	}

	/**
	 * 정답노트에 필요한 데이터를 return
	 * 
	 * @GetMapping(path = "/getMoreStudy", produces = "application/json;charset=utf8")
	 * @ResponseBody
	
	 * @param session 사용자 정보를 담고 있는 세션 데이터 
	 * @return jsonTypeString 정답노트에 필요한 데이터를 가진 suggestionListFormDO의 list가 json으로 변환됨.
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@GetMapping(path = "/getMoreStudy", produces = "application/json;charset=utf8")
	@ResponseBody
	public String getMoreStudyingList(HttpSession session) throws Exception {
		String userindex = session.getAttribute("userindex").toString();
		List<suggestionListFormDO> moreStudyList = diagnose.suggestionList(userindex, 1);

		ObjectMapper mapper = new ObjectMapper();
		String json_moreStudyList = mapper.writeValueAsString(moreStudyList);

		return json_moreStudyList;
	}

	/**
	 * 그래프를 제외한, 도구별 현황을 출력하는데 필요한 종합 정보를 return
	 * 
	 * @GetMapping(path = "/getDataOfAnalysisByToolForTestData", produces = "application/json;charset=utf8")
	 * @ResponseBody
	
	 * @param session 사용자 정보를 담고 있는 세션 데이터 
	 * @param toolname 데이터를 받고자하는 도구의 이름 
	 * @param limitation 최근 진단 횟수 중 몇 회까지를 받을 것인지의 숫자 
	 * @return jsonTypeString 도구에 대한 종합 결과 데이터를 가진 Map 객체가 json으로 변환됨.
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@GetMapping(path = "/getDataOfAnalysisByToolForTestData", produces = "application/json;charset=utf8")
	@ResponseBody
	public String getDataOfAnalysisByToolForTestData(HttpSession session,
			@RequestParam(name = "toolname", required = true) String toolname,
			@RequestParam(name = "limitation", required = true) int limitation) throws Exception {
		String userindex = session.getAttribute("userindex").toString();
		HashMap resultMap = new HashMap();
		List<?> recentData = null;
		try {
			resultMap = myinfo.getUniversalDataForLeftsideOfTooltab(toolname, userindex);
			recentData = myinfo.getRecent3TestResult(toolname, userindex, limitation);
			resultMap.put("recentTestResults", recentData);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("recentTestResults", null);
		}
		ObjectMapper mapper = new ObjectMapper();
		String json_resultMap = mapper.writeValueAsString(resultMap);

		return json_resultMap;
	}

	/**
	 * 도구별 현황을 출력하는데 필요한 그래프의 data를 return
	 * 
	 * @GetMapping(path = "/getDataOfAnalysisByToolForGraph", produces = "application/json;charset=utf8")
	 * @ResponseBody
	
	 * @param session 사용자 정보를 담고 있는 세션 데이터 
	 * @param toolname 그래프에 대한 정보를 받고자 하는 도구의 이름 
	 * @return jsonTypeString 도구에 대한 세션별 개인/평균 점수를 가진 Map 객체가 json으로 변환됨.
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@GetMapping(path = "/getDataOfAnalysisByToolForGraph", produces = "application/json;charset=utf8")
	@ResponseBody
	public String getDataOfAnalysisByToolForGraph(HttpSession session,
			@RequestParam(name = "toolname", required = true) String toolname) throws Exception {
		String userindex = session.getAttribute("userindex").toString();
		HashMap sectionScore = myinfo.getSectionalScore(toolname, userindex);
		HashMap averageScore = myinfo.getAverageSectionScore(toolname);
		
		HashMap resultMap = new HashMap();
		resultMap.put("personal", sectionScore);
		resultMap.put("average", averageScore);

		ObjectMapper mapper = new ObjectMapper();
		String json_resultMap = mapper.writeValueAsString(resultMap);

		return json_resultMap;
	}
	
	/**
	 * 진단을 제공하는 도구들의 이름을 List로 return 
	 * 
	 * @GetMapping(path="/getTestOfferTools", produces = "application/json;charset=utf8")
	 * @ResponseBody
	
	 * @return jsonTypeString 진단을 제공하는 도구들의 이름을 가진 List객체가 json으로 변환됨 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@GetMapping(path="/getTestOfferTools", produces = "application/json;charset=utf8")
	@ResponseBody
	public String getTestOfferToolsList() throws Exception {
		List<String> offeringTools = diagnose.testOfferingToolsList();
		ObjectMapper mapper = new ObjectMapper();
		String json_offeringTools = mapper.writeValueAsString(offeringTools);

		return json_offeringTools;
	}
	
	/**
	 * 내정보 페이지로 redirect
	 * 
	 * @RequestMapping(path="/myinfo")
	 * 
	 * @param device 사용자가 접근한 기기가 PC인지 MOBILE인지 판별하는 library제공 객체 
	 * @return String 내 정보 페이지를 보여줄 .jsp 파일 위치 
	 * 
	 * @author 박승수 
	 */
	@RequestMapping(path="/myinfo")
	public String linkToMyinfoPage(Device device) {
		if(device.isMobile()) {
			System.out.println("mobile version");
			return "mobile-version/mypage";
		}
		else {
			System.out.println("not mobile version");
			return "desktop-version/mypage";
		}
	}
}