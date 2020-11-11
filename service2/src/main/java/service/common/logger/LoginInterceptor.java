package service.common.logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class LoginInterceptor extends HandlerInterceptorAdapter{
	Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try {
			
			if (log.isDebugEnabled()) {
				log.debug("======================================          START         ======================================");
				log.debug(" Request URI \t:  " + request.getRequestURI());
			}
			
			// URL 파싱을 통해서 민원서류 로그인과 스마트 방문처리 로그인 분리
			String str = request.getRequestURI();
					
			String[] array = str.split("/");
			
			System.out.println("array[2] 확인 : " + array[2]);
			
			switch(array[2]) {
		    case "visitor":
				if(request.getSession().getAttribute("userinfo") == null )
				{
						
				   response.sendRedirect("/service/visitor/visitorLogin.do");
				   
				   return false;
				}
		         break;
		    case "iljali":
				if(request.getSession().getAttribute("userinfo") == null )
				{
				   response.sendRedirect("/service/iljali/iljaliLogin.do");
				   
				   return false;
				}
		    	
		         break;
		    case "welfare":
		    	if(request.getSession().getAttribute("userinfo") == null )
				{
				   response.sendRedirect("/service/welfare/welfareLoginList.do");
				   
				   return false;
				}
		    	
		         break;
		    default: 
				if(request.getSession().getAttribute("userinfo") == null )
				{
						
				   response.sendRedirect("/service/");
				   
				   return false;
				}
		         break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
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
		if (log.isDebugEnabled()) {
			log.debug("[Async aftcmpl]======================================           END          ======================================\n");
		}
	}

}