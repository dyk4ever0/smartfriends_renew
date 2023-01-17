package dwsws.controller;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import dwsws.dto.questionListDTO;
import dwsws.outputdata.compareDataForCalcScoreDO;
import dwsws.outputdata.suggestionListFormDO;
import dwsws.outputdata.testResultWithToolnameDO;
import dwsws.service.diagnoseService;

/**
 * - getQuestionListByToolname : 진단을 제공하는 도구 이름으로부터 진단 문항을 return
 * - saveTestResultByToolname : 사용자가 완료한 진단 내용을 저장 
 * - getRecentTestResultByToolname : 진단을 제공하는 도구에 대해 가장 최근의 데이터를 return
 * - getAllRecentTestResultByToolname : 진단을 제공하는 모든 도구에 대해 가장 최근의 데이터를 return
 * - getSuggestionList : 진단을 제공하는 모든 도구에 대해 오답노트 출력에 필요한 데이터를 return
 * - getCorrectNumByIndexAndToolname : 바로 직전 시도한 진단 결과에 대해, 몇개의 문항을 맞췄는지 return
 * - compareTestDataList : 제출된 사용자의 답안과 비교할 정답지를 return
 * - callTotalscoreFromRankingTable : 사용자가 현재까지 진단한 모든 진단 점수의 합을 return
 * - callTestOfferingTools : 진단을 제공하는 도구들의 list를 return
 * - linkToQuestionList : 진단 페이지로 redirect
 * - linkToDiagnoseMain : 진단 Dashboard로 redirect
 * - linkToDiagnoseResult : 진단 결과 페이지로 redirect
 * 
 * @Autowired
	diagnoseService diagnose;
	
 * @author 박승수
 *
 */
@Controller
public class diagnoseController {
	@Autowired
	diagnoseService diagnose;

