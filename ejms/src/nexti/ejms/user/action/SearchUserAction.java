/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� �������� ����� �˻�
 * ����:
 */
package nexti.ejms.user.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.user.form.UsrMgrForm;
import nexti.ejms.user.model.UsrMgrManager;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SearchUserAction extends rootPopupAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {	

		UsrMgrForm usrMgrForm = (UsrMgrForm)form;		
		usrMgrForm.setUser_name((String)request.getParameter("s_word"));

		UsrMgrManager usrMgrManager = UsrMgrManager.instance();
		List usrLists = usrMgrManager.userList(usrMgrForm.getUser_name());
		usrMgrForm.setUsrLists(usrLists);

		return mapping.findForward("list");
	}
}