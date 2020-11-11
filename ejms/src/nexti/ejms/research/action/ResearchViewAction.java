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

public class ResearchViewAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {
		
		ResearchForm rchForm = (ResearchForm)form;
		
		int rchno = 0;
		int mdrchno = 0;
		int researchNo = 0;
		String range = "";
		String tgtdeptnm ="";	
		String otherthtnm ="";
		String limit1 ="";
		String limit2 ="";
		String limit1Chk = "";
		String limit2Chk = "";
		String getLimit1 = "";
		String getLimit2 = "";
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
		if(request.getParameter("rchno") != null){
		 researchNo = Integer.parseInt(request.getParameter("rchno"));
		}
		if(researchNo != 0){
		 getLimit1 = manager.getLimit1Chk(researchNo);
		 getLimit2 = manager.getLimit2Chk(researchNo);
		}else{
		 getLimit1 = manager.getLimit1Chk1(sessionId);
		 getLimit2 = manager.getLimit2Chk2(sessionId);
		}
		tgtdeptnm = bean.getTgtdeptnm();
		otherthtnm = bean.getOthertgtnm();
		 limit1 = bean.getLimit1();
		 limit2 = bean.getLimit2();
		
		 if(bean != null){
			int examcount = rchForm.getExamcount();
				
			if ( examcount < 1 ) {
				examcount = manager.getRchSubExamcount(rchno, sessionId);
			}
			BeanUtils.copyProperties(rchForm, bean);
			rchForm.setExamcount(examcount);
		}
		 
		if(getLimit1 != null ){
			limit1Chk = "ok";
		}else{ limit1Chk = "";}
		if(getLimit2 !=null ){
			limit2Chk = "ok";
		}else{limit2Chk = "";}
		List rchSubList = manager.getRchSubList(rchno, sessionId, rchForm.getExamcount(), "");
		rchForm.setListrch(rchSubList);
		rchForm.setFormcount(rchSubList.size());
		rchForm.setOthertgtnm(otherthtnm);
		rchForm.setTgtdeptnm(tgtdeptnm);
		rchForm.setMdrchno(mdrchno);
		rchForm.setLimit1(limit1);
		rchForm.setLimit2(limit2);
		rchForm.setLimit1chk(limit1Chk);
		rchForm.setLimit2chk(limit2Chk);
		request.setAttribute("limit1Chk", limit1Chk);
		request.setAttribute("limit2Chk", limit2Chk);
		ResearchBean rbean = manager.getRchMst(mdrchno, sessionId);
		if(rbean != null) {
			rchForm.setAnscount(rbean.getAnscount());
		} else {
			rchForm.setAnscount(0);
		}
		
		return mapping.findForward("rchview");
		
	}
}