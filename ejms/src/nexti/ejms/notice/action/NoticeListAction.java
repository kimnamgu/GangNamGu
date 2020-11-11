/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공지사항 목록 action
 * 설명:
 */
package nexti.ejms.notice.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.notice.form.NoticeForm;
import nexti.ejms.notice.model.NoticeManager;
import nexti.ejms.notice.model.SearchBean;
import nexti.ejms.util.commfunction;

public class NoticeListAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {		
		
		//Form에서 넘어온 값 가져오기
		NoticeForm noticeForm = (NoticeForm)form;	
		int cPage = noticeForm.getPage();
		String gubun = noticeForm.getGubun();
		String searchval = noticeForm.getSearchval();
	
		//데이터 범위 결정
		int pageSize = 10;		//한번에 표시한 리스트의 갯수
		int start = commfunction.startIndex(cPage, pageSize);
		int end = commfunction.endIndex(cPage, pageSize);
				
		//조회조건 셋팅		
		SearchBean search = new SearchBean();
		search.setGubun(gubun);
		search.setSearchval(searchval);
		search.setStartidx(start);
		search.setEndidx(end);	
		
		//공지사항 목록 가져오기
		NoticeManager manager = NoticeManager.instance(); 
		List noticeList = manager.noticeList(search);
		noticeForm.setNoticeList(noticeList);	
		
		//페이지 및 조건 셋팅		
		HashMap searchCondition = new HashMap();
		searchCondition.put("gubun",gubun);	
		searchCondition.put("searchval", searchval);			
		request.setAttribute("search",searchCondition);
		
		//화면으로 가져갈 값 설정
		int totalCount = manager.noticeTotCnt(search);
		int totalPage = (int)Math.ceil((double)totalCount/(double)pageSize);
		int tbunho = totalCount - ((cPage - 1) * pageSize) ;
		request.setAttribute("totalPage",new Integer(totalPage));		//총페이지수
		request.setAttribute("totalCount", new Integer(totalCount));    //총 데이터 건수
		request.setAttribute("currpage", new Integer(cPage));           //현재페이지		
		request.setAttribute("tbunho", new Integer(tbunho));            //한페이지당 데이터건수
		
		return mapping.findForward("list");
	}
}