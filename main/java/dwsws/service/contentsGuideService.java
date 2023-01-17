package dwsws.service;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dwsws.outputdata.contentsInfoDO;
import dwsws.outputdata.sectionListforEdit;
import dwsws.repository.contentsGuideRepository;
import dwsws.repository.toolGuideRepository;

/**
 *  admin - 컨텐츠관리 - 가이드관리 - 스마트워크Rule을 제외한 모든 컨텐츠 관련 서비스 ex) Lineworks, Zoom, BearDoc..
 * 
 * - selectContentsInfo 메소드 : contents 관련 기본정보 - 설명, 카테고리, url..
 * - updateComments : update comments
 * - updateUrl : update youtubeurl
 * - updateUrl2 : update youtubeurl2
 * - updateCategory : update category
 * - selectSectionListforEdit : select section List for edit containing sectionIdx
 * - deleteBmk : delete bookmark
 * - updateSection : update section
 * - updatebookmark : update bookmark
 * - insertbookmark : insert bookmark
 * - insertSection : insert section
 * - insertNewSectionNewBookmark : insert newbookmark of new section
 * - deleteSection : delete section
 * - selectBookmarkList : bookmark list
 * 
 * @Autowired
	contentsGuideRepository contentsGuideRepository;
	@Autowired
	toolGuideRepository toolGuideRepository;
 * 
 * @author 김예린
 *
 */
@Service
public class contentsGuideService {
	
	@Autowired
	contentsGuideRepository contentsGuideRepository;
	@Autowired
	toolGuideRepository toolGuideRepository;					
	
	/**
	 * contents 관련 기본정보 - 설명, 카테고리, url..
	 * 
	 * @param tool : contents명(Zoom, Lineworks..)
	 * @return contents 관련 기본정보 
	 */
	@Transactional
	public contentsInfoDO selectContentsInfo(String tool) {
		contentsInfoDO info = contentsGuideRepository.selectContentsInfo(tool);
		return info;
	}
	
	/**
	 * update comments
	 * 
	 * @param tool : 컨텐츠명(Zoom, Lineworks..)
	 * @param comments : 변경할 comments 내용
	 * @param userindex
	 * @return update 결과
	 */
	@Transactional
	public int updateComments(String tool, String comments, String userindex) {
		int result = contentsGuideRepository.updateComments(tool, comments, userindex);
		return result;
	}
	
	/**
	 * update youtubeurl on contents table
	 * 
	 * @param tool : 컨텐츠명(Zoom, Lineworks..)
	 * @param url : 변경할 youtube url
	 * @param userindex
	 * @return update 결과
	 */
	@Transactional
	public int updateUrl(String tool, String url, String userindex) {
		int result = contentsGuideRepository.updateUrl(tool, url, userindex);
		return result;
	}
	
	/**
	 * update youtubeurl2
	 * 베어독의 part2 영상 url을 바꾸는 경우
	 * 
	 * @param tool : 컨텐츠명(Zoom, Lineworks..)
	 * @param url2 : 변경할 youtube url2
	 * @param userindex
	 * @return update 결과
	 */
	@Transactional
	public int updateUrl2(String tool, String url2, String userindex) {
		int result = contentsGuideRepository.updateUrl2(tool, url2, userindex);
		return result;
	}
	
	/**
	 * update category
	 * 
	 * @param tool : 컨텐츠명(Zoom, Lineworks..)
	 * @param category : 변경할 카데고리(도구가이드, 보안)
	 * @param userindex
	 * @return update 결과
	 */
	@Transactional
	public int updateCategory(String tool, String category, String userindex) {
		int result = contentsGuideRepository.updateCategory(tool, category, userindex);
		return result;
	}
	
	/**
	 * select section List for edit containing sectionIdx
	 * 
	 * @param tool : 컨텐츠명(Zoom, Lineworks..)
	 * @return 특정 컨텐츠와 관련된 섹션 리스트
	 */
	@Transactional
	public List<sectionListforEdit> selectSectionListforEdit(String tool){
		List<sectionListforEdit> list = contentsGuideRepository.selectSectionListforEdit(tool);
		return list;
	}
	
	/**
	 * delete bookmark
	 * 
	 * @param idx : 삭제할 북마크 idx
	 * @return delete 결과
	 */
	@Transactional
	public int deleteBmk(int idx) {
		int result = contentsGuideRepository.deleteBmk(idx);
		return result;
	}
	
	/**
	 * update section
	 * 
	 * @param comments : 변경할 section 내용
	 * @param idx : section idx
	 * @param userindex
	 * @return
	 */
	@Transactional
	public int updateSection(String comments, int idx, String userindex) {
		int result = contentsGuideRepository.updateSection(comments, idx, userindex);
		return result;
	}
	
	/**
	 * update bookmark
	 * 
	 * @param timemoment : 시간
	 * @param comments : 북마크 comments
	 * @param userindex
	 * @param bmkidx : 북마크 idx
	 * @return
	 */
	@Transactional
	public int updatebookmark(int timemoment, String comments, String userindex, int bmkidx) {
		int result = contentsGuideRepository.updatebookmark(timemoment, comments, userindex, bmkidx);
		return result;
	}
	
	/**
	 * insert bookmark
	 * 
	 * @param timemoment: 시간
	 * @param comments : 북마크 comments
	 * @param section : 참조하고 있는 section
	 * @param userindex
	 * @return
	 */
	@Transactional
	public int insertbookmark(int timemoment, String comments, int section, String userindex) {
		int result = contentsGuideRepository.insertbookmark(timemoment, comments, section, userindex);
		return result;
	}
	
	/**
	 * insert section
	 * 
	 * @param comments : section comments
	 * @param tool : section테이블의 contents를 알기위해 tool명으로 idx 찾음
	 * @param userindex
	 * @return
	 */
	@Transactional
	public int insertSection(String comments, String tool, String userindex) {
		int result = contentsGuideRepository.insertSection(comments, tool, userindex);
		return result;
	}
	
	/**
	 * insert newbookmark of new section
	 * 새로 추가한 섹션에 새로운 북마크를 추가하는 경우
	 * 
	 * @param timemoment : 시간
	 * @param comments : 북마크 comments
	 * @param section : 참조하고 있는 section
	 * @param userindex
	 * @return
	 */
	@Transactional//insert newbookmark of new section
	public int insertNewSectionNewBookmark(int timemoment, String comments, String section, String userindex) {
		int result = contentsGuideRepository.insertNewSectionNewBookmark(timemoment, comments, section, userindex);
		return result;
	}
	
	/**
	 * delete section
	 * 
	 * @param idx : 삭제할 섹션 idx
	 * @return
	 */
	@Transactional
	public int deleteSection(int idx) {
		int result = contentsGuideRepository.deleteSection(idx);
		return result;
	}
	
	/**
	 * bookmark list
	 * 특정 컨텐츠의 섹션별 북마크 리스트
	 * 
	 * @param tool : 컨텐츠명(Zoom, Lineworks..)
	 * @return 특정 컨텐츠의 섹션별 북마크 리스트
	 * @throws JsonProcessingException
	 */
	@Transactional//bookmark list
	public List<Object> selectBookmarkList(String tool) throws JsonProcessingException{
		List<Object> arrayList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		List<String> sectionList = toolGuideRepository.selectSectionList(tool);
		
		
		int num = toolGuideRepository.selectSectionListNum(tool);
		for(int i=0; i<num; i++) {
			arrayList.add(toolGuideRepository.selectBookmarkList(sectionList.get(i), tool));
		}
		
		return arrayList;
	}
}
