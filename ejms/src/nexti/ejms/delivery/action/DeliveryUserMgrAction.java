/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부담당관리 목록 action
 * 설명:
 */
package nexti.ejms.delivery.action;

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

public class DeliveryUserMgrAction extends rootAction {

	public ActionForward doService(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) 
		throws Exception {

		ChrgUnitForm duForm = (ChrgUnitForm)form;		

		HttpSession session = request.getSession();
		String rep_dept = (String)session.getAttribute("rep_dept");
		String user_gbn = (String)session.getAttribute("user_gbn");
		String user_id  = (String)session.getAttribute("user_id");
		String dept_depth = "";

		if(appInfo.getRootid().equals(rep_dept)){
			if(request.getParameter("orggbn") != null){
				duForm.setOrggbn(request.getParameter("orggbn"));
			}else{
				duForm.setOrggbn(user_gbn);
			}
			dept_depth = "1";
			
		}else{
			duForm.setOrggbn(user_gbn);
			dept_depth = "2";
		}
		
		//부서 목록 
		DeptManager deptMgr = DeptManager.instance();
		List deptList = deptMgr.tgtDeptList(duForm.getOrggbn(), user_id, dept_depth);	
		duForm.setDeptList(deptList);
		
		//배부담당 목록
		String dept_id = duForm.getDept_id();			
		if("".equals(dept_id)) {
			if ( deptList != null && deptList.size() > 0 ) {
				DeptBean deptBean =(DeptBean)deptList.get(0);
				dept_id = deptBean.getDept_id();
			}
		}
		
		List duList = deptMgr.deliveryUserList(dept_id);
		duForm.setChrgList(duList);
		
		//화면처리
		duForm.setDept_id(dept_id);
		if ( deptMgr.getDeptInfo(dept_id) != null ) {
			duForm.setDept_name(deptMgr.getDeptInfo(dept_id).getDept_name());
		}
		
		return mapping.findForward("list") ;
	}
}