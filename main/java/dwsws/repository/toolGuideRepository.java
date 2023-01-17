package dwsws.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;

import dwsws.outputdata.GuideinfoDO;
import dwsws.outputdata.bookmarkDO;
/**
 * 가이드페이지 - 도구가이드 관련 레퍼지토리
 * 
 * - selectGuideInfo : 특정 컨텐츠의 tool, comments, youtubeurl 정보
 * - selectSectionList : 특정 컨텐츠의 섹션 리스트
 * - selectSectionListNum : 특정 컨텐츠의 섹션 갯수
 * - selectBookmarkList : 특정 컨텐츠의 북마크 리스트
 * - selectToolguideList : 도구가이드에 하는 컨텐츠 리스트
 * 
 * @Autowired
	SqlSessionTemplate sqlSession;
 * 
 * @author 김예린
 *
 */
@Repository
public class toolGuideRepository {
	
	@Autowired
	SqlSessionTemplate sqlSession;
	/**
	 * 특정 컨텐츠의 tool, comments, youtubeurl 정보
	 * 
	 * @param tool : 컨텐츠명(Zoom..)
	 * @return 특정 컨텐츠의 tool, comments, youtubeurl 정보
	 */
	public GuideinfoDO selectGuideInfo(String tool) {
		return sqlSession.selectOne("selectGuideInfo", tool);
	}
	/**
	 * 특정 컨텐츠의 섹션 리스트
	 * @param tool: 컨텐츠명(Zoom..)
	 * @return 특정 컨텐츠의 섹션 리스트
	 */
	public List<String> selectSectionList(String tool){
		return sqlSession.selectList("selectSectionList", tool);
	}
	/**
	 *  특정 컨텐츠의 섹션 갯수
	 * @param tool: 컨텐츠명(Zoom..)
	 * @return 특정 컨텐츠의 섹션 갯수
	 */
	public int selectSectionListNum(String tool) {
		return sqlSession.selectOne("selectSectionListNum", tool);
	}
	/**
	 * 특정 컨텐츠의 북마크 리스트
	 * 
	 * 특정 컨텐츠의 섹션리스트 정보르 가지고 와서 섹션 리스트의 for문을 돌면서 섹션별 북마크 정보를 얻음
	 * 
	 * @param tool:컨텐츠명(Zoom..)
	 * @return 특정 컨텐츠의 북마크 리스트
	 * @throws JsonProcessingException
	 */
	public List<bookmarkDO> selectBookmarkList(String comments, String tool) {
		Map<String, Object> param = new HashMap<>();
		param.put("comments",comments );
		param.put("tool", tool);
		return sqlSession.selectList("selectBMKList", param);
	}
	/**
	 * 도구가이드에 하는 컨텐츠 리스트
	 * @return 도구가이드에 하는 컨텐츠 리스트
	 */
	public List<String> selectToolguideList(){
		return sqlSession.selectList("selectToolguideList");
	}
}
