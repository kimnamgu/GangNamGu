/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 진행중인취합문서 취합문서정보 action
 * 설명:
 */
package nexti.ejms.colldoc.action;

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
import nexti.ejms.commtreat.model.CommTreatBean;
import nexti.ejms.commtreat.model.CommTreatManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class CollprocInfoViewAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//Form에서 넘어온 값 가져오기
		CommCollDocInfoForm commCollDocInfoForm = (CommCollDocInfoForm)form;
		
		int sysdocno = 0;
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		request.setAttribute("sysdocno", new Integer(sysdocno));
		
		//진행중인 취합문서 상세 - 취합문서정보 데이터 보기
		CommCollDocInfoManager manager = CommCollDocInfoManager.instance();
		CommCollDocInfoBean collDocInfoBean = manager.viewCommCollDocInfo(sysdocno);

		BeanUtils.copyProperties(commCollDocInfoForm, collDocInfoBean);
		
		//////////////////처리현황 시작//////////////////
		String dept_code = "";
		String user_id = "";
		
		//세션정보 가져오기
		HttpSession session = request.getSession();
		dept_code = session.getAttribute("dept_code").toString();
		user_id = session.getAttribute("user_id").toString();
		
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
		
		UserManager umgr = UserManager.instance();
		UserBean ubean = umgr.getUserInfo(collDocInfoBean.getChrgusrcd());
		commCollDocInfoForm.setColdepttel(Utils.nullToEmptyString(ubean.getTel()));
		
		//처리현황
		CommTreatManager ctmgr = CommTreatManager.instance();
		CommTreatBean treatBean = ctmgr.viewCommTreatStatus1(sysdocno, dept_code, user_id);
		
		commCollDocInfoForm.setTcnt(treatBean.getTcnt());
		commCollDocInfoForm.setScnt(treatBean.getScnt());
		commCollDocInfoForm.setFcnt(treatBean.getFcnt());
		
		//문서상태와 해당날짜 가져오기
		treatBean = ctmgr.getDocStates(sysdocno, dept_code);
		commCollDocInfoForm.setDocstate(treatBean.getDocstate());
		commCollDocInfoForm.setDocstatenm(treatBean.getDocstatenm());
		commCollDocInfoForm.setEnddt(treatBean.getEnddt());
		commCollDocInfoForm.setDeliverydt(treatBean.getDeliverydt());
		//////////////////처리현황 끝//////////////////
		
		//담당단위
		request.setAttribute("chrgunitnm", commCollDocInfoForm.getChrgunitnm());
		
		return mapping.findForward("collprocinfoview");
	}
}