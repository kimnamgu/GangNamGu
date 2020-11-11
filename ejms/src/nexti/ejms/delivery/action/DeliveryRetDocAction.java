/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부하기 반송처리 action
 * 설명:
 */
package nexti.ejms.delivery.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.delivery.form.DeliveryRetDocForm;
import nexti.ejms.delivery.model.DeliveryManager;
import nexti.ejms.delivery.model.DeliveryRetDocBean;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class DeliveryRetDocAction extends rootPopupAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		//세션정보 가져오기
		HttpSession session = request.getSession();
		String dept_code = session.getAttribute("dept_code").toString();
		String user_id = session.getAttribute("user_id").toString();
		
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
		DeliveryRetDocForm deliveryRetDocForm = (DeliveryRetDocForm)form;
		
		DeliveryRetDocBean RetDocBean = new DeliveryRetDocBean();
		
		RetDocBean.setSysdocno(deliveryRetDocForm.getSysdocno());
		RetDocBean.setTgtdeptcd(dept_code);
		RetDocBean.setReturncomment(deliveryRetDocForm.getReturncomment());
		RetDocBean.setSubmitstate("06");
		
		//배부하기 상세 - 반송처리
		DeliveryManager manager = DeliveryManager.instance();
		int retCode = manager.deliveryReturnDoc(RetDocBean, user_id);
		
		String msg = "";
		if(retCode > 0) {
			msg = "반송처리하였습니다.";
		} else {
			msg = "반송처리 중 에러가 발생하여 반송처리에 실패하였습니다.";
		}
		
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
		saveMessages(request,messages);
		
		PrintWriter out = response.getWriter();
		out.write("<script>var opener = window.dialogArguments; self.close(); opener.location.replace('/deliveryList.do');</script>");
		out.close();
				
		return null;
	}
}