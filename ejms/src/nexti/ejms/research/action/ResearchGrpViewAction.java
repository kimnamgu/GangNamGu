/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사그룹 양식작성 action
 * 설명:
 */
package nexti.ejms.research.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.agent.model.SystemAgentManager;
import nexti.ejms.common.rootAction;
import nexti.ejms.research.form.ResearchForm;
import nexti.ejms.research.model.ResearchBean;
import nexti.ejms.research.model.ResearchManager;

public class ResearchGrpViewAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {
		
		ResearchForm rchForm = (ResearchForm)form;
		
		int rchgrpno = 0;
		int mdrchgrpno = 0;
		String range = "";
				
		if(request.getAttribute("range") != null){
			range = request.getAttribute("range").toString();
		}else{
			range = rchForm.getRange();
		}
		
		if(range.equals("2")){
			SystemAgentManager saManager =  SystemAgentManager.instance();
			saManager.agentControl("rchresult","002");
		}
		
		if(request.getAttribute("rchgrpno") != null){
			rchgrpno = Integer.parseInt(request.getAttribute("rchgrpno").toString());
		}else{
			rchgrpno = rchForm.getRchgrpno();
		}
		
		if(request.getAttribute("mdrchgrpno") != null){
			mdrchgrpno = Integer.parseInt(request.getAttribute("mdrchgrpno").toString());
		}else{
			mdrchgrpno = rchForm.getMdrchgrpno();
		}		

		String sessionId = rchForm.getSessionId();
		
		ResearchManager manager = ResearchManager.instance();
		ResearchBean bean = manager.getRchGrpMst(rchgrpno, sessionId);
		
		if(bean != null){
			BeanUtils.copyProperties(rchForm, bean);
		}
		
		rchForm.setMdrchgrpno(mdrchgrpno);
		
		ResearchBean rbean = manager.getRchGrpMst(mdrchgrpno, sessionId);
		if(rbean != null) {
			rchForm.setAnscount(rbean.getAnscount());
		} else {
			rchForm.setAnscount(0);
		}
		
		return mapping.findForward("view");
		
	}
}