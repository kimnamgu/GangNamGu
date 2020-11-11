/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Է��ϱ� ���չ������� action
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
import nexti.ejms.commtreat.model.CommTreatBean;
import nexti.ejms.commtreat.model.CommTreatManager;
import nexti.ejms.commdocinfo.form.CommCollDocInfoForm;
import nexti.ejms.commdocinfo.model.CommCollDocInfoBean;
import nexti.ejms.commdocinfo.model.CommCollDocInfoManager;
import nexti.ejms.elecAppr.model.ElecApprBean;
import nexti.ejms.elecAppr.model.ElecApprManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class InputCollDocInfoViewAction extends rootAction {

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
		
		//�Է��ϱ� �� - ���չ������� ������ ����
		CommCollDocInfoManager manager = CommCollDocInfoManager.instance();
		CommCollDocInfoBean collDocInfoBean = manager.viewCommCollDocInfo(sysdocno);

		BeanUtils.copyProperties(commCollDocInfoForm, collDocInfoBean);
		
		UserManager umgr = UserManager.instance();
		UserBean ubean = umgr.getUserInfo(collDocInfoBean.getChrgusrcd());
		commCollDocInfoForm.setColdepttel(Utils.nullToEmptyString(ubean.getTel()));
		
		//////////ó����Ȳ ����//////////
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
		
		//�Է��ϱ� �� - ó����Ȳ
		CommTreatManager ctmgr = CommTreatManager.instance();
		CommTreatBean treatBean = ctmgr.viewInputTreatStatus(sysdocno, user_id, dept_code);
		
		if (treatBean == null) {
			return new ActionForward("/inputingList.do", false);
		}
		
		commCollDocInfoForm.setInputdeptcd(treatBean.getInputdeptcd());
		commCollDocInfoForm.setChrgunitcd(treatBean.getChrgunitcd());
		commCollDocInfoForm.setAppntusrnm(treatBean.getAppntusrnm());
		commCollDocInfoForm.setInputuser1(treatBean.getInputuser1());
		commCollDocInfoForm.setInputuser2(treatBean.getInputuser2());
		//////////ó����Ȳ ��//////////
		
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
		
		return mapping.findForward("inputcolldocinfo");
	}
}