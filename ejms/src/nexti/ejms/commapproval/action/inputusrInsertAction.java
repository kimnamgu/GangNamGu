/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력담당자지정 추가 action
 * 설명:
 */
package nexti.ejms.commapproval.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.commapproval.model.commapprovalManager;
import nexti.ejms.commapproval.model.commapprovalBean;
import nexti.ejms.common.rootPopupAction;
import nexti.ejms.inputing.model.InputingManager;
import nexti.ejms.messanger.MessangerRelay;
import nexti.ejms.messanger.MessangerRelayBean;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class inputusrInsertAction extends rootPopupAction{

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
	
		request.setCharacterEncoding("UTF-8");
		
		String cmd = request.getParameter("cmd");
		int	sysdocno =0;
		sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		String idList = request.getParameter("idList");
		String nameList = request.getParameter("nameList");
		
		//세션정보 가져오기
		HttpSession session = request.getSession();		
		String user_id = (String)session.getAttribute("user_id");
		String dept_code = (String)session.getAttribute("dept_code");
		
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
		
		int ret = 0;
		ArrayList userList = new ArrayList();
		
		String[] userId = null;
		String[] userName = null;

		commapprovalManager manager = commapprovalManager.instance();
		
		if (!"".equals(idList)){
			userId = idList.split(":");
		}
		
		if (!"".equals(nameList)){
			userName = nameList.split(":");
		}
		
		commapprovalBean bean = null;
		
		if (userId != null){
			for (int i = 0; i < userId.length; i++){
				bean = new commapprovalBean();

				bean.setUserId(userId[i]);
				bean.setUserName(userName[i]);
				
				userList.add(bean);
				bean = null;
			}
		}
		
		if ("INSERT".equals(cmd)){
			ret = manager.inputusrInsert(sysdocno, userList, user_id, dept_code);
		}
		
		String msg = "";
		if ( ret > 0 ) {
			InputingManager inputmgr = InputingManager.instance();
			String[] inputInfo = inputmgr.isTgtdeptInputComplete(sysdocno, dept_code);
			if ( inputInfo != null && inputInfo[0].equals("-1") == false ) {	//제출부서입력완료 완료
				inputmgr.doLastInputCompleteCheck(Integer.parseInt(inputInfo[0], 10), inputInfo[2], inputInfo[1]);
				msg = "입력담당자가 모두 입력완료를 하였습니다.";
			} else {
				MessangerRelayBean mrBean = new MessangerRelayBean();
				mrBean.setRelayMode(MessangerRelay.DELIVERY);
				mrBean.setSysdocno(sysdocno);
				mrBean.setDeptcode(dept_code);
				new MessangerRelay(mrBean).start();				
			}
		}
		
		StringBuffer resultXML = new StringBuffer();
		
		resultXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		resultXML.append("\n<result>");
		resultXML.append("\n<cmd>" + cmd + "</cmd>");
		resultXML.append("\n<retCode>" + ret + "</retCode>");
		resultXML.append("\n<msg>" + msg + "</msg>");
		resultXML.append("\n</result>");
		
		response.setContentType("text/xml;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println(resultXML.toString());
		out.flush();
		out.close();
		
		return null;
	}
}