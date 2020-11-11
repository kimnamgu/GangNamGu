/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� �������� �μ�/��������� ������ 
 * ����:
 */
package nexti.ejms.user.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nexti.ejms.common.rootFrameAction;
import nexti.ejms.user.form.UsrMgrForm;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.user.model.UsrMgrBean;
import nexti.ejms.user.model.UsrMgrManager;
import nexti.ejms.util.Utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class UsrMgrFrame3Action extends rootFrameAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {	

		UsrMgrForm usrMgrForm = (UsrMgrForm)form;		
		
		String mode = request.getParameter("mode");
		String dept_id = request.getParameter("dept_id");
		String error = (String)request.getAttribute("error");

		UsrMgrManager usrMgrManager = UsrMgrManager.instance();

		//�μ��� �����Ͽ��� ��			
		if("dept_M".equals(mode)){
			UsrMgrBean uBean = usrMgrManager.deptView(dept_id);
			uBean.setUpper_dept_name(usrMgrManager.getDeptnm(uBean.getUpper_dept_id()));
			
			BeanUtils.copyProperties(usrMgrForm, uBean);
			request.setAttribute("isNew", "1");
			return mapping.findForward("frame3_1");

		//�űԺμ��� �����Ͽ��� ��			
		} else if("dept_I".equals(mode) || "dept_I".equals(error)){
			UsrMgrBean uBean = new UsrMgrBean();
			
			uBean.setUpper_dept_id(dept_id);
			uBean.setUpper_dept_name(usrMgrManager.getDeptnm(dept_id));
			BeanUtils.copyProperties(usrMgrForm, uBean);
			
			request.setAttribute("isNew", "0");
			return mapping.findForward("frame3_1");
			
		//����ڸ� �����Ͽ��� ��
		} else if("usr_M".equals(mode)){
			String user_id = request.getParameter("user_id");
			UsrMgrBean uBean = usrMgrManager.usrView(user_id);
			uBean.setUpper_dept_name(usrMgrManager.getDeptnm(uBean.getDept_id()));
			BeanUtils.copyProperties(usrMgrForm, uBean);

			request.setAttribute("isNew", "1");
			return mapping.findForward("frame3_2");

		//�ű� ����ڸ� �����Ͽ��� �� 
		} else if("usr_I".equals(mode) || "usr_I".equals(error)){
			UsrMgrBean uBean = new UsrMgrBean();

			uBean.setUser_id(Utils.nullToEmptyString(UserManager.instance().getNextId("B")).trim());
			uBean.setDept_id(dept_id);
			uBean.setDept_name(usrMgrManager.getDeptnm(dept_id));
			BeanUtils.copyProperties(usrMgrForm, uBean);
			
			request.setAttribute("isNew", "0");
			return mapping.findForward("frame3_2");
		}
		return null;
	}
}

