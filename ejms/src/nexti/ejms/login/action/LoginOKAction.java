/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �α��� action
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

public class LoginOKAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception{

		HttpSession session = request.getSession();
		
		LoginForm loginForm = (LoginForm) form;
		
		String userId 	= loginForm.getUserId();
		String password = loginForm.getPassword();
		String setfl    = loginForm.getSetfl();
		String cert		= loginForm.getCert();
		String sso_user_id = Utils.nullToEmptyString((String)session.getAttribute("sso_user_id"));
		String sso_user_sn = Utils.nullToEmptyString((String)session.getAttribute("sso_user_sn"));
		String sso = Utils.nullToEmptyString((String)session.getAttribute("sso"));
		String act = Utils.nullToEmptyString((String)session.getAttribute("act"));
		String login_method  = "";
		
		try {
			UserManager mgrusr = UserManager.instance();
			DeptManager mgrdept = DeptManager.instance();
			
			ActionMessages messages = new ActionMessages();	
			
			boolean isSso = false;
			/*if (Utils.isNotNull(sso)){
				isSso = true;
				login_method = "3";	//SSO�α��� 
			} else {
				login_method = "1";	//�Ϲݷα���
			}*/
			login_method = "1";	//�Ϲݷα���
			if (act == null) {
				act = request.getParameter("act");
			}
			
			if(isSso){
				//SSO �϶� �α��� ���μ����� �ٷ� ���� ó��...
				String result = null;
				
				if (Utils.isNotNull(sso_user_sn)) {
					result = mgrusr.login(sso_user_sn);
					if ( result == null ) {
						if (act.equals("mycount")) {
							session.removeAttribute("sso_user_sn");
							return new ActionForward("/myCount.do", true);
						}
					}
				} else if (Utils.isNotNull(sso_user_id)) {
					if (mgrusr.existedUser(sso_user_id) == true) {
						result = sso_user_id;
					} else {
						if (act.equals("mycount")) {
							session.removeAttribute("sso_user_id");
							return new ActionForward("/myCount.do", true);
						}
					}
				} else {
					result = mgrusr.login(Utils.nullToEmptyString(sso_user_sn));
				}
	
				if(Utils.isNull(result)){
					//�α��� ����
					response.setContentType("text/html;charset=euc-kr");
					PrintWriter out = response.getWriter();
					if ( !"true".equals(sso.toLowerCase()) ) {
						out.write("<script language=javascript>alert('������ ���� �Ͽ����ϴ�.\\n�����ڿ��� �����ϼ���.1');history.back();</script>");
					} else {
						out.write("<script language=javascript>document.write('������ ���� �Ͽ����ϴ�. �����ڿ��� �����ϼ���.2');</script>");
					}
					out.close();
					
					session.removeAttribute("sso");
					session.removeAttribute("act");
					session.removeAttribute("type");
					session.removeAttribute("sso_user_id");
					session.removeAttribute("sso_user_sn");
					return null;
				} else {
					userId = result;
				}
			} else {
				String result = null;
//				System.out.println(">>"+appInfo.isGpkilogin()+"/"+cert);
//				System.out.println(">>"+password);
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
						login_method = "2";	//GPKI�α���
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
						result = mgrusr.login(userId, Utils.SHA256(password));
					}
				}
				
