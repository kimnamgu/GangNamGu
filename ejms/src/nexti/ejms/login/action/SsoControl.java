/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: sso로그인
 * 설명:
 */
package nexti.ejms.login.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Base64;
import nexti.ejms.util.Utils;

public class SsoControl extends Action {

	public ActionForward execute(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception{
		
		HttpSession session = request.getSession();
		session.setAttribute("sso", "true");
		String userReg = (String)session.getAttribute("sso_user_sn");
		if ( userReg == null ) userReg = (String)request.getParameter("sso_user_sn");
		
		session.setAttribute("type", request.getParameter("type"));
		session.setAttribute("remoteAddr", request.getRemoteAddr());
		
		UserManager umgr = UserManager.instance();
		if (Utils.isNotNull(userReg)) {
			try {
				if ( umgr.existedUserSN(Base64.decodeString(userReg)) ) {
					userReg = Base64.decodeString(userReg);
				}
			} catch ( Exception e ) {}
			session.setAttribute("sso_user_sn", userReg);
		} else {
			session.removeAttribute("sso");
			session.removeAttribute("act");
			session.removeAttribute("type");
			session.removeAttribute("sso_user_id");
			session.removeAttribute("sso_user_sn");
		}
		
		return mapping.findForward("loginok");
	}	
}
