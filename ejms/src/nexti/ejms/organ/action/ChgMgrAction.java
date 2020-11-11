/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����ں��� ��� action
 * ����:
 */
package nexti.ejms.organ.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.organ.form.ManagerForm;
import nexti.ejms.organ.model.OrganizeManager;

public class ChgMgrAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {		

		HttpSession session = request.getSession();
		String user_gbn = (String)session.getAttribute("user_gbn");
		String rep_dept = (String)session.getAttribute("rep_dept");
		String user_id  = (String)session.getAttribute("user_id");
		
		//������ ��� ��������
		List mgrlist = OrganizeManager.instance().managerList(user_gbn, rep_dept, user_id);
		
		//Form�� ������ ��� ����
		ManagerForm mgrForm = (ManagerForm)form;
		mgrForm.setMgrList(mgrlist);
		
		return mapping.findForward("view");
	}
}