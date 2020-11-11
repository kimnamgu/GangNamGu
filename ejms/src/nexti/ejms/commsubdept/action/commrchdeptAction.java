/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����������� action
 * ����:
 */
package nexti.ejms.commsubdept.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.commsubdept.form.CommSubdeptForm;

public class commrchdeptAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {


		CommSubdeptForm commSubdeptForm = (CommSubdeptForm)form;
		
		int rchno = 0;

		if(request.getParameter("rchno") != null) {
			rchno = Integer.parseInt(request.getParameter("rchno"));
		}
		
		commSubdeptForm.setRchno(rchno);
		
		return mapping.findForward("commrchdept");

	}
}