package dwsws.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dwsws.outputdata.contentsInfoDO;
import dwsws.outputdata.sectionListforEdit;
import dwsws.service.contentsGuideService;

/**
 * admin - 컨텐츠관리 - 가이드관리 - 스마트워크Rule을 제외한 모든 컨텐츠 관련 컨트롤러 ex) Lineworks, Zoom, BearDoc..
 * 
 * - ContentsTool 메소드 : 컨텐츠(Lineworks, Zoom)관련 기존 데이터를 리턴하는 메소드
 * - submitSet 메소드 : 데이터 수정 후 제출버튼을 눌렀을 경우 실해오디는 함수. 백엔드로 데이터 넘겨줘서 저장
 * 
 * @Autowired
	contentsGuideService contentsGuideService;//컨텐츠수정(tool)
	
 * 
 * @author 김예린
 *
 */
@Controller
public class tool_modifyController {

	
	@Autowired
	contentsGuideService contentsGuideService;//컨텐츠수정(tool)
	
	/**
	 * 컨텐츠(Lineworks, Zoom)관련 기존 데이터를 리턴하는 메소드
	 * 
	 * @PostMapping(path = "/contentsTool", produces = "application/text; charset=utf8")
	   @ResponseBody
	 * 
	 * @param tool : 컨텐츠명 ex) Lineworks, Zoom..
	 * 		
	 * @return 파라미터로 받은 tool과 관련된 기존 데이터를 map에 담고 string 형식으로 변환해서 리턴
	 * 
	 * @throws JsonProcessingException
	 * 
	 * @author 김예린
	 */
	@PostMapping(path = "/contentsTool", produces = "application/text; charset=utf8")
	@ResponseBody
	public String ContentsTool(@RequestBody String tool) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		
		ObjectMapper mapper = new ObjectMapper();
	
		// tool, comments, youtubeurl, section(contents 테이블 관련)
		contentsInfoDO contentsInfo = contentsGuideService.selectContentsInfo(tool);
		map.put("contentsInfo", contentsInfo);
		
		//section list
		List<sectionListforEdit> sectionList = contentsGuideService.selectSectionListforEdit(tool);
		map.put("sectionList", sectionList);

		
		//bookmark list
		List<Object> bmkList = contentsGuideService.selectBookmarkList(tool);
		map.put("bmkList", bmkList);
		
		String json_map = mapper.writeValueAsString(map);
		
		return json_map;
	}

	/**
	 * 제출버튼 눌렀을 경우 프론트에서 변경된 데이터를 map에 담아서 전송
	 * Ajax로 데이터를 받음
	 * 
	 * 받은 map데이터를 newmap으로 받아서 사용
	 * key를 이용해서 경우에 따라 다른 메소드 적용
	 * 
	 * 새로운 섹션에 대한 북마크 추가는 섹션이 추가된 이후에 삽입되어야 하기 때문에 keys2를 생성하여 분리
	 * 
	 * 
	 * @param map : 변경할 데이터
	 * @param session
	 * 
	 * @author 김예린
	 */
	
	@PostMapping(path = "/submitSet")
	@ResponseBody
	public void submitSet(@RequestBody HashMap map, HttpSession session) {
		HashMap newmap = map;
		
		String userindex = session.getAttribute("userindex").toString(); //session에서 userindex가져오기
	
		//tool 이름 저장
		String tool = (String)((HashMap)newmap.get("innerdata")).get("tool");
		//카테고리(도구가이드/보안)
		String category =  (String)((HashMap)newmap.get("innerdata")).get("category");
	    contentsGuideService.updateCategory(tool, category,userindex);
		
		
		Iterator<String> keys = newmap.keySet().iterator();
		
		while(keys.hasNext()) {
			String key = keys.next();
			if(key.equals("desc")) {//설명이 수정된 경우
				
				String comments = (String) newmap.get("desc");
				contentsGuideService.updateComments(tool, comments,userindex);
				
				
			}else if(key.equals("url")) {//url이 수정된 경우
				
				String url = (String) newmap.get("url");
				contentsGuideService.updateUrl(tool, url,userindex);
				
				
			}else if(key.equals("url2")) {//url2가 수정된 경우
				
				String url2 = (String) newmap.get("url2");
				contentsGuideService.updateUrl2(tool, url2,userindex);
				
				
			}else if(key.equals("deleteBmk")) {
				//삭제할 북마크 리스트
				ArrayList<Integer> deletebmk = (ArrayList) newmap.get("deleteBmk");
				for(Integer i : deletebmk) {
					contentsGuideService.deleteBmk(i);
					
				}
				
			}else if(key.contains("section")) {//section update
				
				contentsGuideService.updateSection(((HashMap)newmap.get(key)).get("comments").toString(),Integer.parseInt((String) (((HashMap)newmap.get(key)).get("secIdx"))) ,userindex);
				
				
			}else if(key.contains("bmk")) {//북마크 업데이트

				contentsGuideService.updatebookmark((int) (((HashMap)newmap.get(key)).get("timemoment")), ((HashMap)newmap.get(key)).get("comments").toString(), userindex, Integer.parseInt((String) ((HashMap)newmap.get(key)).get("bmkidx")));
				
				
			}else if(key.contains("newbookmark")) {//기존 section에 북마크 추가
				
				contentsGuideService.insertbookmark((int) (((HashMap)newmap.get(key)).get("timemoment")), ((HashMap)newmap.get(key)).get("bmk").toString(), Integer.parseInt((String) ((HashMap)newmap.get(key)).get("secidx")), userindex);
				
				
			}else if(key.contains("newSection")) {//새로운 section 추가
				
				contentsGuideService.insertSection(newmap.get(key).toString(), tool, userindex);
				
			}else if(key.equals("deleteSection")) {
				//삭제할 섹션 리스트
				ArrayList<Integer> deleteSection = (ArrayList) newmap.get("deleteSection");
				for(Integer i : deleteSection) {
					contentsGuideService.deleteSection(i);
					
				}
			}
		}

		//새로운 섹션에 대한 북마크 추가는 섹션이 추가된 이후에 삽입되어야 하기 때문에 keys2를 생성하여 분리
		Iterator<String> keys2 = newmap.keySet().iterator();
		while(keys2.hasNext()) {
			String key = keys2.next();
			if(key.contains("newBmk4newSec")) {
				
				contentsGuideService.insertNewSectionNewBookmark((int) (((HashMap)newmap.get(key)).get("timemoment")),  ((HashMap)newmap.get(key)).get("bmk").toString(),  ((HashMap)newmap.get(key)).get("section").toString(), userindex);
				
				
			}
		}
	
		
	}
}
