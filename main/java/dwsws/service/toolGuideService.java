package dwsws.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dwsws.outputdata.GuideinfoDO;
import dwsws.repository.toolGuideRepository;

/**
 * 가이드페이지 - 도구가이드 관련 서비스
 * 
 * - selectGuideInfo : 특정 컨텐츠의 tool, comments, youtubeurl 정보
 * - selectSectionList : 특정 컨텐츠의 섹션 리스트
 * - selectSectionListNum : 특정 컨텐츠의 섹션 갯수
 * - selectBookmarkList : 특정 컨텐츠의 북마크 리스트
 * - selectToolguideList : 도구가이드에 하는 컨텐츠 리스트
 * 
 * @Autowired
	toolGuideRepository toolGuideRepository;
 * 
 * @author 김예린
 *
 */
@Service
public class toolGuideService {
	@Autowired
	toolGuideRepository toolGuideRepository;
	
	/**
	 * 특정 컨텐츠의 tool, comments, youtubeurl 정보
	 * 
	 * @param tool : 컨텐츠명(Zoom..)
	 * @return 특정 컨텐츠의 tool, comments, youtubeurl 정보
	 */
	@Transactional
	public GuideinfoDO selectGuideInfo(String tool) {
		GuideinfoDO guideInfo = toolGuideRepository.selectGuideInfo(tool);
		return guideInfo;
	}
	/**
	 * 특정 컨텐츠의 섹션 리스트
	 * @param tool: 컨텐츠명(Zoom..)
	 * @return 특정 컨텐츠의 섹션 리스트
	 */
	@Transactional
	public List<String> selectSectionList(String tool){
		List<String> sectionList = toolGuideRepository.selectSectionList(tool);
		return sectionList;
	}
	/**
	 *  특정 컨텐츠의 섹션 갯수
	 * @param tool: 컨텐츠명(Zoom..)
	 * @return 특정 컨텐츠의 섹션 갯수
	 */
	@Transactional
	public int selectSectionListNum(String tool) {//section list num
		int num = toolGuideRepository.selectSectionListNum(tool);
		return num;
	}
	/**
	 * 특정 컨텐츠의 북마크 리스트
	 * 
	 * 특정 컨텐츠의 섹션리스트 정보르 가지고 와서 섹션 리스트의 for문을 돌면서 섹션별 북마크 정보를 얻음
	 * 
	 * @param tool: 컨텐츠명(Zoom..)
	 * @return 특정 컨텐츠의 북마크 리스트
	 * @throws JsonProcessingException
	 */
	@Transactional
	public List<String> selectBookmarkList(String tool) throws JsonProcessingException{
		List<String> arrayList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		//특정 컨텐츠의 섹션리스트 정보르 가지고 옴
		List<String> sectionList = toolGuideRepository.selectSectionList(tool);
		
		//특정 컨텐츠의 섹션 갯수
		int num = toolGuideRepository.selectSectionListNum(tool);
		for(int i=0; i<num; i++) {
			//for문을 돌면서 섹션별 북마크 정보를 얻음
			arrayList.add(mapper.writeValueAsString(toolGuideRepository.selectBookmarkList(sectionList.get(i), tool)));
		}
		
		return arrayList;
	}
	/**
	 * 도구가이드에 하는 컨텐츠 리스트
	 * @return 도구가이드에 하는 컨텐츠 리스트
	 */
	@Transactional
	public List<String> selectToolguideList(){
		List<String> list = toolGuideRepository.selectToolguideList();
		return list;
	}
}
