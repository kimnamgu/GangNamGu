/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부완료 처리현황 action
 * 설명:
 */
package nexti.ejms.delivery.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.commtreat.form.CommTreatForm;
import nexti.ejms.commtreat.model.CommTreatBean;
import nexti.ejms.commtreat.model.CommTreatManager;
import nexti.ejms.commsubdept.model.commsubdeptManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class DeliCompTreatViewAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//Form에서 넘어온 값 가져오기
		CommTreatForm commTreatForm = (CommTreatForm)form;
		
		int sysdocno = 0;
		
		//세션정보 가져오기
		HttpSession session = request.getSession();
		
		String dept_code = session.getAttribute("dept_code").toString();
		String user_id = session.getAttribute("user_id").toString();
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				user_id = originUserBean.getUser_id();
				dept_code = originUserBean.getDept_id();
			}
		}
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		//배부완료 상세 - 처리현황
		CommTreatManager manager = CommTreatManager.instance();
		CommTreatBean treatBean = manager.viewDeliCompTreatStatus(sysdocno, dept_code);
		
		commsubdeptManager manager1 = commsubdeptManager.instance();
		String processchk = manager1.getProcessChk(sysdocno, dept_code, user_id, "1", "3");
		
		request.setAttribute("processchk", processchk);
		
		commTreatForm.setSubmitstate(treatBean.getSubmitstate());
		commTreatForm.setDeliverydt(treatBean.getDeliverydt());
		commTreatForm.setInputuser1(treatBean.getInputuser1());
		commTreatForm.setInputuser2(treatBean.getInputuser2());
		commTreatForm.setSancList1(treatBean.getSancList1());
		commTreatForm.setSancList2(treatBean.getSancList2());
		
		return mapping.findForward("comptreatview");
	}
}