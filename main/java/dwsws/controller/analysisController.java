package dwsws.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import dwsws.outputdata.numberOfSkillListDO;
import dwsws.service.analysisService;
import dwsws.service.diagnoseService;

@Controller
public class analysisController {
	@Autowired
	analysisService analysis;
	@Autowired
	diagnoseService diagnose;

	/**
	 * On analysisController Returns Json formatted List object that contains # of
	 * skill about crew duty
	 * 
	 * @param nothing
	 * @throws Exception Exception handler for 'analysisRepository' SQLException
	 */
	@GetMapping(path = "/getCrewSkillNumber", produces = "application/json;charset=utf8")
	@ResponseBody
	public String getCrewSkillNumber() throws Exception {
		numberOfSkillListDO crewSkillList = new numberOfSkillListDO();
		try {
			crewSkillList = analysis.getCrewSkillList();
		} catch (SQLException e) {
			System.out.println("SQL Exception occured on analysisController.java");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Non-SQLException occured on analysisController.java");
			e.printStackTrace();
		}
		// refine returned result into json format
		ObjectMapper mapper = new ObjectMapper();
		String json_crewSkillList = mapper.writeValueAsString(crewSkillList);
		return json_crewSkillList;
	}

	/**
	 * On analysisController Returns Json formatted List object that contains # of
	 * skill about non_crew duty
	 * 
	 * @param nothing
	 * @throws Exception Exception handler for 'analysisRepository' SQLException
	 */
	@GetMapping(path = "/getNotCrewSkillNumber", produces = "application/json;charset=utf8")
	@ResponseBody
	public String getNotCrewSkillNumber() throws Exception {
		numberOfSkillListDO notcrewSkillList = new numberOfSkillListDO();
		try {
			notcrewSkillList = analysis.getNotCrewSkillList();
		} catch (SQLException e) {
			System.out.println("SQL Exception occured on analysisController.java");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Non-SQLException occured on analysisController.java");
			e.printStackTrace();
		}
		// refine returned result into json format
		ObjectMapper mapper = new ObjectMapper();
		String json_notcrewSkillList = mapper.writeValueAsString(notcrewSkillList);
		return json_notcrewSkillList;
	}

	/**
	 * On analysisController Returns user's score by tools
	 * 
	 * @throws Exception error handling for every occured exception in this method
	 */
	@GetMapping(path = "/getcrewscoreByTools", produces = "application/json;charset=utf8")
	@ResponseBody
	public String getCrewscoreByTools(HttpSession session,
			@RequestParam(name = "crewflag", required = true) String crewflag) throws Exception {
		String userindex = session.getAttribute("userindex").toString();
		HashMap scoreResult = new HashMap();
		try {
			if (crewflag.equals("notcrew") == true)
				scoreResult = analysis.callNoncrewScoreFromToolRecentTable();
			else if (crewflag.equals("crew") == true)
				scoreResult = analysis.callCrewScoreFromToolRecentTable();
		} catch (SQLException e) {
			System.out.println("SQL Exception occured on analysisController.java");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Non-SQLException occured on analysisController.java");
			e.printStackTrace();
		}
		// refine returned result into json format
		ObjectMapper mapper = new ObjectMapper();
		String json_scoreResult = mapper.writeValueAsString(scoreResult);
		return json_scoreResult;
	}

	/**
	 * On analysisController Returns average score by tools
	 * 
	 * @throws Exception error handling for every occured exception in this method
	 */
	@GetMapping(path = "/getAveragescoreByTools", produces = "application/json;charset=utf8")
	@ResponseBody
	public String getAveragescoreByTools() throws Exception {
		HashMap averageScore = new HashMap();
		try {
			averageScore = analysis.callAverageScoreFromToolRecentTable();
		} catch (SQLException e) {
			System.out.println("SQL Exception occured on analysisController.java");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Non-SQLException occured on analysisController.java");
			e.printStackTrace();
		}
		// refine returned result into json format
		ObjectMapper mapper = new ObjectMapper();
		String json_averageScore = mapper.writeValueAsString(averageScore);
		return json_averageScore;
	}

	/**
	 * On analysisController Returns company name list from user_info using 'group
	 * by' clause
	 * 
	 * @throws Exception error handling for every occured exception in this method
	 */
	@GetMapping(path = "/getCompanynameList", produces = "application/json;charset=utf8")
	@ResponseBody
	public String getCompanynameListFromUserinfo() throws Exception {
		List<String> companylist = new Vector<String>();
		try {
			companylist = analysis.callCompanynameListFromUserinfo();
		} catch (SQLException e) {
			System.out.println("SQL Exception occured on analysisController.java");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Non-SQLException occured on analysisController.java");
			e.printStackTrace();
		}
		// refine returned result into json format
		ObjectMapper mapper = new ObjectMapper();
		String json_companylist = mapper.writeValueAsString(companylist);
		return json_companylist;
	}

	/**
	 * On analysisController returns org name about send company name.
	 * 
	 * @param companyname company's name that want to get the org name about.
	 * @throws Exception error handling for every occured exception in this method
	 */
	@GetMapping(path = "/getOrgnameList", produces = "application/json;charset=utf8")
	@ResponseBody
	public String getOrgnameList(@RequestParam(name = "companyname", required = true) String companyname)
			throws Exception {
		List<String> orgnamelist = new Vector<String>();
		try {
			orgnamelist = analysis.callOrgnameListFromUserinfo(companyname);
		} catch (SQLException e) {
			System.out.println("SQL Exception occured on analysisController.java");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Non-SQLException occured on analysisController.java");
			e.printStackTrace();
		}
		// refine returned result into json format
		ObjectMapper mapper = new ObjectMapper();
		String json_orgnamelist = mapper.writeValueAsString(orgnamelist);
		return json_orgnamelist;
	}

