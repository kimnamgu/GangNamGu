/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���չ����ۼ� ���θ���� action
 * ����:
 */
package nexti.ejms.colldoc.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.colldoc.form.ColldocForm;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.dept.model.DeptManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.DateTime;

public class ColldocMakeAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");		//����� ID
		String user_name = (String)session.getAttribute("user_name");	//����� ����;
		String dept_code = (String)session.getAttribute("dept_code");	//����� �μ��ڵ�;
		String dept_name = (String)session.getAttribute("dept_name");	//����� �μ���;
		String sessi = session.getId().split("[!]")[0];
		
		DeptManager dmgr = DeptManager.instance();
		int chrg_code = dmgr.getChrgunitcd(user_id);
		
		UserManager umgr = UserManager.instance();
		UserBean ubean = umgr.getUserInfo(user_id);
		String tel = ubean.getTel();
		
		ServletContext context = getServlet().getServletContext();
		
		//����� ���պμ�����, ����μ�, ����׷� �ӽ� ���̺� ������, �̿Ϸ�����չ���  ����
		ColldocManager cdmgr = ColldocManager.instance();
		cdmgr.delColldocTempData_TGT_SANC(sessi);
		cdmgr.delColldoc(cdmgr.getListCancelColldoc(), context);

		//������ ���翩��
		DeptManager deptMgr = DeptManager.instance();
		int chrgunit_cnt = deptMgr.getExistedChrg(dept_code);
		request.setAttribute("chrgunit_cnt", String.valueOf(chrgunit_cnt));
		
		//Form ��������
		ColldocForm cdform = (ColldocForm)form;
		
		cdform.setMode(1);	//1:�����ۼ�, 2:������������, 3:����, 4:����������������̵�(3����������2������)
		cdform.setSysdocno(0);				//�����չ����ۼ� : 0
		cdform.setBasicdate(DateTime.getCurrentDate());	//�ڷ������
		cdform.setColdeptcd(dept_code);		//�μ��ڵ�
		cdform.setColdeptnm(dept_name);		//�μ���
		cdform.setChrgunitcd(chrg_code);	//�������ڵ�
		cdform.setChrgusrnm(user_name);		//�����ڸ�(�����)
		cdform.setD_tel(tel);				//�μ���ȭ��ȣ
		cdform.setEnddt_hour("00");			//������
		cdform.setEnddt_min("00");			//������
		cdform.setSancrule("01");			//��������� �⺻��
		
		session.removeAttribute("originuserid");
		
		return mapping.findForward("colldocInfoView");
	}
}