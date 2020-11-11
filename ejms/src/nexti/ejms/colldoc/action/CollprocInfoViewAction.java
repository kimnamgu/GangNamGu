/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����������չ��� ���չ������� action
 * ����:
 */
package nexti.ejms.colldoc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.beanutils.BeanUtils;

import nexti.ejms.commdocinfo.form.CommCollDocInfoForm;
import nexti.ejms.commdocinfo.model.CommCollDocInfoBean;
import nexti.ejms.commdocinfo.model.CommCollDocInfoManager;
import nexti.ejms.common.rootAction;
import nexti.ejms.commtreat.model.CommTreatBean;
import nexti.ejms.commtreat.model.CommTreatManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class CollprocInfoViewAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//Form���� �Ѿ�� �� ��������
		CommCollDocInfoForm commCollDocInfoForm = (CommCollDocInfoForm)form;
		
		int sysdocno = 0;
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		request.setAttribute("sysdocno", new Integer(sysdocno));
		
		//�������� ���չ��� �� - ���չ������� ������ ����
		CommCollDocInfoManager manager = CommCollDocInfoManager.instance();
		CommCollDocInfoBean collDocInfoBean = manager.viewCommCollDocInfo(sysdocno);

		BeanUtils.copyProperties(commCollDocInfoForm, collDocInfoBean);
		
		//////////////////ó����Ȳ ����//////////////////
		String dept_code = "";
		String user_id = "";
		
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
		
		UserManager umgr = UserManager.instance();
		UserBean ubean = umgr.getUserInfo(collDocInfoBean.getChrgusrcd());
		commCollDocInfoForm.setColdepttel(Utils.nullToEmptyString(ubean.getTel()));
		
		//ó����Ȳ
		CommTreatManager ctmgr = CommTreatManager.instance();
		CommTreatBean treatBean = ctmgr.viewCommTreatStatus1(sysdocno, dept_code, user_id);
		
		commCollDocInfoForm.setTcnt(treatBean.getTcnt());
		commCollDocInfoForm.setScnt(treatBean.getScnt());
		commCollDocInfoForm.setFcnt(treatBean.getFcnt());
		
		//�������¿� �ش糯¥ ��������
		treatBean = ctmgr.getDocStates(sysdocno, dept_code);
		commCollDocInfoForm.setDocstate(treatBean.getDocstate());
		commCollDocInfoForm.setDocstatenm(treatBean.getDocstatenm());
		commCollDocInfoForm.setEnddt(treatBean.getEnddt());
		commCollDocInfoForm.setDeliverydt(treatBean.getDeliverydt());
		//////////////////ó����Ȳ ��//////////////////
		
		//������
		request.setAttribute("chrgunitnm", commCollDocInfoForm.getChrgunitnm());
		
		return mapping.findForward("collprocinfoview");
	}
}