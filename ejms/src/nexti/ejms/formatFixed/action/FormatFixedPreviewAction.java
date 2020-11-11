/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식작성 고정양식형 미리보기 action
 * 설명:
 */
package nexti.ejms.formatFixed.action;

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
import nexti.ejms.formatFixed.model.FormatFixedBean;
import nexti.ejms.formatFixed.model.FormatFixedManager;

public class FormatFixedPreviewAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		FormatManager fmgr = FormatManager.instance();
		FormatFixedManager ffmgr = FormatFixedManager.instance();
		
		//행추가형과 고정양식형의 JSP가 동일하므로 행추가형의 ActionForm 사용	
		FormatLineForm flform = (FormatLineForm)form;
		
		//1:새로작성,2:공통양식가져오기,3:사용했던양식가져오기,4:공통양식작성,5:양식수정,6:공통양식수정
		//int type = flform.getType();
		int sysdocno = flform.getSysdocno();
		int formseq = flform.getFormseq();
		
		try {
			InputDeptSearchBoxBean idsbbean = new InputDeptSearchBoxBean();
			//고정양식형 양식 구조 가져오기
			FormatFixedBean ffbean = ffmgr.getFormatFixedDataView(sysdocno, formseq, idsbbean, "정보통신팀", true, true);
			ffbean.setSysdocno(sysdocno);
			ffbean.setFormseq(formseq);			
			ffbean.setSch_deptcd1("");
			ffbean.setSch_deptcd2("");
			ffbean.setSch_deptcd3("");
			ffbean.setSch_deptcd4("");
			ffbean.setSch_deptcd5("");
			ffbean.setSch_deptcd6("");
			ffbean.setSch_chrgunitcd("");
			ffbean.setSch_inputusrid("");
			ffbean.setTotalState(true);
			ffbean.setTotalShowStringState(true);
			ffbean.setSubtotalState(true);
			ffbean.setSubtotalShowStringState(true);
			ffbean.setIncludeNotSubmitData(true);
			ffbean.setListform(ffmgr.getFormFixedDataList(ffbean));
			
			BeanUtils.copyProperties(flform, ffbean);
			//최종취합자료 양식 헤더를 formbodyhtml_ext에 담아서 넘김
			flform.setFormbodyhtml_ext(ffmgr.getFormatFormView(sysdocno, formseq, "부서").getFormheaderhtml());
			
			ffmgr.formatFixedPreviewEnd(sysdocno, formseq);	//미리보기 임시데이터 삭제
			
			flform.setSysdocno(Integer.parseInt((String)request.getParameter("oldSysdocno"), 10));
			flform.setFormseq(Integer.parseInt((String)request.getParameter("oldFormseq"), 10));
			flform.setFormkind("02");
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

		return mapping.findForward("formatFixedPreview");
	}
}