package dwsws.controller;

import java.util.List;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dwsws.outputdata.UserFavoriteList;
import dwsws.service.commonService;

/**
 * header의 즐겨찾기 버튼관련 컨트롤러
 * 
 * - favorite 메소드 : 사용자별 즐겨찾기 목록 호출
 * - favoriteNum : 사용자별 즐겨찾기 목록 갯수 호출
 * - insertFavorite : 즐겨찾기 목록 추가
 * - deleteFavorite : 즐겨찾기 목록 삭제
 * 
 * @Autowired
	commonService commonService;
 * 
 * @author 김예린
 *
 */
@Controller
public class favoriteController {

	@Autowired
	commonService commonService;
	
	/**
	 * 사용자별 즐겨찾기 목록 호출
	 * session에서 userindex를 가져와서 사용자 구분
	 * 
	 * @PostMapping(path = "/favorite",  produces = "application/text; charset=utf8")
	   @ResponseBody
	 * 
	 * @param session
	 * @return json_favoritelist : 사용자별 즐겨찾기 목록
	 * 
	 * @throws JsonProcessingException
	 * 
	 * @author 김예린
	 */
	@PostMapping(path = "/favorite",  produces = "application/text; charset=utf8")
	@ResponseBody
	public Object favorite(HttpSession session) throws JsonProcessingException {
		String userindex = session.getAttribute("userindex").toString(); //session에서 userindex가져오기
		List<UserFavoriteList> favoritelist = commonService.selectUserFavoriteList(userindex); //즐겨찾기 하고싶은 사람의 userindex입력하여 즐겨찾기 리스트를 받음
		
		//데이터를 json형태로 변환
		ObjectMapper mapper = new ObjectMapper();
		String json_favoritelist = mapper.writeValueAsString(favoritelist);
		
		return json_favoritelist;
	}
	
	/**
	 * 사용자별 즐겨찾기 목록 갯수 호출
	 * session에서 userindex를 가져와서 사용자 구분
	 * 
	 * @PostMapping(path = "/favoriteNum")
	   @ResponseBody
	 * 
	 * @param session
	 * 
	 * @return num : 사용자별 즐겨찾기 목록 갯수 리턴
	 * 
	 * @throws JsonProcessingException
	 * 
	 * @author 김예린
	 */
	@PostMapping(path = "/favoriteNum")
	@ResponseBody
	public Object favoriteNum(HttpSession session) throws JsonProcessingException {
		String userindex = session.getAttribute("userindex").toString(); //session에서 userindex가져오기
		int num = commonService.selectNum(userindex); //즐겨찾기 하고싶은 사람의 userindex입력하여 즐겨찾기 리스트를 받음

		return num;
	}
	
	/**
	 * 즐겨찾기 목록 추가
	 * session에서 userindex를 가져와서 사용자 구분
	 * 
	 * @PostMapping(path = "/insertFavorite")
	   @ResponseBody
	 * 
	 * @param param : 즐겨찾기에 추가할 북마크 index
	 * @param session
	 * 
	 * @author 김예린
	 */
	@PostMapping(path = "/insertFavorite")
	@ResponseBody
	public void insertFavorite(@RequestParam Map<String, Object> param, HttpSession session) {
		String userindex = session.getAttribute("userindex").toString(); //session에서 userindex가져오기
		int num = Integer.valueOf((String) param.get("num")) ;
		commonService.insertFavorite(userindex, num);
		
	}
	
	/**
	 * 즐겨찾기 목록 삭제
	 * session에서 userindex를 가져와서 사용자 구분
	 * 
	 * @PostMapping(path = "/deleteFavorite")
	   @ResponseBody
	 * 
	 * @param param : 즐겨찾기에 삭제할 북마크 index
	 * @param session
	 * 
	 * @author 김예린
	 */
	@PostMapping(path = "/deleteFavorite")
	@ResponseBody
	public void deleteFavorite(@RequestParam Map<String, Object> param, HttpSession session) {
		String userindex = session.getAttribute("userindex").toString(); //session에서 userindex가져오기
		int num = Integer.valueOf((String) param.get("num")) ;
	    commonService.deleteFavorite(userindex, num);
		
	}
}
