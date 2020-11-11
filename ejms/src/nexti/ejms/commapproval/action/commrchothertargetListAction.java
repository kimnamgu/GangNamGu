/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ������Ÿ������� ���
 * ����:
 */
package nexti.ejms.commapproval.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.commapproval.model.commapprovalManager;
import nexti.ejms.common.rootAction;

public class commrchothertargetListAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		commapprovalManager manager = commapprovalManager.instance();

		String xml = manager.getGradeListXml();

	    response.setContentType("text/xml;charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    out.write(xml);
	    out.flush();
	    out.close();
		
		return null;
	}
}