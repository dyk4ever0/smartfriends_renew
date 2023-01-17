package dwsws.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static dwsws.service.digital_modifyService.*;

import dwsws.repository.addContents;
/**
 *  [pc] admin - 컨텐츠관리 - 가이드관리 탭 관련 서비스(contents-lnb 관련)
 *  [pc] admin - 컨텐츠관리 - 디지털창 탭 관련 서비스(contents-lnb 관련)
 * 
 * - insertNewContents 메소드 : contents 테이블에 새로운 컨텐츠 추가 (가이드관리 관련)
 * - deleteContents 메소드 : contents 테이블에서 컨텐츠 제거 (가이드관리 관련)
 * - deleteContents4Digital 메소드 : 디지털창에서 컨텐츠 삭제 시(베아월드, 간편도구..) 디비와 서버에서 모두 삭제하는 함수(디지털창 관련)
 * 
 * @Autowired
	addContents addContents;
 * 
 * @author 김예린
 *
 */
@Service
public class addContentsService {

	@Autowired
	addContents addContents;
	
	/**
	 * contents 테이블에 새로운 컨텐츠 추가 (가이드관리 관련)
	 * 사용자가 contents-lnb의 +버튼을 클릭하고 컨텐츠를 추가한 경우 실행
	 * 
	 * @param title : 컨텐츠명
	 * @param comments : 컨텐츠 설명
	 * @param url : youtube url
	 * @param category : 컨텐츠가 속한 카테고리(도구가이드 or 보안)
	 * @param userindex : 사용자정보
	 * 
	 * @return DB에 데이터를 추가한 결과
	 */
	@Transactional
	public int insertNewContents(String title, String comments, String url, String category, String userindex) {
		int result = addContents.insertNewContents(title, comments, url, category, userindex);
		return result;
	}
	
	/**
	 * contents 테이블에서 컨텐츠 제거 (가이드관리 관련)
	 * 사용자가 contents-lnb의 -버튼을 클릭하고 컨텐츠를 삭제한 경우 실행
	 * 
	 * @param contents : 삭제할 컨텐츠명
	 * 
	 * @return DB에서 데이터를 삭제한 결과
	 */
	@Transactional
	public int deleteContents(String contents) {
		int result = addContents.deleteContents(contents);
		return result;
	}
	
	/**
	 * 디지털창에서 컨텐츠 삭제 시(베아월드, 간편도구..) 디비와 서버에서 모두 삭제하는 함수(디지털창 관련)
	 * 
	 * 1. 파라미터로 받은 컨텐츠명을 이용해 해당 컨텐츠와 관련된 모든 이미지 리스트를 DB에서 가져온다.
	 * 2. 이미지 리스트 정보로 서버에서 이미지를 삭제한다.
	 * 3. DB에서 컨텐츠명을 삭제한다.(컨텐츠명을 참조하던 이미지 리스트들은 delete cascade이기 때문에 컨텐츠명이 삭제되면 함께 삭제)
	 * 
	 * @param contentsName : 삭제할 컨텐츠명 ex) 베아월드, 간편도구..와 같은 레벨
	 * 
	 * @return DB에서 데이터를 삭제한 결과
	 */
	@Transactional
	public int deleteContents4Digital(String contentsName) {
		//해당 컨텐츠와 관련된 모든 이미지 리스트
		List<String> imgList = addContents.select_routes_All(contentsName);
		
		for(String i : imgList) {
			// 서버에서 이미지파일 삭제
			String path = filePath + i;
			File file = new File(path);
			if(file.exists() == true) {
				file.delete();
			}
		}
		//디비에서 삭제
		int result = addContents.deleteContents(contentsName);
		return result;
	}
}
