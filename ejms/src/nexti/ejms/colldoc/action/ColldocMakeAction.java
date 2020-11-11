/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합문서작성 새로만들기 action
 * 설명:
 */
package nexti.ejms.colldoc.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.colldoc.form.ColldocForm;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.dept.model.DeptManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.DateTime;

public class ColldocMakeAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");		//사용자 ID
		String user_name = (String)session.getAttribute("user_name");	//사용자 성명;
		String dept_code = (String)session.getAttribute("dept_code");	//사용자 부서코드;
		String dept_name = (String)session.getAttribute("dept_name");	//사용자 부서명;
		String sessi = session.getId().split("[!]")[0];
		
		DeptManager dmgr = DeptManager.instance();
		int chrg_code = dmgr.getChrgunitcd(user_id);
		
		UserManager umgr = UserManager.instance();
		UserBean ubean = umgr.getUserInfo(user_id);
		String tel = ubean.getTel();
		
		ServletContext context = getServlet().getServletContext();
		
		//사용자 취합부서결재, 제출부서, 제출그룹 임시 테이블 데이터, 미완료새취합문서  삭제
		ColldocManager cdmgr = ColldocManager.instance();
		cdmgr.delColldocTempData_TGT_SANC(sessi);
		cdmgr.delColldoc(cdmgr.getListCancelColldoc(), context);

		//담당단위 존재여부
		DeptManager deptMgr = DeptManager.instance();
		int chrgunit_cnt = deptMgr.getExistedChrg(dept_code);
		request.setAttribute("chrgunit_cnt", String.valueOf(chrgunit_cnt));
		
		//Form 가져오기
		ColldocForm cdform = (ColldocForm)form;
		
		cdform.setMode(1);	//1:새로작성, 2:새문서로저장, 3:저장, 4:새문서저장모드시탭이동(3으로저장후2로유지)
		cdform.setSysdocno(0);				//새취합문서작성 : 0
		cdform.setBasicdate(DateTime.getCurrentDate());	//자료기준일
		cdform.setColdeptcd(dept_code);		//부서코드
		cdform.setColdeptnm(dept_name);		//부서명
		cdform.setChrgunitcd(chrg_code);	//담당단위코드
		cdform.setChrgusrnm(user_name);		//취합자명(사용자)
		cdform.setD_tel(tel);				//부서전화번호
		cdform.setEnddt_hour("00");			//마감시
		cdform.setEnddt_min("00");			//마감분
		cdform.setSancrule("01");			//담당자전결 기본값
		
		session.removeAttribute("originuserid");
		
		return mapping.findForward("colldocInfoView");
	}
}