/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 결재하기 action
 * 설명:
 */
package nexti.ejms.sinchung.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.rootAction;
import nexti.ejms.sinchung.model.SinchungManager;

public class DoSancAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {			
		
		HttpSession session = request.getSession();
		String userid = session.getAttribute("user_id").toString();
		
		int reqformno = Integer.parseInt(request.getParameter("reqformno"));
		int reqseq = Integer.parseInt(request.getParameter("reqseq"));
			
		//결재
		SinchungManager smgr = SinchungManager.instance();
		int result = smgr.doSanc(reqformno, reqseq, userid);
		
		String msg = "";
		if(result > 0){
			msg = "결재처리를 완료하였습니다.";
		} else {
			msg = "결재가 비정상적으로 종료되었습니다.";
		}
		
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
		saveMessages(request,messages);		
	
		return mapping.findForward("list");		
	}
}