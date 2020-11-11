/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 코드관리 코드수정 action
 * 설명:
 */
package nexti.ejms.ccd.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.ccd.form.CodeForm;
import nexti.ejms.ccd.model.CcdManager;
import nexti.ejms.ccd.model.CcdBean;

public class CodePopupAction extends rootPopupAction {

	public ActionForward doService(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) 
	 	throws Exception {

		CodeForm codeForm = (CodeForm)form;
		CcdManager codeManager = CcdManager.instance();
		
		String mode = codeForm.getMode();
		String main_cd = codeForm.getCcd_cd();        //주코드
		String sub_cd = codeForm.getCcd_sub_cd();     //부코드		
		
		if("main_s".equals(mode)){
			//주코드 Popup
			CcdBean bean = codeManager.detailCcd(main_cd, "...");
			
			if(bean != null){
				codeForm.setCcd_name(bean.getCcdname());
			}
			
			return mapping.findForward("main");
		} else {
			//부코드 Popup
			CcdBean mainBean = codeManager.detailCcd(main_cd,"...");
			CcdBean subBean = codeManager.detailCcd(main_cd, sub_cd);	
			
			if(mainBean != null){
				codeForm.setCcd_name(mainBean.getCcdname());     //주코드명
			}
			
			if(subBean != null){
				
				codeForm.setCcd_sub_name(subBean.getCcdname()); //부코드명
				codeForm.setCcd_desc(subBean.getCcddesc());     //부코드 상세 설명
			}
			
			return mapping.findForward("sub");
		}
	}
}

