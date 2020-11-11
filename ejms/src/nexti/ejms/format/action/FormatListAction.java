/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식작성 목록 action
 * 설명:
 */
package nexti.ejms.format.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootFrameAction;
import nexti.ejms.format.form.FormatForm;
import nexti.ejms.format.model.FormatManager;

public class FormatListAction extends rootFrameAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//Form에서 넘어온 값 가져오기
		FormatForm formatForm = (FormatForm)form;
		
		int sysdocno = 0;
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"), 10);
		}
		
		// 양식 리스트 가져오기
		FormatManager manager = FormatManager.instance();
		List listform = manager.viewFormList(sysdocno);
		formatForm.setListform(listform);
		
		return mapping.findForward("formatlist");
	}
}