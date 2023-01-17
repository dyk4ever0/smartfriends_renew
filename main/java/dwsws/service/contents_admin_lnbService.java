package dwsws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwsws.repository.contents_admin_lnb;

/**
 * [PC] admin - 컨텐츠관리 - 가이드관리 페이지의 contents-lnb 관련 서비스
 * 
 * - contentsList4admin 메소드 : contents-lnb에 삽입될 컨텐츠 목록(Zoom, Lineworks..) 리턴
 * 
 * @Autowired
	contents_admin_lnb contents_admin_lnb;
 * 
 * @author 김예린
 *
 */
@Service
public class contents_admin_lnbService {
	@Autowired
	contents_admin_lnb contents_admin_lnb;
	
	/**
	 * contents-lnb에 삽입될 컨텐츠 목록(Zoom, Lineworks..) 리턴
	 * 스마트워크룰과 디지털창 관련 컨텐츠는 제외
	 * 생성이유 : 컨텐츠관리 - 가이드관리탭 에서 컨텐츠 추가 시 컨텐츠 목록이 업데이트되기 때문
	 * 
	 * @return list : contents-lnb에 삽입될 컨텐츠 목록(Zoom, Lineworks..) 리턴
	 */
	@Transactional
	public List<String> contentsList4admin(){
		List<String> list = contents_admin_lnb.contentsList4admin();
		return list;
	}
}
