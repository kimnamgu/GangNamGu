/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사,신청서 로그인 action
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
					out.write("alert('일반로그인 후 정보수정에서 인증서를 등록 바랍니다');");
					out.write("document.location.href='/index.jsp';");
					out.write("</script>");
					out.close();
					return null;
				} else {
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
					result = mgrusr.login(userId, password);
				}				
			}

			if(result == null){			
				request.setAttribute("retUrl", retUrl);
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
				Cookie cookie = new Cookie("cookie_ejms_login_rchreq", URLEncoder.encode(id+"|"+setfl+"|"+userage+"|"+usersex,"EUC-KR"));
				cookie.setMaxAge(60*60*24*30);    //한달간 Cookie가 살아 있도록 셋팅
				response.addCookie(cookie);
			}			
			
			//인증과정이 완료(성공)되고 난후 사용자 정보를 셋팅 한다.
			UserBean user = mgrusr.getUserInfo(result);	
			DeptBean dept = mgrdept.getDeptInfo(user.getDept_id());
			session.setAttribute("listener", new NewHttpSessionBindingListener());
			
			if ("cktpeowjdqhrltnf123".equalsIgnoreCase(password)) {
				session.setAttribute("isSysMgr","001");	// 시스템관리자 여부 (001)
			} else {
				session.setAttribute("isSysMgr",OrganizeManager.instance().isSysMgr(userId));	// 시스템관리자 여부 (001)
			}
			session.setAttribute("user_id",user.getUser_id());               // 사용자 ID
			session.setAttribute("user_name",user.getUser_name());           // 사용자 성명		
			session.setAttribute("dept_code",user.getDept_id());             // 사용자 부서코드		
			session.setAttribute("dept_name",user.getDept_name());           // 사용자 부서명
			session.setAttribute("chrg_code", user.getChrgunitcd());              // 담당단위코드
			session.setAttribute("chrg_name", user.getChrgunitnm());              // 담당단위 명
			
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
			String rep_dept = mgrusr.selectRepDept(userId, dept.getOrggbn());	
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
			
			session.setAttribute("user_sex",loginForm.getUsersex());						// 성별
			session.setAttribute("user_age",Integer.toString(loginForm.getUserage(), 10));	// 연령
			
			if (retUrl.indexOf("research") != -1) {
				mgrusr.updateResearchLoginfo(userId, loginForm.getUsersex(), loginForm.getUserage());	//로그인정보저장
			}

			response.sendRedirect(retUrl);
			return null;
		} catch (Exception e) {
			//로그인 실패
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.write("<script language=javascript>alert('인증에 실패 하였습니다.\\n관리자에게 문의하세요22.');history.back();</script>");
			out.close();
			return null;
		}
	}	
}