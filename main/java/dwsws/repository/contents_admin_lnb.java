package dwsws.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * [PC] admin - 컨텐츠관리 - 가이드관리 페이지의 contents-lnb 관련 레퍼지토리
 * 
 * - contentsList4admin 메소드 : contents-lnb에 삽입될 컨텐츠 목록(Zoom, Lineworks..) 리턴
 * 
 * @Autowired
	SqlSessionTemplate sqlSession;
 * 
 * @author 김예린
 *
 */
@Repository
public class contents_admin_lnb {
	@Autowired
	SqlSessionTemplate sqlSession;
	
	/**
	 * contents-lnb에 삽입될 컨텐츠 목록(Zoom, Lineworks..) 리턴
	 * 스마트워크룰과 디지털창 관련 컨텐츠는 제외
	 * 생성이유 : 컨텐츠관리 - 가이드관리탭 에서 컨텐츠 추가 시 컨텐츠 목록이 업데이트되기 때문
	 * 
	 * @return contents-lnb에 삽입될 컨텐츠 목록(Zoom, Lineworks..) 리턴
	 */
	public List<String> contentsList4admin(){
		return sqlSession.selectList("contentsList4admin");
	}
}
