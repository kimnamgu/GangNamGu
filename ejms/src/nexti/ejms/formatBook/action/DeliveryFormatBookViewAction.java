/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ϱ� �����ڷ��� ���� action
 * ����:
 */
package nexti.ejms.formatBook.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.formatBook.form.FileBookFrameForm;
import nexti.ejms.formatBook.form.FormatBookForm;
import nexti.ejms.formatBook.model.FormatBookBean;
import nexti.ejms.formatBook.model.FormatBookManager;

public class DeliveryFormatBookViewAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		//Form���� �Ѿ�� �� �������� 
		FormatBookForm formatBookForm = (FormatBookForm)form;
		
		int sysdocno = 0;
		int formseq = 0;
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		if(request.getParameter("formseq") != null) {
			formseq = Integer.parseInt(request.getParameter("formseq"));
		}
		
		//������ ��� ���� ��������
		FormatBookManager manager = FormatBookManager.instance();
		FormatBookBean bookBean = manager.getFormatFormView(sysdocno, formseq);
		
		formatBookForm.setFormcomment(bookBean.getFormcomment());
		
		//������ �������� ����Ʈ ��������
		FileBookFrameForm fileBookForm = new FileBookFrameForm();
		List fileList = manager.getFileBookFrm(sysdocno, formseq);
		fileBookForm.setListfilebook(fileList);
		
		request.setAttribute("fileBookForm", fileBookForm);
		
		return mapping.findForward("detailview");
	}
}