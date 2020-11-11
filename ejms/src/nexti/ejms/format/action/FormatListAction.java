/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ۼ� ��� action
 * ����:
 */
package nexti.ejms.format.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootFrameAction;
import nexti.ejms.format.form.FormatForm;
import nexti.ejms.format.model.FormatManager;

public class FormatListAction extends rootFrameAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//Form���� �Ѿ�� �� ��������
		FormatForm formatForm = (FormatForm)form;
		
		int sysdocno = 0;
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"), 10);
		}
		
		// ��� ����Ʈ ��������
		FormatManager manager = FormatManager.instance();
		List listform = manager.viewFormList(sysdocno);
		formatForm.setListform(listform);
		
		return mapping.findForward("formatlist");
	}
}