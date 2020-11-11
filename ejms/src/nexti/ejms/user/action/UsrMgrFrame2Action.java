/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� �������� ����ڸ�� ������
 * ����:
 */
package nexti.ejms.user.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nexti.ejms.common.rootFrameAction;
import nexti.ejms.user.form.UsrMgrForm;
import nexti.ejms.user.model.UsrMgrManager;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class UsrMgrFrame2Action extends rootFrameAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {	

		UsrMgrForm usrMgrForm = (UsrMgrForm)form;		
		String dept_id = request.getParameter("dept_id");
		String orggbn  = request.getParameter("orggbn");
		UsrMgrManager usrMgrManager = UsrMgrManager.instance();
		
		if ( "".equals(orggbn) ) orggbn = "001";

		//���� �μ� ��� ��������
		List childList = usrMgrManager.childDeptList(dept_id, orggbn);
		usrMgrForm.setChildList(childList);
		
		//���� ����� ��� ��������
		List userList = usrMgrManager.deptUserList(dept_id);
		usrMgrForm.setUserList(userList);
		
		if(dept_id != null){
			request.setAttribute("dept_id",dept_id);
		}
		return mapping.findForward("frame2");
	}
}
