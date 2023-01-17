package dwsws.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dwsws.service.digitalService;
import dwsws.service.priorKnowService;
import dwsws.service.toolGuideService;
/**
 * [모바일] 가이드페이지 상단의 select박스 관련 컨트롤러
 * 
 * - contentsList 메소드 : [모바일] 가이드페이지 상단의 select박스에서 보여줄 컨텐츠 목록 리턴
 * 
 * @Autowired
	toolGuideService toolGuideService;// 도구가이드
	@Autowired
	priorKnowService priorKnowService;// 보안
	@Autowired
	digitalService digitalService; // 디지털창
 * 
 * @author 김예린
 *
 */
@Controller
public class headerMobileController {
	@Autowired
	toolGuideService toolGuideService;// 도구가이드
	@Autowired
	priorKnowService priorKnowService;// 보안
	@Autowired
	digitalService digitalService; // 디지털창
	
	/**
	 * [모바일] 가이드페이지 상단의 select박스에서 보여줄 컨텐츠 목록 리턴
	 * 
	 * @PostMapping(path = "/ContentsList",  produces = "application/text; charset=utf8")
	   @ResponseBody
	 * 
	 * 
	 * @return json_list : [모바일] 가이드페이지 상단의 select박스에서 보여줄 컨텐츠 목록
	 * 
	 * @throws JsonProcessingException
	 * 
	 *  @author 김예린
	 */
	@PostMapping(path = "/ContentsList",  produces = "application/text; charset=utf8")
	@ResponseBody
	public Object contentsList() throws JsonProcessingException{
		// toolguide list
		List<String> toolGuideList = toolGuideService.selectToolguideList();
		ObjectMapper mapper = new ObjectMapper();
		String json_toolGuideList = mapper.writeValueAsString(toolGuideList);
		
		// priorKnow list
		List<String> priorKnowList = priorKnowService.selectPriorKnowList();
		String json_priorKnowList = mapper.writeValueAsString(priorKnowList);
		
		//digital list 
		List<String> digitalList = digitalService.selectDigitalList();
		String json_digitalList = mapper.writeValueAsString(digitalList);
		
		Map<String, Object> list = new HashMap<>();
		list.put("toolGuideList", json_toolGuideList);
		list.put("priorKnowList", json_priorKnowList);
		list.put("digitalList", json_digitalList);
		
		String json_list = mapper.writeValueAsString(list);
		return json_list;
	}
	
}
