/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사그룹 기존설문가져오기 선택 action
 * 설명:
 */
package nexti.ejms.research.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.research.form.ResearchForm;
import nexti.ejms.research.model.ResearchManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class ResearchGrpChoiceAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		HttpSession session = request.getSession();
		String sessionId = session.getId().split("[!]")[0];
		String user_id = (String)session.getAttribute("user_id");
		String user_name = (String)session.getAttribute("user_name");
		String dept_code = (String)session.getAttribute("dept_code");
		String dept_name = (String)session.getAttribute("dept_name");
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				user_id = originUserBean.getUser_id();
				user_name = originUserBean.getUser_name();
				dept_code = originUserBean.getDept_id();
				dept_name = UserManager.instance().getDeptName(user_id);
			}
		}
		
		ResearchForm rchForm = (ResearchForm)form;
		
		String mode = "";
		String range = "";
		int rchgrpno = 0;
		int result = 0;
		
		if(request.getParameter("rchgrpno")!= null){
			rchgrpno = Integer.parseInt(request.getParameter("rchgrpno"));
		}
		
		if(request.getParameter("mode")!= null){
			mode = request.getParameter("mode");
		}
		
		if(request.getParameter("range")!= null){
			range = request.getParameter("range");
		}
		
		//접수부서 전화번호
		String tel = "";
		UserBean ubean = UserManager.instance().getUserInfo(user_id);
		if ( ubean != null ) {
			tel = ubean.getTel();
		}
		
		ResearchManager manager = ResearchManager.instance();
		manager.delResearchGrpTempData(sessionId); 
		
		result = manager.rchGrpChoice(rchgrpno, sessionId, user_id, user_name, dept_code, dept_name, tel);
		
		if("choice".equals(mode)){
			if(result>0){
				request.setAttribute("rchgrpno", new Integer(0));
				rchForm.setSessionId(sessionId);
				rchForm.setRchgrpno(0);
			}
		}else{
			request.setAttribute("rchgrpno", new Integer(0));
			request.setAttribute("range", range);
			rchForm.setSessionId(sessionId);
			rchForm.setRchgrpno(0);
			request.setAttribute("mdrchgrpno", new Integer(rchgrpno));
			rchForm.setMdrchgrpno(rchgrpno);
		}
		
		return mapping.findForward("view");
	}	
}