/**
 * 작성일: 2013.03.04
 * 작성자: 대리 서동찬
 * 모듈명: 주요업무관리 시행실적삭제 action
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
import nexti.ejms.workplan.form.WorkplanForm;
import nexti.ejms.workplan.model.WorkplanBean;
import nexti.ejms.workplan.model.WorkplanManager;

public class WorkResultDeleteAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");		//사용자 ID
		
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
		
		wBean.setUseyn("N");
		wBean.setUptusrid(user_id);
		
		int resultno = wMgr.saveWorkResult(wBean, null, null); 
		if ( resultno > 0 ) {
			StringBuffer msg = new StringBuffer();
			msg.append("alert('삭제되었습니다.'); \n");
			
			//ifrmae으로 처리하기 위한 코드
			msg.append("parent.location.href = parent.location.href; \n");
			
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg", msg));
			saveMessages(request,messages);
		}
		
		return mapping.findForward("list");
	}
}