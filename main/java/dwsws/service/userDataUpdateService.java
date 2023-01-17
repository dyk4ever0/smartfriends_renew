package dwsws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwsws.dto.userInfoDTO;
import dwsws.repository.userDataUpdateRepository;

/**
 * system - 베어월드 인사정보 연동 구현 시스템
 * 
 * - insertUserData : 베어월드 API 서버에서 반환된 인사 정보를 DB에 저장함 
 * 
 * @Autowired
	userDataUpdateRepository dataRepository;
	
 * @author 박승수 
 *
 */
@Service
public class userDataUpdateService {

	@Autowired
	userDataUpdateRepository dataRepository;

	/**
	 * 베어월드 API 서버에서 반환된 인사 정보를 DB에 저장함 
	 * 
	 * @param userList 베어월드 API 서버에서 반환한 사용자 목록 
	 * @return String 저장 및 삭제와 관련된 정보 출력 
	 * 
	 * @author 박승수 
	 */
	@Transactional
	public String insertUserData(List<userInfoDTO> userList) {
		int flag = 0;
		int userdeletedLength = dataRepository.checkUserInfoNull();
		int favoritedeletedLength = dataRepository.checkFavoriteNull();
		try {
			// for full update of user_info table, flush all current data.
			dataRepository.backupFavoriteData();
			System.out.println("back up favorite data done");
			dataRepository.deleteAllUserData();
			System.out.println("delete All user data done");

			// after flush, refill to new data from Groupware API server.
			if (dataRepository.checkUserInfoNull() == 0) {
				for (userInfoDTO user : userList) {
					dataRepository.insertUserData(user);
					System.out.println("one user data inserted : " + (++flag) + "th insert");
				}
				dataRepository.restoreFavoriteData();
				System.out.println("restored favorite data");
			}
		} catch (Exception e) {
			System.out.println("Error occured in user update service");
			e.printStackTrace();
		}

		// returns length of deleted and inserted data from user_info table
		int insertuserLength = dataRepository.checkUserInfoNull();
		int favoriteinsertLength = dataRepository.checkFavoriteNull();
		String result = new String("Deleted : " + userdeletedLength + " , Inserted : " + insertuserLength
				+ "\n deleted favorite : " + favoritedeletedLength + " , inserted favorite : " + favoriteinsertLength);
		return result;
	}

}
