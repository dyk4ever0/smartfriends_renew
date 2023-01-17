
package dwsws.repository;


import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dwsws.outputdata.digitalBoardDO;

/**
 * 가이드페이지 - 디지털창 관련 레퍼지토리
 * - selectDigitalList : 디지털창 목록 ex) 베아월드, 간편도구..
 * - selectDigitalContentsList : 디지털창의 특정 콘텐츠(베아월드. 간편도구..)와 관련된 컨텐츠 목록 ex) 메일_템플릿 기능, 메일_서명기능..
 * - selectDigitalImgByIdx : 특정 컨텐츠와 관련된 이미지 리스트 ex) 메일_템플릿기능과 관련있는 이미지명 리스트
 * 
 * @Autowired
	SqlSessionTemplate sqlSession;
 * 
 * @author 김예린
 *
 */
@Repository
public class digitalRepository {
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	/**
	 *  디지털창 목록 ex) 베아월드, 간편도구..
	 * 
	 * @return
	 */
	public List<String> selectDigitalList(){
		return sqlSession.selectList("selectDigitalList");
	}
	
	/**
	 * 디지털창의 특정 콘텐츠(베아월드. 간편도구..)와 관련된 컨텐츠 목록 
	 * ex) 메일_템플릿 기능, 메일_서명기능..
	 * 
	 * @param title: 베아월드, 간편도구..와 같은 레벨의 디지털창 컨텐츠 
	 * @return 디지털창의 특정 콘텐츠(베아월드. 간편도구..)와 관련된 컨텐츠 목록
	 */
	public List<digitalBoardDO> selectDigitalContentsList(String title){
		List<digitalBoardDO> list = sqlSession.selectList("selectDigitalContentsList",title);
		return list;
	}
	
	/**
	 * 특정 컨텐츠와 관련된 이미지 리스트
	 * ex) 메일_템플릿기능과 관련있는 이미지명 리스트
	 * 
	 * @param contentsIdx : 특정 컨텐츠의 idx(이미지가 참조하고 있는 contents idx)
	 * @return 이미지명 리스트
	 */
	public List<String> selectDigitalImgByIdx(int contentsIdx){
		List<String> imgList = sqlSession.selectList("selectDigitalImgByIdx", contentsIdx);
		return imgList;
	}
}	

