/**
 * 작성일: 2013.03.04
 * 작성자: 대리 서동찬
 * 모듈명: 주요업무관리 시행실적보기 action
 * 설명:
 */
package nexti.ejms.workplan.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
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
import nexti.ejms.workplan.model.WorkplanBean;
import nexti.ejms.workplan.model.WorkplanManager;

public class WorkResultViewAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		WorkplanForm wForm = (WorkplanForm)form;
		
		if ( wForm.getResultno() == 0 ) {
			StringBuffer msg = new StringBuffer();
			msg.append("alert('실적번호가 잘못되었습니다.'); \n");
			msg.append("history.back(); \n");
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg", msg));
			saveMessages(request,messages);
		}
		
		WorkplanManager wMgr = WorkplanManager.instance();
		
		WorkplanBean wBean = wMgr.getWorkResult(wForm.getPlanno(), wForm.getResultno());
		BeanUtils.copyProperties(wForm, wBean);
		
		UserManager uMgr = UserManager.instance();
		DeptManager dMgr = DeptManager.instance();
		
		UserBean uBean = uMgr.getUserInfo(wBean.getChrgusrcd());
		DeptBean dBean = dMgr.getDeptInfo(wBean.getDeptcd());
		
		if ( uBean != null && !"".equals(Utils.nullToEmptyString(uBean.getTel())) ) {
			wForm.setUser_tel(uBean.getTel());
		} else if ( dBean != null && !"".equals(Utils.nullToEmptyString(dBean.getDept_tel())) ) {
			wForm.setUser_tel(dBean.getDept_tel());				
		}
		
		wForm.setReadcnt(wMgr.readCount(wForm.getPlanno(), wForm.getResultno()));
		
		return mapping.findForward("view");
	}
}