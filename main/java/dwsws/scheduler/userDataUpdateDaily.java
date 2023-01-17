package dwsws.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import dwsws.controller.userDataUpdateManager;

@Component
@EnableScheduling
public class userDataUpdateDaily {
	
	@Autowired
	userDataUpdateManager userDataUpdateController;
	
	@Scheduled(cron = "00 00 06 * * MON-FRI")
	public void userInfoSyncScheduler() {
		userDataUpdateController.userInfoDBSync();
	}
}
