/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����׷� �������� action
 * ����:
 */
package nexti.ejms.commapproval.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;

public class commrchgrpchoiceAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		int rchgrpno = 0;

		if(request.getParameter("rchgrpno") != null) {
			rchgrpno = Integer.parseInt(request.getParameter("rchgrpno"));
		}
		
		request.setAttribute("rchgrpno", new Integer (rchgrpno));
		
		return mapping.findForward("commrchgrpchoice");

	}
}