	/**
	 * On analysisController returns org's ranking and skill grade
	 * 
	 * @param orgname     org's name want to get
	 * @param companyname company's name want to get
	 * @throws Exception error handling for every occured exception in this method
	 */
	@GetMapping(path = "/getRanking", produces = "application/json;charset=utf8")
	@ResponseBody
	public String getRankingAndSkill(@RequestParam(name = "companyname", required = true) String companyname,
			@RequestParam(name = "orgname", required = false) String orgname) throws Exception {
		HashMap resultMap = new HashMap();
		try {
			resultMap.put("orgRanking", analysis.callOrgRankingByOrgname(orgname, companyname));
			resultMap.put("orgSkillGrade", analysis.callOrgSkillGrade((int)resultMap.get("orgRanking")));
			resultMap.put("orgNumbers", analysis.callNumberOfOrgs(orgname, companyname));
		} catch (SQLException e) {
			System.out.println("SQL Exception occured on analysisController.java");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Non-SQLException occured on analysisController.java");
			e.printStackTrace();
		}
		// refine returned result into json format
		ObjectMapper mapper = new ObjectMapper();
		String json_resultMap = mapper.writeValueAsString(resultMap);
		return json_resultMap;
	}
	
	/**
	 * On analysisController returns organization's total people and attended people number
	 * @param orgname org's name want to get
	 * @param companyname company's name that org is in.
	 * @throws Exception error handling for every occured exception in this method
	 */
	@GetMapping(path = "/getPeopleData", produces = "application/json;charset=utf8")
	@ResponseBody
	public String getPeopleData(@RequestParam(name = "companyname", required = true) String companyname,
			@RequestParam(name = "orgname", required = false) String orgname) throws Exception {
		HashMap resultMap = new HashMap();
		try {
			resultMap.put("allpeople", analysis.callNumberOfPeopleInOrg(orgname, companyname));
			resultMap.put("attendedpeople", analysis.callNumberOfAttendencePeopleInOrg(orgname, companyname));
		} catch (SQLException e) {
			System.out.println("SQL Exception occured on analysisController.java");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Non-SQLException occured on analysisController.java");
			e.printStackTrace();
		}
		// refine returned result into json format
		ObjectMapper mapper = new ObjectMapper();
		String json_resultMap = mapper.writeValueAsString(resultMap);
		return json_resultMap;
	}
	
	/**
	 * On analysisController returns org's required data to show pie chart
	 * @param companyname
	 * @param orgname
	 */
	@GetMapping(path = "/getDataForPieChart", produces = "application/json;charset=utf8")
	@ResponseBody
	public String getOrgDataForSkillGradeNumber(@RequestParam(name = "companyname", required = true) String companyname,
			@RequestParam(name = "orgname", required = false) String orgname) throws Exception {
		numberOfSkillListDO orgPeopleSkillList = new numberOfSkillListDO();
		try {
			orgPeopleSkillList = analysis.callOrgSkillList(companyname, orgname);
		} catch (SQLException e) {
			System.out.println("SQL Exception occured on analysisController.java");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Non-SQLException occured on analysisController.java");
			e.printStackTrace();
		}
		// refine returned result into json format
		ObjectMapper mapper = new ObjectMapper();
		String json_orgPeopleSkillList = mapper.writeValueAsString(orgPeopleSkillList);
		return json_orgPeopleSkillList;
	}
	
	/**
	 * On analysisController returns org's required data to show bullet chart
	 * @param orgname org's name want to get
	 * @param companyname company's name want to get
	 * @throws Exception error handling for every occured exception in this method
	 */
	@GetMapping(path = "/getDataForBulletChart", produces = "application/json;charset=utf8")
	@ResponseBody
	public String getOrgDataForComparementOfTools(@RequestParam(name = "companyname", required = true) String companyname,
			@RequestParam(name = "orgname", required = false) String orgname) throws Exception {
		HashMap resultMap = new HashMap();
		try {
			List<String> offeringTools = diagnose.testOfferingToolsList();
			for(int i = 0; i < offeringTools.size(); i++) {
				resultMap.put(offeringTools.get(i), analysis.callOrgComparementDataAboutToolsForBulletChart(offeringTools.get(i), companyname, orgname));
			}
		} catch (SQLException e) {
			System.out.println("SQL Exception occured on analysisController.java");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Non-SQLException occured on analysisController.java");
			e.printStackTrace();
		}
		// refine returned result into json format
		ObjectMapper mapper = new ObjectMapper();
		String json_resultMap = mapper.writeValueAsString(resultMap);
		return json_resultMap;
	}
	
	/**
	 * On analysisController returns analysis page for admin authorized user
	 * This function is called through 'header.js' - 'userAuthCheck()' after get auth data from backend
	 */
	@RequestMapping(path = "/analysis")
	public String linkToAnalysisPage(Device device) {
		if(device.isMobile()) {
			return "mobile-version/admin/Admin-sub/analysis-chart";
		} else {
			return "desktop-version/admin/Admin-sub/analysis-chart";
		}
	}
}
