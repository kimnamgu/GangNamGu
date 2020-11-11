/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 신청하기 action
 * 설명:
 */
package nexti.ejms.sinchung.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.sinchung.form.SinchungListForm;

public class DoListAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {		 
		
		HttpSession session = request.getSession();
		String deptcd = (String)session.getAttribute("dept_code");
		String deptnm = (String)session.getAttribute("dept_name");
		
		SinchungListForm sForm = (SinchungListForm)form;
		sForm.setDeptcd(deptcd);		//화면이 열릴 때 부서코드 셋팅
		sForm.setDeptnm(deptnm);        //화면이 열릴 때 부서명칭 셋팅
		
		return mapping.findForward("parentFrame");
	}
}