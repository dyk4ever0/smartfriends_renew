package dwsws.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//session 객체를 가져옴
		HttpSession session = request.getSession();
		//session에 저장되어있는 데이터 가져옴
		Object username = session.getAttribute("username");
		Object userindex = session.getAttribute("userindex");
		//get requested url so that can move to that page after login action
		String requestedUrl = request.getRequestURI().substring(7);
		if(username == null || userindex == null) { //로그인이 안되어있는 상태
			//pass requested url value into url parameter
			response.sendRedirect(request.getContextPath() + "/login" + "?url=" + requestedUrl);
			return false; //더이상 컨트롤러 요청으로 가지 않도록 false로 반환함
		}
		return true;
	}
}