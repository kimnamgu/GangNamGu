/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력담당자지정 보기 action
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
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class inputusrViewAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		int sysdocno = 0;
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		//세션정보 가져오기
		HttpSession session = request.getSession();		
		String dept_code = (String)session.getAttribute("dept_code");
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				dept_code = originUserBean.getDept_id();
			}
		}
		
		commapprovalManager manager = commapprovalManager.instance();
		
		String usrXML = "";

		usrXML = manager.getInputUsrView(sysdocno, dept_code);
		
		response.setContentType("text/xml;charset=UTF-8");
		
		StringBuffer userListXML = new StringBuffer();
		
		StringBuffer prefixXML = new StringBuffer();
		prefixXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		prefixXML.append("\n<root>");

		
		StringBuffer suffixXML = new StringBuffer();
		suffixXML.append("\n</root>");

		userListXML.append(prefixXML.toString());
		userListXML.append(usrXML);
		userListXML.append(suffixXML.toString());
		
		PrintWriter out = response.getWriter();
		out.println(userListXML.toString());
		out.flush();
		out.close();
		
		return null;

	}
}