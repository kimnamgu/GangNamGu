/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식작성 양식속성목록 저장 action
 * 설명:
 */
package nexti.ejms.attr.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;
import nexti.ejms.attr.model.AttrManager;

public class AttrListSaveAction extends rootAction {

	public ActionForward doService(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) 
		throws Exception {
		
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");  //로그인 유저
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				user_id = originUserBean.getUser_id();
			}
		}
		
		String gbn = (String)request.getParameter("gbn");
		String type = (String)request.getParameter("type");		
		String mcd = (String)request.getParameter("mcd");
		String mnm = (String)request.getParameter("mnm");
		String dcd = (String)request.getParameter("dcd");
		String dnm = (String)request.getParameter("dnm");
		String result = null;
		
		AttrManager attrmgr = AttrManager.instance();
		System.out.println("attr "+gbn+":"+dnm);
		result = attrmgr.saveAttrList(gbn, type, mcd, mnm, dcd, dnm, user_id);
		System.out.println("attr "+result);
		StringBuffer msg = new StringBuffer();
		msg.append("<script> \n");
		msg.append("parent.setList(");
		msg.append("'" + gbn + "',");
		msg.append("'" + type + "',");
		msg.append("'" + result + "'");
		msg.append("); \n");
		msg.append("</script> \n");
		
		response.setContentType("text/html;charset=euc-kr");
		response.getWriter().write(msg.toString());
		response.getWriter().flush();
		response.getWriter().close();
		response.flushBuffer();
		
		return null;
	}
}