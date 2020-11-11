/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��δ����� ���� action
 * ����:
 */
package nexti.ejms.delivery.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.rootAction;
import nexti.ejms.chrgUnit.form.ChrgUnitForm;
import nexti.ejms.dept.model.DeptManager;

public class DeliveryUserMgrDBAction extends rootAction {

	public ActionForward doService(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) 
		throws Exception {		
		
		ActionMessages messages = new ActionMessages();
		
		ChrgUnitForm chrgForm = (ChrgUnitForm)form;
		String user_id = chrgForm.getUser_id(); //����ھ��̵�
		String mode = chrgForm.getMode();		//�߰�(i), ����(d)
		
		DeptManager deptMgr = DeptManager.instance();
		String msg = "";
		if("i".equals(mode)){
			//��δ���߰�
			if ( deptMgr.updateDeliveryUser(user_id, "Y") > 0 ) {
				msg = "�߰��Ǿ����ϴ�";
			}			
		} else if("d".equals(mode)){
			//��δ�����
			if ( deptMgr.updateDeliveryUser(user_id, "") > 0 ) {
				msg = "�����Ǿ����ϴ�";
			}
		}
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg", msg));
		saveMessages(request,messages);
		
		return mapping.findForward("list");
	}
}