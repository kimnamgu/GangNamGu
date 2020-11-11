/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식작성 행추가형 양식정의 action
 * 설명:
 */
package nexti.ejms.formatLine.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.format.model.FormatBean;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.formatLine.form.FormatLineForm;
import nexti.ejms.formatBook.model.FormatBookManager;

public class FormatLineDefAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {

		FormatLineForm flform = (FormatLineForm)form;
		
		//1:새로작성,2:공통양식가져오기,3:사용했던양식가져오기,4:공통양식작성,5:양식수정,6:공통양식수정
		int type = flform.getType();
		int sysdocno = flform.getSysdocno();
		int formseq = flform.getFormseq();
		
		FormatManager fmgr = FormatManager.instance();
		FormatBookManager fbmgr = FormatBookManager.instance();
		
		String formkind = "01";
		
		flform.setFormkind(formkind);
		flform.setFormkindname(fmgr.getFormkindName(formkind));
		
		if(type == 1) {
		
			//완료되지 않은 제본자료형 업로드 파일 삭제
			if(formseq == fmgr.getNewFormatseq(sysdocno)) {
				flform.setFormseq(0);
				
				ServletContext context = getServlet().getServletContext();
				fbmgr.delAllFileBook(sysdocno, fmgr.getNewFormatseq(sysdocno), context);
			}
		
		} else if(type == 4) {
			
			//완료되지 않은 제본자료형 업로드 파일 삭제
			if(formseq == 0) {
				ServletContext context = getServlet().getServletContext();
				fbmgr.delAllCommFileBook(fmgr.getNewCommFormatseq(), context);
			} else if(formseq == fmgr.getNewCommFormatseq()) {
				flform.setFormseq(0);
				
				ServletContext context = getServlet().getServletContext();
				fbmgr.delAllCommFileBook(fmgr.getNewCommFormatseq(), context);
			}
			
		} else if(type == 5) {
			
			FormatBean fbean = fmgr.getFormat(sysdocno, formseq);
			
			if(flform.getFormhtml().equals("") == true) {
				flform.setFormtitle(fbean.getFormtitle());
				flform.setFormcomment(fbean.getFormcomment());
				flform.setFormhtml(fbean.getFormhtml());
			}
			
		} else if(type == 6) {
			
			FormatBean fbean = fmgr.getCommFormat(formseq);
			
			if(flform.getFormhtml().equals("") == true) {
				flform.setFormtitle(fbean.getFormtitle());
				flform.setFormcomment(fbean.getFormcomment());
				flform.setFormhtml(fbean.getFormhtml());
			}
			
		}

		return mapping.findForward("formatLineDef");
	}
}