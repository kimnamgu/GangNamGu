/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 세션 및 권한체크 action
 * 설명:
 */
package nexti.ejms.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class rootAction extends Action {
	//메인화면의 부모 클래스로 쓸것
	private static Logger logger = Logger.getLogger(rootAction.class);
	
	public ActionForward execute(
		ActionMapping mapping, 
		ActionForm form,
		HttpServletRequest request, 
		HttpServletResponse response)
	 	throws Exception {	

		HttpSession session = request.getSession();	
		ActionForward forward = null;
		
		if((session == null) || (session.getAttribute("user_id") == null)){
			logger.debug("Session이 종료되었습니다.");
			return mapping.findForward("login");
		} else {
			String[] roles = mapping.getRoleNames();
			String user_id = (String)session.getAttribute("user_id");			
			
			for(int i=0;i<roles.length;i++){
				if(roles[i].equals("admin")){
					if(!"001".equals(session.getAttribute("isSysMgr"))){
						//시스템 관리자가 아니면 forwarding
						logger.info("시스템 관리자 Page에 권한이 없는 사용자가 접근시도 , 사용자ID["+user_id+"]");
						return mapping.findForward("global_main");
					}
				} 
			}
			
			try{
				forward=doService(mapping, form, request, response);
			}catch(Exception e){
				request.setAttribute("exception",e); 
				request.getRequestDispatcher("/common/error.jsp").forward(request, response); 
			}
			return forward;
		}
	}
	
	abstract public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response)
			throws Exception; 
}
