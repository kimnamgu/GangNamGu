/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력하기 입력담당자 담당단위지정 여부 확인 action
 * 설명:
 */
package nexti.ejms.inputing.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.inputing.model.InputingManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class IsAssignChrgUnitAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//세션정보 가져오기
		HttpSession session = request.getSession();
		
		String user_id = session.getAttribute("user_id").toString();
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				user_id = originUserBean.getUser_id();
			}
		}
		
		//입력담당자의 담당단위 지정 여부 확인
		InputingManager manager = InputingManager.instance();
		String retCode = manager.IsAssignInputUsrcharge(user_id);
		
		StringBuffer resultXML = new StringBuffer();
		
		resultXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		resultXML.append("\n<result>");
		resultXML.append("\n<retCode>" + retCode + "</retCode>");
		resultXML.append("\n</result>");
		
		response.setContentType("text/xml;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println(resultXML.toString());
		out.flush();
		out.close();

		return null;
	}
}