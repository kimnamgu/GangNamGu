/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� �������� �μ�/���������Ȯ�� ����
 * ����:
 */
package nexti.ejms.user.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nexti.ejms.common.rootAction;
import nexti.ejms.user.form.UsrMgrForm;
import nexti.ejms.user.model.UsrMgrManager;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DeptExtModifyAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {	

		UsrMgrForm usrMgrForm = (UsrMgrForm)form;		
		UsrMgrManager usrMgrManager = UsrMgrManager.instance();
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		String rep_dept = (String)session.getAttribute("rep_dept");
		
		//��ü ���� ���������� ����
		usrMgrManager.updateAllExt(user_id, usrMgrForm.getOrggbn(), rep_dept);
		
		//���μ� ����
		for(int i=0;i<usrMgrForm.getMain_ynLen();i++){
			//�� �μ� �ڵ� ���� ��������� �ٽ� ����
			String dept_id = usrMgrForm.getMain_yn()[i];
			usrMgrManager.updateMainYn(user_id, dept_id);
		}

		//��뿩�� ����
		for(int i=0;i<usrMgrForm.getUse_ynLen();i++){
			//�� �μ� �ڵ� ���� ��������� �ٽ� ����
			String dept_id = usrMgrForm.getUse_yn()[i];
			usrMgrManager.updateUseYn(dept_id);
		}

		//���迩�� ����
		for(int i=0;i<usrMgrForm.getCon_ynLen();i++){
			//�� �μ� �ڵ� ���� ��������� �ٽ� ����
			String dept_id = usrMgrForm.getCon_yn()[i];
			usrMgrManager.updateConYn(dept_id);
		}
		
		return mapping.findForward("frame1");
	}
}

