/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Ӽ���ϰ��� �ڵ���� action
 * ����:
 */
package nexti.ejms.attr.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.attr.form.AttrForm;
import nexti.ejms.attr.model.AttrManager;
import nexti.ejms.attr.model.AttrBean;

public class AttrPopupAction extends rootPopupAction {

	public ActionForward doService(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) 
	 	throws Exception {

		AttrForm attrForm = (AttrForm)form;
		AttrManager attrManager = AttrManager.instance();
		
		String mode = attrForm.getMode();
		String p_listcd = attrForm.getListcd();      //���ڵ�
		int p_seq = attrForm.getSeq();               //���ڵ�		
		
		if("main_s".equals(mode)){
			//���ڵ� Popup
			AttrBean bean = attrManager.attrMstInfo(p_listcd);
			
			if(bean != null){
				attrForm.setListnm(bean.getListnm());
			}
			
			return mapping.findForward("main");
		} else {
			//���ڵ� Popup
			AttrBean mainBean = attrManager.attrMstInfo(p_listcd);
			AttrBean subBean = attrManager.attrDtlInfo(p_listcd, p_seq);	
			
			if(mainBean != null){
				attrForm.setListnm(mainBean.getListnm());        //���ڵ��
			}
			
			if(subBean != null){
				
				attrForm.setListdtlnm(subBean.getListdtlnm());   //���ڵ��
				attrForm.setAttr_desc(subBean.getAttr_desc());   //���ڵ� �� ����
			}
			
			return mapping.findForward("sub");
		}
	}
}

