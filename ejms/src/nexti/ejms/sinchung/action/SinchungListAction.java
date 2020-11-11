/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 목록 action
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

import nexti.ejms.common.rootAction;
import nexti.ejms.sinchung.form.SinchungListForm;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.sinchung.model.SearchBean;
import nexti.ejms.util.Utils;
import nexti.ejms.util.commfunction;

public class SinchungListAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {
		System.out.println("ActionForward");
		//세션값 셋팅
		HttpSession session = request.getSession();
		String userid 	= session.getAttribute("user_id").toString();
		String deptid 	= session.getAttribute("dept_code").toString();
		String isSysMgr = (String)session.getAttribute("isSysMgr");
		
		//Form에서 넘어온 값 가져오기
		SinchungListForm sForm = (SinchungListForm)form;	
		int cPage 			= sForm.getPage();             	//현재페이지
		String title 		= sForm.getSearch_title();  	//제목
		String procGbn 		= sForm.getProcGbn();    	 	//미처리건 (0:미처리 , 1:전체)
		String initentry	= sForm.getInitentry();
		String sch_deptcd 	= sForm.getSch_old_deptcd();
		String sch_userid 	= sForm.getSch_old_userid();
		String sch_deptnm 	= sForm.getSch_deptnm();
		String sch_usernm 	= sForm.getSch_usernm();
		
		//동작구청 : 기본으로 관리자가 모든 신청서보이게함
		if ( "동작3190000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
			if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
				if ( "".equals(Utils.nullToEmptyString(sch_usernm)) ) {
					sch_usernm = "%";
					sForm.setSch_usernm(sch_usernm);
					initentry = "second";
				}
			}
		}
		
		//데이터 범위 결정
		int pageSize = 10;		//한번에 표시한 리스트의 갯수
		int start = commfunction.startIndex(cPage, pageSize);
		int end = commfunction.endIndex(cPage, pageSize);
				
		//조회조건 셋팅		
		SearchBean search = new SearchBean();
		search.setDeptid(deptid);
		search.setUserid(userid);
		search.setTitle(title);
		search.setProcFL(procGbn);
		search.setStart_idx(start);
		search.setEnd_idx(end);	
		
		//목록 가져오기
		SinchungManager mgr = SinchungManager.instance();
		String orgid = mgr.getSearch(search, sch_deptcd, sch_userid); 
		List sList = mgr.reqFormList(search, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm);
		sForm.setSinchungList(sList);		
		
		//페이지 및 조건 셋팅		
		HashMap searchCondition = new HashMap();
		searchCondition.put("title",title);	
		searchCondition.put("procGbn", procGbn);			
		request.setAttribute("search",searchCondition);
		
		//화면으로 가져갈 값 설정
		int totalCount = mgr.reqFormTotCnt(search, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm);
		int totalPage = (int)Math.ceil((double)totalCount/(double)pageSize);
		int tbunho = totalCount - ((cPage - 1) * pageSize) ;
		request.setAttribute("totalPage",new Integer(totalPage));		//총페이지수
		request.setAttribute("totalCount", new Integer(totalCount));    //총 데이터 건수
		request.setAttribute("currpage", new Integer(cPage));           //현재페이지		
		request.setAttribute("tbunho", new Integer(tbunho));            //한페이지당 데이터건수
		
		request.setAttribute("initentry", initentry);
		request.setAttribute("orgid", Utils.nullToEmptyString(orgid));
		
		session.removeAttribute("originuserid");
		
		return mapping.findForward("list");
	}
}