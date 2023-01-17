package dwsws.service;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwsws.outputdata.bulletChartFormatDO;
import dwsws.outputdata.numberOfSkillListDO;
import dwsws.repository.analysisRepository;
import dwsws.repository.diagnoseRepository;

/**
 * admin - 통계 데이터 페이지
 * 
 * - getCrewSkillList : 최근 진단 데이터를 기준으로, 총 5단게로 분류된 레벨별 포함된 팀원의 숫자를 반환함.
 * - getNotCrewSkillList : 최근 진단 데이터를 기준으로, 총 5단게로 분류된 레벨별 포함된 직책자의 숫자를 반환함.
 * - callCrewScoreFromToolRecentTable : 팀원의 진단제공 도구별 점수를 return.
 * - callNoncrewScoreFromToolRecentTable : 직책자의 진단제공 도구별 점수를 return.
 * - callAverageScoreFromToolRecentTable : 직책 구분 없이, 진단제공 도구별 점수를 return.
 * - callCompanynameListFromUserinfo : 계열사 이름 목록을 return.
 * - callOrgnameListFromUserinfo : 조직(팀) 이름 목록을 return.
 * - callOrgRankingByOrgname : user_ranking table로부터 조직(팀)의 등수를 계산하여 return.
 * - callOrgSkillGrade : 조직(팀)의 등수를 통해 5단계로 분류된 레벨을 return.
 * - callNumberOfPeopleInOrg : 조직(팀)의 인원 수를 return
 * - callNumberOfAttendencePeopleInOrg : 조직(팀)의 인원 중, 진단에 참여한 인원의 수를 return.
 * - callNumberOfOrgs : 전체 조직(팀)의 개수를 return.
 * - callOrgSkillList : 총 5단계로 분류된 레벨별, 해당 레벨에 속한 조직원의 수를 반환함.
 * - callOrgComparementDataAboutToolsForBulletChart : 통계 페이지에서, bullet chart를 그리는데 필요한 데이터를 반환함.
 * 
 * @Autowired
	analysisRepository analysis;
	@Autowired
	diagnoseRepository diagnoserepository;
	
 * @author 박승수 
 *
 */
@Service
public class analysisService {
	@Autowired
	analysisRepository analysis;
	@Autowired
	diagnoseRepository diagnoserepository;
	
