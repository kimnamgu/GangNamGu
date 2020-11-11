/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식작성 행추가형 미리보기 action
 * 설명:
 */
package nexti.ejms.formatLine.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.format.model.InputDeptSearchBoxBean;
import nexti.ejms.formatLine.form.FormatLineForm;
import nexti.ejms.formatLine.model.FormatLineBean;
import nexti.ejms.formatLine.model.FormatLineManager;

public class FormatLinePreviewAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		FormatManager fmgr = FormatManager.instance();
		FormatLineManager flmgr = FormatLineManager.instance();
		
		FormatLineForm flform = (FormatLineForm)form;
		
		//1:새로작성,2:공통양식가져오기,3:사용했던양식가져오기,4:공통양식작성,5:양식수정,6:공통양식수정
		//int type = flform.getType();
		int sysdocno = flform.getSysdocno();
		int formseq = flform.getFormseq();
		
		try {
			InputDeptSearchBoxBean idsbbean = new InputDeptSearchBoxBean();
			//행추가형 양식 구조 가져오기
			FormatLineBean flbean = flmgr.getFormatLineDataView(sysdocno, formseq, idsbbean, true, true);
			
			//입력양식에서 추가 버튼(컬럼)삭제
			StringBuffer formbodyhtml = new StringBuffer(flbean.getFormbodyhtml());
			int findIndex1 = formbodyhtml.length();
			int findIndex2 = 0;
			while((findIndex2 = formbodyhtml.lastIndexOf("</tr>", findIndex1)) != -1) {
				findIndex1 = formbodyhtml.lastIndexOf("<td", findIndex2);
				int isEmpty = formbodyhtml.lastIndexOf("border:1 solid", findIndex2);
				if(isEmpty < findIndex1) {
					formbodyhtml.delete(findIndex1, findIndex2);
				}
			}
			flbean.setFormbodyhtml(formbodyhtml.toString());
			
			flbean.setListform(flmgr.getFormDataList(sysdocno, formseq, idsbbean, true, true, true));
			
			BeanUtils.copyProperties(flform, flbean);
			//최종취합자료 양식 헤더를 formbodyhtml_ext에 담아서 넘김
			flform.setFormbodyhtml_ext(flmgr.getFormatFormView(sysdocno, formseq).getFormheaderhtml());
			
			flmgr.formatLinePreviewEnd(sysdocno, formseq);	//미리보기 임시데이터 삭제
			
			flform.setSysdocno(Integer.parseInt((String)request.getParameter("oldSysdocno"), 10));
			flform.setFormseq(Integer.parseInt((String)request.getParameter("oldFormseq"), 10));
			flform.setFormkind("01");
			flform.setFormkindname(fmgr.getFormkindName(flform.getFormkind()));
		} catch (Exception e) {
			response.setContentType("text/html;charset=euc-kr");
			response.getWriter().write("<script>" +
									   "window.dialogArguments.parent.processingHide();" +
									   "alert('양식이 올바르지 않습니다.\\n이전단계로 돌아가 다시 작성해 주시기 바랍니다.');" +
									   "window.close();" +
									   "</script>");
			response.getWriter().flush();
			response.flushBuffer();
			return null;
		}

		return mapping.findForward("formatLinePreview");
	}
}