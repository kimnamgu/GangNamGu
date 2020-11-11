/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력완료 목록 action
 * 설명:
 */
package nexti.ejms.inputing.action;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.inputing.form.InputCompleteForm;
import nexti.ejms.inputing.model.InputingManager;
import nexti.ejms.util.commfunction;

public class InputCompleteListAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//세션정보 가져오기
		HttpSession session = request.getSession();
		String user_id = "";
		String dept_code = "";

		user_id = session.getAttribute("user_id").toString();
		dept_code = session.getAttribute("dept_code").toString();
		String isSysMgr = (String)session.getAttribute("isSysMgr");
		
		//Form에서 넘어온 값 가져오기
		InputCompleteForm inputCompleteForm = (InputCompleteForm)form;
		String initentry	= inputCompleteForm.getInitentry();
		String sch_deptcd 	= inputCompleteForm.getSch_old_deptcd();
		String sch_userid 	= inputCompleteForm.getSch_old_userid();
		String sch_deptnm 	= inputCompleteForm.getSch_deptnm();
		String sch_usernm 	= inputCompleteForm.getSch_usernm();
		
		String selyear = "";
		Calendar today = Calendar.getInstance();
		
		if(inputCompleteForm.getInitentry().equals("first")) {
			selyear = Integer.toString(today.get(Calendar.YEAR)) + "년";
		} else {
			selyear = inputCompleteForm.getSelyear();
		}
		
		//데이터 범위 결정
		int pageSize = 10;		//한번에 표시한 리스트의 갯수
		int start = commfunction.startIndex(inputCompleteForm.getPage(), pageSize);
		int end = commfunction.endIndex(inputCompleteForm.getPage(), pageSize);
		
		//입력완료 목록 가져오기
		InputingManager manager = InputingManager.instance();
		List compList = manager.inputCompleteList(user_id, dept_code, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, start, end, inputCompleteForm.getSearchvalue(), selyear.substring(0, 4));
		inputCompleteForm.setCompleteList(compList);
		
		int totalCount = manager.inputCompleteCnt(user_id, dept_code, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, inputCompleteForm.getSearchvalue(), selyear.substring(0, 4));
		int totalPage = (int)Math.ceil((double)totalCount/(double)pageSize);
		request.setAttribute("totalPage", new Integer(totalPage));		
		request.setAttribute("totalCount", new Integer(totalCount));
		request.setAttribute("currpage", new Integer(inputCompleteForm.getPage()));
		request.setAttribute("currentyear", selyear);

		request.setAttribute("initentry", initentry);
		
		session.removeAttribute("originuserid");
		
		return mapping.findForward("list");
	}
}