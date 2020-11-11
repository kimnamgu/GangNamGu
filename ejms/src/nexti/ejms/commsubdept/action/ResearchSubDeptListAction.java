/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 하위대상부서 추가 action
 * 설명:
 */
package nexti.ejms.commsubdept.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nexti.ejms.common.rootAction;
import nexti.ejms.dept.model.DeptBean;
import nexti.ejms.dept.model.DeptManager;
import nexti.ejms.util.Utils;
import nexti.ejms.util.commfunction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ResearchSubDeptListAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {

		HttpSession session = request.getSession();
		String user_id = Utils.nullToEmptyString((String)session.getAttribute("user_id")); 
		String deptid = Utils.nullToEmptyString(request.getParameter("deptid"));
		
		DeptManager deptMgr = DeptManager.instance();
		List subdeptList = deptMgr.getSubDeptList(deptid, true);
		int substring = commfunction.getDeptFullNameSubstringIndex(user_id);

		StringBuffer resultXML = new StringBuffer();
		resultXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		resultXML.append("<result>\n");
		for ( int i = 0; subdeptList != null && i < subdeptList.size(); i++ ) {			
			DeptBean dbean = (DeptBean)subdeptList.get(i);
			resultXML.append("<formation>\n");
			resultXML.append("\t<dept_id>" + dbean.getDept_id() + "</dept_id>\n");
			resultXML.append("\t<dept_name>" + dbean.getDept_name() + "</dept_name>\n");
			resultXML.append("\t<dept_fullname>" + dbean.getDept_fullname().substring(substring) + "</dept_fullname>\n");
			resultXML.append("</formation>\n");
		}
		resultXML.append("</result>\n");
		
		response.setContentType("text/xml;charset=UTF-8");
		response.getWriter().print(resultXML);
		
		return null;
	}
}