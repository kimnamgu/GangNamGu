/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����������չ��� ó����Ȳ action
 * ����:
 */
package nexti.ejms.colldoc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.common.rootAction;
import nexti.ejms.commtreat.form.CommTreatForm;
import nexti.ejms.commtreat.model.CommTreatBean;
import nexti.ejms.commtreat.model.CommTreatManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class CollprocTreatViewAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
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
		
		//Form���� �Ѿ�� �� ��������
		CommTreatForm commTreatForm = (CommTreatForm)form;
		
		//ó����Ȳ
		CommTreatManager manager = CommTreatManager.instance();
		CommTreatBean treatBean = manager.viewCommTreatStatus1(sysdocno, dept_code, user_id);
		
		commTreatForm.setSancusrnm1(treatBean.getSancusrnm1());
		commTreatForm.setSancusrnm2(treatBean.getSancusrnm2());
		commTreatForm.setTcnt(treatBean.getTcnt());
		commTreatForm.setScnt(treatBean.getScnt());
		commTreatForm.setFcnt(treatBean.getFcnt());
		
		//�������¿� �ش糯¥ ��������
		treatBean = manager.getDocStates(sysdocno, dept_code);
		commTreatForm.setDocstate(treatBean.getDocstate());
		commTreatForm.setDocstatenm(treatBean.getDocstatenm());
		commTreatForm.setEnddt(treatBean.getEnddt());
		commTreatForm.setDeliverydt(treatBean.getDeliverydt());
		
		//�������� ��������
		ColldocManager cdmgr = ColldocManager.instance();
		String state = cdmgr.getDocState(sysdocno).getDocstate();
		request.setAttribute("state", state);

		return mapping.findForward("collproctreatview");
	}
}