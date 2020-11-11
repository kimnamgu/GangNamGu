/**
 * 작성일: 2013.03.04
 * 작성자: 대리 서동찬
 * 모듈명: 주요업무관리 시행실적등록 action
 * 설명:
 */
package nexti.ejms.workplan.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.rootAction;
import nexti.ejms.dept.model.DeptBean;
import nexti.ejms.dept.model.DeptManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;
import nexti.ejms.workplan.form.WorkplanForm;

public class WorkResultWriteAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");		//사용자 ID
		
		WorkplanForm wForm = (WorkplanForm)form;
		
		if ( wForm.getPlanno() == 0 ) {
			StringBuffer msg = new StringBuffer();
			msg.append("alert('업무계획번호가 잘못되었습니다.'); \n");
			msg.append("history.back(); \n");
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg", msg));
			saveMessages(request,messages);
		}
		
		UserManager uMgr = UserManager.instance();
		DeptManager dMgr = DeptManager.instance();
		
		UserBean uBean = uMgr.getUserInfo(user_id);
		DeptBean dBean = dMgr.getDeptInfo(uBean.getDept_id());
		
		if ( dMgr.getExistedChrg(uBean.getDept_id()) == 0 ) {
			wForm.setChrgunitcd(-1);
		} else {
			wForm.setChrgunitcd(dMgr.getChrgunitcd(user_id));
		}
		
		if ( uBean != null && !"".equals(Utils.nullToEmptyString(uBean.getTel())) ) {
			wForm.setUser_tel(uBean.getTel());
		} else if ( dBean != null && !"".equals(Utils.nullToEmptyString(dBean.getDept_tel())) ) {
			wForm.setUser_tel(dBean.getDept_tel());				
		}
		
		wForm.setUpperdeptcd(dBean.getUpper_dept_id());
		wForm.setUpperdeptnm(dMgr.getDeptInfo(dBean.getUpper_dept_id()).getDept_name());
		wForm.setDeptcd(dBean.getDept_id());
		wForm.setDeptnm(dBean.getDept_name());
		wForm.setChrgusrcd(uBean.getUser_id());
		wForm.setChrgusrnm(uBean.getUser_name());
		wForm.setMode("write");
		
		return mapping.findForward("view");
	}
}