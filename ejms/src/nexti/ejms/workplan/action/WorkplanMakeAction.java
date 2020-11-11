/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���չ����ۼ� ���θ���� action
 * ����:
 */
package nexti.ejms.workplan.action;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

public class WorkplanMakeAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");		//����� ID
		
		UserManager uMgr = UserManager.instance();
        DeptManager dMgr = DeptManager.instance();
        
        UserBean uBean = uMgr.getUserInfo(user_id);
        DeptBean dBean = dMgr.getDeptInfo(uBean.getDept_id());
		
		//Form ��������
		WorkplanForm wpform = (WorkplanForm)form;
		
		if("modify".equals(wpform.getMode())) {
		    WorkplanBean wpBean = new WorkplanBean();
		    
		    WorkplanManager wpmgr = WorkplanManager.instance();
		    wpBean = wpmgr.viewWorkplan(wpform.getPlanno());
		    BeanUtils.copyProperties(wpform, wpBean);
		} else {
			wpform.setUpperdeptcd(dBean.getUpper_dept_id());
			wpform.setUpperdeptnm(dMgr.getDeptInfo(dBean.getUpper_dept_id()).getDept_name());
			wpform.setDeptcd(dBean.getDept_id());
			wpform.setDeptnm(dBean.getDept_name());
			wpform.setChrgusrcd(uBean.getUser_id());
			wpform.setChrgusrnm(uBean.getUser_name());
		}
		
		if ( dMgr.getExistedChrg(uBean.getDept_id()) == 0 ) {
			wpform.setChrgunitcd(-1);
		} else {
			wpform.setChrgunitcd(dMgr.getChrgunitcd(user_id));
		}
		
		if ( uBean != null && !"".equals(Utils.nullToEmptyString(uBean.getTel())) ) {
			wpform.setUser_tel(uBean.getTel());
		} else if ( dBean != null && !"".equals(Utils.nullToEmptyString(dBean.getDept_tel())) ) {
			wpform.setUser_tel(dBean.getDept_tel());				
		}
		
		if ( "".equals(wpform.getStrdt()) ) {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            wpform.setStrdt(year + "-01-01");
            wpform.setEnddt(year + "-12-31");
        }
		
		return mapping.findForward("workplanMake");
	}
}