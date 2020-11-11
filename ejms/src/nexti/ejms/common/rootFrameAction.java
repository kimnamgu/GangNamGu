/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: Frame ���� �� ����üũ action
 * ����:
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
	//Frame ȭ���� �θ� Ŭ������ ����
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
			logger.debug("Session�� ����Ǿ����ϴ�.");
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
						//�ý��� �����ڰ� �ƴϸ� forwarding						
						logger.info("�ý��� ������ Page�� ������ ���� ����ڰ� ���ٽõ� , �����ID["+user_id+"]");
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
