package dwsws.repository;

import java.util.HashMap;
import java.util.List;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dwsws.outputdata.UserFavoriteList;

/**
 * header의 즐겨찾기 버튼관련 레퍼지토리
 * 
 * - selectUserFavoriteList 메소드 : 사용자별 즐겨찾기 목록 호출
 * - selectNum 메소드 : 사용자별 즐겨찾기 갯수
 * - selectBmkidxList 메소드 : 사용자별 즐겨찾기 목록 중 bookmark index만 존재하는 리스트
 * - insertFavorite 메소드 : 즐겨찾기 목록 추가
 * - deleteFavorite 메소드 : 즐겨찾기 목록 제거
 * 
 * @Autowired
	private SqlSessionTemplate sqlSession;
 * 
 * @author 김예린
 *
 */
@Repository
public class commonRepository {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	/**
	 * 사용자별 즐겨찾기 목록 호출
	 * 
	 * @param userindex : 사용자정보
	 * @return 사용자별 즐겨찾기 목록
	 * 
	 * @author 김예린
	 */
	public List<UserFavoriteList> selectUserFavoriteList(String userindex){
		return sqlSession.selectList("userFavoriteList", userindex);
	}
	
	/**
	 * 사용자별 즐겨찾기 갯수
	 * 
	 * @param userindex : 사용자정보
	 * @return 사용자별 즐겨찾기 갯수
	 * 
	 *  @author 김예린
	 */
	public int selectNum(String userindex) {
		return sqlSession.selectOne("userFavoriteListNum", userindex);
	}
	
	/**
	 * 사용자별 즐겨찾기 목록 중 bookmark index만 존재하는 리스트
	 * 
	 * @param userindex : 사용자정보
	 * @return 사용자별 즐겨찾기 목록 중 bookmark index만 존재하는 리스트
	 * 
	 *  @author 김예린
	 */
	public List<Integer> selectBmkidxList(String userindex){
		return sqlSession.selectList("userFavoriteBmkidxList", userindex);
	}
	
	/**
	 * 즐겨찾기 목록 추가
	 * 
	 * @param param : userindex, 즐겨찾기에 추가할 북마트 인덱스
	 * @return DB에 데이터를 저장한 결과
	 * 
	 * @author 김예린
	 */
	public int insertFavorite(HashMap<String, Object> param) {
		return sqlSession.insert("insertFavorite", param);
	}
	
	/**
	 * 즐겨찾기 목록 제거
	 * 
	 * @param param : userindex, 즐겨찾기에서 제거할 북마크 인덱스
	 * @return : DB에서 데이터를 삭제한 결과
	 * 
	 *  @author 김예린
	 */
	public int deleteFavorite(HashMap<String, Object> param) {
		return sqlSession.delete("deleteFavorite", param);
	}
}
