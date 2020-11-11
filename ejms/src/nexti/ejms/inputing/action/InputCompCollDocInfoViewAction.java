/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Է¿Ϸ� ���չ������� action
 * ����:
 */
package nexti.ejms.inputing.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.commdocinfo.form.CommCollDocInfoForm;
import nexti.ejms.commdocinfo.model.CommCollDocInfoBean;
import nexti.ejms.commdocinfo.model.CommCollDocInfoManager;
import nexti.ejms.commsubdept.model.commsubdeptManager;
import nexti.ejms.commtreat.model.CommTreatBean;
import nexti.ejms.commtreat.model.CommTreatManager;
import nexti.ejms.elecAppr.model.ElecApprBean;
import nexti.ejms.elecAppr.model.ElecApprManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class InputCompCollDocInfoViewAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//Form���� �Ѿ�� �� ��������
		CommCollDocInfoForm commCollDocInfoForm = (CommCollDocInfoForm)form;
		//�������� ��������
		HttpSession session = request.getSession();
		String dept_code = session.getAttribute("dept_code").toString();
		String user_id = session.getAttribute("user_id").toString();
		
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
		
		int sysdocno = 0;
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		//�Է¿Ϸ� �� - ���չ������� ������ ����
		CommCollDocInfoManager manager = CommCollDocInfoManager.instance();
		CommCollDocInfoBean collDocInfoBean = manager.viewCommCollDocInfo(sysdocno);
		
		commsubdeptManager manager1 = commsubdeptManager.instance();
		String processchk = manager1.getProcessChk(sysdocno, dept_code, user_id, "1", "2");
		
		request.setAttribute("processchk", processchk);

		BeanUtils.copyProperties(commCollDocInfoForm, collDocInfoBean);
		
		UserManager umgr = UserManager.instance();
		UserBean ubean = umgr.getUserInfo(collDocInfoBean.getChrgusrcd());
		commCollDocInfoForm.setColdepttel(Utils.nullToEmptyString(ubean.getTel()));
		
		/////////////ó����Ȳ ����///////////
		//�Է¿Ϸ� �� - ó����Ȳ
		CommTreatManager ctmgr = CommTreatManager.instance();
		CommTreatBean treatBean = ctmgr.viewInputCompTreatStatus(sysdocno, user_id, dept_code);
		
		commCollDocInfoForm.setSubmitstate(treatBean.getSubmitstate());
		commCollDocInfoForm.setInputcomp(treatBean.getInputcomp());
		commCollDocInfoForm.setInputstate(treatBean.getInputstate());
		commCollDocInfoForm.setInputuser1(treatBean.getInputuser1());
		commCollDocInfoForm.setInputuser2(treatBean.getInputuser2());
		
		ubean = umgr.getUserInfo(user_id);
		commCollDocInfoForm.setInputdeptnm(ubean.getDept_fullname());
		commCollDocInfoForm.setInputusrnm(ubean.getUser_name());
		/////////////ó����Ȳ ��///////////
		
		//���ڰ�������
		ElecApprManager eamgr = ElecApprManager.instance();
		ElecApprBean eaBean = eamgr.selectColldocSancInfo(sysdocno, dept_code, user_id);
		
		if ( eaBean != null ) {
			String sancresult = Utils.nullToEmptyString(eaBean.getSancresult());
			String submitdt = Utils.nullToEmptyString(eaBean.getSubmitdt());
			String sancusrnm = Utils.nullToEmptyString(eaBean.getSancusrnm());
			if ( eaBean.getSancyn().equals("0") == true ) {
				sancusrnm = "���� ������";
			} else {
				sancusrnm = sancresult + " : " + submitdt + " " + sancusrnm;
			}
			commCollDocInfoForm.setSancusrinfo(sancusrnm);
		}

		//������
		request.setAttribute("chrgunitnm", commCollDocInfoForm.getChrgunitnm());
		
		return mapping.findForward("incompcolldocinfo");
	}
}