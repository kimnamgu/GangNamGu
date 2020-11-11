/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: Frame 세션 및 권한체크 action
 * 설명:
 */
package nexti.ejms.common;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class rootFrameAction extends Action  {
	//Frame 화면의 부모 클래스로 쓸것
	private static Logger logger = Logger.getLogger(rootFrameAction.class);
	
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
			PrintWriter out = response.getWriter();			
			out.write("<script language=javascript>parent.location.href='/';</script>");	
			out.close();
			return null;
		} else {		
			String[] roles = mapping.getRoleNames();
			String user_id = (String)session.getAttribute("user_id");
			
			for(int i=0;i<roles.length;i++){
				if(roles[i].equals("admin")){
					if(!"001".equals(session.getAttribute("isSysMgr"))){					
						//시스템 관리자가 아니면 forwarding						
						logger.info("시스템 관리자 Page에 권한이 없는 사용자가 접근시도 , 사용자ID["+user_id+"]");
						PrintWriter out = response.getWriter();			
						out.write("<script language=javascript>window.close();</script>");
						out.close();
						return null;
					}
				} 				
			}
			
			try{
				forward=doService(mapping, form, request, response);
			}catch(Exception e){
				request.setAttribute("exception",e); 
				request.getRequestDispatcher("/common/frameerror.jsp").forward(request, response); 
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
