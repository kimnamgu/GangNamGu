/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ۼ� ���߰��� ������� action
 * ����:
 */
package nexti.ejms.formatLine.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.format.model.FormatBean;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.formatLine.form.FormatLineForm;
import nexti.ejms.formatBook.model.FormatBookManager;

public class FormatLineDefAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {

		FormatLineForm flform = (FormatLineForm)form;
		
		//1:�����ۼ�,2:�����İ�������,3:����ߴ���İ�������,4:�������ۼ�,5:��ļ���,6:�����ļ���
		int type = flform.getType();
		int sysdocno = flform.getSysdocno();
		int formseq = flform.getFormseq();
		
		FormatManager fmgr = FormatManager.instance();
		FormatBookManager fbmgr = FormatBookManager.instance();
		
		String formkind = "01";
		
		flform.setFormkind(formkind);
		flform.setFormkindname(fmgr.getFormkindName(formkind));
		
		if(type == 1) {
		
			//�Ϸ���� ���� �����ڷ��� ���ε� ���� ����
			if(formseq == fmgr.getNewFormatseq(sysdocno)) {
				flform.setFormseq(0);
				
				ServletContext context = getServlet().getServletContext();
				fbmgr.delAllFileBook(sysdocno, fmgr.getNewFormatseq(sysdocno), context);
			}
		
		} else if(type == 4) {
			
			//�Ϸ���� ���� �����ڷ��� ���ε� ���� ����
			if(formseq == 0) {
				ServletContext context = getServlet().getServletContext();
				fbmgr.delAllCommFileBook(fmgr.getNewCommFormatseq(), context);
			} else if(formseq == fmgr.getNewCommFormatseq()) {
				flform.setFormseq(0);
				
				ServletContext context = getServlet().getServletContext();
				fbmgr.delAllCommFileBook(fmgr.getNewCommFormatseq(), context);
			}
			
		} else if(type == 5) {
			
			FormatBean fbean = fmgr.getFormat(sysdocno, formseq);
			
			if(flform.getFormhtml().equals("") == true) {
				flform.setFormtitle(fbean.getFormtitle());
				flform.setFormcomment(fbean.getFormcomment());
				flform.setFormhtml(fbean.getFormhtml());
			}
			
		} else if(type == 6) {
			
			FormatBean fbean = fmgr.getCommFormat(formseq);
			
			if(flform.getFormhtml().equals("") == true) {
				flform.setFormtitle(fbean.getFormtitle());
				flform.setFormcomment(fbean.getFormcomment());
				flform.setFormhtml(fbean.getFormhtml());
			}
			
		}

		return mapping.findForward("formatLineDef");
	}
}