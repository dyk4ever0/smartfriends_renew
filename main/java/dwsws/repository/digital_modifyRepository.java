package dwsws.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
/**
 * admin - 컨텐츠관리 - 디지털창 관련 레퍼지토리
 * 
 * - select_ContentsIdx_digital 메소드 : contents명으로 contents index 가져오기 
 * - select_count_contents 메소드 : 특정 컨텐츠 인덱스와 관련된 이미지 갯수 리턴
 * - insertImg 메소드 : tip_imgs 테이블에 이미지 추가
 * - insertContents_digital 메소드 : contents테이블에 디지털창 관련 컨텐츠 추가
 * - delete_img 메소드 : tip_imgs에서 이미지 삭제
 * - select_routes 메소드 : tip_imgs 테이블에서 특정 컨텐츠와 관련된 모든 파일명 가져오기
 * - delete_contents 메소드 : contents 테이블에서 컨텐츠 삭제 : 컨텐츠에서 삭제하면 이를 참조하던 tip_imgs테이블의 이미지 파일도 delete cascade
 * - updateContentsName4Digital 메소드 : 디지털창 관련 컨텐츠명 수정 (메일_템플릿 기능, 메일_서명기능..)
 * 
 * @Autowired
	SqlSessionTemplate sqlSession;
 * 
 * @author 김예린
 *
 */
@Repository
public class digital_modifyRepository {
	@Autowired
	SqlSessionTemplate sqlSession;
	
	/**
	 * contents명으로 contents index 가져오기 
	 *  
	 * @param contentsName : 컨텐츠명
	 * @return
	 * 
	 * @author 김예린
	 */
	public int select_ContentsIdx_digital(String contentsName) {
		return sqlSession.selectOne("select_ContentsIdx_digital", contentsName);
	}
	
	/**
	 * 특정 컨텐츠 인덱스와 관련된 이미지 갯수 리턴
	 * 
	 * @param contentsIdx : 컨텐츠 인덱스
	 * @return 특정 컨텐츠 인덱스와 관련된 이미지 갯수 리턴
	 * 
	 * @author 김예린
	 */
	public int select_count_contents(int contentsIdx) {
		return sqlSession.selectOne("select_count_contents", contentsIdx);
	}
	
	/**
	 * tip_imgs 테이블에 이미지 추가
	 * 
	 * @param imgName : 이미지명
	 * @param contentsIdx : 참조할 컨텐츠 index
	 * @param userindex : 사용자정보
	 * @return DB에 insert한 결과
	 * 
	 * @author 김예린
	 */
	public int insertImg(String imgName, int contentsIdx, String userindex) {
		Map<String, Object> param = new HashMap<>();
		param.put("imgName", imgName);
		param.put("contentsIdx", contentsIdx);
		param.put("userindex", userindex);
		return sqlSession.insert("insertImg", param);
	}
	
	/**
	 * contents테이블에 디지털창 관련 컨텐츠 추가
	 * 
	 * @param contentsTitle : 컨텐츠명(contents 테이블의 title)
	 * @param comments : 제목(contents 테이블의 comments)
	 * @param userindex : 사용자명
	 * @return DB에 insert한 결과
	 * 
	 * @author 김예린
	 */
	public int insertContents_digital(String contentsTitle, String comments, String userindex) {
		Map<String, Object> param = new HashMap<>();
		param.put("contentsTitle", contentsTitle);
		param.put("comments", comments);
		param.put("userindex", userindex);
		
		return sqlSession.insert("insertContents_digital", param);
	}
	
	/**
	 * tip_imgs에서 이미지 삭제
	 * 
	 * @param route : 이미지명(tip_imgs테이블의 route)
	 * @return DB에서 delete한 결과
	 */
	public int delete_img(String route) {
		return sqlSession.delete("delete_img", route);
	}
	
	/**
	 * tip_imgs 테이블에서 특정 컨텐츠와 관련된 모든 파일명 가져오기
	 * 
	 * @param contentsIdx : 컨텐츠 index
	 * @return tip_imgs 테이블에서 특정 컨텐츠와 관련된 모든 파일명 리스트
	 */
	public List<String> select_routes(int contentsIdx){
		return  sqlSession.selectList("select_routes", contentsIdx);
	}
	
	/**
	 * contents 테이블에서 컨텐츠 삭제 : 컨텐츠에서 삭제하면 이를 참조하던 tip_imgs테이블의 이미지 파일도 delete cascade
	 * 
	 * @param idxNum : 삭제할 컨텐츠 idx
	 * @return DB에서 delete한 결과
	 */
	public int delete_contents(int idxNum) {
		return sqlSession.delete("delete_contents", idxNum);
	}
	
	/**
	 * 디지털창 관련 컨텐츠명 수정 (메일_템플릿 기능, 메일_서명기능..)
	 * 
	 * @param idx : 수정할 컨텐츠 index
	 * @param comments : 변경할 내용
	 * @return DB에서 update한 결과
	 */
	public int updateContentsName4Digital(int idx, String comments) {
		Map<String, Object> param = new HashMap<>();
		param.put("comments", comments);
		param.put("idx", idx);
		return sqlSession.update("updateContentsName4Digital", param);
	}
}
