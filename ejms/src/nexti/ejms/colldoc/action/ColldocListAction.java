/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합문서작성 목록 action
 * 설명:
 */
package nexti.ejms.colldoc.action;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.colldoc.form.ColldocForm;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.util.Utils;
import nexti.ejms.util.commfunction;

public class ColldocListAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		//세션정보 가져오기
		HttpSession session = request.getSession();		
		String user_id = (String)session.getAttribute("user_id");
		String isSysMgr = (String)session.getAttribute("isSysMgr");
		
		//Form에서 넘어온 값 가져오기
		ColldocForm cdform = (ColldocForm)form;			
		String initentry	= cdform.getInitentry();
		String sch_deptcd 	= cdform.getSch_old_deptcd();
		String sch_userid 	= cdform.getSch_old_userid();
		String sch_deptnm 	= cdform.getSch_deptnm();
		String sch_usernm 	= cdform.getSch_usernm();
	
		//데이터 범위 결정
		int pagesize = 10;		//한번에 표시한 리스트의 갯수
		int start = commfunction.startIndex(cdform.getPage(), pagesize);
		int end = commfunction.endIndex(cdform.getPage(), pagesize);
		
		//최근취합목록 가져오기
		ColldocManager cdmgr = ColldocManager.instance();
		String orgid = cdmgr.getSearchDoc(cdform.getSearchvalue(), sch_deptcd, sch_userid);
		List listcolldoc = cdmgr.getListColldoc(user_id, initentry, isSysMgr, cdform.getSearchvalue(), sch_deptcd, sch_deptnm, sch_userid, sch_usernm, start, end);
		cdform.setListcolldoc(listcolldoc);
		
		int totalcount = cdmgr.getCountColldoc(user_id, initentry, isSysMgr, cdform.getSearchvalue(), sch_deptcd, sch_deptnm, sch_userid, sch_usernm);
		int totalpage = (int)Math.ceil((double)totalcount/(double)pagesize);		
		request.setAttribute("totalpage",new Integer(totalpage));		
		request.setAttribute("totalcount", new Integer(totalcount));
		request.setAttribute("currpage", new Integer(cdform.getPage()));

		request.setAttribute("initentry", initentry);
		request.setAttribute("orgid", Utils.nullToEmptyString(orgid));
		
		//sysdocno가 0인 문서 모두 삭제
		ServletContext context = getServlet().getServletContext();
		String[] docno = {"0"};
		cdmgr.delColldoc(docno, context);
		
		session.removeAttribute("originuserid");

		return mapping.findForward("colldocList");
	}

}