	/**
	 * 최근 진단 데이터를 기준으로, 총 5단게로 분류된 레벨별 포함된 팀원의 숫자를 반환함.
	 * 
	 * @Transactional
	 * 
	 * @return numberOfSkillListDO 5단계로 구분된 레벨별로 포함된 팀원의 수를 담은 list 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public numberOfSkillListDO getCrewSkillList() throws Exception {
		numberOfSkillListDO result = new numberOfSkillListDO();
		try {
			result = analysis.callCrewSkill();
		} catch(SQLException e) {
			System.out.println("SQL Exception occured while calling 'callCrewSkill' in analysisRepository.java on analysisService.java");
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("Non-SQLException occured in getCrewSkillList");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 최근 진단 데이터를 기준으로, 총 5단게로 분류된 레벨별 포함된 직책자의 숫자를 반환함.
	 * 
	 * @Transactional
	 * 
	 * @return numberOfSkillListDO 5단계로 구분된 레벨별로 포함된 직책자의 수를 담은 list 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public numberOfSkillListDO getNotCrewSkillList() throws Exception {
		numberOfSkillListDO result = new numberOfSkillListDO();
		try {
			result = analysis.callNotCrewSkill();
		} catch(SQLException e) {
			System.out.println("SQL Exception occured while calling 'callNotCrewSkill' in analysisRepository.java on analysisService.java");
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("Non-SQLException occured in getNotCrewSkillList");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 팀원의 진단제공 도구별 점수를 return.
	 * 
	 * @Transactional
	 * 
	 * @return HashMap 팀원들의 도구별 점수를 가지고 있는 Map 객체 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public HashMap callCrewScoreFromToolRecentTable() throws Exception {
		List<String> testOfferingTools = new Vector<String>();
		HashMap crewscore = new HashMap();
		try {
			testOfferingTools = diagnoserepository.testOfferingToolsList();
			for(int i = 0; i < testOfferingTools.size(); i++) {
				crewscore.put(testOfferingTools.get(i), analysis.callCrewScoreByToolname(testOfferingTools.get(i)));
			}
		} catch(SQLException e) {
			System.out.println("SQL Exception occured while calling 'callUserScoreByToolname' in analysisRepository.java on analysisService.java");
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("Non-SQLException occured in callUserScoreFromToolRecentTable");
			e.printStackTrace();
		}
		return crewscore;
	}
	
	/**
	 * 직책자의 진단제공 도구별 점수를 return.
	 * 
	 * @Transactional
	 * 
	 * @return HashMap 직책자들의 도구별 점수를 가지고 있는 Map 객체 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public HashMap callNoncrewScoreFromToolRecentTable() throws Exception {
		List<String> testOfferingTools = new Vector<String>();
		HashMap noncrewscore = new HashMap();
		try {
			testOfferingTools = diagnoserepository.testOfferingToolsList();
			for(int i = 0; i < testOfferingTools.size(); i++) {
				noncrewscore.put(testOfferingTools.get(i), analysis.callNonCrewScoreByToolname(testOfferingTools.get(i)));
			}
		} catch(SQLException e) {
			System.out.println("SQL Exception occured while calling 'callUserScoreByToolname' in analysisRepository.java on analysisService.java");
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("Non-SQLException occured in callUserScoreFromToolRecentTable");
			e.printStackTrace();
		}
		return noncrewscore;
	}
	
	/**
	 * 직책 구분 없이, 진단제공 도구별 점수를 return.
	 * 
	 * @Transactional
	 * 
	 * @return HashMap 모든 인원들의 도구별 점수를 가지고 있는 Map 객체 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public HashMap callAverageScoreFromToolRecentTable() throws Exception{
		List<String> testOfferingTools = new Vector<String>();
		HashMap average = new HashMap();
		try {
			testOfferingTools = diagnoserepository.testOfferingToolsList();
			//put tool's average score by iteration about test_offering_tools_list
			for(int i = 0; i < testOfferingTools.size(); i++) {
				average.put(testOfferingTools.get(i), analysis.callAveragescoreByToolname(testOfferingTools.get(i)));
			}
		} catch(SQLException e) {
			System.out.println("SQL Exception occured while calling 'callAveragescoreByToolname' in analysisRepository.java on analysisService.java");
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("Non-SQLException occured in callAverageScoreFromToolRecentTable");
			e.printStackTrace();
		}
		return average;
	}
	
	/**
	 * 계열사 이름 목록을 return.
	 * 
	 * @Transactional
	 * 
	 * @return List<String> 계열사 이름을 가지고 있는 String의 List 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public List<String> callCompanynameListFromUserinfo() throws Exception{
		List<String> companylist = new Vector<String>();
		try {
			companylist = analysis.callCompanynameList();
		} catch(SQLException e) {
			System.out.println("SQL Exception occured while calling 'callCompanynameList' in analysisRepository.java on analysisService.java");
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("Non-SQLException occured in callCompanynameListFromUserinfo");
			e.printStackTrace();
		}
		return companylist;
	}
	
	/**
	 * 조직(팀) 이름 목록을 return.
	 * 
	 * @Transactional
	 * 
	 * @param companyname 검색할 조직(팀)이 소속되어 있는 계열사의 이름 
	 * @return List<String> 해당 계열사에 소속된 조직(팀) 이름의 String list 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public List<String> callOrgnameListFromUserinfo(String companyname) throws SQLException {
		List<String> orgList = new Vector<String>();
		try {
			orgList = analysis.callOrgnameList(companyname);
		} catch(SQLException e) {
			System.out.println("SQL Exception occured while calling 'callOrgnameList' in analysisRepository.java on analysisService.java");
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("Non-SQLException occured in callOrgnameListFromUserinfo");
			e.printStackTrace();
		}
		return orgList;
	}
	
	/**
	 * user_ranking table로부터 조직(팀)의 등수를 계산하여 return.
	 * 
	 * @Transactional
	 * 
	 * @param orgname 등수를 조회하고자 하는 조직(팀)의 이름 
	 * @param companyname 등수를 조회하고자 하는 조직(팀)이 소속된 계열사의 이름 
	 * @return int 해당 조직(팀)의 등수 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public int callOrgRankingByOrgname(String orgname, String companyname) throws Exception {
		int orgranking = -1;
		try {
			orgranking = analysis.callOrgRanking(orgname, companyname);
		} catch(SQLException e) {
			System.out.println("SQL Exception occured while calling 'callOrgRanking' in analysisRepository.java on analysisService.java");
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("Non-SQLException occured in callOrgRankingByOrgname");
			e.printStackTrace();
		}
		return orgranking;
	}
	
	/**
	 * 조직(팀)의 등수를 통해 5단계로 분류된 레벨을 return.
	 * 
	 * @Transactional
	 * 
	 * @param orgranking 레벨을 산출할 때 이용되는 조직(팀)의 등수 
	 * @return String 총 5단계 레벨로 구분된 항목 중 검색한 조직(팀)이 소속된 레벨 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public String callOrgSkillGrade(int orgranking) throws Exception {
		String orgRanking = null;
		String[] rank = { "매우 낮은", "낮은", "보통", "높은", "매우 높은" };
		try {
			int allOrgNumber = analysis.callAllNumberOfOrgs();
			int[] percentage = { allOrgNumber, (int) (allOrgNumber * 0.8), (int) (allOrgNumber * 0.6),
					(int) (allOrgNumber * 0.4), (int) (allOrgNumber * 0.2) };
			for(int i = 0; i < percentage.length; i++) {
				if(orgranking <= percentage[i])
					orgRanking = rank[i];
			}
		} catch(SQLException e) {
			System.out.println("SQL Exception occured while calling 'callAllNumberOfOrgs' in analysisRepository.java on analysisService.java");
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("Non-SQLException occured in callOrgSkillGrade");
			e.printStackTrace();
		}
		return orgRanking;
	}
	
	/**
	 * 조직(팀)의 인원 수를 return
	 * 
	 * @Transactional
	 * 
	 * @param orgname 인원 수를 검색하고자 하는 조직(팀)의 이름 
	 * @param companyname 검색하고자 하는 조직(팀)이 소속된 계열사의 이름 
	 * @return int 해당 조직(팀)에 소속된 사람들의 명 수 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public int callNumberOfPeopleInOrg(String orgname, String companyname) throws Exception {
		int numOfOrgPeople = -1;
		try {
			numOfOrgPeople = analysis.callNumberOfOrgPeople(orgname, companyname);
		} catch(SQLException e) {
			System.out.println("SQL Exception occured while calling 'callNumberOfOrgPeople' in analysisRepository.java on analysisService.java");
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("Non-SQLException occured in callNumberOfPeopleInOrg");
			e.printStackTrace();
		}
		return numOfOrgPeople;
	}
	
	/**
	 * 조직(팀)의 인원 중, 진단에 참여한 인원의 수를 return.
	 * 
	 * @Transactional
	 * 
	 * @param orgname 진단에 참여한 인원을 검색하고싶은 조직(팀)의 이름 
	 * @param companyname 검색하고자 하는 조직(팀)이 소속된 계열사의 이름 
	 * @return int 진단에 참여한 인원 수 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public int callNumberOfAttendencePeopleInOrg(String orgname, String companyname) throws Exception {
		int numOfAttendedOrgPeople = -1;
		try {
			numOfAttendedOrgPeople = analysis.callNumberOfOrgAttendencePeople(orgname, companyname);
		} catch(SQLException e) {
			System.out.println("SQL Exception occured while calling 'callNumberOfOrgAttendencePeople' in analysisRepository.java on analysisService.java");
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("Non-SQLException occured in callNumberOfAttendencePeopleInOrg");
			e.printStackTrace();
		}
		return numOfAttendedOrgPeople;
	}
	
	/**
	 * 전체 조직(팀)의 개수를 return.
	 * 
	 * @Transactional
	 * 
	 * @param orgname 조직(팀)의 개수를 return할지 결정하는 변수 
	 * @param companyname 계열사의 개수를 return할지 결정하는 변수 
	 * @return HashMap 조직(팀) 혹은 계열사의 개수를 포함하고 있는 int 변수 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public HashMap callNumberOfOrgs(String orgname, String companyname) throws Exception {
		HashMap resultMap = new HashMap();
		try {
			if(orgname != null)
				resultMap.put("allorgnumber", analysis.callAllNumberOfOrgs());
			else
				resultMap.put("allorgnumber", analysis.callAllNumberOfCompanies());
		} catch(SQLException e) {
			System.out.println("SQL Exception occured while calling 'callAllNumberOfOrgs' in analysisRepository.java on analysisService.java");
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("Non-SQLException occured in callComparementRankingForPage");
			e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	 * 총 5단계로 분류된 레벨별, 해당 레벨에 속한 조직원의 수를 반환함.
	 * 
	 * @Transactional
	 * 
	 * @param companyname 검색하고자 하는 조직(팀)이 포함된 계열사의 이름.
	 * @param orgname 레벨별 조직원의 수를 반환받고자 하는 조직(팀)의 이름.
	 * @return numberOfSkillListDO 5단계로 구분된 레벨별로 포함된 팀원의 수를 담은 list
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public numberOfSkillListDO callOrgSkillList(String companyname, String orgname) throws Exception {
		numberOfSkillListDO result = new numberOfSkillListDO();
		try {
			result = analysis.callNumberOfOrgSkill(companyname, orgname);
		} catch(SQLException e) {
			System.out.println("SQL Exception occured while calling 'callCrewSkill' in analysisRepository.java on analysisService.java");
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("Non-SQLException occured in getCrewSkillList");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 통계 페이지에서, bullet chart를 그리는데 필요한 데이터를 반환함.
	 * 
	 * @Transactional
	 * 
	 * @param toolname 어떤 도구에 대해서 data를 조회할 것인지에 대한 도구 이름.
	 * @param companyname 해당 조직이 소속된 계열사의 이름.
	 * @param orgname 어느 조직의 bullet chart를 그리기 위한 것인지, 조직(팀)의 이름.
	 * @return bulletChartFormatDO bullet chart를 그리기위한 정보가 포함된 데이터 객체.
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public bulletChartFormatDO callOrgComparementDataAboutToolsForBulletChart(String toolname, String companyname, String orgname) throws Exception {
		bulletChartFormatDO eachToolResult = new bulletChartFormatDO();
		try {
				System.out.println("toolname : " + toolname);
				eachToolResult.setToolname(toolname);
				eachToolResult.setOrgToolscore(analysis.callGroupScoreByCompAndOrgname(toolname, companyname, orgname));
				if(orgname != null) {
					eachToolResult.setTotalOrgNumOnTooltable(analysis.callTotalOrgNumberInTestResultTableByToolname(toolname));
					eachToolResult.setRankingRangeScore(new int[]{analysis.callOrgScoreByRanking(toolname, 1),
							analysis.callOrgScoreByRanking(toolname, (int)(eachToolResult.getTotalOrgNumOnTooltable() * 0.2)),
							analysis.callOrgScoreByRanking(toolname, (int)(eachToolResult.getTotalOrgNumOnTooltable() * 0.6))});
					eachToolResult.setMedianAndMaximum(new int[] {analysis.callOrgScoreByRanking(toolname, (int)(eachToolResult.getTotalOrgNumOnTooltable() * 0.5)),
							analysis.callOrgScoreByRanking(toolname, 1)});
					eachToolResult.setToolMinScore(analysis.callMinimumScoreByToolnameForOrg(toolname));
				}
				else {
					eachToolResult.setTotalOrgNumOnTooltable(analysis.callTotalCompNumberInTestResultTableByToolname(toolname));
					eachToolResult.setRankingRangeScore(new int[]{analysis.callCompanyScoreByRanking(toolname, 1),
							analysis.callCompanyScoreByRanking(toolname, (int)(eachToolResult.getTotalOrgNumOnTooltable() * 0.2)),
							analysis.callCompanyScoreByRanking(toolname, (int)(eachToolResult.getTotalOrgNumOnTooltable() * 0.6))});
					eachToolResult.setMedianAndMaximum(new int[] {analysis.callCompanyScoreByRanking(toolname, (int)(eachToolResult.getTotalOrgNumOnTooltable() * 0.5)),
							analysis.callCompanyScoreByRanking(toolname, 1)});
					eachToolResult.setToolMinScore(analysis.callMinimumScoreByToolnameForCompany(toolname));
				}
		} catch(SQLException e) {
			System.out.println("SQL Exception occured while calling 'bulletChartRelatedRepositories' in analysisRepository.java on analysisService.java");
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("Non-SQLException occured in callOrgComparementDataAboutToolsForBulletChart");
			e.printStackTrace();
		}
		return eachToolResult;
	}
}
