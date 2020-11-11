/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 진행중인취합문서 취합문서정보 수정 action
 * 설명:
 */
package nexti.ejms.colldoc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.apache.commons.beanutils.BeanUtils;

import nexti.ejms.commdocinfo.form.CommCollDocInfoForm;
import nexti.ejms.commdocinfo.model.CommCollDocInfoBean;
import nexti.ejms.commdocinfo.model.CommCollDocInfoManager;
import nexti.ejms.common.rootAction;
import nexti.ejms.dept.model.DeptManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class CollprocInfoModifyAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		//Form에서 넘어온 값 가져오기
		CommCollDocInfoForm commCollDocInfoForm = (CommCollDocInfoForm)form;
		
		int sysdocno = 0;
		int chrgunit_cnt = 0;
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		//진행중인 취합문서 상세 - 취합문서정보 데이터 보기
		CommCollDocInfoManager manager = CommCollDocInfoManager.instance();
		CommCollDocInfoBean collDocInfoBean = manager.viewCommCollDocInfo(sysdocno);

		//담당단위 존재여부
		DeptManager deptMgr = DeptManager.instance();
		
		chrgunit_cnt = deptMgr.getExistedChrg(collDocInfoBean.getColdeptcd());
		if(chrgunit_cnt == 0){
			if ( !"".equals(collDocInfoBean.getChrgunitnm()) ) chrgunit_cnt = 0; 
		}
		
		BeanUtils.copyProperties(commCollDocInfoForm, collDocInfoBean);
		
		UserManager umgr = UserManager.instance();
		UserBean ubean = umgr.getUserInfo(collDocInfoBean.getChrgusrcd());
		commCollDocInfoForm.setColdepttel(Utils.nullToEmptyString(ubean.getTel()));
		
		request.setAttribute("sysdocno", new Integer(sysdocno));
		request.setAttribute("chrgunit_cnt", String.valueOf(chrgunit_cnt));
		
		return mapping.findForward("collprocinfomodify");
	}
}