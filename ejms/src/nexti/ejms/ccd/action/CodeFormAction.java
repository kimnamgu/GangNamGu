/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ڵ���� ȭ�� action
 * ����:
 */
package nexti.ejms.ccd.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.util.Utils;
import nexti.ejms.ccd.form.CodeForm;
import nexti.ejms.ccd.model.CcdManager;
import nexti.ejms.ccd.model.CcdBean;

public class CodeFormAction extends rootAction {

	public ActionForward doService(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) 
		throws Exception {

		CodeForm codeForm = (CodeForm)form;
		String gbn = "";
		if ("".equals(Utils.nullToEmptyString((String)request.getAttribute("gbn")))) {
			gbn = codeForm.getGbn();
			if("".equals(gbn) || gbn ==null){
				gbn = "a";    //���� ������ �����ڿ� �ڵ� ����� �⺻���� ����
			} 
		} else {
			gbn = (String)request.getAttribute("gbn");
		}
		
		//���ڵ� ����Ʈ 
		CcdManager codeManager = CcdManager.instance();
		List mainList = codeManager.mainCodeList(gbn);
		codeForm.setMainlist(mainList);		
		
		//���ڵ� ����Ʈ
		String ccd_cd = codeForm.getCcd_cd();
		String mode = codeForm.getMode();
		if(("".equals(ccd_cd)||"main_d".equals(mode)) && mainList.size() > 0) {
			if ( mainList != null && mainList.size() > 0 ) {
				CcdBean codeBean =(CcdBean)mainList.get(0);
				ccd_cd = codeBean.getCcdcd();
			}
		}
		List subList = codeManager.subCodeList(ccd_cd);
		codeForm.setSublist(subList);
		
		//ȭ��ó��
		codeForm.setCcd_cd(ccd_cd);
		codeForm.setCcd_name(codeManager.getCcd_Name(ccd_cd));
		codeForm.setGbn(gbn);
		
		return mapping.findForward("list") ;
	}
}

