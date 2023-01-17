package dwsws.service;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwsws.dto.questionListDTO;
import dwsws.outputdata.compareDataForCalcScoreDO;
import dwsws.outputdata.suggestionListFormDO;
import dwsws.outputdata.testResultWithToolnameDO;
import dwsws.repository.diagnoseRepository;

/**
 * - QuestionListByToolname : 진단 문항들을 return.
 * - recentTestResultByToolname : 가장 최근 실시한 진단 데이터를 return.
 * - testOfferingToolsList : 진단을 제공하는 도구 목록을 return.
 * - getCompareTestDataList : 사용자가 제출한 답지와 비교하여 점수를 계산하는데 필요한 정답지를 return.
 * - saveTestResult : 사용자가 제출한 답지를 저장함.
 * - calculateScore : 사용자가 제출한 답지와 정답지를 비교하여 점수를 계산함.
 * - calculateSkillGrade : 사용자의 등수를 통해 총 5단계의 레벨 중 어디에 위치하는지 return.
 * - numberOfCorrectByTool : 도구별로, 몇개의 문항이 정답처리 되었는지 return.
 * - suggestionList : 정답/오답노트의 출력에 필요한 데이터를 return.
 * - extractQuestionResultToList : DTO의 field로 존재하는 개별 변수를 array로 변환함.
 * - callTotalscoreByUserindex : user_ranking 테이블에서 도구에 대한 모든 진단 결과 합을 return.
 * 
 * @Autowired
	private diagnoseRepository diagnose;
 * @author 박승수 
 *
 */
@Service
public class diagnoseService {
	@Autowired
	private diagnoseRepository diagnose;

