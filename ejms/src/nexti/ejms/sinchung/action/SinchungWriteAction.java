/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� �ۼ� action
 * ����:
 */
package nexti.ejms.sinchung.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.sinchung.form.SinchungForm;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.DateTime;

public class SinchungWriteAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {		 
		
		//����������������
		HttpSession session = request.getSession();
		String deptcd = session.getAttribute("dept_code").toString();    //�μ��ڵ�
		String deptnm = session.getAttribute("dept_name").toString();    //�μ���Ī
		String usrid = session.getAttribute("user_id").toString();	     //�����ID
		String usrnm = session.getAttribute("user_name").toString();     //������̸�
		String sessi = session.getId().split("[!]")[0];                  //���ǰ�
		
		//�����μ� ��ȭ��ȣ
		String tel = "";
		UserBean ubean = UserManager.instance().getUserInfo(usrid);
		if ( ubean != null ) {
			tel = ubean.getTel();
		}
		
		//�ӽ� ���̺� ����
		SinchungManager smgr = SinchungManager.instance();
		smgr.deleteTempAll(sessi, getServlet().getServletContext());
		
		//�ʱⵥ���� ����
		SinchungForm sForm = (SinchungForm)form;
		sForm.setColdeptcd(deptcd);
		sForm.setColdeptnm(deptnm);
		sForm.setColtel(tel);
		sForm.setChrgusrid(usrid);
		sForm.setChrgusrnm(usrnm);
		sForm.setExamcount(5);	//�⺻ ���� ����
		
		sForm.setStrdt(DateTime.getCurrentDate());
		sForm.setEnddt(DateTime.getCurrentDate());	
		
		return mapping.findForward("write");
	}
}