/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ڵ���� �ڵ���� action
 * ����:
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
		String main_cd = codeForm.getCcd_cd();        //���ڵ�
		String sub_cd = codeForm.getCcd_sub_cd();     //���ڵ�		
		
		if("main_s".equals(mode)){
			//���ڵ� Popup
			CcdBean bean = codeManager.detailCcd(main_cd, "...");
			
			if(bean != null){
				codeForm.setCcd_name(bean.getCcdname());
			}
			
			return mapping.findForward("main");
		} else {
			//���ڵ� Popup
			CcdBean mainBean = codeManager.detailCcd(main_cd,"...");
			CcdBean subBean = codeManager.detailCcd(main_cd, sub_cd);	
			
			if(mainBean != null){
				codeForm.setCcd_name(mainBean.getCcdname());     //���ڵ��
			}
			
			if(subBean != null){
				
				codeForm.setCcd_sub_name(subBean.getCcdname()); //���ڵ��
				codeForm.setCcd_desc(subBean.getCcddesc());     //���ڵ� �� ����
			}
			
			return mapping.findForward("sub");
		}
	}
}

