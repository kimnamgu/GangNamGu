/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 양식작성 action
 * 설명:
 */
package nexti.ejms.research.action;

import java.util.List;

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

public class ResearchViewAction2 extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {
		
		ResearchForm rchForm = (ResearchForm)form;
		
		int rchno = 0;
		int mdrchno = 0;
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
		
		if(request.getAttribute("rchno") != null){
			rchno = Integer.parseInt(request.getAttribute("rchno").toString());
		}else{
			rchno = rchForm.getRchno();
		}
		
		if(request.getAttribute("mdrchno") != null){
			mdrchno = Integer.parseInt(request.getAttribute("mdrchno").toString());
		}else{
			mdrchno = rchForm.getMdrchno();
		}		

		String sessionId = rchForm.getSessionId();
		
		ResearchManager manager = ResearchManager.instance();
		ResearchBean bean = manager.getRchMst(rchno, sessionId);
		
		if(bean != null){
			int examcount = rchForm.getExamcount();
			if ( examcount < 1 ) {
				examcount = manager.getRchSubExamcount(rchno, sessionId);
			}
			BeanUtils.copyProperties(rchForm, bean);
			rchForm.setExamcount(examcount);
		}
		
		List rchSubList = manager.getRchSubList(rchno, sessionId, rchForm.getExamcount(), "");
		rchForm.setListrch(rchSubList);
		rchForm.setFormcount(rchSubList.size());
		rchForm.setMdrchno(mdrchno);
		
		ResearchBean rbean = manager.getRchMst(mdrchno, sessionId);
		if(rbean != null) {
			rchForm.setAnscount(rbean.getAnscount());
		} else {
			rchForm.setAnscount(0);
		}
		
		return mapping.findForward("rchview");
		
	}
}