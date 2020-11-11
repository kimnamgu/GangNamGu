/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����������չ��� ���ո������� action
 * ����:
 */
package nexti.ejms.colldoc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.colldoc.form.CollprocForm;
import nexti.ejms.colldoc.model.CollprocBean;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.common.rootPopupAction;

public class CollprocCloseViewAction extends rootPopupAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		int sysdocno = 0;
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		//Form���� �Ѿ�� �� ��������
		CollprocForm collprocForm = (CollprocForm)form;
		
		//���ո����ϱ� �� - ����ó��
		ColldocManager manager = ColldocManager.instance();
		CollprocBean Bean = manager.getCloseView(sysdocno);
		
		request.setAttribute("sysdocno", new Integer(sysdocno));
		
		BeanUtils.copyProperties(collprocForm, Bean);
		
		return mapping.findForward("collproccloseview");
	}
}