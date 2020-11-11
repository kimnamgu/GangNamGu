/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����׷� �������� action
 * ����:
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

public class commrchgrpchoiceViewAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		HttpSession session = request.getSession();
		String sessionId = session.getId().split("[!]")[0];
		
		int rchgrpno = 0;
		
		if(request.getParameter("rchgrpno") != null) {
			rchgrpno = Integer.parseInt(request.getParameter("rchgrpno"));
		}
		
		commapprovalManager manager = commapprovalManager.instance();
		
		String usrXML = manager.getResearchGrpView(rchgrpno, sessionId);
		
		response.setContentType("text/xml;charset=UTF-8");
		
		StringBuffer xmlList = new StringBuffer();
		
		StringBuffer prefixXML = new StringBuffer();
		prefixXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		prefixXML.append("\n<root>");

		
		StringBuffer suffixXML = new StringBuffer();
		suffixXML.append("\n</root>");

		xmlList.append(prefixXML.toString());
		xmlList.append(usrXML);
		xmlList.append(suffixXML.toString());
		
		PrintWriter out = response.getWriter();
		out.println(xmlList.toString());
		out.flush();
		out.close();
		
		return null;

	}
}