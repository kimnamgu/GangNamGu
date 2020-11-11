/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 결과 조회내용 엑셀다운로드 action
 * 설명: 
 */
package nexti.ejms.research.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.research.model.ResearchManager;

public class ResearchResultToXlsAction extends rootPopupAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		try {
			System.setProperty("java.awt.headless", "true");
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		int rchno = Integer.parseInt(request.getParameter("rchno"));
		
		ResearchManager rchmgr = ResearchManager.instance();
		
		String title = rchmgr.getRchMst(rchno, "").getTitle();		
		boolean existResult = rchmgr.getRchResult(response, rchno, title);
				
		if ( !existResult ) {
			response.setContentType("text/html;charset=euc-kr");
			response.getWriter().print("<script>alert('조회된 결과보기는 응답자가 있을 경우만 가능합니다');</script>");
			response.flushBuffer();
		}
				
		return null;
	}
}