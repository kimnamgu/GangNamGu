/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합문서작성 취합문서정보 action
 * 설명:
 */
package nexti.ejms.colldoc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.rootAction;
import nexti.ejms.colldoc.form.ColldocForm;
import nexti.ejms.colldoc.model.ColldocBean;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.dept.model.DeptManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class ColldocInfoViewAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		//세션정보 가져오기
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");		//사용자 ID
		String user_name = (String)session.getAttribute("user_name");	//사용자 성명;
		String dept_code = (String)session.getAttribute("dept_code");	//사용자 부서코드;
		String dept_name = (String)session.getAttribute("dept_name");	//사용자 부서명;
		
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
		
		DeptManager dmgr = DeptManager.instance();
		int chrg_code = dmgr.getChrgunitcd(user_id);
		
		UserManager umgr = UserManager.instance();
		UserBean ubean = umgr.getUserInfo(user_id);
		String tel = ubean.getTel();
		
		//Form에서 넘어온 값 가져오기
		ColldocForm cdform = (ColldocForm)form;	
		
		//문서저장 후 호출시
		int mode = Integer.parseInt((String)request.getParameter("mode"), 10);
		int sysdocno = Integer.parseInt((String)request.getParameter("sysdocno"), 10);
		
		ColldocManager cdmgr = ColldocManager.instance();
		ColldocBean cdbean = cdmgr.getColldoc(sysdocno);
		
		int formcount = cdmgr.getCountFormat(sysdocno);		

		cdform.setMode(mode);	//1:새로작성, 2:새문서로저장, 3:저장, 4:새문서저장모드시탭이동(3으로저장후2로유지)
		cdform.setSysdocno(sysdocno);					//시스템문서번호
		cdform.setFormcount(formcount);					//양식개수
		
		cdform.setDoctitle(cdbean.getDoctitle());		//문서제목
		cdform.setBasicdate(cdbean.getBasicdate());		//자료기준일
		cdform.setColdeptcd(dept_code);					//취합부서코드
		cdform.setColdeptnm(dept_name);					//취합부서명
		cdform.setChrgunitcd(chrg_code);				//취합담당단위코드
		cdform.setChrgusrnm(user_name);					//취합담당자명
		cdform.setD_tel(tel);							//부서전화번호
		cdform.setBasis(cdbean.getBasis());				//관련근거
		cdform.setSummary(cdbean.getSummary());			//취합개요
		cdform.setEnddt_date(cdbean.getEnddt_date());	//마감날짜
		cdform.setEnddt_hour(cdbean.getEnddt_hour());	//마감시
		cdform.setEnddt_min(cdbean.getEnddt_min());		//마감분
		cdform.setEndcomment(cdbean.getEndcomment());	//마감알림말
		cdform.setTgtdeptnm(cdbean.getTgtdeptnm());		//제출대상부서
		cdform.setSancrule(cdbean.getSancrule());		//제출자료전결
		cdform.setSancusrnm1(cdbean.getSancusrnm1());	//검토자
		cdform.setSancusrnm2(cdbean.getSancusrnm2());	//승인자
		cdform.setOpeninput(cdbean.getOpeninput());		//공개입력
		cdform.setFileseq(cdbean.getFileseq());			//첨부파일번호
		cdform.setFilenm(cdbean.getFilenm());			//첨부파일명
		cdform.setOriginfilenm(cdbean.getOriginfilenm());	//원본파일명
		
		if ( request.getParameter("msg") != null ) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg", "저장하였습니다."));
			saveMessages(request,messages);
		}
		
		//담당단위 존재여부
		DeptManager deptMgr = DeptManager.instance();
		int chrgunit_cnt = deptMgr.getExistedChrg(dept_code);
		request.setAttribute("chrgunit_cnt", String.valueOf(chrgunit_cnt));
			
		return mapping.findForward("colldocInfoView");
	}
}