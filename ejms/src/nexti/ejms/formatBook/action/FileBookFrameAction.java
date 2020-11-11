/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식작성 제본자료형 첨부파일 목록 action
 * 설명:
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
		
		//1:새로작성,2:공통양식가져오기,3:사용했던양식가져오기,4:공통양식작성,5:양식수정,6:공통양식수정
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