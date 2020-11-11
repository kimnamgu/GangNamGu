/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 결재선지정 추가 action
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
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class commreqformInsertAction extends rootPopupAction{

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
	
		request.setCharacterEncoding("UTF-8");
		
		String cmd = request.getParameter("cmd");
		int	reqformno =0;
		int reqseq = 0;
		reqformno = Integer.parseInt(request.getParameter("reqformno"));
		reqseq = Integer.parseInt(request.getParameter("reqseq"));
		String idList = request.getParameter("idList");
		String nameList = request.getParameter("nameList");
		String gubunList = request.getParameter("gubunList"); 
		
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
		String[] gubun = null;

		commapprovalManager manager = commapprovalManager.instance();
		
		if (!"".equals(idList)){
			userId = idList.split(":");
		}
		
		if (!"".equals(nameList)){
			userName = nameList.split(":");
		}
		
		if (!"".equals(gubunList)){
			gubun = gubunList.split(":");
		}
		
		commapprovalBean bean = null;
		
		if (userId != null){
			for (int i = 0; i < userId.length; i++){
				bean = new commapprovalBean();

				bean.setUserId(userId[i]);
				bean.setUserName(userName[i]);
				bean.setGubun(gubun[i]);
				
				userList.add(bean);
				bean = null;
			}
		}
		
		try {
			if ("INSERT".equals(cmd)){
				ret = manager.commreqformInsert(reqformno, reqseq, userList, dept_code, user_id);
			}
		} catch(Exception e) {
			ret = -1; 
		} finally {
			StringBuffer resultXML = new StringBuffer();
			resultXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			resultXML.append("\n<result>");
			resultXML.append("\n<cmd>" + cmd + "</cmd>");
			resultXML.append("\n<retCode>" + ret + "</retCode>");
			resultXML.append("\n</result>");
			
			response.setContentType("text/xml;charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println(resultXML.toString());
			out.flush();
			out.close();
		}
		
		return null;
	}
}