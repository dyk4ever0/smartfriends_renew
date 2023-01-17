package dwsws.repository;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dwsws.outputdata.contentsInfoDO;
import dwsws.outputdata.sectionListforEdit;


/**
 * admin - 컨텐츠관리 - 가이드관리 - 스마트워크Rule을 제외한 모든 컨텐츠 관련 레퍼지토리 ex) Lineworks, Zoom, BearDoc..
 * 
 * - selectContentsInfo :  contents 관련 기본정보 - tool, comments, youtubeurl, category
 * - updateComments : update comments
 * - updateUrl : update youtubeurl
 * - updateUrl2 : update youtubeurl2
 * - updateCategory : update category
 * - selectSectionListforEdit : select section List for edit containing sectionIdx
 * - deleteBmk : delete bookmark
 * - updateSection : update section
 * - updatebookmark : update bookmark
 * - insertbookmark : insert bookmark
 * - insertSection : insert section
 * - insertNewSectionNewBookmark : insert newbookmark of new section
 * - deleteSection : delete section
 * - selectBookmarkList : bookmark list
 * 
 * @Autowired
	SqlSessionTemplate sqlSession
	
 * @author 김예린
 *
 */
@Repository
public class contentsGuideRepository {
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	/**
	 * contents 관련 기본정보 - tool, comments, youtubeurl, category
	 * 
	 * @param tool : contents명(Zoom, Lineworks..)
	 * @return contents 관련 기본정보 
	 */
	public contentsInfoDO selectContentsInfo(String tool) {
		return sqlSession.selectOne("selectContentsInfo", tool);
	}

	/**
	 * update comments
	 * 
	 * @param tool : 컨텐츠명(Zoom, Lineworks..)
	 * @param comments : 변경할 comments 내용
	 * @param userindex
	 * @return update 결과
	 */
	public int updateComments(String tool, String comments, String userindex) {
		Map<String, Object> param = new HashMap<>();
		param.put("tool", tool);
		param.put("comments", comments);
		param.put("userindex", userindex);
		return sqlSession.update("updateComments", param);
	}
	
	/**
	 * update youtubeurl on contents table
	 * 
	 * @param tool : 컨텐츠명(Zoom, Lineworks..)
	 * @param url: 변경할 youtube url
	 * @param userindex
	 * @return update 결과
	 */
	public int updateUrl(String tool, String url, String userindex) {
		Map<String, Object> param = new HashMap<>();
		param.put("tool", tool);
		param.put("youtubeurl", url);
		param.put("userindex", userindex);
		return sqlSession.update("updateUrl", param);
	}
	
	/**
	 * update youtubeurl2  on contents table
	 * 
	 * @param tool : 컨텐츠명(Zoom, Lineworks..)
	 * @param url2 : 변경할 youtube url2
	 * @param userindex
	 * @return update 결과
	 */
		public int updateUrl2(String tool, String url2, String userindex) {
			Map<String, Object> param = new HashMap<>();
			param.put("tool", tool);
			param.put("youtubeurl", url2);
			param.put("userindex", userindex);
			return sqlSession.update("updateUrl2", param);
		}
	
	/**
	 * update category
	 * 
	 * @param tool : 컨텐츠명(Zoom, Lineworks..)
	 * @param category : 변경할 카데고리(도구가이드, 보안)
	 * @param userindex
	 * @return update 결과
	 */
	public int updateCategory(String tool, String category, String userindex) {
		Map<String, Object> param = new HashMap<>();
		param.put("category", category);
		param.put("tool", tool);
		param.put("userindex", userindex);
		return sqlSession.update("updateCategory", param);
	}
	
	/**
	 * select section List for edit containing sectionIdx
	 * 
	 * @param tool : 컨텐츠명(Zoom, Lineworks..)
	 * @return 특정 컨텐츠와 관련된 섹션 리스트
	 */
	public List<sectionListforEdit> selectSectionListforEdit(String tool){
		return sqlSession.selectList("selectSectionListforEdit", tool);
	}
	
	/**
	 * delete bookmark
	 * 
	 * @param idx : 삭제할 북마크 idx
	 * @return delete 결과
	 */
	public int deleteBmk(int idx) {
		return sqlSession.delete("deleteBmk", idx);
	}
	

	/**
	 * update section
	 * 
	 * @param comments : 변경할 section 내용
	 * @param idx : section idx
	 * @param userindex
	 * @return
	 */
	public int updateSection(String comments, int idx, String userindex) {
		Map<String, Object> param = new HashMap<>();
		param.put("idx", idx);
		param.put("comments", comments);
		param.put("userindex", userindex);
		return sqlSession.update("updateSection", param);
	}
	
	/**
	 * update bookmark
	 * 
	 * @param timemoment : 시간
	 * @param comments : 북마크 comments
	 * @param userindex
	 * @param bmkidx : 북마크 idx
	 * @return
	 */
	public int updatebookmark(int timemoment, String comments, String userindex, int bmkidx) {
		Map<String, Object> param = new HashMap<>();
		param.put("timemoment",timemoment);
		param.put("comments", comments);
		param.put("userindex", userindex);
		param.put("bmkidx", bmkidx);
		return sqlSession.update("updatebookmark", param);
	}
	
	/**
	 * insert bookmark
	 * 
	 * @param timemoment: 시간
	 * @param comments : 북마크 comments
	 * @param section : 참조하고 있는 section
	 * @param userindex
	 * @return
	 */
	public int insertbookmark(int timemoment, String comments, int section, String userindex) {
		Map<String, Object> param = new HashMap<>();
		param.put("timemoment",timemoment);
		param.put("comments", comments);
		param.put("userindex", userindex);
		param.put("section", section);
		return sqlSession.insert("insertBookmark", param);
	}
	
	/**
	 * insert section
	 * 
	 * @param comments : section comments
	 * @param tool : section테이블의 contents를 알기위해 tool명으로 idx 찾음
	 * @param userindex
	 * @return
	 */
	public int insertSection(String comments, String tool, String userindex) {
		Map<String, Object> param = new HashMap<>();
		param.put("comments",comments);
		param.put("tool", tool);
		param.put("userindex", userindex);
		return sqlSession.insert("insertSection", param);
	}
	
	/**
	 * insert newbookmark of new section
	 * 새로 추가한 섹션에 새로운 북마크를 추가하는 경우
	 * 
	 * @param timemoment : 시간
	 * @param comments : 북마크 comments
	 * @param section : 참조하고 있는 section
	 * @param userindex
	 * @return
	 */
	public int insertNewSectionNewBookmark(int timemoment, String comments, String section, String userindex) {
		Map<String, Object> param = new HashMap<>();
		param.put("timemoment", timemoment);
		param.put("comments", comments);
		param.put("section", section);
		param.put("userindex", userindex);
		return sqlSession.insert("insertNewSectionNewBookmark", param);
	}
	
	/**
	 * delete section
	 * 
	 * @param idx : 삭제할 섹션 idx
	 * @return
	 */
	public int deleteSection(int idx) {
		return sqlSession.delete("deleteSection", idx);
	}
}
