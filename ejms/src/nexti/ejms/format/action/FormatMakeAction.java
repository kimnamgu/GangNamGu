/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식작성 action
 * 설명:
 */
package nexti.ejms.format.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.format.form.FormatForm;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.formatBook.model.FormatBookManager;

public class FormatMakeAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		FormatManager fmgr = FormatManager.instance();
		FormatBookManager fbmgr = FormatBookManager.instance();
		
		FormatForm fmform = (FormatForm)form;
		
		String reqSysdocno = (String)request.getParameter("sysdocno");
		String reqFormseq = (String)request.getParameter("formseq");
		
		int sysdocno = Integer.parseInt(reqSysdocno, 10);
		int formseq = Integer.parseInt(reqFormseq, 10);
		
		//양식추가시 양식이 추가될 시스템 문서번호,양식 일련번호를 가져오기
		fmform.setSysdocno(sysdocno);
		fmform.setFormseq(formseq);
		
		//새로만들기(formseq=0)일 때 새로운 양식일련번호 가져오기
		if(formseq == 0) {
			ServletContext context = getServlet().getServletContext();
			fbmgr.delAllFileBook(sysdocno, fmgr.getNewFormatseq(sysdocno), context);
		} else if(formseq == fmgr.getNewFormatseq(sysdocno)) {
			fmform.setFormseq(0);
			
			ServletContext context = getServlet().getServletContext();
			fbmgr.delAllFileBook(sysdocno, fmgr.getNewFormatseq(sysdocno), context);
		}
		
		fmform.setType(1);

		return mapping.findForward("formatMake");
	}
}