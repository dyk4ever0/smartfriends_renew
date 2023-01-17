package dwsws.repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dwsws.dto.questionListDTO;
import dwsws.dto.sectionDTO;
import dwsws.outputdata.compareDataForCalcScoreDO;
import dwsws.outputdata.suggestionListFormDO;
import dwsws.outputdata.testResultWithToolnameDO;

/**
 * user - 진단 관련 페이지
 * 
 * - QuestionListByToolname : 진단 제공 도구에 대한 진단 문항을 return.
 * - getQuestionNum :  제공되는 진단 문항의 갯수를 return. 
 * - recentTestResultByToolname : 진단 제공 도구에 대한 최근 시도 진단 데이터를 return. 
 * - testOfferingToolsList : 진단을 제공하는 도구의 이름 리스트를 return. 
 * - compareTestDataList : 제출된 답안지와 비교할 정답지를 return.
 * - sectionByEntryDate : 진단 문항과 연결된 section의 모든 column을 return.
 * - callUserRanking : 사용자의 등수를 return.
 * - callNumberOfTestData : 제공된 진단 제공 도구 이름에 대해, 총 몇회의 진단 데이터가 있는지 return.
 * - saveUserTestResult : 사용자로부터 제출된 진단 답안지를 저장 
 * - saveRecentRankingData : 제출된 진단 답안지를 통해, 구해진 user_ranking table에 필요한 데이터를 저장 
 * - getTotalscoreByUserindex : 제공된 사용자의 고유 값을 통해, 모든 진단제공도구에 대한 점수의 합을 return. 
 * - getSuggestionInfoByBookmarkindex : 정답/오답노트 출력에 필요한 정보를 return.
 * 
 * @Autowired
	 SqlSessionTemplate sqlSession;
	  
 * @author 박승수 
 *
 */
@Repository
public class diagnoseRepository {
	@Autowired
	 SqlSessionTemplate sqlSession;

