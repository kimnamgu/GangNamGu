/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 관리자변경 저장 action
 * 설명:
 */
package nexti.ejms.organ.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.rootAction;
import nexti.ejms.organ.form.ManagerForm;
import nexti.ejms.organ.model.OrganizeManager;
import nexti.ejms.util.Utils;

public class ChgMgrSaveAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		ActionMessages messages = new ActionMessages();
		ManagerForm mgrForm = (ManagerForm)form;
		String gbn = mgrForm.getGbn();
		String userid[] = null;
		
		if("1".equals(gbn)){
			userid = mgrForm.getUser_id().split(",");
		} else {
			userid = mgrForm.getUser_id1().split(",");
		}		

		ArrayList temp = new ArrayList(userid.length);
		temp.add(userid[0]);
		for (int i = 0; i < userid.length; i++) {
			for (int j = 0; j < temp.size(); j++) {
				if (userid[i].equals((String)temp.get(j))) break;
				else if(j + 1 == temp.size()) temp.add(userid[i]);
			}
		}
		userid = new String[temp.size()];
		temp.toArray(userid);
		
		StringBuffer notExistManagerList = new StringBuffer();		//존재하지 않는 사용자
		StringBuffer registerManagerList = new StringBuffer();		//새로 등록된 사용자
		StringBuffer registeredManagerList = new StringBuffer();	//이미 등록된 사용자
		for (int i = 0; i < userid.length; i++) {
			int result = OrganizeManager.instance().existManagerList(userid[i]);
			if (result == 0) {
				String separate = (notExistManagerList.length() == 0) ? "" : ", ";
				notExistManagerList.append(separate);
				notExistManagerList.append(userid[i]);
			} else if (result == 1) {
				String separate = (registerManagerList.length() == 0) ? "" : ", ";
				registerManagerList.append(separate);
				registerManagerList.append(userid[i]);
			} else if (result == 2) {
				String separate = (registeredManagerList.length() == 0) ? "" : ", ";
				registeredManagerList.append(separate);
				registeredManagerList.append(userid[i]);
			}
		}
		
		int result = OrganizeManager.instance().updateMgr(gbn, userid);		
		StringBuffer msg = new StringBuffer();
		
		if ("1".equals(gbn)) {
			if (result > 0) {
				msg.append("======처리결과======\\n\\n");
				
				if (registerManagerList.length() != 0) {
					msg.append("ㅇ새로 등록된 사용자\\n - ");
					msg.append(registerManagerList.toString());
					msg.append("\\n\\n");
				}
				if (registeredManagerList.length() != 0) {
					msg.append("ㅇ이미 등록된 사용자\\n - ");
					msg.append(registeredManagerList.toString());
					msg.append("\\n\\n");
				}
				if (notExistManagerList.length() != 0) {
					msg.append("ㅇ등록되지 않은 사용자\\n - ");
					msg.append(notExistManagerList.toString());
				}
			} else {
				msg.append("======처리결과======\\n\\n");
				msg.append("ㅇ등록되지 않은 사용자\\n - ");
				msg.append(notExistManagerList.toString());
			}
		} else {
			if (result > 0) {
				msg.append("삭제되었습니다.");
			} else {
				msg.append("삭제를 실패하였습니다.");
			}
		}
		
		if (Utils.isNotNull(msg.toString())) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg.toString()));
			saveMessages(request,messages);
		}
		
		return mapping.findForward("view");
	}
}