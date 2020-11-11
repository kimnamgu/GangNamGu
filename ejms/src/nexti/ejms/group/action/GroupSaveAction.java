/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합대상지정 배포목록 저장 action
 * 설명:
 */
package nexti.ejms.group.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.group.model.GroupManager;

public class GroupSaveAction extends rootAction {

	public ActionForward doService(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) 
		throws Exception {
		
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");  //로그인 유저
		
		String type = (String)request.getParameter("type");		
		String groupName = (String)request.getParameter("groupName");
		String codeList = (String)request.getParameter("codeList");
		String codeGbnList = (String)request.getParameter("codeGbnList");
		String nameList = (String)request.getParameter("nameList");
		String deptId = (String)request.getParameter("deptId");
		
		GroupManager grpmgr = GroupManager.instance();
		grpmgr.saveGroup(type, groupName, codeList, codeGbnList, nameList, deptId, user_id);
		
		StringBuffer msg = new StringBuffer();
		msg.append("<script> \n");
		if ( type.equals("insert") == true ) {
			msg.append("parent.divDisplay(1); \n");
			msg.append("parent.document.addButton.onclick = parent.addDept1; \n");
			msg.append("parent.document.forms[0].groupName.value = ''; \n");
			msg.append("alert('저장되었습니다'); \n");
		}
		msg.append("parent.treeformation2State = false; \n");
		msg.append("parent.loadDistribute(); \n");
		msg.append("</script> \n");
		
		response.setContentType("text/html;charset=euc-kr");
		response.getWriter().write(msg.toString());
		response.getWriter().flush();
		response.getWriter().close();
		response.flushBuffer();
		
		return null;
	}
}