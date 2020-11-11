/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���տϷ� ���տϷ�ó�� action
 * ����:
 */
package nexti.ejms.colldoc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.colldoc.model.ColldocManager;

public class CollcompProcessAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		String user_id = "";
		String retPage = "";
		int sysdocno = 0;
		
		//�������� ��������
		HttpSession session = request.getSession();
		user_id = session.getAttribute("user_id").toString();
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		if(request.getParameter("retpage") != null) {
			retPage = request.getParameter("retpage");
		}
		
		//�������ϱ�
		ColldocManager manager = ColldocManager.instance();
		manager.collCompProcess(sysdocno, user_id);
		
		request.setAttribute("sysdocno", new Integer(sysdocno));
		
		return mapping.findForward(retPage);
	}
}