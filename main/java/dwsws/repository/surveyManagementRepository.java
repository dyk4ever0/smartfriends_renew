package dwsws.repository;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSessionException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dwsws.outputdata.questionManagementListDO;

/**
 * manages survey table's CRUD operations
 * @author 박승수 (21.02.08)
 */
@Repository
public class surveyManagementRepository {
	@Autowired
	SqlSessionTemplate sqlSession;
	
	/**
	 * 진단 제공 도구의 index 값을 반
	 * @param toolname index를 얻고자 하는 도구의 이름 
	 * @return int 도구의 index 값 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public int callToolidxFromSurveyOfferingTable(String toolname) throws SQLException {
		return sqlSession.selectOne("callToolidxByToolname", toolname);
	}
	
	/**
	 * 새로운 진단 제공 도구를 test_offer_tools에 추가함.
	 * @param toolname 새로 추가된 진단 제공 도구 이름 
	 * @param userindex 새로운 진단 제공 도구를 추가한 사용자의 고유 값 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public void registerNewSurveyInfoIntoTable(String toolname, String userindex) throws SQLException {
		HashMap map = new HashMap();
		map.put("toolname", toolname);
		map.put("userindex", userindex);
		sqlSession.insert("registerNewSurveyInfo", map);
	}
	
	/**
	 * 새로 추가된 진단 제공 도구에 대해, 진단 문항을 저장할 신규 테이블을 생성.
	 * 
	 * @param toolname 새로 추가된 진단 제공 도구 이름 
	 * @param flag 기존에 이미 같은 이름의 테이블이 있는지 없는지에 대한 판별 flag 변수 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public void createNewSurveyQuestionList(String toolname, byte flag) throws SQLException{
		String executeSql = "";
		if(flag == 1) {
			executeSql = "update " + toolname + "_question_list set TOOLIDX = " + this.callToolidxFromSurveyOfferingTable(toolname);
		} else {
		executeSql = "CREATE TABLE IF NOT EXISTS `" + toolname + "_question_list` (\n"
				+ "  `IDX` int(11) NOT NULL AUTO_INCREMENT,\n"
				+ "  `QUESTION` varchar(300) DEFAULT NULL,\n"
				+ "  `ANSWER` binary(1) DEFAULT NULL,\n"
				+ "  `TOOLIDX` int(11) DEFAULT NULL,\n"
				+ "  `BMKIDX` int(11) DEFAULT NULL,\n"
				+ "  `UPDATEUSERINDEX` varchar(20) DEFAULT NULL,\n"
				+ "  `UPDATEDATE` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),\n"
				+ "  `ENTRYUSERINDEX` varchar(20) DEFAULT NULL,\n"
				+ "  `ENTRYDATE` timestamp NULL DEFAULT current_timestamp(),\n"
				+ "  PRIMARY KEY (`IDX`),\n"
				+ "  UNIQUE KEY `IDX_UNIQUE` (`IDX`),\n"
				+ "  KEY `fk_" + toolname + "_test_result_test_offer_tools1_idx` (`TOOLIDX`),\n"
				+ "  KEY `fk_" + toolname + "_bookmark_idx_idx` (`BMKIDX`),\n"
				+ "  CONSTRAINT `fk_" + toolname + "_bmkidx` FOREIGN KEY (`BMKIDX`) REFERENCES `bookmark` (`IDX`) ON DELETE CASCADE ON UPDATE NO ACTION,\n"
				+ "  CONSTRAINT `fk_" + toolname + "_test_result_test_offer_tools1` FOREIGN KEY (`TOOLIDX`) REFERENCES `test_offer_tools` (`IDX`) ON DELETE SET NULL ON UPDATE SET NULL\n"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
		}
		try {
			sqlSession.selectOne("createNewSurveyTable", executeSql);
		} catch(Exception e) {
			System.out.println("SQL Error occured on createNewSurveyQuestionList");
			e.printStackTrace();
			return ;
		}
		return ;
	}
	
	/**
	 * 새로 추가된 진단 제공 도구에 대해, 최신 진단 데이터만 따로 모아둘 수 있는 신규 테이블을 생성.
	 * 
	 * @param toolname 새로 추가된 진단 제공 도구 이름 
	 * @param flag 기존에 이미 같은 이름의 테이블이 있는지 없는지에 대한 판별 flag 변수 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public void createNewRecentResultTable(String toolname, byte flag) throws SQLException {
		String executeSql = "";
		if(flag == 1) {
			executeSql = "update " + toolname +  "_recent_test_result set TOOLIDX = " + this.callToolidxFromSurveyOfferingTable(toolname);
		} else {
		executeSql = 
				"CREATE TABLE IF NOT EXISTS `" + toolname + "_recent_test_result` (\n"
				+ "  `IDX` int(11) NOT NULL AUTO_INCREMENT,\n"
				+ "  `TOOLIDX` int(11) DEFAULT NULL,\n"
				+ "  `USERINDEX` varchar(20) NOT NULL,\n"
				+ "  `USERNAME` varchar(300) NOT NULL,\n"
				+ "  `TOTALSCORE` int(11) DEFAULT NULL,\n"
				+ "  `SKILL` varchar(45) DEFAULT NULL,\n"
				+ "  `DUTYCODE` int(11) DEFAULT NULL,\n"
				+ "  `COMPANYNAME` varchar(300) DEFAULT NULL,\n"
				+ "  `ORGNAME` varchar(300) DEFAULT NULL,\n"
				+ "  `UPDATEUSERINDEX` varchar(20) DEFAULT NULL,\n"
				+ "  `UPDATEDATE` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),\n"
				+ "  `ENTRYUSERINDEX` varchar(20) DEFAULT NULL,\n"
				+ "  `ENTRYDATE` timestamp NULL DEFAULT current_timestamp(),\n"
				+ "  PRIMARY KEY (`IDX`),\n"
				+ "  UNIQUE KEY `IDX_UNIQUE` (`IDX`),\n"
				+ "  UNIQUE KEY `USERINDEX_UNIQUE` (`USERINDEX`),\n"
				+ "  KEY `toolidx_fk_idx_" + toolname + "` (`TOOLIDX`),\n"
				+ "  CONSTRAINT `toolindex_fk" + toolname + "` FOREIGN KEY (`TOOLIDX`) REFERENCES `test_offer_tools` (`IDX`) ON DELETE SET NULL ON UPDATE SET NULL\n"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
		}
		try {
			sqlSession.selectOne("createNewSurveyTable", executeSql);
		} catch(Exception e) {
			System.out.println("SQL Error occured on createNewSurveyQuestionList");
			e.printStackTrace();
			return ;
		}
		return ;
	}
	
	/**
	 * 새로 추가된 진단 제공 도구에 대해, 진단 데이터를 저장할 수 있는 신규 테이블을 생성.
	 * 
	 * @param toolname 새로 추가된 진단 제공 도구 이름 
	 * @param flag 기존에 이미 같은 이름의 테이블이 있는지 없는지에 대한 판별 flag 변수 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public void createNewTestResultTable(String toolname, byte flag) throws SQLException {
		String executeSql = "";
		if(flag == 1) {
			executeSql = "update " + toolname + "_test_result set TOOLIDX = " + this.callToolidxFromSurveyOfferingTable(toolname);
		} else {
		executeSql =
				"CREATE TABLE IF NOT EXISTS `" + toolname + "_test_result` (\n"
				+ "  `IDX` int(11) NOT NULL AUTO_INCREMENT,\n"
				+ "  `TOOLIDX` int(11) DEFAULT NULL,\n"
				+ "  `USERINDEX` varchar(20) NOT NULL,\n"
				+ "  `USERNAME` varchar(300) NOT NULL,\n"
				+ "  `DUTYCODE` int(11) NOT NULL,\n"
				+ "  `TRIALNUM` int(11) DEFAULT NULL,\n"
				+ "  `Q1` binary(1) DEFAULT NULL,\n"
				+ "  `Q2` binary(1) DEFAULT NULL,\n"
				+ "  `Q3` binary(1) DEFAULT NULL,\n"
				+ "  `Q4` binary(1) DEFAULT NULL,\n"
				+ "  `Q5` binary(1) DEFAULT NULL,\n"
				+ "  `Q6` binary(1) DEFAULT NULL,\n"
				+ "  `Q7` binary(1) DEFAULT NULL,\n"
				+ "  `Q8` binary(1) DEFAULT NULL,\n"
				+ "  `Q9` binary(1) DEFAULT NULL,\n"
				+ "  `Q10` binary(1) DEFAULT NULL,\n"
				+ "  `Q11` binary(1) DEFAULT NULL,\n"
				+ "  `Q12` binary(1) DEFAULT NULL,\n"
				+ "  `Q13` binary(1) DEFAULT NULL,\n"
				+ "  `Q14` binary(1) DEFAULT NULL,\n"
				+ "  `Q15` binary(1) DEFAULT NULL,\n"
				+ "  `Q16` binary(1) DEFAULT NULL,\n"
				+ "  `Q17` binary(1) DEFAULT NULL,\n"
				+ "  `Q18` binary(1) DEFAULT NULL,\n"
				+ "  `Q19` binary(1) DEFAULT NULL,\n"
				+ "  `Q20` binary(1) DEFAULT NULL,\n"
				+ "  `TOTALSCORE` int(11) DEFAULT NULL,\n"
				+ "  `SKILL` varchar(45) DEFAULT NULL,\n"
				+ "  `COMPANYNAME` varchar(300) DEFAULT NULL,\n"
				+ "  `ORGNAME` varchar(300) DEFAULT NULL,\n"
				+ "  `UPDATEUSERINDEX` varchar(20) DEFAULT NULL,\n"
				+ "  `UPDATEDATE` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),\n"
				+ "  `ENTRYUSERINDEX` varchar(20) DEFAULT NULL,\n"
				+ "  `ENTRYDATE` timestamp NULL DEFAULT current_timestamp(),\n"
				+ "  PRIMARY KEY (`IDX`),\n"
				+ "  UNIQUE KEY `IDX_UNIQUE` (`IDX`),\n"
				+ "  KEY `fk_" + toolname + "_test_result_test_offer_tools2_idx` (`TOOLIDX`),\n"
				+ "  CONSTRAINT `fk_" + toolname + "_test_result_test_offer_tools2` FOREIGN KEY (`TOOLIDX`) REFERENCES `test_offer_tools` (`IDX`) ON DELETE SET NULL ON UPDATE SET NULL\n"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
		}
		try {
			sqlSession.selectOne("createNewSurveyTable", executeSql);
		} catch(Exception e) {
			System.out.println("SQL Error occured on createNewSurveyQuestionList");
			e.printStackTrace();
			return ;
		}
		return ;
	}
	
	/**
	 * 기존 존재하던 진단 제공 도구를 test_offer_tools table에서만 제거함 
	 * 
	 * @param toolname 제거하고자 하는 진단 제공 도구의 이름 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public void deleteTestOfferTool(String toolname) throws SQLException {
		sqlSession.selectOne("deleteOneTestOfferTool", toolname);
	}
	
	/**
	 * 진단 문항들을 return.
	 * @param toolname 진단 문항을 불러오고자 하는 도구 이름 
	 * @return List<questionManagementListDO> 진단 문항에 대한 정보를 담고 있는 객체의 list 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public List<questionManagementListDO> callMngQuestionList(String toolname) throws SQLException {
		return sqlSession.selectList("callMngQuestionData", toolname);
	}
	
	/**
	 * 진단 문항과 연결된 section 및 bookmark에 대한 정보를 return. 
	 * 
	 * @param toolname section 및 bookmark를 호출하고자 하는 도구의 이름 
	 * @return List<questionManagementListDO> 문항과 관련된 section 및 bookmark에 대한 정보를 담고 있는 객체의 list 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public List<questionManagementListDO> callSelectionDataByToolname(String toolname) throws SQLException {
		return sqlSession.selectList("callSelectionDataByToolname", toolname);
	}
	
	/**
	 * 수정된 진단 문항을 DB에 반영함 
	 * 
	 * @param toolname 수정된 진단 문항의 도구 이름 
	 * @param bmkidx 변경된 진단 문항이 연결된 bookmark index
	 * @param answer 변경된 진단 문항의 정답 (O , X)
	 * @param idx 진단 문항의 index
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public void updateSurveyMngChange(String toolname, String bmkidx, byte answer, int idx) throws SQLException {
		HashMap map = new HashMap();
		map.put("toolname", toolname);
		map.put("bmkidx", bmkidx);
		map.put("answer", answer);
		map.put("idx", idx);
		sqlSession.update("updateSurveyMngChange", map);
	}
	
	/**
	 * 새로운 진단 문항을 DB에 삽입함 
	 * 
	 * @param toolname 수정된 진단 문항의 도구 이름 
	 * @param question 수정된 진단 문항 내용 
	 * @param answer 변경된 진단 문항의 정답 (O , X)
	 * @param toolidx 수정된 진단 문항의 도구 index 
	 * @param bmkidx 변경된 진단 문항이 연결된 bookmark index
	 * @param entryuserindex 수정을 시도한 사용자의 고유 값 
	 * @return int 수정된 문항 갯수 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public int insertSurveyMngChange(String toolname, String question, byte answer, int toolidx, int bmkidx, String entryuserindex) throws SQLException {
		HashMap map = new HashMap();
		map.put("toolname", toolname);
		map.put("toolidx", toolidx);
		map.put("bmkidx", bmkidx);
		map.put("answer", answer);
		map.put("question", question);
		map.put("entryuserindex", entryuserindex);
		return sqlSession.insert("insertSurveyMngChange", map);
	}
	
	/**
	 * 진단 문항을 삭제함 
	 * @param toolname 삭제하고자 하는 문항이 포함된 도구 이름 
	 * @param idx 삭제하고자 하는 진단 문항의 index 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public void deleteSurveyMngChange(String toolname, int idx) throws SQLException {
		HashMap map = new HashMap();
		map.put("toolname", toolname);
		map.put("idx", idx);
		sqlSession.delete("deleteSurveyMngChange", map);
	}
	
	/**
	 * 해당 도구의 모든 진단문항을 삭제함 
	 * 
	 * @param toolname 진단문항들을 삭제하고자 하는 도구의 이름 
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public void deleteAllSurvey(String toolname) throws SQLException {
		sqlSession.delete("deleteAllSurvey", toolname);
	}
	
	/**
	 * 진단 문항 생성때 생기는 테이블들의 존재 유무 확인 
	 * 
	 * @param toolname 테이블 존재 유무를 파악하고자 하는 도구의 이름 
	 * @return int 존재하는 테이블의 갯수 (존재시 정상 값 : 3)
	 * @throws SQLException
	 * 
	 * @author 박승수 
	 */
	public int checkSurveyExistence(String toolname) throws SQLException {
		int result = 0;
		String tableName = null;
		List<String> sqlSentence = Arrays.asList("_question_list", "_recent_test_result", "_test_result");
		
		try {
			for(int i = 0; i < 3; i++) {
				String parameter = toolname + sqlSentence.get(i);
				if(sqlSession.selectOne("checkTableExistence", parameter) != null) {
					System.out.println("table name : " + sqlSession.selectOne("checkTableExistence", parameter));
					result++;
				}
			}
		} catch(Exception e) {
			System.out.println("SQL error occured on surveyMngRepo _ on calling table existence");
			e.printStackTrace();
			return result;
		}
		return result;
	}
}
