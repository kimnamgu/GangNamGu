/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 코드관리 화면 action
 * 설명:
 */
package nexti.ejms.ccd.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.util.Utils;
import nexti.ejms.ccd.form.CodeForm;
import nexti.ejms.ccd.model.CcdManager;
import nexti.ejms.ccd.model.CcdBean;

public class CodeFormAction extends rootAction {

	public ActionForward doService(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) 
		throws Exception {

		CodeForm codeForm = (CodeForm)form;
		String gbn = "";
		if ("".equals(Utils.nullToEmptyString((String)request.getAttribute("gbn")))) {
			gbn = codeForm.getGbn();
			if("".equals(gbn) || gbn ==null){
				gbn = "a";    //값이 없으면 관리자용 코드 목록을 기본으로 셋팅
			} 
		} else {
			gbn = (String)request.getAttribute("gbn");
		}
		
		//주코드 리스트 
		CcdManager codeManager = CcdManager.instance();
		List mainList = codeManager.mainCodeList(gbn);
		codeForm.setMainlist(mainList);		
		
		//부코드 리스트
		String ccd_cd = codeForm.getCcd_cd();
		String mode = codeForm.getMode();
		if(("".equals(ccd_cd)||"main_d".equals(mode)) && mainList.size() > 0) {
			if ( mainList != null && mainList.size() > 0 ) {
				CcdBean codeBean =(CcdBean)mainList.get(0);
				ccd_cd = codeBean.getCcdcd();
			}
		}
		List subList = codeManager.subCodeList(ccd_cd);
		codeForm.setSublist(subList);
		
		//화면처리
		codeForm.setCcd_cd(ccd_cd);
		codeForm.setCcd_name(codeManager.getCcd_Name(ccd_cd));
		codeForm.setGbn(gbn);
		
		return mapping.findForward("list") ;
	}
}

