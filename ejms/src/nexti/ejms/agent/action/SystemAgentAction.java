/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 시스템에이전트 action
 * 설명:
 */
package nexti.ejms.agent.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.agent.form.SystemAgentForm;
import nexti.ejms.agent.model.SystemAgentManager;

public class SystemAgentAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {
		
		SystemAgentForm saForm = (SystemAgentForm)form;
		SystemAgentManager saManager =  SystemAgentManager.instance();
				
		List saList=saManager.agentList();
		saForm.setSaLists(saList);
		
		return mapping.findForward("list");
	}
}