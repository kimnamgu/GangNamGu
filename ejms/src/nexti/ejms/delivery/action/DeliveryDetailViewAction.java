/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부하기 취합양식자료 action
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
import nexti.ejms.commtreat.model.CommTreatManager;
import nexti.ejms.delivery.model.DeliveryDetailBean;
import nexti.ejms.delivery.model.DeliveryManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class DeliveryDetailViewAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		//세션정보 가져오기
		HttpSession session = request.getSession();
		String dept_code = (String)session.getAttribute("dept_code");
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				dept_code = originUserBean.getDept_id();
			}
		}
		
		int sysdocno = 0;
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		DeliveryManager manager = DeliveryManager.instance();
		DeliveryDetailBean detailBean = manager.viewDeliveryDetail(sysdocno);
		
		//마감시한/마감알림말
		request.setAttribute("enddt", detailBean.getEnddt());
		request.setAttribute("endcomment", detailBean.getEndcomment());
		
		CommTreatManager ctmgr = CommTreatManager.instance();
		//상위부서가 취합대상인지 현재부서가 취합대상인지 체크
		boolean isTargetDept = false;
		boolean isTargetDeptRoot = false;
		
		String predeptcd = ctmgr.getPreDeptcd(sysdocno, dept_code, true);
		if ( dept_code.equals(predeptcd) ) {
			isTargetDept = true;
		}
		
		int level = ctmgr.getTargetDeptLevel(sysdocno, dept_code);
		if ( level == 1 ) {
			isTargetDeptRoot = true;
		}		
		
		request.setAttribute("isTargetDept", Boolean.toString(isTargetDept));
		request.setAttribute("isTargetDeptRoot", Boolean.toString(isTargetDeptRoot));
		
		return mapping.findForward("detailview");
	}
}