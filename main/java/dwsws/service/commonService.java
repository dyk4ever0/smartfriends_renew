package dwsws.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwsws.outputdata.UserFavoriteList;
import dwsws.repository.commonRepository;
/**
 * header의 즐겨찾기 버튼관련 서비스
 * 
 * - selectUserFavoriteList 메소드 : 사용자별 즐겨찾기 목록 호출
 * - selectNum 메소드 : 사용자별 즐겨찾기 갯수
 * - selectBmkidxList 메소드 : 사용자별 즐겨찾기 목록 중 bookmark idx만 존재하는 리스트
 * - insertFavorite 메소드 : 즐겨찾기 추가
 * - deleteFavorite 메소드 : 즐겨찾기 제거
 * 
 * @Autowired
	commonRepository repository;
 * 
 * @author 김예린
 *
 */
@Service
public class commonService {
	
	@Autowired
	commonRepository repository;
	
	/**
	 * 사용자별 즐겨찾기 목록 호출
	 * 
	 * @param userindex 사용자정보
	 * @return list : 해당 사용자의 즐겨찾기 목록
	 * 
	 * @author 김예린
	 */
	@Transactional
	public List<UserFavoriteList> selectUserFavoriteList(String userindex) {
		List<UserFavoriteList> list = repository.selectUserFavoriteList(userindex);
		return list;
	}
	
	/**
	 * 사용자별 즐겨찾기 갯수
	 * 
	 * @param userindex : 사용자정보
	 * @return num : 사용자별 즐겨찾기 갯수
	 * 
	 * @author 김예린
	 */
	@Transactional
	public int selectNum(String userindex) {
		int num = repository.selectNum(userindex);
		return num;
	}
	
	/**
	 * 사용자별 즐겨찾기 목록 중 bookmark idx만 존재하는 리스트
	 * 
	 * @param userindex
	 * @return list : 사용자별 즐겨찾기 목록 중 bookmark idx만 존재하는 리스트
	 * 
	 * @author 김예린
	 */
	@Transactional//사용자별 즐겨찾기 목록 중 bookmark idx만
	public List<Integer> selectBmkidxList(String userindex){
		List<Integer> list = repository.selectBmkidxList(userindex);
		return list;
	}
	
	/**
	 * 즐겨찾기 추가
	 * 
	 * @param userindex : 사용자정보
	 * @param bookmark : 즐겨찾기에 추가할 북마트 인덱스
	 * @return result : DB에 데이터를 저장한 결과
	 * 
	 * @author 김예린
	 */
	@Transactional
	public int insertFavorite(String userindex, int bookmark) {
		Map<String, Object> param = new HashMap<>();
		param.put("userindex", userindex);
		param.put("bookmark", bookmark);
		int result = repository.insertFavorite((HashMap<String, Object>) param);
		return result;
	}
	
	/**
	 * 즐겨찾기 제거
	 * 
	 * @param userindex : 사용자정보
	 * @param bookmark : 즐겨찾기에서 제거할 북마크 인덱스
	 * @return result : DB에서 데이터를 삭제한 결과
	 * 
	 * @author 김예린
	 */
	@Transactional
	public int deleteFavorite(String userindex, int bookmark) {
		Map<String, Object> param = new HashMap<>();
		param.put("userindex", userindex);
		param.put("bookmark", bookmark);
		int result = repository.deleteFavorite((HashMap<String, Object>) param);
		return result;
	}
}
