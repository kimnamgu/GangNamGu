package sosong.common.logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.apache.log4j.Logger;


public class LoginInterceptor extends HandlerInterceptorAdapter{
	Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try {
			
			if (log.isDebugEnabled()) {
				log.debug("======================================          START         ======================================");
				log.debug(" Request URI \t:  " + request.getRequestURI());
			}
			
			
			//����key�� ���� ������ ���ϰ�� �α����������� �̵�
			if(request.getSession().getAttribute("userinfo") == null && !request.getRequestURI().equals("/sosong/common/login.do") && 
			  !request.getRequestURI().equals("/sosong/common/ssoLogin.do") && !request.getRequestURI().equals("/sosong/common/insertUserinfo.do") &&
			  !request.getRequestURI().equals("/sosong/LoginMsgOut.do") && !request.getRequestURI().equals("/sosong/gofirst.do")  &&
			  !request.getRequestURI().equals("/sosong/alrimService.do") && !request.getRequestURI().equals("/sosong/mail.do")){
					
					response.sendRedirect("/sosong/gofirst.do");	
					return false;
			}
			
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		//admin ����key ����� main ������ �̵�
		return true;
	}

	/*@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}*/
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("======================================           END          ======================================\n");
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

}