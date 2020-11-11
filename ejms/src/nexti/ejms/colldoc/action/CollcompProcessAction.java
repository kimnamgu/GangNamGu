/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합완료 취합완료처리 action
 * 설명:
 */
package nexti.ejms.colldoc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.colldoc.model.ColldocManager;

public class CollcompProcessAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		String user_id = "";
		String retPage = "";
		int sysdocno = 0;
		
		//세션정보 가져오기
		HttpSession session = request.getSession();
		user_id = session.getAttribute("user_id").toString();
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		if(request.getParameter("retpage") != null) {
			retPage = request.getParameter("retpage");
		}
		
		//기안취소하기
		ColldocManager manager = ColldocManager.instance();
		manager.collCompProcess(sysdocno, user_id);
		
		request.setAttribute("sysdocno", new Integer(sysdocno));
		
		return mapping.findForward(retPage);
	}
}