package dwsws.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwsws.outputdata.questionManagementListDO;
import dwsws.repository.surveyManagementRepository;

/**
 * - createNewTable : 새로 생성된 진단 제공 도구에 대해, 진단 정보 저장에 필요한 테이블을 신규 생성 
 * - deleteTestOfferToolTableElement : 진단 제공 도구 제거에 대해, test_offer_tools 테이블의 한 row를 제거 (기존 생성된 테이블은 유지) 
 * - callQuestionListForMng : 등록되어있는 진단 문항을 return. 
 * - callSelectionDataForMng : 진단 문항과 연결된 section 및 bookmark 정보를 return. 
 * - applyModificationOnSurvey : 수정된 진단 문항을 DB에 적용함 
 * - callToolidxByToolname : 제공된 진단 제공 도구 이름으로 진단 제공 도구의 index값을 return 
 * 
 * @Autowired
	surveyManagementRepository repository;
	
 * @author 박승수 
 *
 */
@Service
public class surveyManagementService {
	@Autowired
	surveyManagementRepository repository;
	
	/**
	 * 새로 생성된 진단 제공 도구에 대해, 진단 정보 저장에 필요한 테이블을 신규 생성 
	 * 
	 * @param toolname 새로이 진단을 제공할 도구의 이름 
	 * @param userindex 진단을 추가하는 사용자의 고유 값 
	 * @return boolean 진단 추가 성공 혹은 실패 flag 값 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public boolean createNewTable(String toolname, String userindex) throws Exception {
		// set flag value for updating toolidx or creating new tables
		byte flag = 0;
		if(repository.checkSurveyExistence(toolname) > 2) {
			flag = 1; // if there already tables exist
		} else {
			flag = 0; // if there's no existing tables
		}
		//table creation or toolidx update activates
		try {
			repository.registerNewSurveyInfoIntoTable(toolname, userindex);
			repository.createNewRecentResultTable(toolname, flag);
			repository.createNewSurveyQuestionList(toolname, flag);
			repository.createNewTestResultTable(toolname, flag);
		} catch(SQLException e) {
			System.out.println("SQL error occured _ on creating new survey tables");
			e.printStackTrace();
			return false;
		} catch(Exception e) {
			System.out.println("Non SQL error occured _ on creating new survey tables");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 진단 제공 도구 제거에 대해, test_offer_tools 테이블의 한 row를 제거 (기존 생성된 테이블은 유지) 
	 * 
	 * @param toolname 제거할 진단 제공 도구 이름 
	 * @return boolean 진단 제공 도구 삭제 성공 혹은 실패 flag 값 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public boolean deleteTestOfferToolTableElement(String toolname) throws Exception {
		try {
			repository.deleteTestOfferTool(toolname);
		} catch(SQLException e) {
			System.out.println("SQL error occured _ on deleting existing survey tool");
			e.printStackTrace();
			return false;
		} catch(Exception e) {
			System.out.println("Non SQL error occured _ on deleting existing survey tool");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 등록되어있는 진단 문항을 return. 
	 * 
	 * @param toolname 진단 문항을 호출할 도구의 이름 
	 * @return List<questionManagementListDO> 진단 문항들의 정보가 담겨있는 객체의 List 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public List<questionManagementListDO> callQuestionListForMng(String toolname) throws Exception {
		List<questionManagementListDO> result = new Vector();
		try {
			result = repository.callMngQuestionList(toolname);
		} catch(SQLException e) {
			System.out.println("SQL error occured _ on calling Mng Question List");
			e.printStackTrace();
			result = null;
		} catch(Exception e) {
			System.out.println("Non SQL error occured _ on calling Mng Question List");
			e.printStackTrace();
			result = null;
		}
		return result;
	}
	
	/**
	 * 진단 문항과 연결된 section 및 bookmark 정보를 return. 
	 * 
	 * @param toolname section 및 bookmark정보를 불러올 대상인 도구의 이름 
	 * @return List<questionManagementListDO> 해당 진단 문항과 연결된 section 및 bookmark가 저장된 객체 List 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public List<questionManagementListDO> callSelectionDataForMng(String toolname) throws Exception {
		List<questionManagementListDO> result = new Vector();
		try {
			result = repository.callSelectionDataByToolname(toolname);
		} catch(SQLException e) {
			System.out.println("SQL error occured _ on calling selection data list");
			e.printStackTrace();
			result = null;
		} catch(Exception e) {
			System.out.println("Non SQL error occured _ on calling selection data list");
			e.printStackTrace();
			result = null;
		}
		return result;
	}
	
	/**
	 * 수정된 진단 문항을 DB에 적용함 
	 * @param newData 수정된 문항들에 대한 정보가 담겨있는 객체 list 
	 * @param toolname 어느 도구의 문항들을 수정할 것인지에 대한 도구 이름 
	 * @param userindex 수정을 시도하는 사용자의 고유 값 
	 * @return boolean 수정 성공 혹은 실패에 대한 flag 값 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public boolean applyModificationOnSurvey(List<questionManagementListDO> newData, String toolname, String userindex) throws Exception {
		int numberOfQuestions = 0;
		try {
			//1. delete all question list in database
			repository.deleteAllSurvey(toolname);
			//2. insert all ranged 
			for(int index = 0; index < newData.size(); index++) {
				questionManagementListDO oneQuestion = newData.get(index);
				//if there is no selected bmkidx, then do not store.
				if(oneQuestion.getBmkidx() > 0) {
					repository.insertSurveyMngChange(toolname, 
														oneQuestion.getQuestion(),
														oneQuestion.getAnswer(),
														oneQuestion.getToolidx(),
														oneQuestion.getBmkidx(),
														userindex);
					numberOfQuestions++;
				}
			}
		} catch(SQLException e) {
			System.out.println("SQL error occured _ on applying modification on data list");
			e.printStackTrace();
			return false;
		} catch(Exception e) {
			System.out.println("Non SQL error occured _ on applying modification on data list");
			e.printStackTrace();
			return false;
		}
		if(numberOfQuestions > 0) return true;
		else return false;
	}
	
	/**
	 * 제공된 진단 제공 도구 이름으로 진단 제공 도구의 index값을 return 
	 * @param toolname index값을 찾고자하는 도구 이름 
	 * @return int 검색된 도구의 index값 
	 * @throws Exception
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public int callToolidxByToolname(String toolname) throws Exception {
		try {
			return repository.callToolidxFromSurveyOfferingTable(toolname);
		} catch(SQLException e) {
			System.out.println("SQL error occured _ on calling callToolidxByToolname");
			e.printStackTrace();
			return (-1);
		} catch(Exception e) {
			System.out.println("Non-SQL error occured _ on calling callToolidxByToolname");
			e.printStackTrace();
			return (-1);
		}
	}
}

