package dwsws.controller;

import java.util.List;
import java.util.Vector;

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

import dwsws.outputdata.questionManagementListDO;
import dwsws.service.surveyManagementService;


/**
 * admin - 진단문항 관리 페이지
 * 
 * - createNewSurvey : 새로운 진단 제공 도구를 생성 
 * - deleteSurvey : 기존 있던 진단 제공 도구를 삭제함 
 * - callQuestionMngData : 해당 도구에 등록되어있는 진단 문항들을 return 
 * - callSelectionDataByToolname : 진단과 연결시킬 섹션 및 북마크 정보를 return
 * - callToolidxByToolnameFromSurveyTable : 진단 제공 테이블로부터 도구이름과 매칭되는 인덱스 값을 return 
 * - applyModification : 진단문항 수정사항을 적용함 
 * - linkToSurveyModification : 진단문항 수정 페이지로 redirect 
 * 
 * @Autowired
	surveyManagementService service;
	
 * @author 박승수 
 *
 */
@Controller
public class surveyManagementController {
	@Autowired
	surveyManagementService service;

	/**
	 * 새로운 진단 제공 도구를 생성
	 * 
	 * @PostMapping(path = "/createNewSurvey")
	 * @ResponseBody
	 *
	 * @param toolname 신규 생성되는 진단문항의 도구 이름 
	 * @param session 사용자 정보가 담겨있는 세션 데이터 
	 * @return boolean 진단문항 생성 성공 혹은 실패 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@PostMapping(path = "/createNewSurvey")
	@ResponseBody
	public boolean createNewSurvey(@RequestParam(name = "toolname", required = true) String toolname,
			HttpSession session) throws Exception {
		String userindex = session.getAttribute("userindex").toString();
		boolean returnCheck = service.createNewTable(toolname, userindex);
		if (returnCheck == true) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 기존 있던 진단 제공 도구를 삭제함 
	 * 
	 * @PostMapping(path = "/deleteSurvey")
	 * @ResponseBody
	 *
	 * @param toolname 삭제하고자 하는 진단제공 도구 이름 
	 * @return boolean 진단제공 도구 삭제 성공 혹은 실패 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@PostMapping(path = "/deleteSurvey")
	@ResponseBody
	public boolean deleteSurvey(@RequestParam(name = "toolname") String toolname) throws Exception {
		boolean returnCheck = service.deleteTestOfferToolTableElement(toolname);
		if (returnCheck == true) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 해당 도구에 등록되어있는 진단 문항들을 return 
	 * 
	 * @GetMapping(path = "/callQuestionListForManagement")
	 * @ResponseBody
	
	 * @param toolname 진단 문항들을 받고자 하는 진단 제공 도구 이름 
	 * @return List<questionManagementListDO> 하나의 진단문항에 대한 정보를 담고 있는 questionManagementListDO의 List
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@GetMapping(path = "/callQuestionListForManagement")
	@ResponseBody
	public List<questionManagementListDO> callQuestionMngData(
			@RequestParam(name = "toolname", required = true) String toolname) throws Exception {
		List<questionManagementListDO> questionMngList = new Vector();
		questionMngList = service.callQuestionListForMng(toolname);

		return questionMngList;
	}

	/**
	 * 진단과 연결시킬 섹션 및 북마크 정보를 return
	 * 
	 * @GetMapping(path = "/callSelectionBoxDataByToolname")
	 * @ResponseBody
	
	 * @param session 사용자 정보를 담고 있는 세션 데이터 
	 * @param toolname 섹션 및 북마크 정보를 가져오고 싶은 도구 이름 
	 * @return List<questionManagementListDO> 섹션 및 북마크 정보를 포함한 questionManagementListDO 객체의 List 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@GetMapping(path = "/callSelectionBoxDataByToolname")
	@ResponseBody
	public List<questionManagementListDO> callSelectionDataByToolname(HttpSession session,
			@RequestParam(name = "toolname", required = true) String toolname) throws Exception {
		List<questionManagementListDO> dataByToolname = new Vector();
		dataByToolname = service.callSelectionDataForMng(toolname);
		return dataByToolname;
	}

	/**
	 * 진단 제공 테이블로부터 도구이름과 매칭되는 인덱스 값을 return 
	 * 
	 * @GetMapping(path = "/callToolidxFromSurveytable")
	 * @ResponseBody
	
	 * @param toolname 인덱스 값을 알고자 하는 도구 이름 
	 * @return int 도구의 index값 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@GetMapping(path = "/callToolidxFromSurveytable")
	@ResponseBody
	public int callToolidxByToolnameFromSurveyTable(@RequestParam(name = "toolname", required = true) String toolname)
			throws Exception {
		return service.callToolidxByToolname(toolname);
	}

	/**
	 * 진단문항 수정사항을 적용함 
	 * 
	 * @PostMapping(path = "/applyModificationOnSurvey", produces = "application/json;charset=utf8")
	 * @ResponseBody
	 * 
	 * @param session 사용자 정보를 담은 섹션 데이터 
	 * @param submitData 변화된 진단문항만 포함하고 있는 수정사항 객체 
	 * @return boolean 수정사항 적용 성공 혹은 실패 
	 * 
	 * @author 박승수
	 */
	@PostMapping(path = "/applyModificationOnSurvey", produces = "application/json;charset=utf8")
	@ResponseBody
	public boolean applyModification(HttpSession session, @RequestBody questionMngModification submitData)
			throws Exception {
		String userindex = session.getAttribute("userindex").toString();
		boolean result = service.applyModificationOnSurvey(submitData.inputList, submitData.toolname, userindex);
		return result;
	}

	/**
	 * 진단문항 수정 페이지로 redirect 
	 * 
	 * @RequestMapping(path = "/survey-modify")
	 * 
	 * @param device 사용자의 접근 기기가 PC인지 MOBILE인지 판별하는 library의 객체 
	 * @return 진단문항 수정 페이지를 보여주는 .jsp의 파일 경로 
	 */
	@RequestMapping(path = "/survey-modify")
	public String linkToSurveyModification(Device device) {
		if (device.isMobile()) {
			return "desktop-version/login/error";
		} else {
			return "desktop-version/admin/survey-modify";
		}
	}

	static class questionMngModification {
		public String toolname;
		public List<questionManagementListDO> inputList;
	}
}
