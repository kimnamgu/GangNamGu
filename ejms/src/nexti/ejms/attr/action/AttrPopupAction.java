/**
 * 累己老: 2009.09.09
 * 累己磊: 措府 辑悼蛮
 * 葛碘疙: 加己格废包府 内靛荐沥 action
 * 汲疙:
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
		String p_listcd = attrForm.getListcd();      //林内靛
		int p_seq = attrForm.getSeq();               //何内靛		
		
		if("main_s".equals(mode)){
			//林内靛 Popup
			AttrBean bean = attrManager.attrMstInfo(p_listcd);
			
			if(bean != null){
				attrForm.setListnm(bean.getListnm());
			}
			
			return mapping.findForward("main");
		} else {
			//何内靛 Popup
			AttrBean mainBean = attrManager.attrMstInfo(p_listcd);
			AttrBean subBean = attrManager.attrDtlInfo(p_listcd, p_seq);	
			
			if(mainBean != null){
				attrForm.setListnm(mainBean.getListnm());        //林内靛疙
			}
			
			if(subBean != null){
				
				attrForm.setListdtlnm(subBean.getListdtlnm());   //何内靛疙
				attrForm.setAttr_desc(subBean.getAttr_desc());   //何内靛 惑技 汲疙
			}
			
			return mapping.findForward("sub");
		}
	}
}