	/**
	 * 진단을 제공하는 도구 이름으로부터 진단 문항을 return
	 * 
	 * @GetMapping(path = "/callQuestionList", produces = "application/json;charset=utf8")
	 * @ResponseBody
	
	 * @param toolname 조회하고자 하는 도구의 이름 
	 * @param session 사용자 정보를 받을 수 있는 세션 데이터 
	 * @return jsonTypeString 진단 문항을 포함하고 있는 questionListDTO의 list가 json으로 변환됨 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@GetMapping(path = "/callQuestionList", produces = "application/json;charset=utf8")
	@ResponseBody
	public String getQuestionListByToolname(@RequestParam(name = "toolname", required = true) String toolname,
			HttpSession session) throws Exception {
		List<questionListDTO> returnedList = diagnose.QuestionListByToolname(toolname);

		// refine returned data from List to json
		ObjectMapper mapper = new ObjectMapper();
		String json_returnedList = mapper.writeValueAsString(returnedList);

		// returns json format variable
		return json_returnedList;
	}

	/**
	 * 사용자가 완료한 진단 내용을 저장 
	 * 
	 * @PostMapping(path = "/saveTestResult")
	 * @ResponseBody
	
	 * @param userBody 진단 결과 저장에 필요한 내용을 담고 있는 static class 객체 ( controller 파일 하단에 임시 객체로 선언됨 )
	 * @param request
	 * @param session 사용자 정보를 담고 있는 세션 데이터 
	 * @return String success 혹은 nothing
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@PostMapping(path = "/saveTestResult")
	@ResponseBody
	public String saveTestResultByToolname(@RequestBody UserResultBody userBody, HttpServletRequest request,
			HttpSession session) throws Exception {
		String userindex = session.getAttribute("userindex").toString();
		String username = session.getAttribute("username").toString();
		String toolname = userBody.toolname;
		int trialnum = userBody.trialnum + 1;
		int mycurrentscore = userBody.mycurrentscore;
		int mynewscore = userBody.mynewscore;
		List<Byte> ansResult = userBody.ansResult;
		String rankingActivationFlag = userBody.rankingActivationFlag;
		int mycurrentTotalscore = userBody.mycurrentTotalscore;
		
		System.out.println("user body contetns : ");
		System.out.println();

		// inserting service start
		diagnose.saveTestResult(userindex, username, toolname, trialnum, mycurrentscore, mynewscore, ansResult, rankingActivationFlag, mycurrentTotalscore);

		return "success";
	}
	
	/**
	 * 진단을 제공하는 도구에 대해 가장 최근의 데이터를 return
	 * 
	 * @GetMapping(path = "/recentTestResult", produces = "application/json;charset=utf8")
	 * @ResponseBody
	
	 * @param session 사용자 정보를 담고 있는 세션 데이터 
	 * @param toolname 최근 데이터를 얻고자 하는 도구 이름 
	 * @return jsonTypeString 해당 도구에 대한 최근 데이터를 담고 있는 testResultWithToolnameDO list가 json으로 변환됨. 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@GetMapping(path = "/recentTestResult", produces = "application/json;charset=utf8")
	@ResponseBody
	public String getRecentTestResultByToolname(HttpSession session,
			@RequestParam(name = "toolname", required = true) String toolname) throws Exception {
		String userindex = session.getAttribute("userindex").toString();
		testResultWithToolnameDO returnedRecentTest = diagnose.recentTestResultByToolname(toolname, userindex);

		ObjectMapper mapper = new ObjectMapper();
		String json_returnedRecentTest = mapper.writeValueAsString(returnedRecentTest);

		return json_returnedRecentTest;
	}

	/**
	 * 진단을 제공하는 모든 도구에 대해 가장 최근의 데이터를 return
	 * 
	 * @GetMapping(path = "/getTestMainPageData", produces = "application/json;charset=utf8")
	 * @ResponseBody
	 * 
	 * @param session 사용자 정보를 담고 있는 세션 데이
	 * @return jsonTypeString 진단 결과를 담고 있는 testResultWithToolnameDO list가 json으로 변환됨. 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@GetMapping(path = "/getTestMainPageData", produces = "application/json;charset=utf8")
	@ResponseBody
	public String getAllRecentTestResultByToolname(HttpSession session) throws Exception {
		String userindex = session.getAttribute("userindex").toString();
		List<String> offeringTools = diagnose.testOfferingToolsList();

		List<testResultWithToolnameDO> allRecentResult = new Vector<testResultWithToolnameDO>();
		for (int i = 0; i < offeringTools.size(); i++) {
			allRecentResult.add(diagnose.recentTestResultByToolname(offeringTools.get(i), userindex));
			System.out.println("2. allRecentResult : " + allRecentResult.get(i));
		}

		ObjectMapper mapper = new ObjectMapper();
		String json_allRecentResult = mapper.writeValueAsString(allRecentResult);

		return json_allRecentResult;
	}

	/**
	 * 진단을 제공하는 모든 도구에 대해 오답노트 출력에 필요한 데이터를 return
	 * 
	 * @GetMapping(path = "/getSuggestionList", produces = "application/json;charset=utf8")
	 * @ResponseBody
	
	 * @param session 사용자 정보를 담고 있는 세션 데이터 
	 * @return jsonTypeString 오답노트 정보를 담고 있는 suggestionListFormDO의 list가 json으로 변환됨. 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@GetMapping(path = "/getSuggestionList", produces = "application/json;charset=utf8")
	@ResponseBody
	public String getSuggestionList(HttpSession session) throws Exception {
		String userindex = session.getAttribute("userindex").toString();
		List<suggestionListFormDO> suggestionList = diagnose.suggestionList(userindex, 0);
		
		ObjectMapper mapper = new ObjectMapper();
		String json_suggestionList = mapper.writeValueAsString(suggestionList);

		return json_suggestionList;
	}
	
	/**
	 * 바로 직전 시도한 진단 결과에 대해, 몇개의 문항을 맞췄는지 return
	 * 
	 * @GetMapping(path = "/getCorrectNum")
	 * @ResponseBody
	
	 * @param session 사용자 정보를 담고 있는 세션 데이터 
	 * @param toolname 정답 문항을 return받고자 하는 도구의 이름 
	 * @return int 해당 도구의 진단에 대한 맞춘 문항 개수 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@GetMapping(path = "/getCorrectNum")
	@ResponseBody
	public int getCorrectNumByIndexAndToolname(HttpSession session,
			@RequestParam(name = "toolname", required = true) String toolname) throws Exception {
		int numOfCorrect = diagnose.numberOfCorrectByTool((String)session.getAttribute("userindex"), toolname);
		return numOfCorrect;
	}
	
	/**
	 * 제출된 사용자의 답안과 비교할 정답지를 return
	 * 
	 * @GetMapping(path = "/compareTestDataList", produces = "application/json;charset=utf8")
	 * @ResponseBody
	
	 * @param toolname 정답지를 받고자 하는 도구의 이름 
	 * @return jsonTypeString 정답지 내용을 담고 있는 compareDataForCalcScoreDO list가 json으로 변환됨. 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@GetMapping(path = "/compareTestDataList", produces = "application/json;charset=utf8")
	@ResponseBody
	public String compareTestDataList(@RequestParam(name="toolname", required = true) String toolname) throws Exception {
		List<compareDataForCalcScoreDO> compareData = diagnose.getCompareTestDataList(toolname);
		
		ObjectMapper mapper = new ObjectMapper();
		String json_compareData = mapper.writeValueAsString(compareData);
		
		return json_compareData;
	}
	
	/**
	 * 사용자가 현재까지 진단한 모든 진단 점수의 합을 return
	 * 
	 * @GetMapping(path = "/getTotalscore")
	 * @ResponseBody
	
	 * @param session 사용자 정보를 담고 있는 세션 데이터 
	 * @return jsonTypeString 모든 진단 결과의 점수 합을 가진 int형 데이터가 json으로 변환됨. 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@GetMapping(path = "/getTotalscore")
	@ResponseBody
	public String callTotalscoreFromRankingTable(HttpSession session) throws Exception {
		String userindex = session.getAttribute("userindex").toString();
		int mycurrentTotalscore = 0;
		try {
			mycurrentTotalscore = diagnose.callTotalscoreByUserindex(userindex);
		} catch(Exception e) {
			e.printStackTrace();
			mycurrentTotalscore = (-1);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String json_mycurrentTotalscore = mapper.writeValueAsString(mycurrentTotalscore);
		return json_mycurrentTotalscore;
	}
	
	/**
	 * 진단을 제공하는 도구들의 list를 return
	 * 
	 * @GetMapping(path = "/testOfferingTools")
	 * @ResponseBody
	
	 * @param session 사용자 정보를 담고 있는 세션 데이터 
	 * @return jsonTypeString 진단을 제공하는 도구들의 정보가 담긴 String list가 json으로 변환됨.
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@GetMapping(path = "/testOfferingTools")
	@ResponseBody
	public String callTestOfferingTools(HttpSession session) throws Exception {
		List<String> testTools = diagnose.testOfferingToolsList();
		
		ObjectMapper mapper = new ObjectMapper();
		String json_testTools = mapper.writeValueAsString(testTools);
		return json_testTools;
	}

	/**
	 * 진단 페이지로 redirect
	 * 
	 * @RequestMapping(path = "/diagnose-test")
	 * 
	 * @return String 유저 권한 관리 페이지에서 보여줄 .jsp 파일 위치
	 * 
	 * @author 박승수 
	 */
	@RequestMapping(path = "/diagnose-test")
	public String linkToQuestionList(Device device) {
		if(device.isMobile()) {
			return "mobile-version/diagnose/survey";
		}
		else {
			return "desktop-version/diagnose/survey";
		}
	}

