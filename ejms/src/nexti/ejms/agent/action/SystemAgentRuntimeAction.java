/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 시스템에이전트 보기 action
 * 설명:
 */
package nexti.ejms.agent.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.agent.form.SystemAgentForm;
import nexti.ejms.agent.model.SystemAgentManager;

public class SystemAgentRuntimeAction extends rootAction {

	public ActionForward doService(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) 
		throws Exception {
		
		SystemAgentForm saForm = (SystemAgentForm)form;
		String p_id=request.getParameter("p_id");
		
		SystemAgentManager saManager =  SystemAgentManager.instance();
		
		List saList = new ArrayList();
		//saList=saManager.agentRuntimeList(saForm.getP_id());
		saList=saManager.agentRuntimeList(p_id);
		saForm.setSaLists(saList);
		saForm.setP_id(p_id);
		saForm.setP_nm(saManager.getAgentName(p_id));
		
		return mapping.findForward("list");
	}

}
