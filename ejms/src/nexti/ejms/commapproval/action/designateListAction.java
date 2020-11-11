/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���缱���� ��� action
 * ����:
 */
package nexti.ejms.commapproval.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.commapproval.model.commapprovalManager;
import nexti.ejms.common.rootPopupAction;

public class designateListAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		HttpSession session = request.getSession();
		String deptId = (String)session.getAttribute("dept_code");
		
		commapprovalManager manager = commapprovalManager.instance();

		String designateTree = manager.getUserListXml(deptId);

	    response.setContentType("text/xml;charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    out.write(designateTree);
	    out.flush();
	    out.close();
		
		return null;
	}
}