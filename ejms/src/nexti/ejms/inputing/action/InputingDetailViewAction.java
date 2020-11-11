/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Է��ϱ� ���վ���ڷ� action
 * ����:
 */
package nexti.ejms.inputing.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.delivery.model.DeliveryDetailBean;
import nexti.ejms.delivery.model.DeliveryManager;

public class InputingDetailViewAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		int sysdocno = 0;
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		DeliveryManager manager = DeliveryManager.instance();
		DeliveryDetailBean detailBean = manager.viewDeliveryDetail(sysdocno);
		
		//��������/�����˸���
		request.setAttribute("enddt", detailBean.getEnddt());
		request.setAttribute("endcomment", detailBean.getEndcomment());
		
		return mapping.findForward("inputdetailview");
	}
}