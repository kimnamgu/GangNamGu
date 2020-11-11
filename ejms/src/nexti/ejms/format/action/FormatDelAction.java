/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식작성 양식삭제 action
 * 설명:
 */
package nexti.ejms.format.action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.colldoc.form.ColldocForm;
import nexti.ejms.format.model.FormatManager;

public class FormatDelAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
				
		//Form에서 넘어온 값 가져오기
		ColldocForm cdform = (ColldocForm)form;
		
		//1:새로작성,2:공통양식가져오기,3:사용했던양식가져오기,4:공통양식작성,5:양식수정,6:공통양식수정
		int type = cdform.getType();
		int sysdocno = cdform.getSysdocno();

		ServletContext context = getServlet().getServletContext();
		
		//선택한 취합양식 삭제
		FormatManager fmmgr = FormatManager.instance();
		
		if(type == 1) {
			int formseq = cdform.getFormseq();
			fmmgr.delFormat(sysdocno, formseq, context);
			return mapping.findForward("colldocFormView");
			
		} else if(type == 4) {

			for(int i = 0; i < cdform.getListdelete().length; i++) {
				int formseq = Integer.parseInt(cdform.getListdelete()[i], 10);
				fmmgr.delCommFormat(formseq, context);
			}
			
			PrintWriter out = response.getWriter();
			out.write("<script language=javascript>" +
					  "  parent.form_submit();" +
					  "</script>");
			out.close();
			
			return null;
			
		}
		
		return null;
	}
}