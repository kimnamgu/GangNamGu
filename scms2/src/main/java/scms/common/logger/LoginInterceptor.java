package scms.common.logger;

import java.io.PrintWriter;

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
			
			//scms
			if(request.getSession().getAttribute("userinfo") == null )
			{
				
				//response.sendRedirect("/scms/");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				
				out.println("<script language='javascript'>"); 
				out.println("parent.window.location.href=\"/scms/\""); 
				out.println("</script>");
			   
			   return false;
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