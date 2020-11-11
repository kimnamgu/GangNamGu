/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������Ͼ��ε� ������ action
 * ����:
 */
package nexti.ejms.format.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootFrameAction;
import nexti.ejms.formatBook.form.DataBookForm;

public class XlsUploadFrameAction extends rootFrameAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		DataBookForm dbform = (DataBookForm)form;
		
		int sysdocno = Integer.parseInt((String)request.getParameter("sysdocno"));
		int formseq = Integer.parseInt((String)request.getParameter("formseq"));
		
		dbform.setSysdocno(sysdocno);
		dbform.setFormseq(formseq);

		return mapping.findForward("xlsUploadFrame");
	}
}