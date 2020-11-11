/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���չ����ۼ� �������� action
 * ����:
 */
package nexti.ejms.colldoc.action;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.appInfo;
import nexti.ejms.common.rootAction;
import nexti.ejms.colldoc.form.ColldocForm;
import nexti.ejms.colldoc.model.ColldocBean;
import nexti.ejms.colldoc.model.ColldocFileBean;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.formatBook.model.DataBookBean;
import nexti.ejms.formatBook.model.FormatBookManager;
import nexti.ejms.dept.model.DeptManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class ColldocSaveAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		response.setContentType("text/html;charset=euc-kr");
		PrintWriter out = response.getWriter();
		
		//�������� ��������
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");		//����� ID
		String user_name = (String)session.getAttribute("user_name");	//����� ����;
		String dept_code = (String)session.getAttribute("dept_code");	//����� �μ��ڵ�;
		String dept_name = (String)session.getAttribute("dept_name");	//����� �μ���;
		String sessi = session.getId().split("[!]")[0];
		
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
		
		String formapprep = (String)request.getParameter("formapprep");
		
		//Form ��������
		ColldocForm cdform = (ColldocForm)form;
		
		ColldocManager cdmgr = ColldocManager.instance();
		DeptManager dmgr = DeptManager.instance();
		
		ColldocBean cdbean = null;
		
		int sysdocno = cdform.getSysdocno();
		int chrg_code = 0;
		String chrg_name = null;
		
		if(formapprep == null) {
			chrg_code = cdform.getChrgunitcd();
			chrg_name = dmgr.getChrgunitnm(dept_code, chrg_code);
			
			String enddate = cdform.getEnddt_date();
			if(enddate.equals("") == false) {
				enddate = enddate + " " +
						  cdform.getEnddt_hour() + ":" +
						  cdform.getEnddt_min() + ":00";
			}
			
			cdbean = new ColldocBean();
			
			cdbean.setDoctitle(cdform.getDoctitle());		//��������
			cdbean.setBasicdate(cdform.getBasicdate());		//�ڷ������
			cdbean.setColdeptcd(dept_code);					//���պμ��ڵ�
			cdbean.setColdeptnm(dept_name);					//���պμ���
			cdbean.setChrgunitcd(chrg_code);				//���մ������ڵ�
			cdbean.setChrgunitnm(chrg_name);				//���մ�������
			cdbean.setChrgusrcd(user_id);					//���մ�����ڵ�
			cdbean.setChrgusrnm(user_name);					//���մ���ڸ�
			cdbean.setBasis(cdform.getBasis());				//���ñٰ�
			cdbean.setSummary(cdform.getSummary());			//���հ���
			cdbean.setEnddt(enddate);						//�����Ͻ�
			cdbean.setEndcomment(cdform.getEndcomment());	//�����˸���
			cdbean.setTgtdeptnm(cdform.getTgtdeptnm());		//����μ���,�׷��
			cdbean.setSancrule(cdform.getSancrule());		//�����ڷ�����
			
			cdbean.setSysdocno(sysdocno);					//�ý��۹�����ȣ(hidden)
			cdbean.setDocgbn("01");							//��������
			cdbean.setDocstate("01");						//��������
			cdbean.setDelyn("0");							//��Ͽ�����������
			cdbean.setUptusrid(user_id);					//�ۼ���ID
			
			if ( !"Y".equals(Utils.nullToEmptyString(cdform.getOpeninput())) ) cdform.setOpeninput("N");
			cdbean.setOpeninput(cdform.getOpeninput());		//�����Է�
		} else if(formapprep.equals("1") == true) {
			cdbean = cdmgr.getColldoc(sysdocno);
			
			chrg_code = cdbean.getChrgunitcd();
			chrg_name = dmgr.getChrgunitnm(dept_code, chrg_code);
		}
		
		ServletContext context = getServlet().getServletContext();

		//������ ���丮 ����
		Calendar cal = Calendar.getInstance();
		String saveDir = appInfo.getColldocSampleDir() + cal.get(Calendar.YEAR) + "/";
		
		cdbean.setAttachFile(cdform.getAttachFile());
		cdbean.setAttachFileYN(cdform.getAttachFileYN());
		
		String formatfileDir = context.getRealPath("");
		
		//1:��������, 2:�������� ����, 3:����, 4:�����ϰ�纻����, 5:�ӽ�����, 6:����
		//mode=5�� �����ۼ� ���� ������ �ӽ�����(��Ͽ��� �Ⱥ���)�ϱ� ���� ���
		//mode=6�� ���� ���� ������ ������������ ���� �ϵ��� �ϱ� ���� ���(�����)
		int mode = cdform.getMode();

		if(mode == 2 || mode == 4) {
			//���չ��� ���� ���� üũ
			List existFile = cdmgr.getExistColldocFile(formatfileDir, sysdocno);
			if(existFile != null && cdform.getAttachFile().getFileName().equals("") == true && cdform.getAttachFileYN().equals("N") == false ) {
				StringBuffer msg = new StringBuffer();
				msg.append("���չ����� ÷�ε� ������ �����ϴ�.\\n÷�������� �����Ͻðų� �����ڿ��� ���ǹٶ��ϴ�.\\n");
					
				for(int i = 0; i < existFile.size(); i++) {
					ColldocFileBean dbbean = (ColldocFileBean)existFile.get(i);
					msg.append("\\n" + " �� " + dbbean.getOriginfilenm());
				}
				
				out.write("<script language=javascript>location='/colldocInfoView.do?mode=" + 3 + "&sysdocno=" + sysdocno + "';alert(\"" + msg + "\");</script>");
				out.close();

				return null;
			}
			
			//�����ڷ��� ������� ���� üũ
			FormatBookManager fbmgr = FormatBookManager.instance();
			existFile = fbmgr.getExistBookFile(formatfileDir, sysdocno, 0);
			if(existFile != null) {
				StringBuffer msg = new StringBuffer();
				msg.append("�Ʒ� ��Ŀ� ÷�ε� ������ �����ϴ�.\\n����� �����Ͻðų� �����ڿ��� ���ǹٶ��ϴ�.\\n");
					
				for(int i = 0; i < existFile.size(); i++) {
					DataBookBean dbbean = (DataBookBean)existFile.get(i);
					msg.append("\\n" + dbbean.getFormseq() + ". " + dbbean.getFormtitle() + " �� " + dbbean.getOriginfilenm());
				}
				
				out.write("<script language=javascript>location='/colldocFormView.do?mode=" + 3 + "&sysdocno=" + sysdocno + "';alert(\"" + msg + "\");</script>");
				out.close();

				return null;
			}
		}

		sysdocno = cdmgr.saveColldoc(cdbean, formatfileDir, sessi, mode, context, saveDir);
		
		UserManager umgr = UserManager.instance();
		umgr.updateChrgunitcd(user_id, chrg_code);
		
		if(mode == 1) {
			mode = 2;
		} else if(mode == 2) {
			mode = 3;
		} else if(mode == 3) {
			cdmgr.setDelyn(sysdocno, 0);
		} else if(mode == 4) {
			cdmgr.setDelyn(cdbean.getSysdocno(), 0);	//�������� ���̰�
		} else if(mode == 5) {
			mode = 3;
			cdmgr.setDelyn(sysdocno, 1);				//�ӽù��� �Ⱥ���
		} else if(mode == 6) {
			mode = 2;
		}
		
		String reqAction = (String)request.getParameter("action");
		String action = null;
		
		if(reqAction.equals("INFOVIEW") == true) {			//���� �� ��������
			
			action = "document.location.href = " +
					 "'/colldocInfoView.do?mode=" + mode + "&sysdocno=" + sysdocno + "&msg=SAVE';";
			
		} else if(reqAction.equals("FORMVIEW") == true) {		//���� �� �������
			
			action = "document.location.href = " +
					 "'/colldocFormView.do?mode=" + mode + "&sysdocno=" + sysdocno + "';";
			
		} else if(reqAction.equals("TEMPFORMVIEW") == true) {	//�ӽ����� �� �������
			
			action = "document.location.href = " +
			 		 "'/colldocFormView.do?mode=" + mode + "&sysdocno=" + sysdocno + "';";
			
		} else if(reqAction.equals("APPREP") == true) {			//���� �� ������
			
			//���չ��� ������ ���·� ����
			String docstate = "04";		//������(02), ���δ��(03), ��������(04)		
			if(cdform.getSancusrnm2().trim().equals("") == false) {	//������
				docstate = "03";
			}
			if(cdform.getSancusrnm1().trim().equals("") == false) {	//������
				docstate = "02";
			}
			
			action = "document.location.href = " +
			 		 "'/colldocAppRep.do?sysdocno=" + sysdocno + "&docstate=" + docstate +"';";
			
		}
		
		out.write("<script language=javascript>" + action + "</script>");
		out.close();

		return null;
	}	
}