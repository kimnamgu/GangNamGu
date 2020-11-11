/**
 * �ۼ���: 2013.03.04
 * �ۼ���: �븮 ������
 * ����: �ֿ�������� ����������� action
 * ����:
 */
package nexti.ejms.workplan.action;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.appInfo;
import nexti.ejms.common.rootAction;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.workplan.form.WorkplanForm;
import nexti.ejms.workplan.model.WorkplanBean;
import nexti.ejms.workplan.model.WorkplanManager;

public class WorkResultSaveAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");		//����� ID
		
		WorkplanForm wForm = (WorkplanForm)form;
		
		if ( wForm.getResultno() == 0 ) {
			StringBuffer msg = new StringBuffer();
			msg.append("alert('������ȣ�� �߸��Ǿ����ϴ�.'); \n");
			msg.append("history.back(); \n");
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg", msg));
			saveMessages(request,messages);
		}
		
		WorkplanBean wBean = new WorkplanBean();
		BeanUtils.copyProperties(wBean, wForm);
		
		wBean.setUseyn("Y");
		wBean.setUptusrid(user_id);
		
		String saveDir = appInfo.getWorkplanDir() + Calendar.getInstance().get(Calendar.YEAR) + "/";
		
		WorkplanManager wMgr = WorkplanManager.instance();
		int resultno = wMgr.saveWorkResult(wBean, getServlet().getServletContext(), saveDir); 
		if ( resultno > 0 ) {
			StringBuffer msg = new StringBuffer();
			msg.append("alert('����Ǿ����ϴ�.'); \n");
			
			//ifrmae���� ó���ϱ� ���� �ڵ�
			msg.append("parent.location.href = parent.location.href; \n");
			
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg", msg));
			saveMessages(request,messages);
			
			try {
				wBean = wMgr.getWorkResult(wBean.getPlanno(), resultno);
				UserManager uMgr = UserManager.instance();
				uMgr.updateChrgunitcd(wBean.getChrgusrcd(), wBean.getChrgunitcd());
			} catch ( Exception e ) {
				msg.append("alert('�������� ������� �ʾҽ��ϴ�.'); \n");
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg", msg));
			}
		}
		
		return mapping.findForward("list");
	}
}