/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ϱ� ���߰��� ���� action
 * ����:
 */
package nexti.ejms.formatLine.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.formatLine.form.FormatLineForm;
import nexti.ejms.formatLine.model.FormatLineBean;
import nexti.ejms.formatLine.model.FormatLineManager;

public class DeliveryFormatLineViewAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//Form���� �Ѿ�� �� �������� 
		FormatLineForm formatLineForm = (FormatLineForm)form;
		
		int sysdocno = 0;
		int formseq = 0;
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		if(request.getParameter("formseq") != null) {
			formseq = Integer.parseInt(request.getParameter("formseq"));
		}
		
		//���߰��� ��� ���� ��������
		FormatLineManager manager = FormatLineManager.instance();
		FormatLineBean lineBean = manager.getFormatFormView(sysdocno, formseq);
		
		BeanUtils.copyProperties(formatLineForm, lineBean);
		
		return mapping.findForward("detailview");
	}
}