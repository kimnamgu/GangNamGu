/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ������ϰ��� ��� action
 * ����:
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

		//�������� ��������
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		String user_gbn = (String)session.getAttribute("user_gbn");
		
		GroupForm groupForm = (GroupForm)form;

		//������� ����Ʈ ����Ʈ 
		GroupManager Manager = GroupManager.instance();
		List mainList = Manager.getGrpMstList(user_gbn, "%", "0");
		groupForm.setMainlist(mainList);		
		
		//������� ���ڵ� ����Ʈ
		int grplistcd = groupForm.getGrplistcd();
		//String grplistnm = groupForm.getGrplistnm();
		String mode = groupForm.getMode();
		if((grplistcd == 0||"main_d".equals(mode)) && mainList.size() > 0) {
			GroupBean groupBean =(GroupBean)mainList.get(0);
			grplistcd = groupBean.getGrplistcd();
		}
		List subList = Manager.getGrpDtlList(grplistcd, "%", user_id);
		groupForm.setSublist(subList);
		
		//ȭ��ó��
		groupForm.setGrplistcd(grplistcd);
		groupForm.setGrplistnm(Manager.getGrpListName(grplistcd));
		
		return mapping.findForward("list") ;
	}
}

