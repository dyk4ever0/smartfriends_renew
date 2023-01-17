package dwsws.repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSessionException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dwsws.outputdata.numberOfSkillListDO;

@Repository
public class analysisRepository {
	@Autowired
	SqlSessionTemplate sqlSession;
	
	/**
	 * Returns Skill of crew among recent test data
	 * @param nothing
	 * @throws SQLException Error handling occured in sqlSession
	 * 
	 * @author 박승수 
	 */
	public numberOfSkillListDO callCrewSkill() throws SQLException {
		return (numberOfSkillListDO) sqlSession.selectOne("numberOfCrewSkill");
	}
	
	/**
	 * Returns Skill of non_crew among recent test data
	 * @param nothing
	 * @throws SQLException Error handling occured in sqlSession
	 * 
	 * @author 박승수 
	 */
	public numberOfSkillListDO callNotCrewSkill() throws SQLException {
		return (numberOfSkillListDO) sqlSession.selectOne("numberOfNotcrewSkill");
	}
	
	/**
	 * Returns crew's score of tool from tool_recent_data table
	 * @param toolname name of tool want to get
	 * @throws SQLException Error handling occured in sqlSession
	 * 
	 * @author 박승수 
	 */
	public int callCrewScoreByToolname(String toolname) throws SQLException {
		int result = 0;
		try {
			result = sqlSession.selectOne("getCrewToolScore", toolname);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Returns non crew's score of tool from tool_recent_data table
	 * @param toolname name of tool want to get
	 * @throws SQLException Error handling occured in sqlSession
	 * 
	 * @author 박승수 
	 */
	public int callNonCrewScoreByToolname(String toolname) throws SQLException {
		int result = 0;
		try {
			result = sqlSession.selectOne("getNonCrewToolScore", toolname);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * returns average score of tool's recent data table
	 * @param toolname tool's name 'll be used on configuring the table be searched
	 * @throws SQLException Error handling occured in sqlSession
	 * 
	 * @author 박승수 
	 */
	public int callAveragescoreByToolname(String toolname) throws SQLException {
		try {
			return sqlSession.selectOne("getAverageToolScore", toolname);
		} catch(NullPointerException e) {
			e.printStackTrace();
			return 0;
		}
//		return sqlSession.selectOne("getAverageToolScore", toolname);
	}
	
	/**
	 * returns all company name from user_info table with 'group by'
	 * @throws SQLException Error handling occured in sqlSession
	 * 
	 * @author 박승수 
	 */
	public List<String> callCompanynameList() throws SQLException {
		return sqlSession.selectList("getCompanynameList");
	}
	
	/**
	 * returns organization name of given company name with 'group by orgname'
	 * @param companyname company's name that wants to find out the orgname of its.
	 * @throws SQLException Error handling occured in sqlSession
	 * 
	 * @author 박승수 
	 */
	public List<String> callOrgnameList(String companyname) throws SQLException {
		return sqlSession.selectList("getOrgnameListGroupbyCompanyname", companyname);
	}
	
	/**
	 * returns org's ranking from user_ranking table
	 * this method uses average score of user_ranking table score group by orgname, companyname so that can figure out fair ranking
	 * @param orgname organization's name want to search
	 * @param companyname company's name want to search
	 * @throws SQLException Error handling occured in sqlSession
	 * 
	 * @author 박승수 
	 */
	public int callOrgRanking(String orgname, String companyname) throws SQLException {
		HashMap map = new HashMap();
		if(orgname != null)
			map.put("orgname", orgname);
		map.put("companyname", companyname);
		return sqlSession.selectOne("getRankingByCompOrOrg", map);
	}
	
	/**
	 * returns # of people in selected Organization from user_info table
	 * @param orgname org name want to get
	 * @param companyname company name want to get
	 * @throws SQLException Error handling occured in sqlSession
	 * 
	 * @author 박승수 
	 */
	public int callNumberOfOrgPeople(String orgname, String companyname) throws SQLException {
		HashMap map = new HashMap();
		if(orgname != null)
			map.put("orgname", orgname);
		map.put("companyname", companyname);
		return sqlSession.selectOne("getNumberOfPeopleInCompOrOrg", map);
	}
	
	/**
	 * returns # of diagnose attended people in selected Organization from user_ranking table
	 * @param orgname org name want to get
	 * @param companyname company name want to get
	 * @throws SQLException Error handling occured in sqlSession
	 * 
	 * @author 박승수 
	 */
	public int callNumberOfOrgAttendencePeople(String orgname, String companyname) throws SQLException {
		HashMap map = new HashMap();
		map.put("orgname", orgname);
		map.put("companyname", companyname);
		return sqlSession.selectOne("getNumberOfPeopleOnAttendenceInfo", map);
	}
	
	/**
	 * returns # of all orgs group by orgname and companyname
	 * @throws SQLException Error handling occured in sqlSession
	 * 
	 * @author 박승수 
	 */
	public int callAllNumberOfOrgs() throws SQLException {
		return sqlSession.selectOne("getAllOrgNumber");
	}
	
	/**
	 * returns # of all Companies group by orgname and companyname
	 * @throws SQLException Error handling occured in sqlSession
	 * 
	 * @author 박승수 
	 */
	public int callAllNumberOfCompanies() throws SQLException {
		return sqlSession.selectOne("getAllCompanyNumber");
	}
	
	/**
	 * returns # of all orgs from tool_test_result table
	 * This is used for calculating bullet chart data in org tab of analysis page
	 * @param toolname tool's name want to get about.
	 * @throws SQLException Error handling occured in sqlSession
	 * 
	 * @author 박승수 
	 */
	public int callTotalOrgNumberInTestResultTableByToolname(String toolname) throws SQLException {
		return sqlSession.selectOne("getTotalOrgNumberDataFromRecentTableByToolname", toolname);
	}
	
	/**
	 * returns # of all companys from tool_test_result table
	 * This is used for calculating bullet chart data in company tab of analysis page
	 * @param toolname tool's name want to get about.
	 * @throws SQLException Error handling occured in sqlSession
	 * 
	 * @author 박승수 
	 */
	public int callTotalCompNumberInTestResultTableByToolname(String toolname) throws SQLException {
		return sqlSession.selectOne("getTotalCompNumberDataFromRecentTableByToolname", toolname);
	}
	
	/**
	 * returns org's score from test_result table by ranking
	 * This is used for searching required data in bullet chart on org tab of analysis page
	 * Especially this is used to get maximum and median score in bullet chart.
	 * @param toolname tool's name want to get about.
	 * @param orgranking org's ranking want to get the score about.
	 * @throws SQLException Error handling occured in sqlSession
	 * 
	 * @author 박승수 
	 */
	public int callOrgScoreByRanking(String toolname, int orgranking) throws SQLException {
		if(orgranking == 0) orgranking = 1;
		int result = 0;
		HashMap map = new HashMap();
		map.put("toolname", toolname);
		map.put("ranking", orgranking);
		try {
			result = sqlSession.selectOne("getOrgScoreByOrgRanking", map);
		} catch(Exception e) {
			System.out.println("casted to 0 due to the nullPointerException from sqlSession in analysisRepository - callOrgScoreByRanking");
		}
		return result;
	}
	
	/**
	 * returns Company's score from test_result table by ranking
	 * This is used for searching required data in bullet chart on Company tab of analysis page
	 * Especially this is used to get maximum and median score in bullet chart.
	 * @param toolname tool's name want to get about.
	 * @param orgranking org's ranking want to get the score about.
	 * @throws SQLException Error handling occured in sqlSession
	 * 
	 * @author 박승수 
	 */
	public int callCompanyScoreByRanking(String toolname, int companyranking) throws SQLException {
		if(companyranking == 0) companyranking = 1;
		int result = 0;
		HashMap map = new HashMap();
		map.put("toolname", toolname);
		map.put("ranking", companyranking);
		try {
			result = sqlSession.selectOne("getCompanyScoreByRanking", map);
		} catch(Exception e) {
			System.out.println("casted to 0 due to the nullPointerException from sqlSession in analysisRepository - callCompanyScoreByRanking");
		}
		return result;
	}
	
	/**
	 * returns org's score from test_result table by orgname and companyname
	 * This is used for searching required data in bullet chart on org tab of analysis page
	 * Especially this is used to get specific org's score in bullet chart.
	 * @param toolname tool's name want to get about.
	 * @param companyname company name want to get about.
	 * @param orgname org's name want to get about.
	 * @throws SQLException Error handling occured in sqlSession
	 * 
	 * @author 박승수 
	 */
	public int callGroupScoreByCompAndOrgname(String toolname, String companyname, String orgname) throws SQLException {
		try {
			HashMap map = new HashMap();
			map.put("toolname", toolname);
			map.put("companyname", companyname);
			map.put("orgname", orgname);
			return sqlSession.selectOne("getGroupScoreByCompAndOrgName", map);
		} catch(NullPointerException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * returns tool_result table's minimum value group by orgname
	 * This is used to avoid always ranging lower 40% from 0 to somewhere. So, it can show real minimum score on bullet chart
	 * @param toolname tool's name want to get about.
	 * @throws SQLException Error handling occured in sqlSession
	 * 
	 * @author 박승수 
	 */
	public int callMinimumScoreByToolnameForOrg(String toolname) throws SQLException {
		return sqlSession.selectOne("getMinscoreFromTooltableForOrg", toolname);
	}
	
	/**
	 * returns tool_result table's minimum value group by companyname
	 * This is used to avoid always ranging lower 40% from 0 to somewhere. So, it can show real minimum score on bullet chart
	 * @param toolname tool's name want to get about.
	 * @throws SQLException Error handling occured in sqlSession
	 * 
	 * @author 박승수 
	 */
	public int callMinimumScoreByToolnameForCompany(String toolname) throws SQLException {
		try {
			return sqlSession.selectOne("getMinscoreFromTooltableForCompany", toolname);
		} catch(NullPointerException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * returns org people's skill grade distribution
	 * This is used for appending pie chart on org tab of analysis page
	 * @param companyname company's name want to get about.
	 * @param orgname org's name want to get about.
	 * 
	 * @author 박승수 
	 */
	public numberOfSkillListDO callNumberOfOrgSkill(String companyname, String orgname) throws SQLException{
		HashMap map = new HashMap();
		map.put("companyname", companyname);
		map.put("orgname", orgname);
		return sqlSession.selectOne("getNumberOfCompOrOrgSkill", map);
	}
}
