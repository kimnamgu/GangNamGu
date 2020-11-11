/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 메세지 전송(Active Post)
 * 설명:
 */
package nexti.ejms.messanger;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nexti.ejms.common.rootAction;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MessageSendAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		HttpSession session = request.getSession();
		String user_id = Utils.nullToEmptyString((String)session.getAttribute("user_id"));  
		String target = Utils.nullToEmptyString(request.getParameter("target"));
		String title = Utils.nullToEmptyString(request.getParameter("title"));
		String content = Utils.nullToEmptyString(request.getParameter("content"));
		String deptTarget = Utils.nullToEmptyString(request.getParameter("deptTarget"));
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				user_id = originUserBean.getUser_id();
			}
		}
		
		target = URLDecoder.decode(target, "UTF-8");
		title = URLDecoder.decode(title, "UTF-8");
		content = URLDecoder.decode(content, "UTF-8");
		deptTarget = URLDecoder.decode(deptTarget, "UTF-8");
		
		int returnCode;
		try {
			MessangerRelayBean mrBean = new MessangerRelayBean();
			mrBean.setRelayMode(MessangerRelay.SEND_MESSAGE);
			mrBean.setDoc_name_title(title);
			mrBean.setDoc_desc_content(content);
			mrBean.setSnd_user_usersn(UserManager.instance().getUserInfo(user_id).getUser_sn());
			
			ArrayList rcv_user_usersn_list = new ArrayList();
			String[] arrTarget = target.split(",");
			for ( int i = 0; i < arrTarget.length; i++ ) {
				rcv_user_usersn_list.add(new String(arrTarget[i]));
			}
			
			String[] arrDeptTarget = deptTarget.split(",");
			for ( int i = 1; arrDeptTarget.length > 1 && i < arrDeptTarget.length; i++ ) {
				List deliveryWaitUserList = MessangerRelayManager.instance().getDeliveryWaitUserList(Integer.parseInt(arrDeptTarget[0]), arrDeptTarget[i]);
				for ( int j = 0; deliveryWaitUserList != null && j < deliveryWaitUserList.size(); j++ ) {
					rcv_user_usersn_list.add((String)deliveryWaitUserList.get(j));
				}
			}
			mrBean.setList(rcv_user_usersn_list);
			
			new MessangerRelay(mrBean).start();
			returnCode = 0;
		} catch ( Exception e ) {
			returnCode = 1;
		}
		
		StringBuffer resultXML = new StringBuffer();
		resultXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		resultXML.append("<result>\n");
		resultXML.append("\t<returnCode>" + returnCode + "</returnCode>\n");
		resultXML.append("</result>\n");
		response.setContentType("text/xml;charset=UTF-8");
		response.getWriter().print(resultXML);
		return null;
	}
}