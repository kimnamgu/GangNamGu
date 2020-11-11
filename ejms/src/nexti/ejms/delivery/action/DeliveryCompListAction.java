/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��οϷ� ��� action
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

import nexti.ejms.common.rootAction;
import nexti.ejms.delivery.form.DeliveryCompForm;
import nexti.ejms.delivery.model.DeliveryManager;
import nexti.ejms.util.Utils;
import nexti.ejms.util.commfunction;

public class DeliveryCompListAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		String deptcd = "";
		
		//�������� ��������
		HttpSession session = request.getSession();		
		
		if(session.getAttribute("dept_code") != null) {
			deptcd = session.getAttribute("dept_code").toString();
		}
		String isSysMgr = (String)session.getAttribute("isSysMgr");
		
		//Form���� �Ѿ�� �� ��������
		DeliveryCompForm deliveryCompForm = (DeliveryCompForm) form;
		String initentry	= deliveryCompForm.getInitentry();
		String sch_deptcd 	= deliveryCompForm.getSch_old_deptcd();
		
		//������ ���� ����
		int pageSize = 10;		//�ѹ��� ǥ���� ����Ʈ�� ����
		int start = commfunction.startIndex(deliveryCompForm.getPage(), pageSize);
		int end = commfunction.endIndex(deliveryCompForm.getPage(), pageSize);
		
		//��οϷ� ��� ��������
		DeliveryManager manager = DeliveryManager.instance(); 
		String orgid = manager.getSearchDeliveryComp(sch_deptcd);
		List deliCompList = manager.deliveryCompList(deptcd, isSysMgr, sch_deptcd, start, end);	
		deliveryCompForm.setDeliCompList(deliCompList);
		
		int totalCount = manager.deliCompCnt(deptcd, isSysMgr, sch_deptcd);
		int totalPage = (int)Math.ceil((double)totalCount/(double)pageSize);		
		request.setAttribute("totalPage", new Integer(totalPage));		
		request.setAttribute("totalCount", new Integer(totalCount));
		request.setAttribute("currpage", new Integer(deliveryCompForm.getPage()));	

		request.setAttribute("initentry", initentry);
		request.setAttribute("orgid", Utils.nullToEmptyString(orgid));
		
		session.removeAttribute("originuserid");
		
		return mapping.findForward("list");
	}
}