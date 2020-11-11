/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부하기 제본자료형 보기 action
 * 설명:
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
		
		//Form에서 넘어온 값 가져오기 
		FormatBookForm formatBookForm = (FormatBookForm)form;
		
		int sysdocno = 0;
		int formseq = 0;
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		if(request.getParameter("formseq") != null) {
			formseq = Integer.parseInt(request.getParameter("formseq"));
		}
		
		//제본형 양식 구조 가져오기
		FormatBookManager manager = FormatBookManager.instance();
		FormatBookBean bookBean = manager.getFormatFormView(sysdocno, formseq);
		
		formatBookForm.setFormcomment(bookBean.getFormcomment());
		
		//제본형 샘플파일 리스트 가져오기
		FileBookFrameForm fileBookForm = new FileBookFrameForm();
		List fileList = manager.getFileBookFrm(sysdocno, formseq);
		fileBookForm.setListfilebook(fileList);
		
		request.setAttribute("fileBookForm", fileBookForm);
		
		return mapping.findForward("detailview");
	}
}