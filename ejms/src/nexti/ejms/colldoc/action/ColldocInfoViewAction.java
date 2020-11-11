/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���չ����ۼ� ���չ������� action
 * ����:
 */
package nexti.ejms.colldoc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.rootAction;
import nexti.ejms.colldoc.form.ColldocForm;
import nexti.ejms.colldoc.model.ColldocBean;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.dept.model.DeptManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class ColldocInfoViewAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		//�������� ��������
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");		//����� ID
		String user_name = (String)session.getAttribute("user_name");	//����� ����;
		String dept_code = (String)session.getAttribute("dept_code");	//����� �μ��ڵ�;
		String dept_name = (String)session.getAttribute("dept_name");	//����� �μ���;
		
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
		
		DeptManager dmgr = DeptManager.instance();
		int chrg_code = dmgr.getChrgunitcd(user_id);
		
		UserManager umgr = UserManager.instance();
		UserBean ubean = umgr.getUserInfo(user_id);
		String tel = ubean.getTel();
		
		//Form���� �Ѿ�� �� ��������
		ColldocForm cdform = (ColldocForm)form;	
		
		//�������� �� ȣ���
		int mode = Integer.parseInt((String)request.getParameter("mode"), 10);
		int sysdocno = Integer.parseInt((String)request.getParameter("sysdocno"), 10);
		
		ColldocManager cdmgr = ColldocManager.instance();
		ColldocBean cdbean = cdmgr.getColldoc(sysdocno);
		
		int formcount = cdmgr.getCountFormat(sysdocno);		

		cdform.setMode(mode);	//1:�����ۼ�, 2:������������, 3:����, 4:����������������̵�(3����������2������)
		cdform.setSysdocno(sysdocno);					//�ý��۹�����ȣ
		cdform.setFormcount(formcount);					//��İ���
		
		cdform.setDoctitle(cdbean.getDoctitle());		//��������
		cdform.setBasicdate(cdbean.getBasicdate());		//�ڷ������
		cdform.setColdeptcd(dept_code);					//���պμ��ڵ�
		cdform.setColdeptnm(dept_name);					//���պμ���
		cdform.setChrgunitcd(chrg_code);				//���մ������ڵ�
		cdform.setChrgusrnm(user_name);					//���մ���ڸ�
		cdform.setD_tel(tel);							//�μ���ȭ��ȣ
		cdform.setBasis(cdbean.getBasis());				//���ñٰ�
		cdform.setSummary(cdbean.getSummary());			//���հ���
		cdform.setEnddt_date(cdbean.getEnddt_date());	//������¥
		cdform.setEnddt_hour(cdbean.getEnddt_hour());	//������
		cdform.setEnddt_min(cdbean.getEnddt_min());		//������
		cdform.setEndcomment(cdbean.getEndcomment());	//�����˸���
		cdform.setTgtdeptnm(cdbean.getTgtdeptnm());		//������μ�
		cdform.setSancrule(cdbean.getSancrule());		//�����ڷ�����
		cdform.setSancusrnm1(cdbean.getSancusrnm1());	//������
		cdform.setSancusrnm2(cdbean.getSancusrnm2());	//������
		cdform.setOpeninput(cdbean.getOpeninput());		//�����Է�
		cdform.setFileseq(cdbean.getFileseq());			//÷�����Ϲ�ȣ
		cdform.setFilenm(cdbean.getFilenm());			//÷�����ϸ�
		cdform.setOriginfilenm(cdbean.getOriginfilenm());	//�������ϸ�
		
		if ( request.getParameter("msg") != null ) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg", "�����Ͽ����ϴ�."));
			saveMessages(request,messages);
		}
		
		//������ ���翩��
		DeptManager deptMgr = DeptManager.instance();
		int chrgunit_cnt = deptMgr.getExistedChrg(dept_code);
		request.setAttribute("chrgunit_cnt", String.valueOf(chrgunit_cnt));
			
		return mapping.findForward("colldocInfoView");
	}
}