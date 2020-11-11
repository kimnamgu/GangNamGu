/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����������չ��� ���չ������� �������� action
 * ����:
 */
package nexti.ejms.colldoc.action;

import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.appInfo;
import nexti.ejms.common.rootAction;
import nexti.ejms.commdocinfo.form.CommCollDocInfoForm;
import nexti.ejms.commdocinfo.model.CommCollDocInfoBean;
import nexti.ejms.commdocinfo.model.CommCollDocInfoManager;
import nexti.ejms.dept.model.ChrgUnitBean;
import nexti.ejms.dept.model.DeptManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class CollprocInfoSaveAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		int sysdocno = 0;
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		//�������� ��������
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");		//����� ���̵�
		String user_name = (String)session.getAttribute("user_name");;	//����� �̸�
		String dept_code = (String)session.getAttribute("dept_code");	//����� �μ��ڵ�
		String dept_name = (String)session.getAttribute("dept_name");	//����� �μ���
		
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
		
		CommCollDocInfoManager manager = CommCollDocInfoManager.instance();
		
		//Form ��������
		CommCollDocInfoForm cdform = (CommCollDocInfoForm)form;
		
		CommCollDocInfoBean cdbean = new CommCollDocInfoBean();
		
		String enddate = cdform.getEnddt_date() + " " +
						 cdform.getEnddt_hour() + ":" +
						 cdform.getEnddt_min() + ":00";
		
		String chrg_name = "";
		DeptManager dmgr = DeptManager.instance();
		ChrgUnitBean cubean = dmgr.chrgUnitInfo(dept_code, cdform.getChrgunitcd());
		if (cubean != null) {
			chrg_name = cubean.getChrgunitnm();
		}
		
		//ȭ����� ��������
		cdbean.setDoctitle(cdform.getDoctitle());		//��������
		cdbean.setBasicdate(cdform.getBasicdate());		//�ڷ������
		cdbean.setColdeptcd(dept_code);					//���պμ��ڵ�
		cdbean.setColdeptnm(dept_name);					//���պμ���
		cdbean.setChrgunitcd(cdform.getChrgunitcd());	//���մ������ڵ�
		cdbean.setChrgunitnm(chrg_name);				//���մ�������
		cdbean.setChrgusrnm(user_name);					//���մ���ڸ�
		cdbean.setBasis(cdform.getBasis());				//���ñٰ�
		cdbean.setSummary(cdform.getSummary());			//���հ���		
		cdbean.setEnddt(enddate);						//�����Ͻ�
		cdbean.setEndcomment(cdform.getEndcomment());	//�����˸���
		cdbean.setTargetdeptnm(cdform.getTgtdeptnm());	//����μ���,�׷��
		cdbean.setSancrulecd(cdform.getSancrulecd());	//�����ڷ�����
		
		if ( !"Y".equals(Utils.nullToEmptyString(cdform.getOpeninput())) ) cdform.setOpeninput("N");
		cdbean.setOpeninput(cdform.getOpeninput());		//�����Է�
		
		ServletContext context = getServlet().getServletContext();

		//������ ���丮 ����
		Calendar cal = Calendar.getInstance();
		String saveDir = appInfo.getColldocSampleDir() + cal.get(Calendar.YEAR) + "/";
		
		cdbean.setAttachFile(cdform.getAttachFile());
		cdbean.setAttachFileYN(cdform.getAttachFileYN());

		//����� ���պμ�����, ����μ�, ����׷� �ӽ� ���̺� ������ ���󹮼� �����ͷ� ���� 
		int result = manager.saveCommCollDocInfo(cdbean, sysdocno, user_id, context, saveDir);
		
		String msg = "";
		if(result > 0){
			msg = "����Ǿ����ϴ�.";
		}else{
			msg = "���忡 �����߽��ϴ�.";
		}
		
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
		saveMessages(request,messages);
		
		//������ ���� ����
		request.setAttribute("sysdocno", new Integer(sysdocno));
		
		return mapping.findForward("collprocinfosave");
	}	
}