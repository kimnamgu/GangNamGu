/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 진행중인취합문서 취합문서정보 action
 * 설명:
 */
package nexti.ejms.workplan.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class WorkplanInfoViewAction extends rootAction {
    
    public ActionForward doService(
            ActionMapping mapping, 
            ActionForm form,
            HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
        
        WorkplanForm wpform = (WorkplanForm)form;
        
        int planno = 0;
        
        if(request.getParameter("planno") != null) {
            planno = Integer.parseInt(request.getParameter("planno"));
        }
        
        request.setAttribute("planno", new Integer(planno));
        
        WorkplanManager manager = WorkplanManager.instance();
        WorkplanBean workplanBean = manager.viewWorkplan(planno);
        BeanUtils.copyProperties(wpform, workplanBean);
        
        UserManager uMgr = UserManager.instance();
        DeptManager dMgr = DeptManager.instance();
        
        UserBean uBean = uMgr.getUserInfo(workplanBean.getChrgusrcd());
        DeptBean dBean = dMgr.getDeptInfo(workplanBean.getDeptcd());
        
        if ( uBean != null && !"".equals(Utils.nullToEmptyString(uBean.getTel())) ) {
            wpform.setUser_tel(uBean.getTel());
        } else if ( dBean != null && !"".equals(Utils.nullToEmptyString(dBean.getDept_tel())) ) {
            wpform.setUser_tel(dBean.getDept_tel());                
        }
        
        wpform.setReadcnt(manager.workplanReadCount(planno));
        wpform.setSearchyear(request.getParameter("searchyear"));
        
        return mapping.findForward("workplanInfoView");
    }
}
