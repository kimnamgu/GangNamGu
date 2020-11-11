/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 작성 action
 * 설명:
 */
package nexti.ejms.sinchung.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.sinchung.form.SinchungForm;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.DateTime;

public class SinchungWriteAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {		 
		
		//세션정보가져오기
		HttpSession session = request.getSession();
		String deptcd = session.getAttribute("dept_code").toString();    //부서코드
		String deptnm = session.getAttribute("dept_name").toString();    //부서명칭
		String usrid = session.getAttribute("user_id").toString();	     //사용자ID
		String usrnm = session.getAttribute("user_name").toString();     //사용자이름
		String sessi = session.getId().split("[!]")[0];                  //세션값
		
		//접수부서 전화번호
		String tel = "";
		UserBean ubean = UserManager.instance().getUserInfo(usrid);
		if ( ubean != null ) {
			tel = ubean.getTel();
		}
		
		//임시 테이블 삭제
		SinchungManager smgr = SinchungManager.instance();
		smgr.deleteTempAll(sessi, getServlet().getServletContext());
		
		//초기데이터 설정
		SinchungForm sForm = (SinchungForm)form;
		sForm.setColdeptcd(deptcd);
		sForm.setColdeptnm(deptnm);
		sForm.setColtel(tel);
		sForm.setChrgusrid(usrid);
		sForm.setChrgusrnm(usrnm);
		sForm.setExamcount(5);	//기본 보기 개수
		
		sForm.setStrdt(DateTime.getCurrentDate());
		sForm.setEnddt(DateTime.getCurrentDate());	
		
		return mapping.findForward("write");
	}
}