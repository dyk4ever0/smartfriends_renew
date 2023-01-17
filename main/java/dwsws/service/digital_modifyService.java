package dwsws.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import dwsws.dto.imageDTO;
import dwsws.repository.digital_modifyRepository;

/**
 * admin - 컨텐츠관리 - 디지털창 관련 서비스
 * 
 * - imgUpload 메소드 : 서버에 이미지 저장 + 디비에 저장
 * - insertContents_digital 메소드 : contents테이블에 디지털창 관련 컨텐츠 추가 (메일_템플릿 기능, 메일_서명기능..)
 * - delete_img 메소드 : 디비에서 이미지 삭제 + 서버에서 이미지 삭제
 * - delete_contents 메소드 : lnb에서 디지털창 컨텐츠(베아월드, 간편도구와 같은 레벨) 삭제한 경우 - 디비와 서버(이미지)에서 모두 삭제
 * - updateContentsName4Digital 메소드 : 컨텐츠명 변경 (메일_템플릿 기능, 메일_서명기능..)
 * 
 * @Autowired
	digital_modifyRepository digital_modifyRepository;
	
	서버에서 디지털창 관련 이미지 파일 저장 경로
	public static final String filePath = "/data/app/tomcat8.5.9/webapps/DWSWS/images/digital/";
 * 
 * @author 김예린
 *
 */
@Service
public class digital_modifyService {
	//서버에서 디지털창 관련 이미지 파일 저장 경로
	//public static final String filePath = "C:\\sws_git\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\DWSWS\\images\\digital\\";
	public static final String filePath = "/data/app/tomcat8.5.9/webapps/DWSWS/images/digital/";
	
	@Autowired
	digital_modifyRepository digital_modifyRepository;
	
	/**
	 * 서버에 이미지 저장 + 디비에 저장
	 * 
	 * @param image : 이미지파일과 컨텐츠명이 담긴 객체
	 * @param userindex : 사용자정보
	 * 
	 * @throws IllegalStateException
	 * @throws IOException
	 * 
	 * @author 김예린
	 */
	@Transactional 
	public void imgUpload(imageDTO image, String userindex) throws IllegalStateException, IOException {
		MultipartFile file = image.getFile(); //이미지파일
		String originContentsName = image.getContentsName(); //컨텐츠명
		//컨텐츠 idx
		int contentsIdx = digital_modifyRepository.select_ContentsIdx_digital(originContentsName); 

		//DB에 이미지 저장
	    digital_modifyRepository.insertImg(file.getOriginalFilename(), contentsIdx, userindex);
	  
	
		 // Save File on system
	    if (!file.getOriginalFilename().isEmpty()) {
	        
	        file.transferTo (new File(filePath+file.getOriginalFilename()));
	       
	    } 
	    
	}
	
	/**
	 * contents테이블에 디지털창 관련 컨텐츠 추가 (메일_템플릿 기능, 메일_서명기능..)
	 * 
	 * @param addContentsArr : 추가할 컨텐츠명 배열
	 * @param tool : contents 테이블의 title 정보(베아월드, 간편도구..)
	 * @param userindex : 사용자정보
	 * @return DB에 데이터를 저장한 결과
	 * 
	 * @author 김예린
	 */
	@Transactional 
	public int insertContents_digital(ArrayList<String> addContentsArr, String tool, String userindex) {
		int result = 0;
		for(String i : addContentsArr) {
			result += digital_modifyRepository.insertContents_digital(tool, i, userindex);
		}
		return result;
	}
	
	/**
	 * 디비에서 이미지 삭제 + 서버에서 이미지 삭제
	 * 
	 * @param deleteImg : 삭제할 이미지 리스트
	 * 
	 * @return DB에 데이터를 삭제한 결과
	 * 
	 * @author 김예린
	 */
	@Transactional 
	public int delete_img(ArrayList<String> deleteImg) {
		int result =0;
		
		for(String i : deleteImg) {
			//디비에서 이미지파일 삭제
			result += digital_modifyRepository.delete_img(i);
			
			// 서버에서 이미지파일 삭제
			String path = filePath + i;
			File file = new File(path);
			if(file.exists() == true) {
				file.delete();
			}
		}
		
		return result;
	}

	/**
	 * lnb에서 디지털창 컨텐츠(베아월드, 간편도구와 같은 레벨) 삭제한 경우 - 디비와 서버(이미지)에서 모두 삭제
	 * 
	 * @param deleteContents : 삭제할 컨텐츠 리스트
	 * 
	 * @return DB에 데이터를 삭제한 결과
	 * 
	 * @author 김예린
	 */
	@Transactional
	public int delete_contents(ArrayList<String> deleteContents) {
		int result = 0;
		
		for(String i : deleteContents) {
			//삭제할 컨텐츠명
			int contentsIdx  = Integer.parseInt(i);
			//해당 컨텐츠와 관련된 이미지 파일명 리스트
			List<String> routeList =  digital_modifyRepository.select_routes(contentsIdx);
			
			for(String j : routeList) {
				//서버에서 이미지파일 삭제
				String path = filePath + j;
				File file = new File(path);
				if(file.exists() == true) {
					file.delete();
				}
			}
			//디비에서 컨텐츠 삭제 : 컨텐츠에서 삭제하면 이를 참조하던 tip_imgs테이블의 이미지 파일도 delete cascade
			result += digital_modifyRepository.delete_contents(contentsIdx);
		}
		return result;
	}
	
	/**
	 * 컨텐츠명 변경 (메일_템플릿 기능, 메일_서명기능..)
	 * 
	 * @param idx : 변경할 컨텐츠 index
	 * @param comments : 변경할 내용
	 * 
	 * @return DB에서 데이터를 변경한 결과
	 * 
	 * @author 김예린
	 */
	@Transactional
	public int updateContentsName4Digital(int idx, String comments) {
		int result = digital_modifyRepository.updateContentsName4Digital(idx, comments);
		return result;
	}
}
