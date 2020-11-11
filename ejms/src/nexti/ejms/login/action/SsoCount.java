/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: sso로그인 건수연계
 * 설명:
 */
package nexti.ejms.login.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Base64;
import nexti.ejms.util.Utils;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SsoCount extends Action {

	public ActionForward execute(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception{
		
		HttpSession	session = request.getSession();
		session.setAttribute("sso", "true");
		session.setAttribute("remoteAddr", request.getRemoteAddr());
		
		//SSO를 통하여 호출한 경우
		String sso_userid = Utils.nullToEmptyString((String)session.getAttribute("USERID"));
		String sso_servicegbn = Utils.nullToEmptyString((String)session.getAttribute("SERVICEGBN"));
		
		if ( "CNT".equals(sso_servicegbn) ) {
			sso_servicegbn = "mycount";
			session.setAttribute("type", "COUNT");
		} else {
			session.setAttribute("type", Utils.nullToEmptyString((String)request.getParameter("type")));
		}
		
		String reqMethod = request.getMethod();
		if (reqMethod.equals("POST") == true || reqMethod.equals("GET") == true) {
			UserManager umgr = UserManager.instance();
			
			String usersn = Utils.nullToEmptyString((String)request.getParameter("usersn"));
			if ( Utils.isNotNull(sso_userid) && Utils.isNotNull(sso_servicegbn) ) {
				usersn = sso_userid;
			}
			if (Utils.isNotNull(usersn)) {
				try {
					if ( umgr.existedUserSN(Base64.decodeString(usersn)) ) {
						usersn = Base64.decodeString(usersn);
					}
				} catch ( Exception e ) {}
				session.setAttribute("sso_user_sn", usersn);
			} else {
				session.removeAttribute("sso_user_sn");
			}

			String userid = Utils.nullToEmptyString((String)request.getParameter("userid"));
			if (Utils.isNotNull(userid)) {
				try {
					if ( umgr.existedUser(Base64.decodeString(userid)) ) {
						userid = Base64.decodeString(userid);
					}
				} catch ( Exception e ) {}
				session.setAttribute("sso_user_id", userid);
			} else {
				session.removeAttribute("sso_user_id");
			}
			
			String act = Utils.nullToEmptyString((String)request.getParameter("act"));
			if ( Utils.isNotNull(sso_userid) && Utils.isNotNull(sso_servicegbn) ) {
				act = sso_servicegbn;
			}
			if (Utils.isNotNull(act)) {
				session.setAttribute("act", act);
			} else {
				session.removeAttribute("act");
			}
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