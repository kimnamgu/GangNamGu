/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 자료공유 목록
 * 설명:
 */
package nexti.ejms.exhibit.action;

import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.commdocinfo.model.CommCollDocInfoManager;
import nexti.ejms.commdocinfo.model.CommCollDocSearchBean;
import nexti.ejms.common.rootAction;
import nexti.ejms.exhibit.form.ExhibitListForm;
import nexti.ejms.util.commfunction;

public class ExhibitListAction extends rootAction {
	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception 
	 */
	public ActionForward doService(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");		//사용자아이디;
		String dept_code = (String)session.getAttribute("dept_code");	//사용자 부서코드;
		
		//Form에서 넘어온 값 가져오기
		ExhibitListForm listForm = (ExhibitListForm) form;
		
		//데이터 범위 결정
		int pageSize = 10;		//한번에 표시한 리스트의 갯수
		int start = commfunction.startIndex(listForm.getPage(), pageSize);
		int end = commfunction.endIndex(listForm.getPage(), pageSize);
		
		if(listForm.getSearchdept().equals("") == true) {
			listForm.setSearchdept(dept_code);
		}
		
		//최근취합목록 가져오기
		CommCollDocSearchBean searchBean = new CommCollDocSearchBean();
		searchBean.setUserid(user_id);
		searchBean.setSearchdept(listForm.getSearchdept());
		searchBean.setSearchdoctitle(listForm.getSearchdoctitle());
		searchBean.setSearchformtitle(listForm.getSearchformtitle());
		searchBean.setSearchkey(listForm.getSearchkey());
		searchBean.setSearchcrtdtfr(listForm.getSearchcrtdtfr());
		searchBean.setSearchcrtdtto(listForm.getSearchcrtdtto());
		searchBean.setSearchbasicdatefr(listForm.getSearchbasicdatefr());
		searchBean.setSearchbasicdateto(listForm.getSearchbasicdateto());
		searchBean.setSearchchrgusrnm(listForm.getSearchchrgusrnm());
		searchBean.setSearchinputusrnm(listForm.getSearchinputusrnm());
		
		if ( "".equals(searchBean.getSearchcrtdtfr()) ) {
			int year = Calendar.getInstance().get(Calendar.YEAR);
			searchBean.setSearchcrtdtfr(year + "-01-01");
			searchBean.setSearchcrtdtto(year + "-12-31");
		}
		
		CommCollDocInfoManager manager = CommCollDocInfoManager.instance();
		listForm.setDoclist(manager.getExhibitList(searchBean, start, end));

		//페이지 및 조건 셋팅
		HashMap searchCondition = new HashMap();
		searchCondition.put("searchdept", searchBean.getSearchdept());			
		searchCondition.put("searchdoctitle", searchBean.getSearchdoctitle());			
		searchCondition.put("searchformtitle", searchBean.getSearchformtitle());			
		searchCondition.put("searchkey", searchBean.getSearchkey());			
		searchCondition.put("searchcrtdtfr", searchBean.getSearchcrtdtfr());			
		searchCondition.put("searchcrtdtto", searchBean.getSearchcrtdtto());			
		searchCondition.put("searchbasicdatefr", searchBean.getSearchbasicdatefr());			
		searchCondition.put("searchbasicdateto", searchBean.getSearchbasicdateto());			
		searchCondition.put("searchchrgusrnm", searchBean.getSearchchrgusrnm());			
		searchCondition.put("searchinputusrnm", searchBean.getSearchinputusrnm());			
		request.setAttribute("search",searchCondition);
		
		//화면으로 가져갈 값 설정
		int totalCount = manager.getExhibitTotCnt(searchBean);
		int totalPage = (int)Math.ceil((double)totalCount/(double)pageSize);		
		request.setAttribute("totalPage",new Integer(totalPage));		
		request.setAttribute("totalCount", new Integer(totalCount));
		request.setAttribute("currpage", new Integer(listForm.getPage()));

		return mapping.findForward("list");
	}
}