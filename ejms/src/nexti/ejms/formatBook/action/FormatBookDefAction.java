/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ۼ� �����ڷ��� ������� action
 * ����:
 */
package nexti.ejms.formatBook.action;

import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.format.model.FormatBean;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.formatBook.form.FormatBookForm;
import nexti.ejms.formatBook.model.FormatBookManager;

public class FormatBookDefAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		FormatBookForm fbform = (FormatBookForm)form;
		
		//1:�����ۼ�,2:�����İ�������,3:����ߴ���İ�������,4:�������ۼ�,5:��ļ���,6:�����ļ���
		int type = fbform.getType();
		int sysdocno = fbform.getSysdocno();
		int formseq = fbform.getFormseq();
		
		FormatManager fmgr = FormatManager.instance();
		FormatBookManager fbmgr = FormatBookManager.instance();
		
		fbform.setFormkind("03");
		
		String[] listcategorynm = null;
		
		if(type == 1) {
			
			fbform.setFormseq(fmgr.getNewFormatseq(sysdocno));
			fbform.setFilecount(fmgr.getFilecount(sysdocno, formseq));
			
			listcategorynm = fbform.getListcategorynm1();

			
		} else if(type == 4) {
			
			fbform.setFormseq(fmgr.getNewCommFormatseq());
			fbform.setFilecount(fmgr.getCommFilecount(formseq));
			
			listcategorynm = fbform.getListcategorynm1();

			
		} else if(type == 5) {
			
			if(request.getParameter("load") != null) {	
				FormatBean fbean = fmgr.getFormat(sysdocno, formseq);
				
				fbform.setFormtitle(fbean.getFormtitle());
				fbform.setFormcomment(fbean.getFormcomment());
				fbform.setFilecount(fmgr.getFilecount(sysdocno, formseq));
				
				listcategorynm = fbmgr.getListCategory(sysdocno, formseq);
			} else {
				fbform.setFilecount(fmgr.getFilecount(sysdocno, formseq));
				listcategorynm = fbform.getListcategorynm1();
			}

			
		} else if(type == 6) {
			
			if(request.getParameter("load") != null) {
				FormatBean fbean = fmgr.getCommFormat(formseq);
				
				fbform.setFormtitle(fbean.getFormtitle());
				fbform.setFormcomment(fbean.getFormcomment());
				fbform.setFilecount(fmgr.getCommFilecount(formseq));
				
				listcategorynm = fbmgr.getListCommCategory(formseq);
			} else {
				fbform.setFilecount(fmgr.getCommFilecount(formseq));
				listcategorynm = fbform.getListcategorynm1();
			}
		
		}
		
		if(listcategorynm != null) {
			Vector vt = new Vector();
			
			for(int i = 0; i < listcategorynm.length; i++)
				vt.add(new LabelValueBean(listcategorynm[i], listcategorynm[i]));
			
			Collection col = vt;
			
			fbform.setListcategorynm2(col);
		}

		return mapping.findForward("formatBookDef");
	}
}