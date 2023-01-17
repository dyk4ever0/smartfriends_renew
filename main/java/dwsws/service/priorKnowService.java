package dwsws.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dwsws.outputdata.GuideinfoDO;
import dwsws.repository.priorKnowRepository;

/**
 * 가이드페이지 - 보안관련 서비스
 * 
 * - selectPriorKnowList : 보안관련 컨텐츠 리스트 - BeaDoc..
 * - selectGuideInfo : 특정 컨텐츠의 tool, comments, youtubeurl 정보
 * - selectSectionList : 특정 컨텐츠의 섹션 리스트
 * - selectSectionListNum : 특정 컨텐츠의 섹션 갯수
 * - selectBookmarkList : 특정 컨텐츠의 북마크 리스트
 * - selectUrl2 : 두번째 영상 URL
 * 
 * @Autowired
	priorKnowRepository priorKnowRepository;
	
 * @author 김에린
 *
 */
@Service
public class priorKnowService {
	@Autowired
	priorKnowRepository priorKnowRepository;
	
	/**
	 * 보안관련 컨텐츠 리스트 - BeaDoc..
	 * 
	 * @return 보안관련 컨텐츠 리스트
	 */
	@Transactional
	public List<String> selectPriorKnowList(){
		List<String> list = priorKnowRepository.selectPriorKnowList();
		return list;
	}
	
	/**
	 * 특정 컨텐츠의 tool, comments, youtubeurl 정보
	 * 
	 * @param tool : 컨텐츠명(BearDoc..)
	 * @return 특정 컨텐츠의 tool, comments, youtubeurl 정보
	 */
	@Transactional
	public GuideinfoDO selectGuideInfo(String tool) {
		GuideinfoDO guideInfo = priorKnowRepository.selectGuideInfo(tool);
		return guideInfo;
	}
	
	/**
	 * 특정 컨텐츠의 섹션 리스트
	 * 
	 * @param tool: 컨텐츠명(BearDoc..)
	 * @return 특정 컨텐츠의 섹션 리스트
	 */
	@Transactional
	public List<String> selectSectionList(String tool){
		List<String> sectionList = priorKnowRepository.selectSectionList(tool);
		return sectionList;
	}
	
	/**
	 * 특정 컨텐츠의 섹션 갯수
	 * 
	 * @param tool: 컨텐츠명(BearDoc..)
	 * @return 특정 컨텐츠의 섹션 갯수
	 */
	@Transactional
	public int selectSectionListNum(String tool) {//section list num
		int num = priorKnowRepository.selectSectionListNum(tool);
		return num;
	}
	
	/**
	 * 특정 컨텐츠의 북마크 리스트
	 * 
	 * 특정 컨텐츠의 섹션리스트 정보르 가지고 와서 섹션 리스트의 for문을 돌면서 섹션별 북마크 정보를 얻음
	 * 
	 * @param tool: 컨텐츠명(BearDoc..)
	 * @return 특정 컨텐츠의 북마크 리스트
	 * @throws JsonProcessingException
	 */
	@Transactional
	public List<String> selectBookmarkList(String tool) throws JsonProcessingException{//bookmark list
		List<String> arrayList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		//특정 컨텐츠의 섹션리스트 정보르 가지고 옴
		List<String> sectionList = priorKnowRepository.selectSectionList(tool);
		
		//특정 컨텐츠의 섹션 갯수
		int num = priorKnowRepository.selectSectionListNum(tool);
		for(int i=0; i<num; i++) {
			//for문을 돌면서 섹션별 북마크 정보를 얻음
			arrayList.add(mapper.writeValueAsString(priorKnowRepository.selectBookmarkList(sectionList.get(i), tool)));
		}
		
		return arrayList;
	}
	
	/**
	 * 두번째 영상 URL
	 * 자동화를 위해 파라미터로 컨텐츠명을 받지만, 베어독에서만 사용중
	 * 
	 * @param tool: 컨텐츠명(BearDoc..)
	 * @return
	 */
	@Transactional
	public String selectUrl2(String tool) {
		String url = priorKnowRepository.selectUrl2(tool);
		
		return url;
	}
	
}
