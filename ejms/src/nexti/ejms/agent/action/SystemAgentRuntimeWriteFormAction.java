/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 시스템에이전트 등록화면 action
 * 설명:
 */
package nexti.ejms.agent.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.util.Utils;
import nexti.ejms.agent.form.SystemAgentForm;
import nexti.ejms.agent.model.SystemAgentManager;
import nexti.ejms.agent.model.SystemAgentRuntimeBean;

public class SystemAgentRuntimeWriteFormAction extends rootPopupAction {

	public ActionForward doService(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		SystemAgentForm saForm = (SystemAgentForm)form;
		
		String mode = request.getParameter("mode");
		String p_id = request.getParameter("p_id");

		SystemAgentManager saManager =  SystemAgentManager.instance();
		saForm.setMode(mode);
		saForm.setP_id(p_id);
		saForm.setP_nm(saManager.getAgentName(p_id));
		
		if ("m".equals(mode)) {	//수정일 경우
			if (Utils.isNotNull(request.getParameter("p_seq"))) {
				int p_seq = Integer.parseInt(request.getParameter("p_seq"));
				SystemAgentRuntimeBean arBean = new SystemAgentRuntimeBean();
				arBean = saManager.agentRuntimeDtlInfo(p_id,p_seq);
				saForm.setP_type(arBean.getP_type());
				//타입에 따라 시간셋팅
				if ("001".equals(arBean.getP_type())) {
					if (Utils.isNotNull(arBean.getP_t1())) {
						saForm.setP_t1_1(arBean.getP_t1().substring(0,2));
						saForm.setP_t1_2(arBean.getP_t1().substring(2,4));
					} else {
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","오류가 발생하였습니다.\n\n관리자에게 문의하시기 바랍니다."));
						saveMessages(request,messages);				
					}
				} else if ("002".equals(arBean.getP_type())) {
					if (Utils.isNotNull(arBean.getP_t2())) {
						saForm.setP_t2_1(arBean.getP_t2().substring(0,2));
						saForm.setP_t2_2(arBean.getP_t2().substring(2,4));
						saForm.setP_t2_3(arBean.getP_t2().substring(4,6));
					} else {
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","오류가 발생하였습니다.\n\n관리자에게 문의하시기 바랍니다."));
						saveMessages(request,messages);				
					}
				} else if ("003".equals(arBean.getP_type())) {
					if (Utils.isNotNull(arBean.getP_t3())) {
						saForm.setP_t3_1(arBean.getP_t3().substring(0,2));
						saForm.setP_t3_2(arBean.getP_t3().substring(2,4));
						saForm.setP_t3_3(arBean.getP_t3().substring(4,6));
					} else {
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","오류가 발생하였습니다.\n\n관리자에게 문의하시기 바랍니다."));
						saveMessages(request,messages);				
					}
				} else if ("004".equals(arBean.getP_type())) {
					if (Utils.isNotNull(arBean.getP_t4())) {
						saForm.setP_t4_1(arBean.getP_t4().substring(0,2));
						saForm.setP_t4_2(arBean.getP_t4().substring(2,4));
						saForm.setP_t4_3(arBean.getP_t4().substring(4,6));
						saForm.setP_t4_4(arBean.getP_t4().substring(6,8));
					} else {
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","오류가 발생하였습니다.\n\n관리자에게 문의하시기 바랍니다."));
						saveMessages(request,messages);				
					}
				}
				saForm.setP_use(arBean.getP_use());
				saForm.setP_t5(arBean.getP_t5());
			} else {	//에러발생
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","오류가 발생하였습니다.\n\n잠시 후 다시 사용하여 주시기 바랍니다."));
				saveMessages(request,messages);				
			}
			
		} else	{	//등록일 경우
			saForm.setP_type("001");
		}
		
		return mapping.findForward("view");
	}

}
