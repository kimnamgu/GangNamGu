/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���缱���� �߰� action
 * ����:
 */
package nexti.ejms.commapproval.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.approval.model.ApprovalManager;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.commapproval.model.commapprovalManager;
import nexti.ejms.commapproval.model.commapprovalBean;
import nexti.ejms.commtreat.model.CommTreatManager;
import nexti.ejms.common.rootPopupAction;
import nexti.ejms.delivery.model.DeliveryManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class designateInsertAction extends rootPopupAction{

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
	
		request.setCharacterEncoding("UTF-8");
		
		String cmd = request.getParameter("cmd");
		String type = request.getParameter("type");
		int isMaking = Integer.parseInt(request.getParameter("isMaking"));
		int	sysdocno =0;
		sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		String idList = request.getParameter("idList");
		String nameList = request.getParameter("nameList");
		String gubunList = request.getParameter("gubunList");
		
		//�������� ��������
		HttpSession session = request.getSession();		
		String sessionId = session.getId().split("[!]")[0];
		String user_id = (String)session.getAttribute("user_id");
		String dept_code = (String)session.getAttribute("dept_code");
		
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
		
		boolean result = false;
		ArrayList userList = new ArrayList();
		
		String[] userId = null;
		String[] userName = null;
		String[] gubun = null;

		commapprovalManager manager = commapprovalManager.instance();
		
		if (!"".equals(idList)){
			userId = idList.split(":");
		}
		
		if (!"".equals(nameList)){
			userName = nameList.split(":");
		}
		
		if (!"".equals(gubunList)){
			gubun = gubunList.split(":");
		}
		
		commapprovalBean bean = null;
		
		if (userId != null){
			for (int i = 0; i < userId.length; i++){
				bean = new commapprovalBean();

				bean.setUserId(userId[i]);
				bean.setUserName(userName[i]);
				bean.setGubun(gubun[i]);
				
				userList.add(bean);
				bean = null;
			}
		}
		
		if ("INSERT".equals(cmd)){
			result = manager.designateInsert(sysdocno, userList, sessionId, dept_code, user_id, type);
		}

		String msg = "";
		if(result == true && isMaking != 1) {
			ApprovalManager aprvmgr = ApprovalManager.instance();
			CommTreatManager ctmgr = CommTreatManager.instance();
			if(type.equals("1") == true) { //���պμ�
				ColldocManager cdmgr = ColldocManager.instance();
				String docstate = cdmgr.getDocState(sysdocno).getDocstate();		
				String[] sancInfo = null;	//�ý��۹�����ȣ, ������ڵ�
				if(docstate.equals("02") == true) {	//������
					sancInfo = aprvmgr.isColSancComplete(sysdocno, "1");
					if(sancInfo == null) {	//�����ڰ� ���� ��
						sancInfo = aprvmgr.isColSancComplete(sysdocno, "2");
						if(sancInfo == null) {	//�����ڰ� ���� ��
							cdmgr.appovalColldoc(user_id, dept_code, sysdocno, "04");
							msg = "�����ڿ� �����ڰ� �������� �ʾ�\n����� ����� �������� ó���Ǿ����ϴ�.";
						} else if(sancInfo != null && sancInfo[0].equals("-1") == false) {	//���� �Ϸ�
							aprvmgr.doLastColSancCheck(Integer.parseInt(sancInfo[0], 10), "2", dept_code, sancInfo[1]);
							msg = "������ ��� ���縦 �Ͽ�\n�������� ó���Ǿ����ϴ�.";
						} else {	//���� �̿Ϸ�
							ctmgr.updateDocState(sysdocno, user_id, "03");
							msg = "�����ڰ� �������� �ʾ�\n���δ�� ó���Ǿ����ϴ�.";
						}
					} else if(sancInfo != null && sancInfo[0].equals("-1") == false) {	//���� �Ϸ�
						aprvmgr.doLastColSancCheck(Integer.parseInt(sancInfo[0], 10), "1", dept_code, sancInfo[1]);
						msg = "�����ڰ� ��� ���縦 �Ͽ�\n���δ�� ó���Ǿ����ϴ�.";
					}
				} else if(docstate.equals("03") == true) {	//���δ��
					sancInfo = aprvmgr.isColSancComplete(sysdocno, "2");
					if(sancInfo == null) {	//�����ڰ� ���� ��
						cdmgr.appovalColldoc(user_id, dept_code, sysdocno, "04");
						msg = "�����ڿ� �����ڰ� �������� �ʾ�\n����� ����� �������� ó���Ǿ����ϴ�.";
					} else if(sancInfo != null && sancInfo[0].equals("-1") == false) {	//���� �Ϸ�
						aprvmgr.doLastColSancCheck(Integer.parseInt(sancInfo[0], 10), "2", dept_code, sancInfo[1]);
						msg = "������ ��� ���縦 �Ͽ�\n�������� ó���Ǿ����ϴ�.";
					}
				}
			} else if(type.equals("1") == false) { //����μ�
				String submitState = ctmgr.getTgtdeptState(sysdocno, dept_code);		
				String[] sancInfo = null;	//�ý��۹�����ȣ, ����μ��ڵ�, ������ڵ�
				if(submitState.equals("03") == true) {	//������
					sancInfo = aprvmgr.isTgtSancComplete(sysdocno, dept_code, "1");
					if(sancInfo == null) {	//�����ڰ� ���� ��
						sancInfo = aprvmgr.isTgtSancComplete(sysdocno, dept_code, "2");
						if(sancInfo == null) {	//�����ڰ� ���� ��
							ctmgr.updateSubmitState(sysdocno, dept_code, user_id, "05");
							msg = "�����ڿ� �����ڰ� �������� �ʾ�\n����� ����� ����Ϸ� ó���Ǿ����ϴ�.";
						} else if(sancInfo != null && sancInfo[0].equals("-1") == false) {	//���� �Ϸ�
							aprvmgr.doLastTgtSancCheck(Integer.parseInt(sancInfo[0], 10), "2", sancInfo[1], sancInfo[2]);
							msg = "������ ��� ���縦 �Ͽ�\n����Ϸ� ó���Ǿ����ϴ�.";
						} else {	//���� �̿Ϸ�
							ctmgr.updateSubmitState(sysdocno, dept_code, user_id, "04");
							msg = "�����ڰ� �������� �ʾ�\n���δ�� ó���Ǿ����ϴ�.";
						}
					} else if(sancInfo != null && sancInfo[0].equals("-1") == false) {	//���� �Ϸ�
						aprvmgr.doLastTgtSancCheck(Integer.parseInt(sancInfo[0], 10), "1", sancInfo[1], sancInfo[2]);
						msg = "�����ڰ� ��� ���縦 �Ͽ�\n���δ�� ó���Ǿ����ϴ�.";
					}
				} else if(submitState.equals("04") == true) {	//���δ��
					DeliveryManager dvmgr = DeliveryManager.instance();
					sancInfo = aprvmgr.isTgtSancComplete(sysdocno, dept_code, "2");
					if(sancInfo == null) {	//�����ڰ� ���� ��
						ctmgr.updateSubmitState(sysdocno, dept_code, user_id, "05");
						msg = "�����ڿ� �����ڰ� �������� �ʾ�\n����� ����� ����Ϸ� ó���Ǿ����ϴ�.";
						if(dvmgr.IsLastDeliveryDept(sysdocno, dept_code) == true) {	//������ ����μ��̸�
							ctmgr.updateDocState(sysdocno, user_id, "05");
						}
					} else if(sancInfo != null && sancInfo[0].equals("-1") == false) {	//���� �Ϸ�
						aprvmgr.doLastTgtSancCheck(Integer.parseInt(sancInfo[0], 10), "2", sancInfo[1], sancInfo[2]);
						msg = "������ ��� ���縦 �Ͽ�\n����Ϸ� ó���Ǿ����ϴ�.";
					}
				}
			}			
		}
		
		StringBuffer resultXML = new StringBuffer();
		
		resultXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		resultXML.append("\n<result>");
		resultXML.append("\n<cmd>" + cmd + "</cmd>");
		resultXML.append("\n<retCode>" + result + "</retCode>");
		resultXML.append("\n<msg>" + msg + "</msg>");
		resultXML.append("\n</result>");
		
		response.setContentType("text/xml;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println(resultXML.toString());
		out.flush();
		out.close();
		
		return null;
	}
}