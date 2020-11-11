/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 로그인 이력 조회(쿠키) action
 * 설명:
 */
package nexti.ejms.login.action;

import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.appInfo;
import nexti.ejms.common.form.LoginForm;

public class LoginYNAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {
		
		LoginForm loginForm = (LoginForm) form;
		
		Cookie[] cookies = request.getCookies();
		if ( cookies != null ) {
			for ( int i = 0; i < cookies.length; i++ ) {
				Cookie cookie = cookies[i];
				if ( cookie.getName().equals("cookie_ejms_login") ) {	
					String[] temp = URLDecoder.decode(cookie.getValue(),"EUC-KR").split("[|]");				
					loginForm.setUserId(temp[0]);
					loginForm.setSetfl(temp[1]);						
				}					
			}
		}
		
		if ( appInfo.isGpkilogin() == false ) {
			return mapping.findForward("login");
		} else {
			return mapping.findForward("loginGpki");
		}
	}
}