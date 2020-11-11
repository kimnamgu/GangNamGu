/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부하기 취합문서정보 action
 * 설명:
 */
package nexti.ejms.delivery.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.beanutils.BeanUtils;

import nexti.ejms.commdocinfo.form.CommCollDocInfoForm;
import nexti.ejms.commdocinfo.model.CommCollDocInfoBean;
import nexti.ejms.commdocinfo.model.CommCollDocInfoManager;
import nexti.ejms.common.rootAction;
import nexti.ejms.commsubdept.model.commsubdeptManager;
import nexti.ejms.commtreat.model.CommTreatBean;
import nexti.ejms.commtreat.model.CommTreatManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class DeliveryCollDocInfoViewAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		//세션정보 가져오기
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		String dept_code = (String)session.getAttribute("dept_code");
		
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
		
		//Form에서 넘어온 값 가져오기
		CommCollDocInfoForm commCollDocInfoForm = (CommCollDocInfoForm)form;
		
		int sysdocno = 0;
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		//배부하기 상세 - 취합문서정보 데이터 보기
		CommCollDocInfoManager manager = CommCollDocInfoManager.instance();
		CommCollDocInfoBean collDocInfoBean = manager.viewCommCollDocInfo(sysdocno);

		BeanUtils.copyProperties(commCollDocInfoForm, collDocInfoBean);
		
		UserManager umgr = UserManager.instance();
		UserBean ubean = umgr.getUserInfo(collDocInfoBean.getChrgusrcd());
		commCollDocInfoForm.setColdepttel(Utils.nullToEmptyString(ubean.getTel()));
		
		//처리현황
		CommTreatManager ctmgr = CommTreatManager.instance();
		CommTreatBean treatBean = ctmgr.viewCommTreatStatus(sysdocno, dept_code);
		
		commsubdeptManager csmgr = commsubdeptManager.instance();
		
		commCollDocInfoForm.setAppntusrnm(treatBean.getAppntusrnm());
		commCollDocInfoForm.setAppntdeptnm(csmgr.getCommsubdeptList(sysdocno, dept_code, "", user_id));
		////////////처리현황 끝////////////		

		//담당단위
		request.setAttribute("chrgunitnm", commCollDocInfoForm.getChrgunitnm());
		
		//상위부서가 취합대상인지 현재부서가 취합대상인지 체크
		boolean isTargetDept = false;		//취합대상부서
		boolean isTargetDeptRoot = false;	//취합문서작성부서
		
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
		
		return mapping.findForward("colldocinfo");
	}
}