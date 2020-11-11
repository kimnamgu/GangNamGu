/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 진행중인취합문서 취합문서정보 수정저장 action
 * 설명:
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
		
		//세션정보 가져오기
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");		//사용자 아이디
		String user_name = (String)session.getAttribute("user_name");;	//사용자 이름
		String dept_code = (String)session.getAttribute("dept_code");	//사용자 부서코드
		String dept_name = (String)session.getAttribute("dept_name");	//사용자 부서명
		
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
		
		//Form 가져오기
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
		
		//화면출력 문서정보
		cdbean.setDoctitle(cdform.getDoctitle());		//문서제목
		cdbean.setBasicdate(cdform.getBasicdate());		//자료기준일
		cdbean.setColdeptcd(dept_code);					//취합부서코드
		cdbean.setColdeptnm(dept_name);					//취합부서명
		cdbean.setChrgunitcd(cdform.getChrgunitcd());	//취합담당단위코드
		cdbean.setChrgunitnm(chrg_name);				//취합담당단위명
		cdbean.setChrgusrnm(user_name);					//취합담당자명
		cdbean.setBasis(cdform.getBasis());				//관련근거
		cdbean.setSummary(cdform.getSummary());			//취합개요		
		cdbean.setEnddt(enddate);						//마감일시
		cdbean.setEndcomment(cdform.getEndcomment());	//마감알림말
		cdbean.setTargetdeptnm(cdform.getTgtdeptnm());	//제출부서명,그룹명
		cdbean.setSancrulecd(cdform.getSancrulecd());	//제출자료전결
		
		if ( !"Y".equals(Utils.nullToEmptyString(cdform.getOpeninput())) ) cdform.setOpeninput("N");
		cdbean.setOpeninput(cdform.getOpeninput());		//공개입력
		
		ServletContext context = getServlet().getServletContext();

		//저장할 디렉토리 지정
		Calendar cal = Calendar.getInstance();
		String saveDir = appInfo.getColldocSampleDir() + cal.get(Calendar.YEAR) + "/";
		
		cdbean.setAttachFile(cdform.getAttachFile());
		cdbean.setAttachFileYN(cdform.getAttachFileYN());

		//사용자 취합부서결재, 제출부서, 제출그룹 임시 테이블 데이터 정상문서 데이터로 적용 
		int result = manager.saveCommCollDocInfo(cdbean, sysdocno, user_id, context, saveDir);
		
		String msg = "";
		if(result > 0){
			msg = "저장되었습니다.";
		}else{
			msg = "저장에 실패했습니다.";
		}
		
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
		saveMessages(request,messages);
		
		//저장한 문서 열기
		request.setAttribute("sysdocno", new Integer(sysdocno));
		
		return mapping.findForward("collprocinfosave");
	}	
}