/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문대상지정 action
 * 설명:
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