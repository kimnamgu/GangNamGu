/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합문서작성 문서저장 action
 * 설명:
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
        
        //세션정보 가져오기
        HttpSession session = request.getSession();
        String user_id = (String)session.getAttribute("user_id");        //사용자 ID
        
        //Form 가져오기
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
                msg = "alert('해당 계획이 삭제되었습니다.'); \n";                
            } else if(mode.equals("end")){
                msg = "alert('해당 계획이 종료되었습니다.'); \n";
            } else if(mode.equals("start")){
                msg = "alert('해당 계획이 재개되었습니다.'); \n";
            }
            
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg", msg));
            saveMessages(request,messages);
            
        }
        
        return mapping.findForward("list");
    }    
}