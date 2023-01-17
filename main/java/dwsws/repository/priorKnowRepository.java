package dwsws.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dwsws.outputdata.GuideinfoDO;
import dwsws.outputdata.bookmarkDO;

/**
 * 가이드페이지 - 보안관련 레퍼지토리
 * 
 * - selectPriorKnowList : 보안관련 컨텐츠 리스트 - BeaDoc..
 * - selectGuideInfo : 특정 컨텐츠의 tool, comments, youtubeurl 정보
 * - selectSectionList : 특정 컨텐츠의 섹션 리스트
 * - selectSectionListNum : 특정 컨텐츠의 섹션 갯수
 * - selectBookmarkList : 특정 컨텐츠의 북마크 리스트
 * - selectUrl2 : 두번째 영상 URL
 * 
 * @Autowired
	SqlSessionTemplate sqlSession;
 * 
 * @author 김예린
 *
 */
@Repository
public class priorKnowRepository {
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	/**
	 * 보안관련 컨텐츠 리스트 - BeaDoc..
	 * 
	 * @return 보안관련 컨텐츠 리스트
	 */
	public List<String> selectPriorKnowList(){
		return sqlSession.selectList("selectPriorKnowList");
	}
	/**
	 * 특정 컨텐츠의 tool, comments, youtubeurl 정보
	 * 
	 * @param tool : 컨텐츠명(BearDoc..)
	 * @return 특정 컨텐츠의 tool, comments, youtubeurl 정보
	 */
	public GuideinfoDO selectGuideInfo(String tool) {
		return sqlSession.selectOne("selectPriorKnowInfo", tool);
	}
	/**
	 * 특정 컨텐츠의 섹션 리스트
	 * 
	 * @param tool: 컨텐츠명(BearDoc..)
	 * @return 특정 컨텐츠의 섹션 리스트
	 */
	public List<String> selectSectionList(String tool){
		return sqlSession.selectList("selectPriorSectionList", tool);
	}
	/**
	 * 특정 컨텐츠의 섹션 갯수
	 * 
	 * @param tool: 컨텐츠명(BearDoc..)
	 * @return 특정 컨텐츠의 섹션 갯수
	 */
	public int selectSectionListNum(String tool) {
		return sqlSession.selectOne("selectPriorSectionListNum", tool);
	}
	/**
	 * 특정 컨텐츠의 북마크 리스트
	 * 
	 * @param comments : 섹션명
	 * @param tool : 컨텐츠명(BearDoc..)
	 * @return 특정 컨텐츠의 북마크 리스트
	 */
	public List<bookmarkDO> selectBookmarkList(String comments, String tool) {
		Map<String, Object> param = new HashMap<>();
		param.put("comments",comments );
		param.put("tool", tool);
		return sqlSession.selectList("selectPriorBMKList", param);
	}
	/**
	 * 두번째 영상 URL
	 * 자동화를 위해 파라미터로 컨텐츠명을 받지만, 베어독에서만 사용중
	 * 
	 * @param tool: 컨텐츠명(BearDoc..)
	 * @return
	 */
	public String selectUrl2(String tool) {
		return sqlSession.selectOne("selectUrl2", tool);
	}
}
