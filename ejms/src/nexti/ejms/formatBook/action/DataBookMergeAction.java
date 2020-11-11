/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합완료 제본자료형 제본자료생성 action
 * 설명:
 */
package nexti.ejms.formatBook.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;

public class DataBookMergeAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		//기본 정보 설정
		int sysdocno = 0;
		int formseq = 0;
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		if(request.getParameter("formseq") != null) {
			formseq = Integer.parseInt(request.getParameter("formseq"));
		}
		
		request.setAttribute("filenmlist", request.getParameter("filenmlist"));
		request.setAttribute("sysdocno", new Integer(sysdocno));
		request.setAttribute("formseq", new Integer(formseq));
		
		return mapping.findForward("databookmerge");
	}
}