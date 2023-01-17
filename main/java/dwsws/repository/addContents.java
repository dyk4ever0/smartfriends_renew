package dwsws.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
/**
 * [pc] admin - 컨텐츠관리 - 가이드관리 탭 관련 레퍼지토리(contents-lnb 관련)
 * [pc] admin - 컨텐츠관리 - 디지털창 탭 관련 레퍼지토리(contents-lnb 관련)
 * 
 * - insertNewContents 메소드 : contents 테이블에 새로운 컨텐츠 추가 (가이드관리 관련)
 * - deleteContents 메소드 : contents 테이블에서 컨텐츠 제거 (가이드관리 관련)
 * - select_routes_All 메소드 : 특정 컨텐츠(베아월드, 간편도구..)와 관련된 모든 이미지 파일 select (디지털창 관련)
 * 
 * @Autowired
	SqlSessionTemplate sqlSession;
 * 
 * @author 김예린
 *
 */
@Repository
public class addContents {
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	/**
	 * contents 테이블에 새로운 컨텐츠 추가 (가이드관리 관련)
	 * 
	 * @param title : 컨텐츠명
	 * @param comments : 컨텐츠 설명
	 * @param url : youtube url
	 * @param category : 컨텐츠가 속한 카테고리(도구가이드 or 보안)
	 * @param userindex: 사용자정보
	 * @return DB에 데이터를 추가한 결과
	 */
	public int insertNewContents(String title, String comments, String url, String category, String userindex) {
		Map<String, Object> param = new HashMap<>();
		param.put("title", title);
		param.put("comments", comments);
		param.put("url", url);
		param.put("category", category);
		param.put("userindex", userindex);
		return sqlSession.insert("insertNewContents", param);
	}
	
	/**
	 * contents 테이블에서 컨텐츠 제거 (가이드관리 관련)
	 * 
	 * @param contents : 삭제할 컨텐츠명
	 * @return DB에서 데이터를 삭제한 결과
	 */
	public int deleteContents(String contents) {
		return sqlSession.delete("deleteContents", contents);
	}
	
	/**
	 * 특정 컨텐츠(베아월드, 간편도구..)와 관련된 모든 이미지 파일 select (디지털창 관련)
	 * 
	 * @param contentsName : 이미지 파일 리스트를 가져올 컨텐츠명
	 * @return  특정 컨텐츠(베아월드, 간편도구..)와 관련된 모든 이미지 파일 리스트
	 */
	public List<String> select_routes_All(String contentsName){
		return sqlSession.selectList("select_routes_All", contentsName);
	}
}
