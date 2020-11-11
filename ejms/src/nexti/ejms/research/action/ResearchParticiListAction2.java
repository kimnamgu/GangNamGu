/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 참여하기 목록 action
 * 설명:
 */
package nexti.ejms.research.action;

import java.util.HashMap;
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
import nexti.ejms.util.commfunction;

public class ResearchParticiListAction2 extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {		 
		
		//세션값 셋팅
		HttpSession session = request.getSession();
		String usrid = session.getAttribute("user_id").toString();
		String deptcd = session.getAttribute("dept_code").toString();
		
		//Form에서 넘어온 값 가져오기
		ResearchForm rchForm = (ResearchForm)form;	
		int cPage = rchForm.getPage();             //현재페이지
		String groupyn = rchForm.getGroupyn();
		String schtitle = rchForm.getSch_title();         //제목

		//데이터 범위 결정
		int pageSize = 10;		//한번에 표시한 리스트의 갯수
		int start = commfunction.startIndex(cPage, pageSize);
		int end = commfunction.endIndex(cPage, pageSize);	
		
		//목록 가져오기
		ResearchManager manager = ResearchManager.instance(); 
		List rchList = manager.getRchParticiList(usrid, deptcd, schtitle, groupyn, start, end);
		rchForm.setListrch(rchList);		
		
		//페이지 및 조건 셋팅		
		HashMap schCondition = new HashMap();
		schCondition.put("schdept", deptcd);			
		schCondition.put("schtitle", schtitle);											
		request.setAttribute("sch",schCondition);
		
		//화면으로 가져갈 값 설정
		int totalCount = manager.getRchParticiTotCnt(usrid, deptcd, schtitle, groupyn);
		int totalPage = (int)Math.ceil((double)totalCount/(double)pageSize);		
		request.setAttribute("totalPage",new Integer(totalPage));		
		request.setAttribute("totalCount", new Integer(totalCount));
		request.setAttribute("currpage", new Integer(rchForm.getPage()));
		
		return mapping.findForward("list");
	}
}