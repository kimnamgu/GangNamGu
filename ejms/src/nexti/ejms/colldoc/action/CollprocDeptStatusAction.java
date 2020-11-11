/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 진행중인취합문서 진행현황 action
 * 설명:
 */
package nexti.ejms.colldoc.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.commtreat.form.CommTreatDeptStatusForm;
import nexti.ejms.commtreat.model.CommTreatBean;
import nexti.ejms.commtreat.model.CommTreatManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class CollprocDeptStatusAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		HttpSession session = request.getSession();
		String dept_code 	= session.getAttribute("dept_code").toString();
		String user_id = session.getAttribute("user_id").toString();
		
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
		
		//Form에서 넘어온 값 가져오기
		CommTreatDeptStatusForm statusForm = (CommTreatDeptStatusForm)form;
		
		String viewmode = "";
		if(statusForm.getViewmode().equals("all")) viewmode = "";
		else if(statusForm.getViewmode().equals("comp"))   viewmode = "05,06";
		else if(statusForm.getViewmode().equals("nocomp")) viewmode = "01,02,03,04";
		
		//mode에 따른 처리
		CommTreatManager manager = CommTreatManager.instance();
		if ("modify".equalsIgnoreCase(statusForm.getMode()) ) {
			if (Utils.isNull(statusForm.getModifyvalue())) {
				statusForm.setModifyvalue("0");
			}
			List tgtdeptList = manager.getTgtdeptList(statusForm.getModifydept(), statusForm.getSysdocno(), user_id, viewmode);
			for(int i = 0; i < tgtdeptList.size(); i++) {
				String tgtdept = ((CommTreatBean)tgtdeptList.get(i)).getTgtdeptcd();
				manager.updateTgtdept(statusForm.getSysdocno(), tgtdept, statusForm.getModifyvalue());	
			}
			//manager.updateTgtdept(statusForm.getSysdocno(), statusForm.getModifydept(), statusForm.getModifyvalue());
			statusForm.setMode("view");
			statusForm.setModifydept("");
		} else if ("modifyAll".equalsIgnoreCase(statusForm.getMode()) ) {
			if (Utils.isNull(statusForm.getModifyvalue())) {
				statusForm.setModifyvalue("0");
			}
			List tgtdeptList = manager.getTgtdeptList(dept_code, statusForm.getSysdocno(), user_id, viewmode);
			for(int i = 0; i < tgtdeptList.size(); i++) {
				String tgtdept = ((CommTreatBean)tgtdeptList.get(i)).getTgtdeptcd();
				//String tgtdept = ((CommTreatDeptStatusBean)tgtdeptList.get(i)).getTgtdeptcd();
				manager.updateTgtdept(statusForm.getSysdocno(), tgtdept, statusForm.getModifyvalue());	
			}
			statusForm.setMode("view");
			statusForm.setModifydept("");
			
			if ("1".equals(statusForm.getModifyvalue())) {
				request.setAttribute("checkAll", "checked");
			} else {
				request.setAttribute("checkAll", "");
			}
		} else {
			request.setAttribute("checkAll", "");
		}
		
		//목록 가져오기
		CommTreatBean treatBean = new CommTreatBean();
		treatBean = manager.getDocStates(statusForm.getSysdocno(), dept_code);
		
		statusForm.setDocstate(treatBean.getDocstate());			//문서상태
		statusForm.setSubmitdepttotcount(treatBean.getTcnt());		//전체건
		statusForm.setSubmitdeptcompcount(treatBean.getScnt());		//완료건
		statusForm.setSubmitdeptnocompcount(treatBean.getFcnt());	//미제출건
		
		statusForm.setDeptlist(manager.getTgtdeptList(dept_code, statusForm.getSysdocno(), user_id, viewmode));
		
		return mapping.findForward("view");

	}
}