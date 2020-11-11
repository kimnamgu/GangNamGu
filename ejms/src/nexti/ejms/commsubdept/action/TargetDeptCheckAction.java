/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합대상부서 체크 action
 * 설명:
 */
package nexti.ejms.commsubdept.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nexti.ejms.common.rootAction;
import nexti.ejms.commtreat.model.CommTreatManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class TargetDeptCheckAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		HttpSession session = request.getSession();	
		String sessionId = session.getId().split("[!]")[0];		
		String dept_code = Utils.nullToEmptyString((String)session.getAttribute("dept_code"));
		String type = Utils.nullToEmptyString(request.getParameter("type"));
		int sysdocno = Integer.parseInt("0" + Utils.nullToEmptyString(request.getParameter("sysdocno")));
		String sch_deptid = Utils.nullToEmptyString(request.getParameter("sch_deptid"));
		String sch_submitsate = Utils.nullToEmptyString(request.getParameter("sch_submitsate"));
		String sch_userid = Utils.nullToEmptyString(request.getParameter("sch_userid"));
		String sch_inputstate = Utils.nullToEmptyString(request.getParameter("sch_inputstate"));
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
					dept_code = originUserBean.getDept_id();
			}
		}
		
		CommTreatManager ctmgr = CommTreatManager.instance();
		String resultXML = ctmgr.targetDeptCheck(type, sessionId, sysdocno, dept_code, sch_deptid, sch_submitsate, sch_userid, sch_inputstate);
		
		response.setContentType("text/xml;charset=UTF-8");
		response.getWriter().print(resultXML);
		
		return null;
	}
}