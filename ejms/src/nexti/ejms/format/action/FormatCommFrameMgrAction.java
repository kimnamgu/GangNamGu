/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공통양식관리 목록 action
 * 설명:
 */
package nexti.ejms.format.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootFrameAction;
import nexti.ejms.format.form.FormatForm;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.format.model.FormatBean;
import nexti.ejms.util.commfunction;

public class FormatCommFrameMgrAction extends rootFrameAction {
	
	public ActionForward doService(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response
			) throws Exception {

		HttpSession session = request.getSession();
		String rep_dept = (String)session.getAttribute("rep_dept");
		String user_id  = (String)session.getAttribute("user_id");
		
		//Form에서 넘어온 값 가져오기
		FormatForm fform = (FormatForm)form;
		
		//데이터 범위 결정
		int pagesize = 10;		//한번에 표시한 리스트의 갯수
		int start = commfunction.startIndex(fform.getPage(), pagesize);
		int end = commfunction.endIndex(fform.getPage(), pagesize);
		
		FormatBean fbean = new FormatBean();
		if(fform.getSearchdept().equals("")) fform.setSearchdept(rep_dept);
		fbean.setSearchdept(fform.getSearchdept());
		fbean.setSearchtitle(fform.getSearchtitle());
		fbean.setSearchcomment(fform.getSearchcomment());
		
		FormatManager fmgr = FormatManager.instance();
		
		List listform = fmgr.getListCommFormatMgr(fbean, user_id, start, end);
		fform.setListform(listform);
		
		int totalcount = fmgr.getCountCommFormat(fbean);
		int totalpage = (int)Math.ceil((double)totalcount/(double)pagesize);		
		request.setAttribute("totalpage",new Integer(totalpage));		
		request.setAttribute("totalcount", new Integer(totalcount));
		request.setAttribute("currpage", new Integer(fform.getPage()));
		request.setAttribute("pagesize", new Integer(pagesize));

		return mapping.findForward("formatCommFrameMgr");
	}
}
