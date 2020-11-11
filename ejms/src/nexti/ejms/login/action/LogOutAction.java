/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 로그아웃 action
 * 설명:
 */
package nexti.ejms.login.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LogOutAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response)
		throws Exception {
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		if(false){
			//SSO 일때 로그아웃 처리
			PrintWriter out = response.getWriter();			
			out.write("<script language=javascript>window.close();</script>");
			out.close();
			return null;
		} else {
			return mapping.findForward("logout");
		}		
	}
}