	/**
	 * 진단 Dashboard로 redirect
	 * 
	 * @RequestMapping(path = "/diagnose-main")
	 * 
	 * @return String 유저 권한 관리 페이지에서 보여줄 .jsp 파일 위치
	 * 
	 * @author 박승수 
	 */
	@RequestMapping(path = "/diagnose-main")
	public String linkToDiagnoseMain(Device device) {
		if(device.isMobile()) {
			return "mobile-version/diagnose/dashboard";
		}
		else {
			return "desktop-version/diagnose/dashboard";
		}
	}
	
	/**
	 * 진단 결과 페이지로 redirect
	 * 
	 * @RequestMapping(path = "/diagnose-result")
	 * 
	 * @return String 유저 권한 관리 페이지에서 보여줄 .jsp 파일 위치
	 * 
	 * @author 박승수 
	 */
	@RequestMapping(path = "/diagnose-result")
	public String linkToDiagnoseResult(Device device) {
		if(device.isMobile()) {
			return "mobile-version/diagnose/result";
		}
		else {
			return "desktop-version/diagnose/result";
		}
	}

	static class UserResultBody {
		public String toolname;
		public int trialnum;
		public int mynewscore;
		public int mycurrentscore;
		public List<Byte> ansResult;
		public String rankingActivationFlag;
		public int mycurrentTotalscore;
	}
}
