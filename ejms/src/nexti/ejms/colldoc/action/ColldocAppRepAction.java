/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합문서작성 취합문서발송 action
 * 설명:
 */
package nexti.ejms.colldoc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.messanger.MessangerRelay;
import nexti.ejms.messanger.MessangerRelayBean;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class ColldocAppRepAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		//세션정보 가져오기
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");		//사용자 아이디
		String dept_code = (String)session.getAttribute("dept_code");	//사용자 부서코드;
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				user_id = originUserBean.getUser_id();
				dept_code = originUserBean.getDept_id();
			}
		}

		int sysdocno = Integer.parseInt((String)request.getParameter("sysdocno"), 10);
		String docstate = (String)request.getParameter("docstate");
		
		ColldocManager cdmgr = ColldocManager.instance();
		
		int result = cdmgr.appovalColldoc(user_id, dept_code, sysdocno, docstate);
		
		if ( result > 0 ) {
			MessangerRelayBean mrBean = new MessangerRelayBean();
			mrBean.setRelayMode(MessangerRelay.COLLECT_START);
			mrBean.setSysdocno(sysdocno);
			new MessangerRelay(mrBean).start();
		}
				
		return mapping.findForward("colldocList");
	}
}