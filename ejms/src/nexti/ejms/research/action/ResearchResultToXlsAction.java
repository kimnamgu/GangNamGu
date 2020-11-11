/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� ��� ��ȸ���� �����ٿ�ε� action
 * ����: 
 */
package nexti.ejms.research.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.research.model.ResearchManager;

public class ResearchResultToXlsAction extends rootPopupAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		try {
			System.setProperty("java.awt.headless", "true");
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		int rchno = Integer.parseInt(request.getParameter("rchno"));
		
		ResearchManager rchmgr = ResearchManager.instance();
		
		String title = rchmgr.getRchMst(rchno, "").getTitle();		
		boolean existResult = rchmgr.getRchResult(response, rchno, title);
				
		if ( !existResult ) {
			response.setContentType("text/html;charset=euc-kr");
			response.getWriter().print("<script>alert('��ȸ�� �������� �����ڰ� ���� ��츸 �����մϴ�');</script>");
			response.flushBuffer();
		}
				
		return null;
	}
}