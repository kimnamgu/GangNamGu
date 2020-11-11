/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력하기 목록 action
 * 설명:
 */
package nexti.ejms.inputing.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.inputing.form.InputingForm;
import nexti.ejms.inputing.model.InputingManager;
import nexti.ejms.util.Utils;
import nexti.ejms.util.commfunction;

public class InputingListAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//세션정보 가져오기
		HttpSession session = request.getSession();
		String userid = "";
		String deptcd = "";
		
		userid = session.getAttribute("user_id").toString();
		deptcd = session.getAttribute("dept_code").toString();
		String isSysMgr = (String)session.getAttribute("isSysMgr");
		
		//Form에서 넘어온 값 가져오기
		InputingForm inputingForm = (InputingForm)form;
		String initentry	= inputingForm.getInitentry();
		String sch_deptcd 	= inputingForm.getSch_old_deptcd();
		String sch_userid 	= inputingForm.getSch_old_userid();
		String sch_deptnm 	= inputingForm.getSch_deptnm();
		String sch_usernm 	= inputingForm.getSch_usernm();
		
		int gubun = inputingForm.getGubun();
		
		//데이터 범위 결정
		int pageSize = 10;		//한번에 표시한 리스트의 갯수
		int start = commfunction.startIndex(inputingForm.getPage(), pageSize);
		int end = commfunction.endIndex(inputingForm.getPage(), pageSize);
		
		//입력하기 목록 가져오기
		InputingManager manager = InputingManager.instance();
		String orgid = manager.getSearchInputing(gubun, sch_deptcd, sch_userid);
		List inputList = manager.inputingList(userid, deptcd, gubun, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, start, end);
		inputingForm.setInputList(inputList);
		
		int totalCount = manager.inputingCnt(userid, deptcd, gubun, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm);
		int totalPage = (int)Math.ceil((double)totalCount/(double)pageSize);		
		request.setAttribute("totalPage", new Integer(totalPage));		
		request.setAttribute("totalCount", new Integer(totalCount));
		request.setAttribute("currpage", new Integer(inputingForm.getPage()));

		request.setAttribute("initentry", initentry);
		request.setAttribute("orgid", Utils.nullToEmptyString(orgid));
		
		session.removeAttribute("originuserid");
		
		return mapping.findForward("list");
	}
}