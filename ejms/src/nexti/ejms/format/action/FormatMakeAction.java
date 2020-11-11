/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ۼ� action
 * ����:
 */
package nexti.ejms.format.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.format.form.FormatForm;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.formatBook.model.FormatBookManager;

public class FormatMakeAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		FormatManager fmgr = FormatManager.instance();
		FormatBookManager fbmgr = FormatBookManager.instance();
		
		FormatForm fmform = (FormatForm)form;
		
		String reqSysdocno = (String)request.getParameter("sysdocno");
		String reqFormseq = (String)request.getParameter("formseq");
		
		int sysdocno = Integer.parseInt(reqSysdocno, 10);
		int formseq = Integer.parseInt(reqFormseq, 10);
		
		//����߰��� ����� �߰��� �ý��� ������ȣ,��� �Ϸù�ȣ�� ��������
		fmform.setSysdocno(sysdocno);
		fmform.setFormseq(formseq);
		
		//���θ����(formseq=0)�� �� ���ο� ����Ϸù�ȣ ��������
		if(formseq == 0) {
			ServletContext context = getServlet().getServletContext();
			fbmgr.delAllFileBook(sysdocno, fmgr.getNewFormatseq(sysdocno), context);
		} else if(formseq == fmgr.getNewFormatseq(sysdocno)) {
			fmform.setFormseq(0);
			
			ServletContext context = getServlet().getServletContext();
			fbmgr.delAllFileBook(sysdocno, fmgr.getNewFormatseq(sysdocno), context);
		}
		
		fmform.setType(1);

		return mapping.findForward("formatMake");
	}
}