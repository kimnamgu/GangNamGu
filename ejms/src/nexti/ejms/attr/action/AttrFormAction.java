/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 속성목록관리 action
 * 설명:
 */package nexti.ejms.attr.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.util.Utils;
import nexti.ejms.attr.form.AttrForm;
import nexti.ejms.attr.model.AttrManager;
import nexti.ejms.attr.model.AttrBean;

public class AttrFormAction extends rootAction {

	public ActionForward doService(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) 
		throws Exception {

		AttrForm attrForm = (AttrForm)form;
		String gbn = "";
		if ("".equals(Utils.nullToEmptyString((String)request.getAttribute("gbn")))) {
			gbn = attrForm.getGbn();
			if("".equals(gbn) || gbn ==null){
				gbn = "a";    //값이 없으면 관리자용 코드 목록을 기본으로 셋팅
			} 
		} else {
			gbn = (String)request.getAttribute("gbn");
		}
		
		//주코드 리스트 
		AttrManager attrManager = AttrManager.instance();
		List mainList = attrManager.attrMstList(gbn);
		attrForm.setMainlist(mainList);		
		
		//부코드 리스트
		String p_listcd = attrForm.getListcd();
		String mode = attrForm.getMode();
		if(("".equals(p_listcd)||"main_d".equals(mode)) && mainList.size() > 0) {
			if ( mainList != null && mainList.size() > 0 ) {
				AttrBean attrBean =(AttrBean)mainList.get(0);
				p_listcd = attrBean.getListcd();
			}
		}
		List subList = attrManager.attrDtlList(p_listcd);
		attrForm.setSublist(subList);
		
		//화면처리
		attrForm.setListcd(p_listcd);
		attrForm.setListnm(attrManager.getMst_Name(p_listcd));
		attrForm.setGbn(gbn);
		
		return mapping.findForward("list") ;
	}
}