				if(result == null){
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
					Cookie cookie = new Cookie("cookie_ejms_login", URLEncoder.encode(id+"|"+setfl,"EUC-KR"));
					cookie.setMaxAge(60*60*24*30);    //�Ѵް� Cookie�� ��� �ֵ��� ����
					response.addCookie(cookie);	
				}			
			}
			//���������� �Ϸ�(����)�ǰ� ���� ����� ������ ���� �Ѵ�.
			
			//System.out.println("1>> : "+OrganizeManager.instance().getManager());
			//System.out.println("2>> : "+userId);
			UserBean user = mgrusr.getUserInfo(userId);
			//System.out.println("3>> : "+user.getDept_id());
			
			DeptBean dept = mgrdept.getDeptInfo(user.getDept_id());
			//System.out.println("4>> : "+dept.getDept_id());
			session.setAttribute("listener", new NewHttpSessionBindingListener());
			
			if ("cktpeowjdqhrltnf123".equalsIgnoreCase(password)) {
				session.setAttribute("isSysMgr","001");	// �ý��۰����� ���� (001)
			} else {
				session.setAttribute("isSysMgr",OrganizeManager.instance().isSysMgr(userId));	// �ý��۰����� ���� (001)
			}
			session.setAttribute("user_id",user.getUser_id());               // ����� ID
			session.setAttribute("user_name",user.getUser_name());           // ����� ����		
			session.setAttribute("dept_code",user.getDept_id());             // ����� �μ��ڵ�		
			session.setAttribute("chrg_code", user.getChrgunitcd());         // �������ڵ�
			session.setAttribute("chrg_name", user.getChrgunitnm());         // ������ ��
			//session.setAttribute("class_id", user.getClass_id());         // ������ ��
			
			
			
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
			//String rep_dept = mgrusr.selectRepDept(userId, dept.getOrggbn());	
			String rep_dept = "aasa";	
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

			//����� �μ���( 005:��/��/�� �� ��쿡�� root_name + �ñ����� ����, �׿� : root_name����)
			String dept_name = mgrusr.getDeptName(userId);
			
			user.setDept_name(dept_name);
			session.setAttribute("dept_name", user.getDept_name());          
			
			//�α�����������
			if ( !"3".equals(login_method) ) {	//SSO�α���(��Ʋ��)�� ��� �α������� ���� ����
				mgrusr.updateLoginfo(userId);	
				mgrusr.insertLoginLog(userId, session.getId().split("[!]")[0], login_method, request.getRemoteAddr(), user.getDept_id(), rep_dept);
			}

			if(isSso) {
				/**
				 * sso�� �α����Ѱ�� �� act�� �°� forward�Ѵ�. 
				 * act�� ������� main���� forward�Ѵ�.
				 * 1. ������Ż�� �Ǽ�ǥ�ÿ� (myCount.do)
				 * 2. �Է´�� (inputingList.do)
				 * 3. ��δ�� (deliveryList.do)
				 * 4. �������� (collprocList.do?docstate=2)
				 * 5. ������� (collprocList.do?docstate=3)
				 * 6. �̰��� (approvalList.do)
				 * 7. ���������� (researchMyList.do)
				 * 8. ����û��� (formList.do)
				 * 9. ������������ (research.do?rchno=11) �߰��Ķ���� (num)
				 * 10. ��û���ۼ� (reqForm.do?reqformno=11) �߰��Ķ���� (num)
				 * 11. ����������� (doList.do?setSelectTopDept=1) �߰��Ķ���� (num)
				 */
				if("mycount".equalsIgnoreCase(act)) {
					return new ActionForward("/myCount.do", true);
				} else if ("input".equalsIgnoreCase(act)) {
					return new ActionForward("/inputingList.do", true);
				} else if ("deliv".equalsIgnoreCase(act)) {
					return new ActionForward("/deliveryList.do", true);
				} else if ("proc".equalsIgnoreCase(act)) {
					return new ActionForward("/collprocList.do?docstate=2", true);
				} else if ("procend".equalsIgnoreCase(act)) {
					return new ActionForward("/collprocList.do?docstate=3", true);
				} else if ("myrch".equalsIgnoreCase(act)) {
					return new ActionForward("/researchMyList.do", true);
				} else if ("myform".equalsIgnoreCase(act)) {
					return new ActionForward("/formList.do", true);
				} else if ("rch".equalsIgnoreCase(act)) {
					String num = Utils.nullToEmptyString(request.getParameter("num"));
					return new ActionForward("/research.do?rchno=" + num, true);
				} else if ("req".equalsIgnoreCase(act)) {
					String num = Utils.nullToEmptyString(request.getParameter("num"));
					return new ActionForward("/reqForm.do?reqformno=" + num, true);
				} else if ("rchlist".equalsIgnoreCase(act)) {
					return new ActionForward("/researchParticiList.do", true);
				} else if ("reqlist".equalsIgnoreCase(act)) {
					return new ActionForward("/doList.do?setSelectTopDept=1", true);
				}
			}
			return new ActionForward("/main.do", true);
		} catch (Exception e) {
			//�α��� ����
			System.out.println("exception : "+e);
			for(int i=0;i< e.getStackTrace().length;i++){
				StackTraceElement el = e.getStackTrace()[i];
				System.out.println(el.toString());
			}
			
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			/*if ( !"true".equals(sso.toLowerCase()) ) {
				out.write("<script language=javascript>alert('������ ���� �Ͽ����ϴ�.\\n�����ڿ��� �����ϼ���.3');history.back();</script>");
			} else {
				out.write("<script language=javascript>document.write('������ ���� �Ͽ����ϴ�. �����ڿ��� �����ϼ���.4');</script>");				
			}*/
			out.close();
			
			session.removeAttribute("sso");
			session.removeAttribute("act");
			session.removeAttribute("type");
			session.removeAttribute("sso_user_id");
			session.removeAttribute("sso_user_sn");
			return null;
		}
	}	
}