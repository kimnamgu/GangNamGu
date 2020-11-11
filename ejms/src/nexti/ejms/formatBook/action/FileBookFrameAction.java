/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ۼ� �����ڷ��� ÷������ ��� action
 * ����:
 */
package nexti.ejms.formatBook.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.formatBook.form.FileBookFrameForm;
import nexti.ejms.formatBook.model.FormatBookManager;

public class FileBookFrameAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
				
		FileBookFrameForm fbform = (FileBookFrameForm)form;
		
		//1:�����ۼ�,2:�����İ�������,3:����ߴ���İ�������,4:�������ۼ�,5:��ļ���,6:�����ļ���
		int type = fbform.getType();
		int sysdocno = fbform.getSysdocno();
		int formseq = fbform.getFormseq();
		
		FormatBookManager fbmgr = FormatBookManager.instance();
		
		if(type <= 3 || type == 5) {
		
			fbform.setListfilebook(fbmgr.getListFileBook(sysdocno, formseq));

		} else if(type == 4 || type == 6) {
			
			fbform.setListfilebook(fbmgr.getListCommFileBook(formseq));
			
		}
		
		return mapping.findForward("fileBookFrame");
	}
}