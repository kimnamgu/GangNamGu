/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��δ����� ��� action
 * ����:
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
		
		//�μ� ��� 
		DeptManager deptMgr = DeptManager.instance();
		List deptList = deptMgr.tgtDeptList(duForm.getOrggbn(), user_id, dept_depth);	
		duForm.setDeptList(deptList);
		
		//��δ�� ���
		String dept_id = duForm.getDept_id();			
		if("".equals(dept_id)) {
			if ( deptList != null && deptList.size() > 0 ) {
				DeptBean deptBean =(DeptBean)deptList.get(0);
				dept_id = deptBean.getDept_id();
			}
		}
		
		List duList = deptMgr.deliveryUserList(dept_id);
		duForm.setChrgList(duList);
		
		//ȭ��ó��
		duForm.setDept_id(dept_id);
		if ( deptMgr.getDeptInfo(dept_id) != null ) {
			duForm.setDept_name(deptMgr.getDeptInfo(dept_id).getDept_name());
		}
		
		return mapping.findForward("list") ;
	}
}