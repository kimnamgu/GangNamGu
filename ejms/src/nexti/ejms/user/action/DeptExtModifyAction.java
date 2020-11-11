/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 조직관리 조직관리 부서/사용자정보확장 수정
 * 설명:
 */
package nexti.ejms.user.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nexti.ejms.common.rootAction;
import nexti.ejms.user.form.UsrMgrForm;
import nexti.ejms.user.model.UsrMgrManager;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DeptExtModifyAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {	

		UsrMgrForm usrMgrForm = (UsrMgrForm)form;		
		UsrMgrManager usrMgrManager = UsrMgrManager.instance();
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		String rep_dept = (String)session.getAttribute("rep_dept");
		
		//전체 열을 사용안함으로 변경
		usrMgrManager.updateAllExt(user_id, usrMgrForm.getOrggbn(), rep_dept);
		
		//대상부서 셋팅
		for(int i=0;i<usrMgrForm.getMain_ynLen();i++){
			//각 부서 코드 마다 사용함으로 다시 셋팅
			String dept_id = usrMgrForm.getMain_yn()[i];
			usrMgrManager.updateMainYn(user_id, dept_id);
		}

		//사용여부 셋팅
		for(int i=0;i<usrMgrForm.getUse_ynLen();i++){
			//각 부서 코드 마다 사용함으로 다시 셋팅
			String dept_id = usrMgrForm.getUse_yn()[i];
			usrMgrManager.updateUseYn(dept_id);
		}

		//연계여부 셋팅
		for(int i=0;i<usrMgrForm.getCon_ynLen();i++){
			//각 부서 코드 마다 사용함으로 다시 셋팅
			String dept_id = usrMgrForm.getCon_yn()[i];
			usrMgrManager.updateConYn(dept_id);
		}
		
		return mapping.findForward("frame1");
	}
}

