/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사그룹 설문지정 추가 action
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
import nexti.ejms.util.Utils;

public class commrchgrpchoiceInsertAction extends rootPopupAction{

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		HttpSession session = request.getSession();
		String sessionId = session.getId().split("[!]")[0];
	
		request.setCharacterEncoding("UTF-8");
		
		String cmd = request.getParameter("cmd");
		int	rchgrpno = Integer.parseInt(request.getParameter("rchgrpno"));
		String idList = request.getParameter("idList");
		idList = idList.replaceAll(":", ",");
		
		int ret = 0;
		
		commapprovalManager manager = commapprovalManager.instance();
		
		if ("INSERT".equals(cmd)){
			if ( Utils.nullToEmptyString(request.getHeader("referer")).indexOf("rchgrpno") != -1 ) {
				ret = manager.insertGrpChoice(rchgrpno, sessionId, idList);
			}
		}
		
		String msg = "";
		if(ret > 0) {
			msg = "";
		}
		
		StringBuffer resultXML = new StringBuffer();
		
		resultXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		resultXML.append("\n<result>");
		resultXML.append("\n<cmd>" + cmd + "</cmd>");
		resultXML.append("\n<retCode>" + ret + "</retCode>");
		resultXML.append("\n<msg>" + msg + "</msg>");
		resultXML.append("\n</result>");
		
		response.setContentType("text/xml;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println(resultXML.toString());
		out.flush();
		out.close();
		
		return null;
	}
}