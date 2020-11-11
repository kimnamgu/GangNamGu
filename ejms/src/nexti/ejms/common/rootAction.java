/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���� �� ����üũ action
 * ����:
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
	//����ȭ���� �θ� Ŭ������ ����
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
			logger.debug("Session�� ����Ǿ����ϴ�.");
			return mapping.findForward("login");
		} else {
			String[] roles = mapping.getRoleNames();
			String user_id = (String)session.getAttribute("user_id");			
			
			for(int i=0;i<roles.length;i++){
				if(roles[i].equals("admin")){
					if(!"001".equals(session.getAttribute("isSysMgr"))){
						//�ý��� �����ڰ� �ƴϸ� forwarding
						logger.info("�ý��� ������ Page�� ������ ���� ����ڰ� ���ٽõ� , �����ID["+user_id+"]");
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
