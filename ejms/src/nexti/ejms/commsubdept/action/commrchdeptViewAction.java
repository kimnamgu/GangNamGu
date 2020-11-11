/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문대상지정 보기 action
 * 설명:
 */
package nexti.ejms.commsubdept.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.commsubdept.model.commsubdeptManager;
import nexti.ejms.common.rootPopupAction;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class commrchdeptViewAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		int rchno = 0;
		int rchgrpno = 0;
		
		if(request.getParameter("rchno") != null) {
			rchno = Integer.parseInt(request.getParameter("rchno"));
		}
		if(request.getParameter("rchgrpno") != null) {
			rchgrpno = Integer.parseInt(request.getParameter("rchgrpno"));
		}
		
		//세션정보 가져오기
		HttpSession session = request.getSession();		
		String sessionId = session.getId().split("[!]")[0];
		String user_id = (String)session.getAttribute("user_id");
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				user_id = originUserBean.getUser_id();
			}
		}
		
		commsubdeptManager manager = commsubdeptManager.instance();
		
		String commsubdeptXML = "";
		
		if ( Utils.nullToEmptyString(request.getHeader("referer")).indexOf("rchgrpno") != -1 ) {
			commsubdeptXML = manager.getCommrchGrpdeptView(rchgrpno, sessionId, user_id);
		} else {
			commsubdeptXML = manager.getCommrchdeptView(rchno, sessionId, user_id);
		}

		response.setContentType("text/xml;charset=UTF-8");
		
		StringBuffer deptViewXML = new StringBuffer();
		
		StringBuffer prefixXML = new StringBuffer();
		prefixXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		prefixXML.append("\n<root>");

		
		StringBuffer suffixXML = new StringBuffer();
		suffixXML.append("\n</root>");

		deptViewXML.append(prefixXML.toString());
		deptViewXML.append(commsubdeptXML);
		deptViewXML.append(suffixXML.toString());
		
		PrintWriter out = response.getWriter();
		out.println(deptViewXML.toString());
		out.flush();
		out.close();
		
		return null;

	}
}