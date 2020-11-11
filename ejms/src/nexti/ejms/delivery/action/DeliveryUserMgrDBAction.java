/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부담당관리 저장 action
 * 설명:
 */
package nexti.ejms.delivery.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.rootAction;
import nexti.ejms.chrgUnit.form.ChrgUnitForm;
import nexti.ejms.dept.model.DeptManager;

public class DeliveryUserMgrDBAction extends rootAction {

	public ActionForward doService(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) 
		throws Exception {		
		
		ActionMessages messages = new ActionMessages();
		
		ChrgUnitForm chrgForm = (ChrgUnitForm)form;
		String user_id = chrgForm.getUser_id(); //사용자아이디
		String mode = chrgForm.getMode();		//추가(i), 삭제(d)
		
		DeptManager deptMgr = DeptManager.instance();
		String msg = "";
		if("i".equals(mode)){
			//배부담당추가
			if ( deptMgr.updateDeliveryUser(user_id, "Y") > 0 ) {
				msg = "추가되었습니다";
			}			
		} else if("d".equals(mode)){
			//배부담당삭제
			if ( deptMgr.updateDeliveryUser(user_id, "") > 0 ) {
				msg = "삭제되었습니다";
			}
		}
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg", msg));
		saveMessages(request,messages);
		
		return mapping.findForward("list");
	}
}