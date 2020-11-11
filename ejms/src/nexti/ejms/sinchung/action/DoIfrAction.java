/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 신청하기 목록 action
 * 설명:
 */
package nexti.ejms.sinchung.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootFrameAction;
import nexti.ejms.sinchung.form.SinchungListForm;
import nexti.ejms.sinchung.model.SearchBean;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.util.commfunction;

public class DoIfrAction extends rootFrameAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {
		
		//세션값 셋팅
		HttpSession session = request.getSession();
		String userid 	= session.getAttribute("user_id").toString();
			
		//Form에서 넘어온 값 가져오기
		SinchungListForm sForm = (SinchungListForm)form;
		String deptid = sForm.getDeptcd();       //부서코드		
		String title = sForm.getSearch_title();  //제목	
		int cPage = sForm.getPage();             //현재페이지
	
		//데이터 범위 결정
		int pageSize = 10;		//한번에 표시한 리스트의 갯수
		int start = commfunction.startIndex(cPage, pageSize);
		int end = commfunction.endIndex(cPage, pageSize);
				
		//조회조건 셋팅		
		SearchBean search = new SearchBean();
		search.setDeptid(deptid);		
		search.setTitle(title);		
		search.setStart_idx(start);
		search.setEnd_idx(end);
		search.setPresentid(userid);
		
		//목록 가져오기
		SinchungManager mgr = SinchungManager.instance(); 
		List sList = mgr.doSinchungList(search);
		sForm.setSinchungList(sList);		
		
		//페이지 및 조건 셋팅		
		HashMap searchCondition = new HashMap();
		searchCondition.put("title",title);			
		searchCondition.put("deptcd", deptid);
		request.setAttribute("search",searchCondition);
		
		//화면으로 가져갈 값 설정
		int totalCount = mgr.doSinchungTotCnt(search);
		int totalPage = (int)Math.ceil((double)totalCount/(double)pageSize);
		int tbunho = totalCount - ((cPage - 1) * pageSize) ;
		request.setAttribute("totalPage",new Integer(totalPage));		//총페이지수
		request.setAttribute("totalCount", new Integer(totalCount));    //총 데이터 건수
		request.setAttribute("currpage", new Integer(cPage));           //현재페이지		
		request.setAttribute("tbunho", new Integer(tbunho));            //한페이지당 데이터건수
		
		return mapping.findForward("list");
	}
}