	/**
	 * 진단 제공 도구에 대한 진단 문항을 return.
	 * 
	 * @param toolname 문항을 얻고자 하는 진단 제공 도구 이름 
	 * @return List<questionListDTO> 진단 문항에 대한 정보를 담고 있는 객체의 list 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public List<questionListDTO> QuestionListByToolname(String toolname) throws SQLException {
		List<questionListDTO> questionlist = sqlSession.selectList("callQuestionsByTool", toolname);
		return questionlist;
	}

	/**
	 * 제공되는 진단 문항의 갯수를 return. 
	 * 
	 * @param toolname 진단 갯수를 구하고 싶은 도구 이름 
	 * @return int 진단 문항의 갯수 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public int getQuestionNum(String toolname) throws SQLException {
		return sqlSession.selectOne("callQuestionNum", toolname);
	}

	/**
	 * 진단 제공 도구에 대한 최근 시도 진단 데이터를 return. 
	 * 
	 * @param toolname 최근 진단 시도 데이터를 받고 싶은 도구 이름 
	 * @param userindex 누구의 진단시도 데이터를 받을 것인지에 대한 사용자의 고유 값 
	 * @return testResultWithToolnameDO 최근 진단 데이터를 담고 있는 객체 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public testResultWithToolnameDO recentTestResultByToolname(String toolname, String userindex) throws SQLException {
		HashMap map = new HashMap();
		map.put("toolname", toolname);
		map.put("userindex", userindex);

		testResultWithToolnameDO returnedResult = new testResultWithToolnameDO();
		returnedResult = sqlSession.selectOne("callRecentTestResultByTool", map);
		returnedResult.setQuestionnum(this.getQuestionNum(toolname));
		return returnedResult;
	}

	/**
	 * 진단을 제공하는 도구의 이름 리스트를 return. 
	 * 
	 * @return List<String> 진단을 제공하는 도구 이름들의 list 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public List<String> testOfferingToolsList() throws SQLException {
		return sqlSession.selectList("callTestOfferingTools");
	}

	/**
	 * 제출된 답안지와 비교할 정답지를 return.
	 * 
	 * @param toolname 정답지를 얻고 싶은 도구의 이름 
	 * @return List<compareDataForCalcScoreDO> 각 문항에 대한 정답지 내용을 담고 있는 객체의 list 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public List<compareDataForCalcScoreDO> compareTestDataList(String toolname) throws SQLException {
		return sqlSession.selectList("callCompareDataForCalcScore", toolname);
	}

	/**
	 * 진단 문항과 연결된 section의 모든 column을 return.
	 * 
	 * @param toolname 어떤 도구에 대한 section을 얻고 싶은지에 대한, 도구의 이름 
	 * @return List<sectionDTO> 해당 도구와 관련된 section에 대한 정보를 담고 있는 객체의 list 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public List<sectionDTO> sectionByEntryDate(String toolname) throws SQLException {
		return sqlSession.selectList("callSectionByEntryDate", toolname);
	}

	/**
	 * 사용자의 등수를 return.
	 * 
	 * @param toolname 어느 진단 결과에 대한 등수를 반환할 것인지에 대한, 도구의 이름 
	 * @param userindex 어느 사용자의 등수를 반환할 것인지에 대한, 사용자의 고유 값 
	 * @param currentscore 현재 사용자의 점수 
	 * @return int 등수 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public int callUserRanking(String toolname, String userindex, int currentscore) throws SQLException {
		HashMap map = new HashMap();
		map.put("toolname", toolname);
		map.put("userindex", userindex);
		map.put("currentscore", currentscore);
		return sqlSession.selectOne("callranking", map);
	}

	/**
	 * 제공된 진단 제공 도구 이름에 대해, 총 몇회의 진단 데이터가 있는지 return.
	 * 
	 * @param toolname 진단 데이터의 갯수를 얻고자 하는 도구의 이름 
	 * @return int 저장된 데이터의 개수 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public int callNumberOfTestData(String toolname) throws SQLException {
		return sqlSession.selectOne("callNumberOfTestData", toolname);
	}

	/**
	 * 사용자로부터 제출된 진단 답안지를 저장 
	 * 
	 * @param toolname 진행한 진단의 도구 이름 
	 * @param userindex 진단을 시행한 사용자의 고유 값 
	 * @param username 진단을 시행한 사용자의 이름 
	 * @param trialnum 해당 도구에 대한 사용자의 진단 회차 
	 * @param totalscore 진행한 진단의 점수 
	 * @param skill 시행 진단에 대한 레벨 값 
	 * @param resultData 사용자가 제출한 답안의 정답/오답 목록 
	 * @param rankingActivationFlag user_ranking table을 업데이트 하는데 필요한 flag 변수 
	 * @param mycurrentTotalscore 현재까지 진행한 최근 진단 데이터 점수의 합  
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public void saveUserTestResult(String toolname, String userindex, String username, int trialnum, int totalscore,
			String skill, List<Byte> resultData, String rankingActivationFlag, int mycurrentTotalscore) throws SQLException {
		List<String> testOffering = this.testOfferingToolsList();
		HashMap map = new HashMap();
		map.put("toolname", toolname);
		map.put("userindex", userindex);
		map.put("username", username);
		map.put("trialnum", trialnum);
		map.put("resultdata", resultData);
		map.put("totalscore", totalscore);
		map.put("skill", skill);

		// make list for q1 ~ q? to pick column name in mybatis with iteration(foreach)
		List<String> questionColumnName = new Vector<String>();
		for (int i = 0; i < resultData.size(); i++) {
			questionColumnName.add(new String("q" + (i + 1)));
		}
		map.put("answerNum", questionColumnName);
		
		//save submitted result data into DB
		sqlSession.insert("saveUserTestResultData", map);
		//save calculated result data into DB table made for effectively calculating analysis data
		sqlSession.insert("updateRecentTestResult", map);
		
		for(int i = 0; i < testOffering.size(); i++) {
			//store dummy value into another calculated result data table for calculation of analysis data
			if(testOffering.get(i).equals(toolname) != true) {
				map.put("toolname", testOffering.get(i));
				sqlSession.insert("dummyRecentTestResult", map);
			}
		}
		//save into user_ranking table
		this.saveRecentRankingData(testOffering, userindex, username, mycurrentTotalscore, rankingActivationFlag);
		
		System.out.println("test result inserted well");
	}
	
	/**
	 * 제출된 진단 답안지를 통해, 구해진 user_ranking table에 필요한 데이터를 저장 
	 * 
	 * @param testOffering 시행한 진단의 도구 이름 
	 * @param userindex 진단을 시행한 사용자의 고유 값 
	 * @param username 진단을 시행한 사용자의 이름 
	 * @param mycurrentTotalscore 최근 진단 결과 점수들의 총 합 
	 * @param rankingActivationFlag user_ranking table을 업데이트 하는데 필요한 flag 변수
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public void saveRecentRankingData(List<String> testOffering, String userindex, String username, int mycurrentTotalscore, String rankingActivationFlag) throws SQLException {
		HashMap rankingCallMap = new HashMap();
		rankingCallMap.put("testOfferTools", testOffering);
		rankingCallMap.put("userindex", userindex);
		rankingCallMap.put("username", username);
		rankingCallMap.put("mynewRanking", sqlSession.selectOne("getRankingFromRecentData", rankingCallMap));
		rankingCallMap.put("mycurrentTotalscore", mycurrentTotalscore);
		//modify ranking table data case by ranking movement
		switch(rankingActivationFlag) {
			case "add new rank" : 
				sqlSession.update("updateRankNewly", rankingCallMap);
				sqlSession.insert("insertMyNewRankData", rankingCallMap);
				break;
			case "no activation" :
				sqlSession.update("updateNothing", userindex);
				break;
			case "between rank plus" :
				sqlSession.update("rankIncrease", rankingCallMap);
				sqlSession.update("updateMyNewRankData", rankingCallMap);
				break;
			case "between rank minus" :
				int temp = (int)sqlSession.selectOne("getRankingFromRecentData", rankingCallMap);
				rankingCallMap.put("mynewRanking", temp);
				sqlSession.update("rankDecrease", rankingCallMap);
				sqlSession.update("updateMyNewRankData", rankingCallMap);
				break;
		}
		System.out.println("after inserting user_ranking here \n\n");
	}
	
	/**
	 * 제공된 사용자의 고유 값을 통해, 모든 진단제공도구에 대한 점수의 합을 return. 
	 * 
	 * @param userindex 어느 사용자에 대한 총 합을 구할 것인지에 대한, 사용자의 고유 값 
	 * @return int 해당 사용자의 점수 총 합 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public int getTotalscoreByUserindex(String userindex) throws SQLException {
		return sqlSession.selectOne("getTotalscoreByUserindex", userindex);
	}

	/**
	 * 정답/오답노트 출력에 필요한 정보를 return.
	 * @param bmkidx 어느 bookmark에 대한 정/오답 노트 데이터를 출력하는지에 대한, bookmark index
	 * @param secidx bookmark가 속해 있는 section의 index
	 * @param toolidx section이 속해 있는 도구의 index
	 * @return suggestionListFormDO 제공된 bookmark에 대한 정/오답노트 정보를 담은 객체 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public suggestionListFormDO getSuggestionInfoByBookmarkindex(int bmkidx, int secidx, int toolidx) throws SQLException {
		HashMap map = new HashMap();
		map.put("bmkidx", bmkidx);
		map.put("secidx", secidx);
		map.put("toolidx", toolidx);
		return sqlSession.selectOne("suggestionReturn", map);
	}
}
