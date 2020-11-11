/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����������չ��� ������Ȳ action
 * ����:
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
		
		//Form���� �Ѿ�� �� ��������
		CommTreatDeptStatusForm statusForm = (CommTreatDeptStatusForm)form;
		
		String viewmode = "";
		if(statusForm.getViewmode().equals("all")) viewmode = "";
		else if(statusForm.getViewmode().equals("comp"))   viewmode = "05,06";
		else if(statusForm.getViewmode().equals("nocomp")) viewmode = "01,02,03,04";
		
		//mode�� ���� ó��
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
		
		//��� ��������
		CommTreatBean treatBean = new CommTreatBean();
		treatBean = manager.getDocStates(statusForm.getSysdocno(), dept_code);
		
		statusForm.setDocstate(treatBean.getDocstate());			//��������
		statusForm.setSubmitdepttotcount(treatBean.getTcnt());		//��ü��
		statusForm.setSubmitdeptcompcount(treatBean.getScnt());		//�Ϸ��
		statusForm.setSubmitdeptnocompcount(treatBean.getFcnt());	//�������
		
		statusForm.setDeptlist(manager.getTgtdeptList(dept_code, statusForm.getSysdocno(), user_id, viewmode));
		
		return mapping.findForward("view");

	}
}