/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Է��ϱ� ó����Ȳ action
 * ����:
 */
package nexti.ejms.inputing.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.commtreat.form.CommTreatForm;
import nexti.ejms.commtreat.model.CommTreatBean;
import nexti.ejms.commtreat.model.CommTreatManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class InputTreatViewAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//Form���� �Ѿ�� �� ��������
		CommTreatForm commTreatForm = (CommTreatForm)form;
		
		String dept_code = "";
		String user_id = "";
		int sysdocno = 0;
		
		//�������� ��������
		HttpSession session = request.getSession();
		dept_code = session.getAttribute("dept_code").toString();
		user_id = session.getAttribute("user_id").toString();
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				user_id = originUserBean.getUser_id();
				dept_code = originUserBean.getDept_id();
			}
		}
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		//�Է��ϱ� �� - ó����Ȳ
		CommTreatManager manager = CommTreatManager.instance();
		CommTreatBean treatBean = manager.viewInputTreatStatus(sysdocno, user_id, dept_code);
		
		if (treatBean == null) {
			return new ActionForward("/inputingList.do", false);
		}
		
		commTreatForm.setSubmitstate(treatBean.getSubmitstate());
		commTreatForm.setInputdeptcd(treatBean.getInputdeptcd());
		commTreatForm.setChrgunitcd(treatBean.getChrgunitcd());
		commTreatForm.setAppntusrnm(treatBean.getAppntusrnm());
		commTreatForm.setInputuser1(treatBean.getInputuser1());
		commTreatForm.setInputuser2(treatBean.getInputuser2());
		commTreatForm.setSancList1(treatBean.getSancList1());
		commTreatForm.setSancList2(treatBean.getSancList2());
		
		return mapping.findForward("inputtreatview");
	}
}