/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 조직관리 조직관리 부서목록 검색 
 * 설명:
 */
package nexti.ejms.user.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.user.form.UsrMgrForm;
import nexti.ejms.user.model.UsrMgrManager;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SearchDeptAction extends rootPopupAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {	

		UsrMgrForm usrMgrForm = (UsrMgrForm)form;		
		UsrMgrManager usrMgrManager = UsrMgrManager.instance();
		
		String s_data  = request.getParameter("s_data");
		String s_data1 = request.getParameter("s_data1");

		usrMgrForm.setS_data(s_data);
		usrMgrForm.setS_data1(s_data1);
		
		List deptBeanList = usrMgrManager.deptList("ALL", "001", "", "");
		usrMgrForm.setDeptBeanList(deptBeanList);
		
		return mapping.findForward("list");
	}
}