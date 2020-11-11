/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� �������� �μ���� ������ 
 * ����:
 */
package nexti.ejms.user.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.appInfo;
import nexti.ejms.common.rootFrameAction;
import nexti.ejms.user.form.UsrMgrForm;
import nexti.ejms.user.model.UsrMgrManager;

public class UsrMgrFrame1Action extends rootFrameAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {	

		UsrMgrForm usrMgrForm = (UsrMgrForm)form;		
		UsrMgrManager usrMgrManager = UsrMgrManager.instance();

		HttpSession session = request.getSession();
		String rep_dept = (String)session.getAttribute("rep_dept");
		String user_gbn = (String)session.getAttribute("user_gbn");
		String user_id  = (String)session.getAttribute("user_id");
		String dept_depth = "";

		if(appInfo.getRootid().equals(rep_dept)){
			if(request.getParameter("orggbn") != null){
				usrMgrForm.setOrggbn(request.getParameter("orggbn"));
			}else{
				usrMgrForm.setOrggbn(user_gbn);
			}
			dept_depth = "1";
			
		}else{
			usrMgrForm.setOrggbn(user_gbn);
			dept_depth = "2";
		}
		
		List deptBeanList = usrMgrManager.deptList("", usrMgrForm.getOrggbn(), user_id, dept_depth);
		usrMgrForm.setDeptBeanList(deptBeanList);
		
		return mapping.findForward("frame1");
	}
}
