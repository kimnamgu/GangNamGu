/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� ��û�ϱ� action
 * ����:
 */
package nexti.ejms.sinchung.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.sinchung.form.SinchungListForm;

public class DoListAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {		 
		
		HttpSession session = request.getSession();
		String deptcd = (String)session.getAttribute("dept_code");
		String deptnm = (String)session.getAttribute("dept_name");
		
		SinchungListForm sForm = (SinchungListForm)form;
		sForm.setDeptcd(deptcd);		//ȭ���� ���� �� �μ��ڵ� ����
		sForm.setDeptnm(deptnm);        //ȭ���� ���� �� �μ���Ī ����
		
		return mapping.findForward("parentFrame");
	}
}