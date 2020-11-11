/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부하기 목록 action
 * 설명:
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
import nexti.ejms.delivery.form.DeliveryForm;
import nexti.ejms.delivery.model.DeliveryManager;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;
import nexti.ejms.util.commfunction;

public class DeliveryListAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
	
		//세션정보 가져오기
		HttpSession session = request.getSession();
		String userid = (String)session.getAttribute("user_id");
		String deptcd = (String)session.getAttribute("dept_code");
		String isSysMgr = (String)session.getAttribute("isSysMgr");
		
		//Form에서 넘어온 값 가져오기
		DeliveryForm deliForm = (DeliveryForm)form;
		String initentry	= deliForm.getInitentry();
		String sch_deptcd 	= deliForm.getSch_old_deptcd();
	
		//데이터 범위 결정
		int pageSize = 10;		//한번에 표시한 리스트의 갯수
		int start = commfunction.startIndex(deliForm.getPage(), pageSize);
		int end = commfunction.endIndex(deliForm.getPage(), pageSize);
		
		//배부하기 목록 가져오기
		String delivery_yn = Utils.nullToEmptyString(UserManager.instance().getUserInfo(userid).getDelivery_yn());
		DeliveryManager manager = DeliveryManager.instance();
		String orgid = manager.getSearchDelivery(sch_deptcd);
		int totalCount = 0;
		int totalPage = 0;
		if ( "Y".equals(delivery_yn) || "001".equals(isSysMgr) ) {
			List deliList = manager.deliveryList(deptcd, isSysMgr, sch_deptcd, start, end);
			deliForm.setDeliList(deliList);
			
			totalCount = manager.deliCnt(deptcd, isSysMgr, sch_deptcd);
			totalPage = (int)Math.ceil((double)totalCount/(double)pageSize);
		}
		
		request.setAttribute("totalPage", new Integer(totalPage));		
		request.setAttribute("totalCount", new Integer(totalCount));
		request.setAttribute("currpage", new Integer(deliForm.getPage()));		 

		request.setAttribute("initentry", initentry);
		request.setAttribute("orgid", Utils.nullToEmptyString(orgid));
		
		session.removeAttribute("originuserid");
		
		return mapping.findForward("list");
	}
}