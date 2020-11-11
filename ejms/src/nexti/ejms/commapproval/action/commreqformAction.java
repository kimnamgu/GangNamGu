/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 결재선지정 action
 * 설명:
 */
package nexti.ejms.commapproval.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
//import nexti.ejms.sinchung.model.SinchungManager;

public class commreqformAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		int reqformno = 0;
		int reqseq = 0;
		String retid = "";
		
		//세션정보 가져오기
		HttpSession session = request.getSession();	
		String deptnm = (String)session.getAttribute("dept_name");
		//String sessi = session.getId().split("[!]")[0];
		
		if(request.getParameter("reqformno") != null) {
			reqformno = Integer.parseInt(request.getParameter("reqformno"));
		}
		
		if(request.getParameter("reqseq") != null) {
			reqseq = Integer.parseInt(request.getParameter("reqseq"));
		}
		
		if(request.getParameter("retid") != null){
			retid = (String)request.getParameter("retid");
		}	
		
		//SinchungManager manager = new SinchungManager();
		//manager.delReqsancTempData(sessi);
		
		request.setAttribute("reqformno", new Integer(reqformno));
		request.setAttribute("reqseq", new Integer(reqseq));
		request.setAttribute("deptnm", deptnm);
		request.setAttribute("retid", retid);
		
		return mapping.findForward("reqform");

	}
}