/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문기타대상지정 추가 action
 * 설명:
 */
package nexti.ejms.commapproval.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.commapproval.model.commapprovalManager;
import nexti.ejms.commapproval.model.commapprovalBean;
import nexti.ejms.common.rootPopupAction;
import nexti.ejms.util.Utils;

public class commrchothertargetInsertAction extends rootPopupAction{

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
		int	rchno = Integer.parseInt(request.getParameter("rchno"));
		int	rchgrpno = Integer.parseInt(request.getParameter("rchgrpno"));
		String idList = request.getParameter("idList");
		String nameList = request.getParameter("nameList");
		String gbnList = request.getParameter("gbnList");
		
		int ret = 0;
		ArrayList tgtList = new ArrayList();
		
		String[] tgtcode = null;
		String[] tgtname = null;
		String[] tgtgbn = null;

		commapprovalManager manager = commapprovalManager.instance();
		
		if (!"".equals(idList)){
			tgtcode = idList.split(":");
		}
		
		if (!"".equals(nameList)){
			tgtname = nameList.split(":");
		}
		
		if (!"".equals(gbnList)){
			tgtgbn = gbnList.split(":");
		}
		
		commapprovalBean bean = null;
		
		if (tgtcode != null){
			for (int i = 0; i < tgtcode.length; i++){
				bean = new commapprovalBean();

				bean.setTgtcode(tgtcode[i]);
				bean.setTgtname(tgtname[i]);
				bean.setTgtgbn(tgtgbn[i]);
				
				tgtList.add(bean);
				bean = null;
			}
		}
		
		if ("INSERT".equals(cmd)){
			if ( Utils.nullToEmptyString(request.getHeader("referer")).indexOf("rchgrpno") != -1 ) {
				ret = manager.insertGrpOtherTarget(rchgrpno, sessionId, tgtList);
			} else {
				ret = manager.insertOtherTarget(rchno, sessionId, tgtList);
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