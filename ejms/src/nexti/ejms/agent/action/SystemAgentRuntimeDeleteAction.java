/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 시스템에에전트 삭제 action
 * 설명:
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

public class SystemAgentRuntimeDeleteAction extends rootAction {

	public ActionForward doService(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		
		SystemAgentForm saForm = (SystemAgentForm)form;

		SystemAgentManager saManager =  SystemAgentManager.instance();
		saManager.deleteSystemAgentRuntime(saForm.getP_id(), saForm.getP_seq());
			
		return mapping.findForward("delete_ok");
	}

}
