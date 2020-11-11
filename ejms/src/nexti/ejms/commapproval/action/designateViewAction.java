/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 결재선지정 보기 action
 * 설명:
 */
package nexti.ejms.commapproval.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.commapproval.model.commapprovalManager;
import nexti.ejms.common.rootPopupAction;

public class designateViewAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		int sysdocno = 0;
		String type = "";
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		if(request.getParameter("type") != null) {
			type = (String)request.getParameter("type");
		}
		
		//세션정보 가져오기
		HttpSession session = request.getSession();	
		String sessionId = session.getId().split("[!]")[0];
		String deptcd = (String)session.getAttribute("dept_code");
		
		
		commapprovalManager manager = commapprovalManager.instance();
		
		String designateXML = "";

		designateXML = manager.getDesigNateView(sysdocno, sessionId, deptcd, type);
	
		response.setContentType("text/xml;charset=UTF-8");
		
		StringBuffer userGListXML = new StringBuffer();
		
		StringBuffer prefixXML = new StringBuffer();
		prefixXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		prefixXML.append("\n<root>");

		
		StringBuffer suffixXML = new StringBuffer();
		suffixXML.append("\n</root>");

		userGListXML.append(prefixXML.toString());
		userGListXML.append(designateXML);
		userGListXML.append(suffixXML.toString());
		
		PrintWriter out = response.getWriter();
		out.println(userGListXML.toString());
		out.flush();
		out.close();
		
		return null;

	}
}