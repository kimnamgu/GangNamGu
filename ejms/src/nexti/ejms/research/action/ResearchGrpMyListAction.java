/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 내설문조사그룹 목록 action
 * 설명:
 */
package nexti.ejms.research.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.research.form.ResearchForm;
import nexti.ejms.research.model.ResearchManager;
import nexti.ejms.util.Utils;
import nexti.ejms.util.commfunction;

public class ResearchGrpMyListAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
	
		//세션정보 가져오기
		HttpSession session = request.getSession();
		String usrid 	= (String)session.getAttribute("user_id");
		String deptcd 	= (String)session.getAttribute("dept_code");
		String isSysMgr = (String)session.getAttribute("isSysMgr");
		
		//Form에서 넘어온 값 가져오기
		ResearchForm researchForm = (ResearchForm)form;			
	
		//데이터 범위 결정
		int pageSize = 10;		//한번에 표시한 리스트의 갯수
		int start = commfunction.startIndex(researchForm.getPage(), pageSize);
		int end = commfunction.endIndex(researchForm.getPage(), pageSize);
		
		//검색조건 : 부서명, 담당자, 제목
		String sch_title  	= researchForm.getSch_title();
		String initentry	= researchForm.getInitentry();
		String sch_deptcd 	= researchForm.getSch_old_deptcd();
		String sch_userid 	= researchForm.getSch_old_userid();
		String sch_deptnm 	= researchForm.getSch_deptnm();
		String sch_usernm 	= researchForm.getSch_usernm();
				
		//내설문자료  목록 가져오기
		ResearchManager manager = ResearchManager.instance(); 
		String orgid = manager.getGrpSearch("", sch_title, sch_deptcd, sch_userid);
		List researchList = manager.getResearchGrpMyList(usrid, deptcd, initentry, isSysMgr, sch_title, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, start, end);	
		researchForm.setListrch(researchList);
		researchForm.setSch_title(sch_title);
		
		int totalCount = manager.getResearchGrpTotCnt(usrid, deptcd, initentry, isSysMgr, sch_title, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, "");
		int totalPage = (int)Math.ceil((double)totalCount/(double)pageSize);

		request.setAttribute("totalPage", new Integer(totalPage));
		request.setAttribute("totalCount", new Integer(totalCount));
		request.setAttribute("currpage", new Integer(researchForm.getPage()));	
		
		request.setAttribute("orgid", Utils.nullToEmptyString(orgid));
		request.setAttribute("initentry", initentry);
		
		session.removeAttribute("originuserid");
		
		return mapping.findForward("list");
	}
}