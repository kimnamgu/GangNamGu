/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사,신청서 세션 및 권한체크 action
 * 설명:
 */
package nexti.ejms.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nexti.ejms.dept.model.DeptBean;
import nexti.ejms.dept.model.DeptManager;
import nexti.ejms.organ.model.OrganizeManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.DateTime;
import nexti.ejms.util.Utils;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class rootDirectAction extends Action {
	//메인화면의 부모 클래스로 쓸것
	private static Logger logger = Logger.getLogger(rootDirectAction.class);
	
	public ActionForward execute(
		ActionMapping mapping, 
		ActionForm form,
		HttpServletRequest request, 
		HttpServletResponse response)
	 	throws Exception {	

		HttpSession session = request.getSession();	
		ActionForward forward = null;
		
		if( ((session == null) || (session.getAttribute("user_id") == null) )
				&& request.getParameter("userid") == null){
			logger.debug("Session이 종료되었습니다.");
			return mapping.findForward("loginRchReqYN");
		} else {
			//인증과정이 완료(성공)되고 난후 사용자 정보를 셋팅 한다.
			if ( session.getAttribute("user_id") == null && request.getParameter("userid") != null ) {
				String ip = Utils.nullToEmptyString(request.getRemoteAddr());
				String retUrl = Utils.nullToEmptyString(request.getRequestURI());
				String userid = Utils.nullToEmptyString(request.getParameter("userid"));
				doLogin(session, ip, retUrl, userid);
			}
			
			String[] roles = mapping.getRoleNames();
			String user_id = (String)session.getAttribute("user_id");			
			
			for(int i=0;i<roles.length;i++){
				if(roles[i].equals("admin")){
					if(!"001".equals(session.getAttribute("isSysMgr"))){
						//시스템 관리자가 아니면 forwarding
						logger.info("시스템 관리자 Page에 권한이 없는 사용자가 접근시도 , 사용자ID["+user_id+"]");
						return mapping.findForward("global_main");
					}
				} 
			}
			
			try{
				forward=doService(mapping, form, request, response);
			}catch(Exception e){
				request.setAttribute("exception",e); 
				request.getRequestDispatcher("/common/popuperror.jsp").forward(request, response); 
			}
			return forward;
		}
	}
	
	private void doLogin(HttpSession session, String ip, String retUrl, String userid) throws Exception{
		UserManager mgrusr = UserManager.instance();
		DeptManager mgrdept = DeptManager.instance();

		UserBean user = mgrusr.getUserInfo(userid);
		
		if ( user != null ) {
			DeptBean dept = mgrdept.getDeptInfo(user.getDept_id());
			session.setAttribute("listener", new NewHttpSessionBindingListener());
			
			session.setAttribute("isSysMgr",OrganizeManager.instance().isSysMgr(userid));     	 // 시스템관리자 여부 (001)
			session.setAttribute("user_id",user.getUser_id());               // 사용자 ID
			session.setAttribute("user_name",user.getUser_name());           // 사용자 성명		
			session.setAttribute("dept_code",user.getDept_id());             // 사용자 부서코드		
			session.setAttribute("dept_name",user.getDept_name());           // 사용자 부서명
			
			if(Utils.isNull(dept.getDept_tel())){
				session.setAttribute("d_tel", "없음");
			} else {
				session.setAttribute("d_tel", dept.getDept_tel());           // 부서대표전화
			}																
			
			session.setAttribute("chrg_code", user.getChrgunitcd());              // 담당단위코드
			session.setAttribute("chrg_name", user.getChrgunitnm());              // 담당단위 명
			session.setAttribute("c_tel",user.getTel());                     // 사용자 일반전화번호
			session.setAttribute("h_tel",user.getMobile());                  // 사용자 휴대전화번호
			session.setAttribute("logindate",DateTime.getCurrentTime());   	 // 사용자 로그인시각 (yyyy/MM/dd HH:mm:ss)
			session.setAttribute("loginip",ip);         					 // 사용자 로그인IP (127.0.0.1)
			
			if (retUrl.indexOf("research") != -1) {
				mgrusr.updateResearchLoginfo(userid, "M", 20);	//로그인정보저장(로그인화면 거치기 않으므로 기본값 세팅)
			}
		}
	}
	
	abstract public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response)
			throws Exception; 
}
