/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사그룹 작성 action
 * 설명:
 */
package nexti.ejms.research.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.research.form.ResearchForm;
import nexti.ejms.research.model.ResearchManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.DateTime;

public class ResearchGrpWriteAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {		 
		
		//세션정보가져오기
		HttpSession session = request.getSession();
		String sessionId = session.getId().split("[!]")[0];
		String deptcd = session.getAttribute("dept_code").toString();    //부서코드
		String deptnm = session.getAttribute("dept_name").toString();    //부서명칭
		String usrid = session.getAttribute("user_id").toString();	     //사용자ID
		String usrnm = session.getAttribute("user_name").toString();     //사용자이름

		//접수부서 전화번호
		String tel = "";
		UserBean ubean = UserManager.instance().getUserInfo(usrid);
		if ( ubean != null ) {
			tel = ubean.getTel();
		}

		int rchgrpno = 0;
		
		ResearchManager manager = ResearchManager.instance();
		manager.delResearchGrpTempData(sessionId); 
		
		//초기데이터 설정
		ResearchForm rchForm = (ResearchForm)form;
		rchForm.setRchgrpno(rchgrpno);
		rchForm.setSessionId(sessionId);
		rchForm.setColdeptcd(deptcd);
		rchForm.setColdeptnm(deptnm);
		rchForm.setTelno(tel);
		rchForm.setColdepttel(tel);		
		rchForm.setChrgusrid(usrid);
		rchForm.setChrgusrnm(usrnm);
		rchForm.setExamcount(5);	//기본 보기 개수
		
		rchForm.setStrdt(DateTime.getCurrentDate());
		rchForm.setEnddt(DateTime.getCurrentDate());
		
		return mapping.findForward("write");
	}
}