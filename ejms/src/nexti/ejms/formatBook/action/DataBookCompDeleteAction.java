/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���տϷ� �����ڷ��� �����ڷ���� action
 * ����:
 */
package nexti.ejms.formatBook.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.formatBook.form.DataBookForm;
import nexti.ejms.formatBook.model.DataBookBean;
import nexti.ejms.formatBook.model.FormatBookManager;
import nexti.ejms.util.FileManager;

public class DataBookCompDeleteAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		//�⺻ ���� ����
		ServletContext context = getServlet().getServletContext();
		int sysdocno = 0;
		int formseq = 0;
		String filenm = null;
		
		//Form���� �Ѿ�� �� �������� 
		DataBookForm dataBookForm = (DataBookForm)form;
		
		sysdocno = dataBookForm.getSysdocno();
		formseq = dataBookForm.getFormseq();
		
		DataBookBean dataBean = new DataBookBean();
		
		//Form �����͸� Bean���� ����
		BeanUtils.copyProperties(dataBean, dataBookForm);
		
		FormatBookManager manager = FormatBookManager.instance();
		
		//DataBookFrm �������������� ��������
		filenm = manager.getDataBookCompView(sysdocno, formseq).getFilenm();
		
		int retCode = manager.DataBookCompDelete(sysdocno, formseq);
		
		if(retCode > 0) {
			String delFile = filenm;
			if ( delFile != null && delFile.trim().equals("") == false) {
				FileManager.doFileDelete(context.getRealPath(delFile));
			}
		}
		
		request.setAttribute("savecheck", new Integer(retCode));
		request.setAttribute("sysdocno", new Integer(sysdocno));
		request.setAttribute("formseq", new Integer(formseq));
		
		return mapping.findForward("hiddenframe");
	}
}