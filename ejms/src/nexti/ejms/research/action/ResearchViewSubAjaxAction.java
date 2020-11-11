/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 양식작성 action
 * 설명:
 */
package nexti.ejms.research.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.agent.model.SystemAgentManager;
import nexti.ejms.common.rootAction;
import nexti.ejms.common.rootPopupAction;
import nexti.ejms.research.form.ResearchForm;
import nexti.ejms.research.model.ResearchBean;
import nexti.ejms.research.model.ResearchManager;

public class ResearchViewSubAjaxAction extends rootPopupAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {
		System.out.println("ResearchViewSubAjaxAction");
		ResearchForm rchForm = (ResearchForm)form;
		String sessionId = rchForm.getSessionId();
		int rchno = 0;
		int formseq = 0;
		String researchExamXML ="";
		ResearchManager manager = ResearchManager.instance();
		
		if(request.getAttribute("rchno") != null){
			rchno = Integer.parseInt(request.getAttribute("rchno").toString());
		}else{
			rchno = rchForm.getRchno();
		}
		
		if(request.getAttribute("formseq") != null){
			formseq =Integer.parseInt(request.getAttribute("formseq").toString());
		}else{
			formseq = rchForm.getFormseq()[0];
		}
		
		researchExamXML = manager.getRchExamList(rchno, formseq, sessionId);
		
		
		response.setContentType("text/xml;charset=UTF-8");
		
		StringBuffer userGListXML = new StringBuffer();
		
		StringBuffer prefixXML = new StringBuffer();
		prefixXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		prefixXML.append("\n<root>");

		
		StringBuffer suffixXML = new StringBuffer();
		suffixXML.append("\n</root>"); 

		userGListXML.append(prefixXML.toString());
		userGListXML.append(researchExamXML);
		userGListXML.append(suffixXML.toString());
		
		PrintWriter out = response.getWriter();
		out.println(userGListXML.toString());
		out.flush();
		out.close();
		
		return null;
		
	}
}