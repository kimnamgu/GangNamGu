/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부하기 처리현황 action
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
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class DeliveryTreatViewAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		String dept_code = "";
		int sysdocno = 0;
		
		//세션정보 가져오기
		HttpSession session = request.getSession();
		
		if(session.getAttribute("dept_code") != null) {
			dept_code = session.getAttribute("dept_code").toString();
		}
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				dept_code = originUserBean.getDept_id();
			}
		}
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		//Form에서 넘어온 값 가져오기
		CommTreatForm commTreatForm = (CommTreatForm)form;
		
		//처리현황
		CommTreatManager manager = CommTreatManager.instance();
		CommTreatBean treatBean = manager.viewCommTreatStatus(sysdocno, dept_code);
		
		commTreatForm.setAppntusrnm(treatBean.getAppntusrnm());
		commTreatForm.setSancusrnm1(treatBean.getSancusrnm1());
		commTreatForm.setSancusrnm2(treatBean.getSancusrnm2());
		
		return mapping.findForward("treatview");
	}
}