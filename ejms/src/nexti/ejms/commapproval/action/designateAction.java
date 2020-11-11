/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���缱���� action
 * ����:
 */
package nexti.ejms.commapproval.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.commapproval.model.commapprovalManager;
import nexti.ejms.common.rootPopupAction;

public class designateAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		int isMakeing = 0;
		int sysdocno = 0;
		String type = "";
		String retid = "";
		String state = "";
		
		//�������� ��������
		HttpSession session = request.getSession();	
		String deptcd = (String)session.getAttribute("dept_code");
		String deptnm = (String)session.getAttribute("dept_name");
		
		if(request.getParameter("isMakeing") != null) {
			isMakeing = Integer.parseInt(request.getParameter("isMakeing"));
		}
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		if(request.getParameter("type") != null){
			type = (String)request.getParameter("type");
		}
		
		if(request.getParameter("retid") != null){
			retid = (String)request.getParameter("retid");
		}	
		
		commapprovalManager manager = commapprovalManager.instance();
		state = manager.getState(sysdocno, deptcd, type);
		
		request.setAttribute("state", state);
		request.setAttribute("isMakeing", new Integer (isMakeing));
		request.setAttribute("sysdocno", new Integer (sysdocno));
		request.setAttribute("deptnm", deptnm);
		request.setAttribute("type", type);
		request.setAttribute("retid", retid);
		
		return mapping.findForward("designate");

	}
}