/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���չ����ۼ� �������� action
 * ����:
 */
package nexti.ejms.workplan.action;

import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nexti.ejms.common.appInfo;
import nexti.ejms.common.rootAction;
import nexti.ejms.dept.model.DeptBean;
import nexti.ejms.dept.model.DeptManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;
import nexti.ejms.workplan.form.WorkplanForm;
import nexti.ejms.workplan.model.WorkplanBean;
import nexti.ejms.workplan.model.WorkplanManager;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class WorkplanSaveAction extends rootAction {

    public ActionForward doService(
            ActionMapping mapping, 
            ActionForm form, 
            HttpServletRequest request, 
            HttpServletResponse response) 
    throws Exception {
        
        //�������� ��������
        HttpSession session = request.getSession();
        String user_id = (String)session.getAttribute("user_id");        //����� ID
        String user_name = (String)session.getAttribute("user_name");    //����� ����;
        String dept_code = (String)session.getAttribute("dept_code");    //����� �μ��ڵ�;
        String dept_name = (String)session.getAttribute("dept_name");    //����� �μ���;       
        
        DeptManager dMgr = DeptManager.instance();
    	DeptBean dBean = dMgr.getDeptInfo(dept_code);
        dept_name = dBean.getDept_name();
        
        if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
            String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
            if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
            originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
            UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
            if ( originUserBean != null ) {
                user_id = originUserBean.getUser_id();
                user_name = originUserBean.getUser_name();
                dept_code = originUserBean.getDept_id();
                dept_name = UserManager.instance().getDeptName(user_id);
            }
        }
        
        //Form ��������
        WorkplanForm wpform = (WorkplanForm)form;
        
        UserManager uMgr = UserManager.instance();
        UserBean uBean = uMgr.getUserInfo(wpform.getChrgusrcd());
        
        WorkplanBean wpbean = new WorkplanBean();
        BeanUtils.copyProperties(wpbean, wpform);
        
        wpbean.setDeptcd(dBean.getDept_id());
		wpbean.setDeptnm(dBean.getDept_name());
        wpbean.setChrgunitnm(dMgr.getChrgunitnm(dBean.getDept_id(), wpform.getChrgunitcd()));
        //wpbean.setChrgusrcd(user_id);
        wpbean.setChrgusrnm(uBean.getUser_name());
        wpbean.setUptusrid(user_id);
        
        ServletContext context = getServlet().getServletContext();
        String formatfileDir = context.getRealPath("");

        String saveDir = appInfo.getWorkplanDir() + Calendar.getInstance().get(Calendar.YEAR) + "/";
        
        WorkplanManager wpmgr = WorkplanManager.instance();
        int planno = wpmgr.saveWorkplan(wpbean, formatfileDir, context, saveDir, wpform.getMode());
        
        if ( planno > 0 ) {
			StringBuffer msg = new StringBuffer();
			msg.append("alert('����Ǿ����ϴ�.'); \n");
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg", msg));
			saveMessages(request,messages);
			
			try {
				wpbean = wpmgr.viewWorkplan(planno);
				//UserManager uMgr = UserManager.instance();
				uMgr.updateChrgunitcd(wpbean.getChrgusrcd(), wpbean.getChrgunitcd());
			} catch ( Exception e ) {
				msg.append("alert('�������� ������� �ʾҽ��ϴ�.'); \n");
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg", msg));
			}
		}
        
        return mapping.findForward("list");
    }    
}