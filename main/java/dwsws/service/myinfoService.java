package dwsws.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwsws.outputdata.recentTestDataOnMyinfoDO;
import dwsws.outputdata.sectionIndexFromQuestionDO;
import dwsws.repository.diagnoseRepository;
import dwsws.repository.myinfoRepository;

/**
 * user - 내 정보 페이지
 * 
 * - getUniversalSkillGrade : 모든 최근 진단 결과에 대한 레벨 값을 return.
 * - getUniversalMypageInfoByUserindex : 내 정보 페이지에서, 사용자에 대한 종합 데이터를 보여줄 때 필요한 데이터를 return.
 * - getUniversalDataForLeftsideOfTooltab : 각 툴별 정보를 보여주는 탭의, 왼쪽 구역을 출력하는데 필요한 데이터를 return.
 * - getRecent3TestResult : 최근 3회 진단 결과에 대한 데이터를 return
 * - getAverageSectionScore : 도구의 섹션별 평균 점수를 return.
 * - getSectionalScore : 사용자에 대한 도구별 섹션 점수를 return.
 * - calculateSectionalScore : 제공된 정보를 바탕으로, 도구의 섹션별 점수를 연산함.
 * - calculateScore : 섹션별 점수를 10점 만점 기준 몇 점인지 환산함. 
 * 
 * @Autowired
	private myinfoRepository myinfo;
	@Autowired
	private diagnoseRepository diagnose;
	@Autowired
	private diagnoseService diagnoseservice;
	
 * @author 박승수 
 *
 */
@Service
public class myinfoService {
	@Autowired
	private myinfoRepository myinfo;
	@Autowired
	private diagnoseRepository diagnose;
	@Autowired
	private diagnoseService diagnoseservice;

