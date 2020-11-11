/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합완료 목록 action
 * 설명:
 */
package nexti.ejms.colldoc.action;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.ccd.model.CcdManager;
import nexti.ejms.common.rootAction;
import nexti.ejms.colldoc.form.ColldocForm;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.util.commfunction;

public class CollcompListAction extends rootAction {
	
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
		ColldocForm colldocForm = (ColldocForm)form;	
		String initentry	= colldocForm.getInitentry();
		String sch_deptcd 	= colldocForm.getSch_old_deptcd();
		String sch_userid 	= colldocForm.getSch_old_userid();
		String sch_deptnm 	= colldocForm.getSch_deptnm();
		String sch_usernm 	= colldocForm.getSch_usernm();
		
		String selyear = "";
		String searchnm = "";
		Calendar today = Calendar.getInstance();
		
		if(colldocForm.getInitentry().equals("first")) {
			selyear = Integer.toString(today.get(Calendar.YEAR)) + "년";
		} else {
			selyear = colldocForm.getSelyear();
		}
		
		if(request.getParameter("searchnm")!= null){
			searchnm = request.getParameter("searchnm");
		}else{
			searchnm = colldocForm.getSearchvalue();
		}

		//데이터 범위 결정
		int pageSize = 10;		//한번에 표시한 리스트의 갯수
		int start = commfunction.startIndex(colldocForm.getPage(), pageSize);
		int end = commfunction.endIndex(colldocForm.getPage(), pageSize);
				
		//취합완료 목록 가져오기
		ColldocManager manager = ColldocManager.instance(); 
		List compList = manager.getCollcompList(usrid, deptcd, searchnm , selyear.substring(0, 4), initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, start, end);	
		colldocForm.setListcolldoc(compList);	
		
		int totalCount = manager.getCollcompTotCnt(usrid, deptcd, searchnm, selyear.substring(0, 4), initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm);
		int totalPage = (int)Math.ceil((double)totalCount/(double)pageSize);
		request.setAttribute("totalPage", new Integer(totalPage));
		request.setAttribute("totalCount", new Integer(totalCount));
		request.setAttribute("currpage", new Integer(colldocForm.getPage()));
		request.setAttribute("currentyear", selyear);
		
		request.setAttribute("initentry", initentry);
		request.setAttribute("docDeleteYN", CcdManager.instance().getCcd_SubName("016", "01"));
		
		session.removeAttribute("originuserid");
		
		return mapping.findForward("collcomplist");
	}
}