/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Է¿Ϸ� �Է¿Ϸ�ó�� action
 * ����:
 */
package nexti.ejms.inputing.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.rootAction;
import nexti.ejms.inputing.model.InputingManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class InputingCompleteAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//�������� ��������
		HttpSession session = request.getSession();
		String user_id = "";
		String dept_code = "";
		
		int sysdocno = 0;
		
		if(session.getAttribute("user_id") != null) {
			user_id = session.getAttribute("user_id").toString();
		}
		
		if(session.getAttribute("dept_code") != null) {
			dept_code = session.getAttribute("dept_code").toString();
		}
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
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
		
		//�Է��ϱ� �� - �Է¿Ϸ� ó��
		InputingManager manager = InputingManager.instance();
		int retCode = manager.inputingComplete(sysdocno, user_id, dept_code);
		
		String msg = "";
		if(retCode <= 0) {
			msg = "�Է¿Ϸ� ó�� ����\n�ٽ� �õ����ֽñ� �ٶ��ϴ�";
			
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg", msg));
			saveMessages(request,messages);
		}

		return mapping.findForward("returnlist");
	}
}