/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력하기 담당단위변경 action
 * 설명:
 */
package nexti.ejms.inputing.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.rootAction;
import nexti.ejms.commdocinfo.form.CommCollDocInfoForm;
import nexti.ejms.commtreat.model.CommTreatManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class InputChangeChrgunitAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//Form에서 넘어온 값 가져오기
		CommCollDocInfoForm commTreatForm = (CommCollDocInfoForm)form;
		
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
		
		//입력하기 상세(처리현황) - 입력담당자 담당단위 변경
		CommTreatManager manager = CommTreatManager.instance();
		int retCode = manager.changeChrgUnit(commTreatForm.getChrgunitcd(), user_id, commTreatForm.getSysdocno(), dept_code);
		
		String msg = "";
		if(retCode > 0) {
			msg = "담당단위를 변경하였습니다.";
		} else {
			msg = "변경에 실패하였습니다.";
		}
		
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg", msg));
		saveMessages(request,messages);
		
		return mapping.findForward("inputtreatview");
	}
}