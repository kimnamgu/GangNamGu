/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 담당단위관리 목록 action
 * 설명:
 */
package nexti.ejms.chrgUnit.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.appInfo;
import nexti.ejms.common.rootAction;
import nexti.ejms.chrgUnit.form.ChrgUnitForm;
import nexti.ejms.dept.model.DeptManager;
import nexti.ejms.dept.model.DeptBean;

public class ChrgUnitFormAction extends rootAction {

	public ActionForward doService(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) 
		throws Exception {

		ChrgUnitForm chrgForm = (ChrgUnitForm)form;		

		HttpSession session = request.getSession();
		String rep_dept = (String)session.getAttribute("rep_dept");
		String user_gbn = (String)session.getAttribute("user_gbn");
		String user_id  = (String)session.getAttribute("user_id");
		String dept_depth = "";

		if(appInfo.getRootid().equals(rep_dept)){
			if(request.getParameter("orggbn") != null){
				chrgForm.setOrggbn(request.getParameter("orggbn"));
			}else{
				chrgForm.setOrggbn(user_gbn);
			}
			dept_depth = "1";
			
		}else{
			chrgForm.setOrggbn(user_gbn);
			dept_depth = "2";
		}
		
		//부서 목록 
		DeptManager deptMgr = DeptManager.instance();
		List deptList = deptMgr.tgtDeptList(chrgForm.getOrggbn(), user_id, dept_depth);	
		chrgForm.setDeptList(deptList);
		
		//담당단위 목록
		String dept_id = chrgForm.getDept_id();			
		if("".equals(dept_id)) {
			if ( deptList != null && deptList.size() > 0 ) {
				DeptBean deptBean =(DeptBean)deptList.get(0);
				dept_id = deptBean.getDept_id();
			}
		}
		
		List chrgList = deptMgr.chrgUnitList(dept_id);
		chrgForm.setChrgList(chrgList);
		
		//화면처리
		chrgForm.setDept_id(dept_id);
		if ( deptMgr.getDeptInfo(dept_id) != null ) {
			chrgForm.setDept_name(deptMgr.getDeptInfo(dept_id).getDept_name());
		}
		
		return mapping.findForward("list") ;
	}
}