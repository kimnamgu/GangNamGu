/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력담당자지정 action
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
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class inputusrAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		//세션정보 가져오기
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		String dept_code = (String)session.getAttribute("dept_code");
		String dept_name = (String)session.getAttribute("dept_name");
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				dept_code = originUserBean.getDept_id();
				dept_name = UserManager.instance().getDeptName(originUserBean.getUser_id());
			}
		}
		
		request.setAttribute("origindeptcd", dept_code);
		request.setAttribute("class_name", UserManager.instance().getUserInfo(user_id).getClass_name());
		
		int sysdocno = 0;
		String retid = "";
		String mode = "";
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		if(request.getParameter("retid") != null) {
			retid = (String)request.getParameter("retid");
		}
		
		if(request.getParameter("mode") != null) {
			mode = (String)request.getParameter("mode");
		}
		
		request.setAttribute("sysdocno", new Integer (sysdocno));
		request.setAttribute("retid", retid);
		request.setAttribute("mode", mode);
		request.setAttribute("deptnm", dept_name);
		
		return mapping.findForward("inputusr");

	}
}