package dwsws.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



import dwsws.dto.digitalDTO2;
import dwsws.dto.imageDTO;
import dwsws.outputdata.digitalBoardDO;
import dwsws.service.digitalService;

import dwsws.service.digital_modifyService;

/**
 * admin - 컨텐츠관리 - 디지털창 관련 컨트롤러
 * 
 * - digitalLnb 메소드 : 디지털창 탭 클릭 시 lnb에 들어갈 디지털창 목록 호출
 * - digitalContents 메소드 : 클릭한 컨텐츠에 관련된 기존 데이터 정보 호출
 * - imgUpload 메소드 : 이미지 데이터를 서버와 DB에 저장하기 위한 메소드
 * - digitalModify 메소드 : 디지털창 편집페이지에서 수정완료 클릭 시 실행되는 메소드
 * 
 * @Autowired
	digitalService digitalService; // 디지털창
	@Autowired
	digital_modifyService digital_modifyService; //디지털창 수정
 * 
 * 
 * @author 김예린
 *
 */
@Controller
public class digital_modifyController {
	@Autowired
	digitalService digitalService; // 디지털창
	@Autowired
	digital_modifyService digital_modifyService; //디지털창 수정
	
	/**
	 * 디지털창 탭 클릭 시 lnb에 들어갈 디지털창 목록 호출 
	 * ex) 베아월드, 간편도구..
	 * 
	 * @PostMapping(path = "/digitalLnb")
	   @ResponseBody
	 * 
	 * @return digitalList : 디지털창 목록
	 * @throws JsonProcessingException
	 * 
	 * @author 김예린
	 */
	@PostMapping(path = "/digitalLnb")
	@ResponseBody
	public List<String> digitalLnb() throws JsonProcessingException {
		
		List<String> digitalList = digitalService.selectDigitalList();
		
		return digitalList;
	}
	
	/**
	 * 클릭한 컨텐츠에 관련된 기존 데이터 정보 호출
	 * 
	 * @PostMapping(path = "/digitalContents", produces = "application/text; charset=utf8")
	   @ResponseBody
	 * 
	 * @param tool : 클릭한 컨텐츠명 ex) 베아월드, 간편도구..
	 * 
	 * @return json_map : 클릭한 컨텐츠명과 관련된 데이터
	 * 
	 * @throws Exception
	 * 
	 * @author 김예린
	 */
	@PostMapping(path = "/digitalContents", produces = "application/text; charset=utf8")
	@ResponseBody
	public String digitalContents(@RequestBody String tool) throws Exception {
		//digital contents list 반환		
		List<digitalBoardDO> list = digitalService.digitalListAll(tool); // 제목, 컨텐츠명 ex)베아월드, 메일_템플릿
		List<digitalDTO2> digitalContentslList = new ArrayList<>(list.size());

		ObjectMapper mapper = new ObjectMapper();
		
		for(int i = 0; i < list.size(); i++) {
			List<String> digitalImgs = digitalService.digitalImgs(list.get(i).getIdx()); // 이미지 리스트
			
			digitalContentslList.add(new digitalDTO2(list.get(i).getIdx(), mapper.writeValueAsString(digitalImgs), 
					list.get(i).getTitle(), list.get(i).getComments()));
		}
		
		String json_map = mapper.writeValueAsString(digitalContentslList);//특정 컨텐츠에 대한 이미지리스트 ex)베아월드에 해당하는 모든 이미지
		return json_map;
	}
	
	/**
	 * 이미지 데이터를 서버와 DB에 저장하기 위한 메소드
	 * 이미지배열와 이미지와 관련된 컨텐츠명을 저장하는 배열을 받은 후 컨트롤러에서 이미지와 컨텐츠명을 매칭시켜준 후 service계층으로 전달
	 * 
	 * 컨텐츠명에 ,가 존재하는 경우 ,를 기준으로 spring framework에서 글자를 split하기 때문에 HttpServletRequest 이용
	 * 
	 * 이미지배열 : imgArray, 컨텐츠명 배열 : contentsArray
	 * 
	 * @PostMapping(path = "/imgUpload")
	   @ResponseBody
	 * 
	 * @param session
	 * @param imgArray : 이미지배열
	 * @param req : 컨텐츠명 정보를 담고있음
	 * 
	 * @throws IOException
	 * 
	 * @author 김예린
	 */
	@PostMapping(path = "/imgUpload")
	@ResponseBody
	public void imgUpload(HttpSession session, @RequestParam("imgArray") MultipartFile[] imgArray, HttpServletRequest req) throws IOException {
		
		String userindex = session.getAttribute("userindex").toString(); //session에서 userindex가져오기
		
		//컨텐츠명에 ,가 존재하는 경우 ,를 기준으로 spring에서 글자를 split하기 때문에 HttpServletRequest 이용
		String contentsString = req.getParameter("contentsArray");
		//컨텐츠명 여러개를 하나의 string으로 묶어서 보내주기 때문에 ",를 기준으로 split
		String[] contentsTempArray = contentsString.split("\",");
		//contents명을 저장할 배열 선언
		String[] contentsArray = new String[contentsTempArray.length];
		
		for(int i=0 ; i<contentsTempArray.length; i++) {
			//큰따옴표 제거한 후 contentsArray에 삽입
			contentsArray[i] = contentsTempArray[i].replaceAll("\"", "");
			//이미지파일과 관련 컨텐츠명을 매칭해줘서 imageDTO에 담아준다.
			imageDTO image = new imageDTO((MultipartFile) imgArray[i], contentsArray[i] );
			digital_modifyService.imgUpload(image, userindex);
		}
		
	}
	
	
	
	/**
	 * 디지털창 편집페이지에서 수정완료 클릭 시 실행되는 메소드
	 * 제출버튼 눌렀을 경우 프론트에서 변경된 데이터를 map에 담아서 전송
	 * Ajax로 데이터를 받음
	 * 
	 * 받은 map데이터를 newmap으로 받아서 사용
	 * key를 이용해서 경우에 따라 다른 메소드 적용
	 * 
	 * @PostMapping(path = "/digitalModify")
	   @ResponseBody
	 * 
	 * 
	 * @param map : 변경할 데이터
	 * @param session
	 * 
	 * @author 김예린
	 */
	@PostMapping(path = "/digitalModify")
	@ResponseBody
	public void digitalModify(@RequestBody HashMap map, HttpSession session) {
		HashMap newmap = map;
		
		String userindex = session.getAttribute("userindex").toString(); //session에서 userindex가져오기
		
		//tool 이름 저장 : 베아월드..
		String tool = (String) newmap.get("tool");
		//컨텐츠명 변경
		Iterator<String> keys = newmap.keySet().iterator();
		
		while(keys.hasNext()) {
			String key = keys.next();
			
			if(key.contains("nameChange")) {
				int idx =  (int)((HashMap)newmap.get(key)).get("idx");
				String comments = ((HashMap)newmap.get(key)).get("name").toString();
				digital_modifyService.updateContentsName4Digital(idx, comments);
			}
			
		}
		
		//새로 추가하는 컨텐츠 배열
		ArrayList<String> addContentsArr = (ArrayList) newmap.get("addContents");
		digital_modifyService.insertContents_digital(addContentsArr, tool, userindex);
		
		
		//이미지파일 삭제
		ArrayList<String> deleteImg = (ArrayList) newmap.get("deleteImg");
		digital_modifyService.delete_img(deleteImg);
		
		
		//컨텐츠 삭제
		ArrayList<String> deleteContents = (ArrayList) newmap.get("deleteContents");
		digital_modifyService.delete_contents(deleteContents);
		
	}
}
