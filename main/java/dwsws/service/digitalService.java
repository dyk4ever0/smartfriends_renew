
package dwsws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwsws.outputdata.digitalBoardDO;
import dwsws.repository.digitalRepository;

/**
 * 가이드페이지 - 디지털창 관련 서비스
 * - selectDigitalList : 디지털창 목록 ex) 베아월드, 간편도구..
 * - digitalListAll : 디지털창의 특정 콘텐츠(베아월드. 간편도구..)와 관련된 컨텐츠 목록 ex) 메일_템플릿 기능, 메일_서명기능..
 * - digitalImgs : 특정 컨텐츠와 관련된 이미지 리스트 ex) 메일_템플릿기능과 관련있는 이미지명 리스트
 * 
 * @Autowired
	digitalRepository digitalRepository;
	
 * @author 김예린
 *
 */
@Service
public class digitalService {
	@Autowired
	digitalRepository digitalRepository;
	
	/**
	 * 디지털창 목록 ex) 베아월드, 간편도구..
	 * 
	 * @return 디지털창 목록
	 */
	@Transactional
	public List<String> selectDigitalList(){
		List<String> list = digitalRepository.selectDigitalList();
		return list;
	}
	
	/**
	 * 디지털창의 특정 콘텐츠(베아월드. 간편도구..)와 관련된 컨텐츠 목록
	 *  ex) 메일_템플릿 기능, 메일_서명기능..
	 *  
	 * @param title : 베아월드, 간편도구..와 같은 레벨의 디지털창 컨텐츠 
	 * @return 디지털창의 특정 콘텐츠(베아월드. 간편도구..)와 관련된 컨텐츠 목록
	 * @throws Exception
	 */
	@Transactional
	public List<digitalBoardDO> digitalListAll(String title) throws Exception {
		return digitalRepository.selectDigitalContentsList(title);
	}
	
	/**
	 * 특정 컨텐츠와 관련된 이미지 리스트
	 * ex) 메일_템플릿기능과 관련있는 이미지명 리스트
	 * 
	 * @param idx : 특정 컨텐츠의 idx(이미지가 참조하고 있는 idx)
	 * @return 이미지명 리스트
	 * @throws Exception
	 */
	@Transactional
	public List<String> digitalImgs(int idx) throws Exception {
		return digitalRepository.selectDigitalImgByIdx(idx);
	}

}


