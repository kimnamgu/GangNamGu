/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���չ����ۼ� �������� action
 * ����:
 */
package nexti.ejms.workplan.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nexti.ejms.common.rootAction;
import nexti.ejms.workplan.form.WorkplanForm;
import nexti.ejms.workplan.model.WorkplanBean;
import nexti.ejms.workplan.model.WorkplanManager;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class WorkplanDelEndAction extends rootAction {

    public ActionForward doService(
            ActionMapping mapping, 
            ActionForm form, 
            HttpServletRequest request, 
            HttpServletResponse response) 
    throws Exception {
        
        //�������� ��������
        HttpSession session = request.getSession();
        String user_id = (String)session.getAttribute("user_id");        //����� ID
        
        //Form ��������
        WorkplanForm wpform = (WorkplanForm)form;
        WorkplanBean wpbean = new WorkplanBean();
        WorkplanManager wpmgr = WorkplanManager.instance();
        
        wpbean = wpmgr.viewWorkplan(wpform.getPlanno());
        
        int planno = 0;
        String mode = wpform.getMode();
        
        wpbean.setUptusrid(user_id);
        if(mode.equals("delete")){
            wpbean.setUseyn("N");
            planno = wpmgr.saveWorkplan(wpbean, null, null, null, mode);
        } else if(mode.equals("end")){
            wpbean.setStatus("2");
            planno = wpmgr.saveWorkplan(wpbean, null, null, null, mode);
        } else if(mode.equals("start")){
            wpbean.setStatus("1");
            planno = wpmgr.saveWorkplan(wpbean, null, null, null, mode);
        }
        
        
        if ( planno > 0) {
            String msg = "";
            if(mode.equals("delete")){
                msg = "alert('�ش� ��ȹ�� �����Ǿ����ϴ�.'); \n";                
            } else if(mode.equals("end")){
                msg = "alert('�ش� ��ȹ�� ����Ǿ����ϴ�.'); \n";
            } else if(mode.equals("start")){
                msg = "alert('�ش� ��ȹ�� �簳�Ǿ����ϴ�.'); \n";
            }
            
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg", msg));
            saveMessages(request,messages);
            
        }
        
        return mapping.findForward("list");
    }    
}