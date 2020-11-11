/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ý��ۿ�����Ʈ action
 * ����:
 */
package nexti.ejms.agent.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.agent.form.SystemAgentForm;
import nexti.ejms.agent.model.SystemAgentManager;

public class SystemAgentControlAction extends rootAction {

	public ActionForward doService(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) 
		throws Exception {
		
		SystemAgentForm saForm = (SystemAgentForm)form;
		SystemAgentManager saManager =  SystemAgentManager.instance();
		
		saManager.agentControl(saForm.getP_id(),saForm.getP_ctr());
		
		return mapping.findForward("list");
	}

}
