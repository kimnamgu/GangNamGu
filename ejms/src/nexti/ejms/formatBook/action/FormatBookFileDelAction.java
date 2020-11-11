/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ۼ� �����ڷ��� ÷������ ���� action
 * ����:
 */
package nexti.ejms.formatBook.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.formatBook.form.FileBookFrameForm;
import nexti.ejms.formatBook.model.FileBookBean;
import nexti.ejms.formatBook.model.FormatBookManager;

public class FormatBookFileDelAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		//�⺻ ���� ����
		ServletContext context = getServlet().getServletContext();
		
		//Form���� �Ѿ�� �� �������� 
		FileBookFrameForm fbfform = (FileBookFrameForm)form;
		
		//1:�����ۼ�,2:�����İ�������,3:����ߴ���İ�������,4:�������ۼ�,5:��ļ���,6:�����ļ���
		int type = fbfform.getType();
		int sysdocno = fbfform.getSysdocno();
		int formseq = fbfform.getFormseq();
		int fileseq = fbfform.getFileseq();
		
		FileBookBean fbbean = new FileBookBean();
		
		fbbean.setSysdocno(sysdocno);
		fbbean.setFormseq(formseq);
		fbbean.setFileseq(fileseq);
		
		FormatBookManager fbmgr = FormatBookManager.instance();
		
		if(type <= 3 || type == 5) {
			
			fbmgr.delFileBook(fbbean, context);
			
		} else if(type == 4 || type == 6) {
			
			fbmgr.delCommFileBook(fbbean, context);
			
		}
				
		return mapping.findForward("fileBookFrame");
	}
}