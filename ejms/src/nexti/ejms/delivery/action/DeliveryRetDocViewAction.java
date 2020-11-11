/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부하기 반송처리 정보입력 action
 * 설명:
 */
package nexti.ejms.delivery.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.delivery.form.DelveryRetDocViewForm;
import nexti.ejms.delivery.model.DeliveryManager;
import nexti.ejms.delivery.model.DeliveryRetDocViewBean;

public class DeliveryRetDocViewAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//Form에서 넘어온 값 가져오기
		DelveryRetDocViewForm deliveryRetDocViewForm = (DelveryRetDocViewForm)form;
		
		int sysdocno = 0;
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		DeliveryManager manager = DeliveryManager.instance();
		DeliveryRetDocViewBean RetDocViewBean = manager.viewReturnDoc(sysdocno);
		
		deliveryRetDocViewForm.setDoctitle(RetDocViewBean.getDoctitle());
		deliveryRetDocViewForm.setColdeptnm(RetDocViewBean.getColdeptnm());
		deliveryRetDocViewForm.setChrgunitnm(RetDocViewBean.getChrgunitnm());
		deliveryRetDocViewForm.setChrgusrnm(RetDocViewBean.getChrgusrnm());
		deliveryRetDocViewForm.setColdeptcd(RetDocViewBean.getColdeptcd());
		deliveryRetDocViewForm.setColdepttel(RetDocViewBean.getColdepttel());
		
		return mapping.findForward("popreturndoc");
	}
}