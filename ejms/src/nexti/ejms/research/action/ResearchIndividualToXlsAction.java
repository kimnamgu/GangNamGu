/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 결과 개인별 엑셀다운로드 action
 * 설명:
 */
package nexti.ejms.research.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.research.model.ResearchManager;

public class ResearchIndividualToXlsAction extends rootPopupAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		int rchno = Integer.parseInt(request.getParameter("rchno"));
		
		ResearchManager rchmgr = ResearchManager.instance();
		
		String title = rchmgr.getRchMst(rchno, "").getTitle();
		int formCount = rchmgr.getRchSubCount(rchno);
		List resultList = rchmgr.getRchIndividualList(rchno);
		
		if (resultList == null || resultList.size() == 0) {
			response.setContentType("text/html;charset=euc-kr");
			response.getWriter().print("<script>alert('개인별 결과보기는 응답자가 있을 경우만 가능합니다');</script>");
			response.flushBuffer();
			return null;
		} else {
			request.setAttribute("title", title);
			request.setAttribute("formCount", new Integer(formCount));
			request.setAttribute("resultList", resultList);
		}
				
		return mapping.findForward("xls");
	}
}