/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���������� ���� action
 * ����:
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
				
		String dept_id = chrgForm.getDept_id();       //�μ��ڵ�
		int chrgunitcd = chrgForm.getChrgunitcd();    //�������ڵ�		
		
		//������ POPUP
		ChrgUnitBean chrgBean = deptMgr.chrgUnitInfo(dept_id, chrgunitcd);	
		
		if(chrgBean != null){
			chrgForm.setDept_id(dept_id);  //�μ��ڵ�
			chrgForm.setDept_name(deptMgr.getDeptInfo(dept_id).getDept_name()); //�μ���Ī
			chrgForm.setChrgunitcd(chrgBean.getChrgunitcd());	//������ �ڵ�
			chrgForm.setChrgunitnm(chrgBean.getChrgunitnm());   //��������Ī	
			chrgForm.setOrd(chrgBean.getOrd());                 //���ļ���
		}
		
		return mapping.findForward("modify");		
	}
}

