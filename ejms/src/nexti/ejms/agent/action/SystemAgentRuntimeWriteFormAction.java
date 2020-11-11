/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ý��ۿ�����Ʈ ���ȭ�� action
 * ����:
 */
package nexti.ejms.agent.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.util.Utils;
import nexti.ejms.agent.form.SystemAgentForm;
import nexti.ejms.agent.model.SystemAgentManager;
import nexti.ejms.agent.model.SystemAgentRuntimeBean;

public class SystemAgentRuntimeWriteFormAction extends rootPopupAction {

	public ActionForward doService(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		SystemAgentForm saForm = (SystemAgentForm)form;
		
		String mode = request.getParameter("mode");
		String p_id = request.getParameter("p_id");

		SystemAgentManager saManager =  SystemAgentManager.instance();
		saForm.setMode(mode);
		saForm.setP_id(p_id);
		saForm.setP_nm(saManager.getAgentName(p_id));
		
		if ("m".equals(mode)) {	//������ ���
			if (Utils.isNotNull(request.getParameter("p_seq"))) {
				int p_seq = Integer.parseInt(request.getParameter("p_seq"));
				SystemAgentRuntimeBean arBean = new SystemAgentRuntimeBean();
				arBean = saManager.agentRuntimeDtlInfo(p_id,p_seq);
				saForm.setP_type(arBean.getP_type());
				//Ÿ�Կ� ���� �ð�����
				if ("001".equals(arBean.getP_type())) {
					if (Utils.isNotNull(arBean.getP_t1())) {
						saForm.setP_t1_1(arBean.getP_t1().substring(0,2));
						saForm.setP_t1_2(arBean.getP_t1().substring(2,4));
					} else {
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","������ �߻��Ͽ����ϴ�.\n\n�����ڿ��� �����Ͻñ� �ٶ��ϴ�."));
						saveMessages(request,messages);				
					}
				} else if ("002".equals(arBean.getP_type())) {
					if (Utils.isNotNull(arBean.getP_t2())) {
						saForm.setP_t2_1(arBean.getP_t2().substring(0,2));
						saForm.setP_t2_2(arBean.getP_t2().substring(2,4));
						saForm.setP_t2_3(arBean.getP_t2().substring(4,6));
					} else {
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","������ �߻��Ͽ����ϴ�.\n\n�����ڿ��� �����Ͻñ� �ٶ��ϴ�."));
						saveMessages(request,messages);				
					}
				} else if ("003".equals(arBean.getP_type())) {
					if (Utils.isNotNull(arBean.getP_t3())) {
						saForm.setP_t3_1(arBean.getP_t3().substring(0,2));
						saForm.setP_t3_2(arBean.getP_t3().substring(2,4));
						saForm.setP_t3_3(arBean.getP_t3().substring(4,6));
					} else {
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","������ �߻��Ͽ����ϴ�.\n\n�����ڿ��� �����Ͻñ� �ٶ��ϴ�."));
						saveMessages(request,messages);				
					}
				} else if ("004".equals(arBean.getP_type())) {
					if (Utils.isNotNull(arBean.getP_t4())) {
						saForm.setP_t4_1(arBean.getP_t4().substring(0,2));
						saForm.setP_t4_2(arBean.getP_t4().substring(2,4));
						saForm.setP_t4_3(arBean.getP_t4().substring(4,6));
						saForm.setP_t4_4(arBean.getP_t4().substring(6,8));
					} else {
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","������ �߻��Ͽ����ϴ�.\n\n�����ڿ��� �����Ͻñ� �ٶ��ϴ�."));
						saveMessages(request,messages);				
					}
				}
				saForm.setP_use(arBean.getP_use());
				saForm.setP_t5(arBean.getP_t5());
			} else {	//�����߻�
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","������ �߻��Ͽ����ϴ�.\n\n��� �� �ٽ� ����Ͽ� �ֽñ� �ٶ��ϴ�."));
				saveMessages(request,messages);				
			}
			
		} else	{	//����� ���
			saForm.setP_type("001");
		}
		
		return mapping.findForward("view");
	}

}
