/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� �ڷ��̰� �ڷ��̰�ó�� action
 * ����:
 */
package nexti.ejms.dataTransfer.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.rootAction;
import nexti.ejms.dataTransfer.form.DataTransferForm;
import nexti.ejms.dataTransfer.model.DataTransferBean;
import nexti.ejms.dataTransfer.model.DataTransferManager;

public class DataTransferProcAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		DataTransferForm dtform = (DataTransferForm)form;
		
		DataTransferBean dtbean = new DataTransferBean();
		BeanUtils.copyProperties(dtbean, dtform);
		
		DataTransferManager dtmgr = DataTransferManager.instance();
		
		int result = dtmgr.dataTransferProc(dtbean);
		String msg = null;
		if ( result > 0 ) {
			msg = "�̰�ó���Ǿ����ϴ�.";
		} else {
			msg = "�̰�ó���� �����Ͽ����ϴ�.\\n�ٽ� �õ��ϰų� �����ڿ��� ���ǹٶ��ϴ�.";
		}
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg", msg));
		saveMessages(request,messages);

		return mapping.findForward("dataTransferList");
	}
}