	/**
	 * 모든 최근 진단 결과에 대한 레벨 값을 return.
	 * 
	 * @Transactional
	 * 
	 * @param userindex 어느 사용자에 대한 종합 데이터를 받을지에 대한 사용자 고유 값.
	 * @param totalscore 사용자의, 모든 도구에 대한 총 합 점수 
	 * @return String 산출된 사용자의 레벨 결과 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public String getUniversalSkillGrade(String userindex, int totalscore) throws Exception {
		String[] rank = { "매우 낮은", "낮은", "보통", "높은", "매우 높은" };

		// get number of current data in DB
		int numOfData = myinfo.getNumOfRankingTable();

		// calculate percentage of each rank
		// and returns user's rank
		int[] percentage = { numOfData, (int) (numOfData * 0.8), (int) (numOfData * 0.6), (int) (numOfData * 0.4),
				(int) (numOfData * 0.2) };
		int userRank = myinfo.getRankingByUserindex(userindex);
		String userDegree = null;
		for (int i = 0; i < percentage.length; i++) {
			if (userRank <= percentage[i]) {
				userDegree = rank[i];
			} else {
			}
		}
		return userDegree;
	}

	/**
	 * 내 정보 페이지에서, 사용자에 대한 종합 데이터를 보여줄 때 필요한 데이터를 return.
	 * 
	 * @Transactional
	 * 
	 * @param userindex 어떤 사용자의 종합 데이터를 보여줄 것인지에 대한, 사용자의 고유 값.
	 * @return HashMap 종합 데이터를 가지고 있는 객체 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public HashMap getUniversalMypageInfoByUserindex(String userindex) throws Exception {
		HashMap result = new HashMap();
		result.put("userskill", this.getUniversalSkillGrade(userindex, diagnose.getTotalscoreByUserindex(userindex)));
		result.put("userrank", myinfo.getRankingByUserindex(userindex));
		result.put("numOfAllPeople", myinfo.getNumOfUserInfoTable());
		result.put("participatedPeople", myinfo.getNumOfRankingTable());
		return result;
	}

	// returns trialnum, rank, # of participant for left upper side on 'tool' tab.
	/**
	 * 각 도구별 정보를 보여주는 탭의, 왼쪽 구역을 출력하는데 필요한 데이터를 return.
	 * 
	 * @param toolname 어떤 도구에 대한 정보를 보여줄지에 대한 도구 이름 
	 * @param userindex 어떤 사용자의 정보를 보여줄지에 대한 사용자 고유 값 
	 * @return HashMap 도구별 정보의 왼쪽 구역 출력에 필요한 데이터를 담고 있는 객체 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	public HashMap getUniversalDataForLeftsideOfTooltab(String toolname, String userindex) throws Exception {
		HashMap resultMap = new HashMap();
		resultMap.put("trialnum", myinfo.getRecentTrialnumByToolname(toolname, userindex));
		resultMap.put("ranking", myinfo.getRankingByToolnameFromRecentData(toolname, userindex));
		resultMap.put("NumOfAllPeople", myinfo.getNumOfUserInfoTable());
		resultMap.put("participant", myinfo.getNumOfRankingTable());

		return resultMap;
	}

	/**
	 * 최근 3회 진단 결과에 대한 데이터를 return
	 * 
	 * @param toolname 최근 데이터를 호출할 도구의 이름 
	 * @param userindex 어느 사용자에 대한 정보를 호출할지에 대한 사용자 고유 값 
	 * @param limitation 몇 개의 데이터를 호출할 것인지에 대한 변수 
	 * @return List<recentTestDataOnMyinfoDO> 최근 진단 정보를 표현하는데 필요한 데이터를 담은 객체 list
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public List<recentTestDataOnMyinfoDO> getRecent3TestResult(String toolname, String userindex, int limitation)
			throws Exception {
		return myinfo.getRecentDataByLimitationNum(toolname, userindex, limitation);
	}

	/**
	 * 도구의 섹션별 평균 점수를 return.
	 * 
	 * @param toolname 평균 점수를 구하고자 하는 도구의 이름 
	 * @return HashMap 섹션별 평균 점수에 대한 정보를 담고 있는 객체 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public HashMap getAverageSectionScore(String toolname) throws Exception {
		HashMap sectionalScore = new HashMap();
		List<String> userindexList = new Vector<String>();
		try {
			userindexList = myinfo.getUserindexFromTestresultByToolname(toolname);
		} catch (SQLException e) {
			System.out.println("SQL Exception occured : for getAverageSectionScore \n");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<String> sectionList = new Vector<String>();
		try {
			sectionList = myinfo.getSectionCommentsByToolname(toolname);
			for (String comment : sectionList) {
				comment.substring(3);
			}
		} catch (SQLException e) {
			System.out.println("SQLException occured : for getBmkSectionByQuestionList");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int[] finalResult = this.calculateSectionalScore(sectionList, toolname, userindexList);
		sectionalScore.put("sectionComment", sectionList);
		sectionalScore.put("sectionScore", finalResult);

		return sectionalScore;
	}

	/**
	 * 사용자에 대한 도구별 섹션 점수를 return.
	 * 
	 * @param toolname 섹션별 점수를 구하고자 하는 도구의 이름 
	 * @param userindex 섹션별 점수를 구하고자 하는 대상 사용자의 고유 값 
	 * @return HashMap 섹션별 점수에 대한 정보를 담고 있는 객체 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public HashMap getSectionalScore(String toolname, String userindex) throws Exception {
		HashMap sectionalScore = new HashMap();
		// get user recent result, bookmark&section connection List to compare each
		// other and get section score
		List<String> userindexList = new Vector<String>();
		userindexList.add(userindex);
		List<String> sectionList = new Vector<String>();
		try {
			sectionList = myinfo.getSectionCommentsByToolname(toolname);
			for (String comment : sectionList)
				comment.substring(3);
		} catch (SQLException e) {
			System.out.println("SQLException occured : for getBmkSectionByQuestionList");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int[] finalResult = this.calculateSectionalScore(sectionList, toolname, userindexList);
		sectionalScore.put("sectionComment", sectionList);
		sectionalScore.put("sectionScore", finalResult);

		return sectionalScore;
	}

	/**
	 * 제공된 정보를 바탕으로, 도구의 섹션별 점수를 연산함.
	 * 
	 * @param sectionList 섹션들에 대한 정보를 담은 List 객체 
	 * @param toolname 어떤 도구에 대한 섹션별 점수를 연산할 것인지에 대한, 도구 이름 
	 * @param userindexList 섹션별 평균의 경우, 진단을 시행한 모든 유저들의 고유 값이 list로, 개인 섹션 점수의 경우, 그 사용자의 고유 값 하나만 존재.
	 * @return int[] 섹션별 점수 값을 가지고 있는 int형 array
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public int[] calculateSectionalScore(List<String> sectionList, String toolname, List<String> userindexList)
			throws Exception {
		List<Integer> userTestResult = new Vector<Integer>();
		List<sectionIndexFromQuestionDO> bmkSectionList = new Vector<sectionIndexFromQuestionDO>();
		HashMap<Integer, Integer> correct = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> total = new HashMap<Integer, Integer>();
		for (int usercount = 0; usercount < userindexList.size(); usercount++) {
			try {
				userTestResult = diagnoseservice.extractQuestionResultToList(
						diagnoseservice.recentTestResultByToolname(toolname, userindexList.get(usercount)));
				bmkSectionList = myinfo.getBmkSectionByQuestionList(toolname, userindexList.get(usercount));
			} catch (SQLException e) {
				System.out.println("SQL Exception occured in calculating section score \n");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (int i = 0; i < bmkSectionList.size(); i++) {
				total.merge((bmkSectionList.get(i).getBmksection() - 1), 1, (oldValue, newValue) -> ((int)oldValue + (int)newValue));
				
				if (bmkSectionList.get(i).getRecentflag() != 1) {
					//add +1 point to section score
					correct.merge((bmkSectionList.get(i).getBmksection() - 1), userTestResult.get(i), (oldValue, newValue) -> ((int)oldValue + (int)newValue));
				}
				else {
					correct.merge((bmkSectionList.get(i).getBmksection() - 1), 0, (oldValue, newValue) -> ((int)oldValue + (int)newValue));
				}
			}
		}
		int[] sectionScorePercentage = this.calculateScore(total, correct);
		return sectionScorePercentage;
	}

	/**
	 * 섹션별 점수를 10점 만점 기준 몇 점인지 환산함. 
	 * 
	 * @param total 총 문항 개수 
	 * @param correct 정답 문항 개수 
	 * @return int[] 10점 만점으로 환산된 섹션별 점수의 array 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public int[] calculateScore(HashMap<Integer, Integer> total, HashMap<Integer, Integer> correct) throws Exception {
		int i = 0;
		int[] calculationResult = new int[total.size()];
		for(int temp : total.keySet()) {
			float total_get = total.get(temp);
			float correct_get = correct.get(temp);
			calculationResult[i++] = (int) ((float) (correct_get / total_get) * 10);
		}
		return calculationResult;
	}
}
