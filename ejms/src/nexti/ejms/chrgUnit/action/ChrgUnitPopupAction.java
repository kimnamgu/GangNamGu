/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 담당단위관리 수정 action
 * 설명:
 */
package nexti.ejms.chrgUnit.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.chrgUnit.form.ChrgUnitForm;
import nexti.ejms.dept.model.DeptManager;
import nexti.ejms.dept.model.ChrgUnitBean;

public class ChrgUnitPopupAction extends rootPopupAction {

	public ActionForward doService(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) 
	 	throws Exception {

		ChrgUnitForm chrgForm = (ChrgUnitForm)form;
		DeptManager deptMgr = DeptManager.instance();
				
		String dept_id = chrgForm.getDept_id();       //부서코드
		int chrgunitcd = chrgForm.getChrgunitcd();    //담당단위코드		
		
		//담당단위 POPUP
		ChrgUnitBean chrgBean = deptMgr.chrgUnitInfo(dept_id, chrgunitcd);	
		
		if(chrgBean != null){
			chrgForm.setDept_id(dept_id);  //부서코드
			chrgForm.setDept_name(deptMgr.getDeptInfo(dept_id).getDept_name()); //부서명칭
			chrgForm.setChrgunitcd(chrgBean.getChrgunitcd());	//담당단위 코드
			chrgForm.setChrgunitnm(chrgBean.getChrgunitnm());   //담당단위명칭	
			chrgForm.setOrd(chrgBean.getOrd());                 //정렬순서
		}
		
		return mapping.findForward("modify");		
	}
}

