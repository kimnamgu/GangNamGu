/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배포목록관리 목록 action
 * 설명:
 */
package nexti.ejms.group.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.group.form.GroupForm;
import nexti.ejms.group.model.GroupManager;
import nexti.ejms.group.model.GroupBean;

public class GroupFormAction extends rootAction {

	public ActionForward doService(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) 
		throws Exception {

		//세션정보 가져오기
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		String user_gbn = (String)session.getAttribute("user_gbn");
		
		GroupForm groupForm = (GroupForm)form;

		//배포목록 마스트 리스트 
		GroupManager Manager = GroupManager.instance();
		List mainList = Manager.getGrpMstList(user_gbn, "%", "0");
		groupForm.setMainlist(mainList);		
		
		//배포목록 부코드 리스트
		int grplistcd = groupForm.getGrplistcd();
		//String grplistnm = groupForm.getGrplistnm();
		String mode = groupForm.getMode();
		if((grplistcd == 0||"main_d".equals(mode)) && mainList.size() > 0) {
			GroupBean groupBean =(GroupBean)mainList.get(0);
			grplistcd = groupBean.getGrplistcd();
		}
		List subList = Manager.getGrpDtlList(grplistcd, "%", user_id);
		groupForm.setSublist(subList);
		
		//화면처리
		groupForm.setGrplistcd(grplistcd);
		groupForm.setGrplistnm(Manager.getGrpListName(grplistcd));
		
		return mapping.findForward("list") ;
	}
}

