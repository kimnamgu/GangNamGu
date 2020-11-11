/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부하기 배부처리 action
 * 설명:
 */
package nexti.ejms.delivery.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.rootAction;
import nexti.ejms.delivery.model.DeliveryManager;
import nexti.ejms.messanger.MessangerRelay;
import nexti.ejms.messanger.MessangerRelayBean;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class DeliveryProcessAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		int sysdocno = 0;
		
		//세션정보 가져오기
		HttpSession session = request.getSession();
		
		String dept_code = session.getAttribute("dept_code").toString();
		String user_id = session.getAttribute("user_id").toString();
		String user_name = session.getAttribute("user_name").toString();
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				user_id = originUserBean.getUser_id();
				user_name = originUserBean.getUser_name();
				dept_code = originUserBean.getDept_id();
			}
		}
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		//배부하기
		DeliveryManager manager = DeliveryManager.instance();
		int retCode = manager.deliveryProcess("02", user_name, user_id, sysdocno, dept_code);
		
		String msg = "";
		if ( retCode <= 0 ) {
			msg = "배부처리 시 오류가 발생하였습니다.";
			
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg", msg));
			saveMessages(request,messages);
		} else {
			MessangerRelayBean mrBean = new MessangerRelayBean();
			mrBean.setRelayMode(MessangerRelay.DELIVERY);
			mrBean.setSysdocno(sysdocno);
			mrBean.setDeptcode(dept_code);
			new MessangerRelay(mrBean).start();
		}

		request.setAttribute("sysdocno", new Integer(sysdocno));
		
		return mapping.findForward("list");
	}
}