	/**
	 * 진단 문항들을 return.
	 * 
	 * @Transactional
	 * 
	 * @param toolname 진단 문항을 받고자하는 도구의 이름 
	 * @return List<questionListDTO> 해당 도구에 대한 진단 문항들을 담고 있는 객체의 List
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public List<questionListDTO> QuestionListByToolname(String toolname) throws Exception {
		List<questionListDTO> returnedQuestionList = diagnose.QuestionListByToolname(toolname);
		return returnedQuestionList;
	}

	/**
	 * 가장 최근 실시한 진단 데이터를 return.
	 * 
	 * @Transactional
	 * 
	 * @param toolname 어떤 도구의 진단 데이터를 검색할 것인지에 대한 도구 이름 
	 * @param userindex 누구의 진단 데이터를 검색할 것인지에 대한 사용자 이름 
	 * @return testResultWithToolnameDO 최근 진단 데이터를 포함한 객체 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public testResultWithToolnameDO recentTestResultByToolname(String toolname, String userindex) throws Exception {
		testResultWithToolnameDO result = new testResultWithToolnameDO();
		try {
			result = diagnose.recentTestResultByToolname(toolname, userindex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result.getToolname() == null) {
			result.setToolname(toolname);
			result.setTrialnum(0);
			//due to the 'nullpointerexception' from repository when there is no recent data for current user,
			//setQuestionnum is again used after usement at repository.
			result.setQuestionnum(diagnose.getQuestionNum(toolname));
		}
		return result;
	}

	/**
	 * 진단을 제공하는 도구 목록을 return.
	 * 
	 * @Transactional
	 * 
	 * @return List<String> 진단을 제공하는 도구들의 이름을 가진 List 객체 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public List<String> testOfferingToolsList() throws Exception {
		return diagnose.testOfferingToolsList();
	}
	
	/**
	 * 사용자가 제출한 답지와 비교하여 점수를 계산하는데 필요한 정답지를 return.
	 * 
	 * @Transactional
	 * 
	 * @param toolname 어떤 진단 제공 도구에 대한 정답지를 받을 것인지 도구 이름.
	 * @return List<compareDataForCalcScoreDO> 각 문항에 대한 정답을 가지고 있는 정답지 List 객체 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public List<compareDataForCalcScoreDO> getCompareTestDataList(String toolname) throws SQLException {
		return diagnose.compareTestDataList(toolname);
	}

	/**
	 * 사용자가 제출한 답지를 저장함.
	 * 
	 * @Transactional
	 * 
	 * @param userindex 진단을 시행한 사용자의 고유값인 index값 
	 * @param username 진단을 시행한 사용자의 이름 
	 * @param toolname 사용자가 실시한 진단 도구 이름 
	 * @param trialnum 사용자가 몇 번째 진단 시도인지에 대한 값 
	 * @param mycurrentscore 이전 진단 정보가 있다면, 이전 회차의 점수 값 
	 * @param mynewscore 이번 회차 진단의 점수 
	 * @param ansResult 사용자가 제출한 정답 목록 
	 * @param rankingActivationFlag user_ranking 테이블의 ranking값을 조절하는데 필요한 case 분류 flag 변수 
	 * @param mycurrentTotalscore user_ranking 테이블에 저장되어 있는 사용자의 모든 진단 결과 합 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public void saveTestResult(String userindex, String username, String toolname, int trialnum, int mycurrentscore, int mynewscore, List<Byte> ansResult, String rankingActivationFlag, int mycurrentTotalscore)
			throws Exception {
		// make skill grade
		String userDegree = this.calculateSkillGrade(toolname, userindex, mynewscore);

		// insert final result into accumulation table in database
		diagnose.saveUserTestResult(toolname, userindex, username, trialnum, mynewscore, userDegree, ansResult, rankingActivationFlag, mycurrentTotalscore);
	}

	/**
	 * 사용자가 제출한 답지와 정답지를 비교하여 점수를 계산함.
	 * 
	 * @Transactional
	 * 
	 * @param total 진단 문항 총 개수 
	 * @param correct 정답 문항 총 개수 
	 * @return int 100점 만점으로 환산된 결과 값 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public int calculateScore(int total, int correct) throws Exception {
		float total_get = total;
		float correct_get = correct;
		int calculatedResult = (int) ((float) (correct_get / total_get) * 100);
		return calculatedResult;
	}

	/**
	 * 사용자의 등수를 통해 총 5단계의 레벨 중 어디에 위치하는지 return.
	 * 
	 * @Transactional
	 * 
	 * @param toolname 어느 도구의 진단에 대해 레벨을 판별할 것인지에 대한 도구 이름 
	 * @param userindex 어느 사용자의 정보를 검색할 것인지에 대한 사용자 고유 값 
	 * @param currentscore 레벨을 측정하는데 사용될 점수 값 
	 * @return String 총 5단계로 구분된 레벨 중 사용자가 포함된 레벨 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public String calculateSkillGrade(String toolname, String userindex, int currentscore)
			throws Exception {
		String[] rank = { "매우 낮은", "낮은", "보통", "높은", "매우 높은" };

		// get number of current test data in DB
		int numOfTestData = diagnose.callNumberOfTestData(toolname);

		// calculate percentage of each rank
		// and returns user's rank
		int[] percentage = { numOfTestData, (int) (numOfTestData * 0.8), (int) (numOfTestData * 0.6),
				(int) (numOfTestData * 0.4), (int) (numOfTestData * 0.2) };
		int userRank = diagnose.callUserRanking(toolname, userindex, currentscore);
		String userDegree = null;
		for (int i = 0; i < percentage.length; i++) {
			if (userRank <= percentage[i]) {
				userDegree = rank[i];
			}
		}
		return userDegree;
	}
	
	/**
	 * 도구별로, 몇개의 문항이 정답처리 되었는지 return.
	 * 바로 직전 시행된 진단에 대한 정답 문항 개수를 return함. 즉, survey-result에서 사용됨.
	 * @Transactional
	 * 
	 * @param userindex 진단을 제출한 사용자의 고유 값.
	 * @param toolname 진단을 진행한 도구의 이름.
	 * @return int 정답 문항 수 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public int numberOfCorrectByTool(String userindex, String toolname) throws Exception {
		testResultWithToolnameDO recentTestResult = new testResultWithToolnameDO();
		recentTestResult = diagnose.recentTestResultByToolname(toolname, userindex);
		List<Integer> ansList = this.extractQuestionResultToList(recentTestResult);
		
		int numOfCorrect = 0;
		for(int i = 0; i < ansList.size(); i++) {
			if(ansList.get(i) == 1)
				numOfCorrect++;
		}
		return numOfCorrect;
	}

	/**
	 * 정답/오답노트의 출력에 필요한 데이터를 return.
	 * 
	 * @Transactional
	 * @param userindex 사용자 고유 값.
	 * @param delimiter 정답노트를 받을지, 오답노트를 받을지에 대한 구분 flag 변수.
	 * @return List<suggestionListFormDO> 정답/오답노트를 출력하는데에 필요한 정보를 담은 객체 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public List<suggestionListFormDO> suggestionList(String userindex, int delimiter) throws Exception {
		// declare requested parameters
		List<suggestionListFormDO> suggestionResult = new Vector<suggestionListFormDO>();
		List<String> testOfferTools = diagnose.testOfferingToolsList();
		// temporary variable for inserting 'suggestionResult' index element
		int suggestionindex = 0;
		testResultWithToolnameDO recentTestList = new testResultWithToolnameDO();

		// iteration about tool's name
		
		for (int i = 0; i < testOfferTools.size(); i++) {
			try {
				List<compareDataForCalcScoreDO> compareList = new Vector<compareDataForCalcScoreDO>();
				// get compare list and recent user result by tool name
				compareList.addAll(diagnose.compareTestDataList(testOfferTools.get(i)));
				recentTestList = diagnose.recentTestResultByToolname(testOfferTools.get(i), userindex);
	
				// preparation to use q1 ~ q20 as list
				List<Integer> testResultAsList = this.extractQuestionResultToList(recentTestList);
	
				// iteration about # of question's length
				for (int j = 0; j < compareList.size(); j++) {
					suggestionListFormDO insertionTemp = new suggestionListFormDO();
	
					// if user's recent answer is wrong, then add to returning list
					if (testResultAsList.get(j) == delimiter) {
						// increase return List's index element
						suggestionindex++;
						int bmkindex = compareList.get(j).getBmkidx();
						insertionTemp = diagnose.getSuggestionInfoByBookmarkindex(compareList.get(j).getBmkidx(),
										compareList.get(j).getSecidx(),
										compareList.get(j).getToolidx());
						try {
							insertionTemp.setIdx(suggestionindex);
						} catch(NullPointerException e) {
							e.printStackTrace();
						}
						
	
						// add result data into here
						suggestionResult.add(insertionTemp);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return suggestionResult;
	}

	/**
	 * DTO의 field로 존재하는 개별 변수를 array로 변환함.
	 * method 객체를 활용하여, getter method를 통해 값을 받아오며, return값이 있는 경우에만 List<Integer>에 add함.
	 * @param input DTO class의 field로 존재하는 문항 답 객체 
	 * @return List<Integer> 정답/오답에 대한 정보가 담겨있는 List 객체 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	public List<Integer> extractQuestionResultToList(testResultWithToolnameDO input) throws Exception {
		List<Integer> result = new Vector<Integer>();

		// i is from 0 to 19 due to the limitation of # of q's in DB
		// only when q? has the value, add into List. otherwise, do nothing
		for (int i = 0; i < 20; i++) {
			Method method = testResultWithToolnameDO.class.getDeclaredMethod("getQ" + Integer.toString(i + 1));
			if ((Integer) method.invoke(input, null) != null) {
				result.add((Integer) method.invoke(input, null));
			}
		}
		return result;
	}
	
	/**
	 * user_ranking 테이블에서 도구에 대한 모든 진단 결과 합을 return.
	 * 
	 * @Transactional
	 * 
	 * @param userindex 어느 사용자의 진단 결과 합을 return할 지에 대한 사용자 고유 값.
	 * @return int 모든 최근 진단에 대한 결과 합.
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public int callTotalscoreByUserindex(String userindex) throws Exception {
		return diagnose.getTotalscoreByUserindex(userindex);
	}
}
