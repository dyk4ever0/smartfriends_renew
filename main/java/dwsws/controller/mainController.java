package dwsws.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;

import dwsws.service.guideService;

@Controller
public class mainController {
	@Autowired
	guideService guideService;//스마트워크 rule
	
	// main page
	@RequestMapping(path = "/main")
	public String main(ModelMap model, HttpSession session, Device device) throws JsonProcessingException {
		//regarding to smartworkRule fullversoin video url
		String url = guideService.selectFullVersionUrl();
		model.addAttribute("fullUrl", url);
		//영상제목
		String videoTitle = guideService.selectVideoTitle();
		model.addAttribute("videoTitle", videoTitle);
		
		if(device.isMobile()) {
			return "mobile-version/main";
		}
		else {
			return "desktop-version/main";
		}
//		return "desktop-version/main";
	}
}