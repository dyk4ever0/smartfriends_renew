package dwsws.controller;

import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import dwsws.service.addContentsService;
import dwsws.service.contentsGuideService;

/**
 * admin - 컨텐츠관리 - 가이드관리 탭 관련 컨트롤러(contents-lnb 관련)
 * admin - 컨텐츠관리 - 디지털창 탭 관련 컨트롤러(contents-lnb 관련)
 * 
 * - submitSet 메소드 : contents-lnb에서 +버튼을 클릭하고 새로운 컨텐츠(Zoom, lineworks..와 같은 레벨)를 추가한 뒤 제출버튼 클릭 시 실행되는 함수(가이드관리 탭)
 * - submitSet2 메소드 : contents-lnb에서 -버튼을 클릭하고 컨텐츠(Zoom, lineworks..와 같은 레벨)를 삭제하는 경우(가이드관리 탭)
 * - submitSet4Digital : contents-lnb에서 -버튼을 클릭하고 컨텐츠(베어월드, 간편도구..와 같은 레벨)를 삭제하는 경우(디지털창 탭)
 * 
 * @Autowired
	addContentsService addContentsService; // 컨텐츠 추가 관련 service
	@Autowired
	contentsGuideService contentsGuideService; // 섹션, 북마크 추가 관련 service
	
 * 
 * @author 김예린
 *
 */
@Controller
public class add_contentsController {

	@Autowired
	addContentsService addContentsService; // 컨텐츠 추가 관련 service
	@Autowired
	contentsGuideService contentsGuideService; // 섹션, 북마크 추가 관련 service
	
	/**
	 * 제출버튼 눌렀을 경우 프론트에서 변경된 데이터를 map에 담아서 전송
	 * contents-lnb에서 +버튼을 클릭하고 새로운 컨텐츠(Zoom, lineworks..와 같은 레벨)를 추가한 뒤 제출버튼 클릭 시 실행되는 함수(가이드관리 탭)
	 * 
	 * 받은 map데이터를 newmap으로 받아서 사용
	 * key를 이용해서 경우에 따라 다른 메소드 적용
	 * 
	 * 새로운 섹션에 대한 북마크 추가는 섹션이 추가된 이후에 삽입되어야 하기 때문에 keys2를 생성하여 분리
	 * 
	 * @PostMapping(path = "/submitSet4add")
	   @ResponseBody
	   
	   
	 * 
	 * @param map : 변경할 데이터
	 * @param session
	 * 
	 * @author 김예린
	 */
		@PostMapping(path = "/submitSet4add")
		@ResponseBody
		public void submitSet(@RequestBody HashMap map, HttpSession session) {
			HashMap newmap = map;
			
			String userindex = session.getAttribute("userindex").toString(); //session에서 userindex가져오기
		
			//tool 이름 저장
			String tool = (String)((HashMap)newmap.get("innerdata")).get("tool");
			//카테고리(도구가이드/보안)
			String category =  (String)((HashMap)newmap.get("innerdata")).get("category");
			//설명
			String comments = (String) newmap.get("desc");
			//url
			String url = (String) newmap.get("url");
			
			//contents 테이블에 새로운 컨텐츠 추가 
			addContentsService.insertNewContents(tool, comments, url, category, userindex);
			
			Iterator<String> keys = newmap.keySet().iterator();
			
			while(keys.hasNext()) {
				String key = keys.next();
				  if(key.contains("newSection")) {//새로운 section 추가
					
					contentsGuideService.insertSection(newmap.get(key).toString(), tool, userindex);
					
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
		
		/**
		 * contents-lnb에서 -버튼을 클릭하고 컨텐츠(Zoom, lineworks..와 같은 레벨)를 삭제하는 경우(가이드관리 탭)
		 * 
		 * @PostMapping(path = "/deleteContents")
		   @ResponseBody
		 * 
		 * @param contents : 삭제할 컨텐츠명
		 * 
		 * @author 김예린
		 */
		@PostMapping(path = "/deleteContents")
		@ResponseBody
		public void submitSet2(@RequestBody String contents) {
			addContentsService.deleteContents(contents);
			
		}
		
		/**
		 * contents-lnb에서 -버튼을 클릭하고 컨텐츠(베어월드, 간편도구..와 같은 레벨)를 삭제하는 경우(디지털창 탭)
		 * 
		 * @PostMapping(path = "/deleteContents4Digital")
		   @ResponseBody
		 * 
		 * @param contents : 삭제할 컨텐츠명
		 * 
		 * @author 김예린
		 */
		@PostMapping(path = "/deleteContents4Digital")
		@ResponseBody
		public void submitSet4Digital(@RequestBody String contents) {
			addContentsService.deleteContents4Digital(contents);
			
		}
}
