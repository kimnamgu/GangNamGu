/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식작성 제본자료형 첨부파일 삭제 action
 * 설명:
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
		
		//기본 정보 설정
		ServletContext context = getServlet().getServletContext();
		
		//Form에서 넘어온 값 가져오기 
		FileBookFrameForm fbfform = (FileBookFrameForm)form;
		
		//1:새로작성,2:공통양식가져오기,3:사용했던양식가져오기,4:공통양식작성,5:양식수정,6:공통양식수정
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