/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� ���������� ��� action
 * ����:
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

public class ResearchMyListAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
	
		//�������� ��������
		HttpSession session = request.getSession();
		String usrid 	= (String)session.getAttribute("user_id");
		String deptcd 	= (String)session.getAttribute("dept_code");
		String isSysMgr = (String)session.getAttribute("isSysMgr");
		
		//Form���� �Ѿ�� �� ��������
		ResearchForm researchForm = (ResearchForm)form;			
	
		//������ ���� ����
		int pageSize = 10;		//�ѹ��� ǥ���� ����Ʈ�� ����
		int start = commfunction.startIndex(researchForm.getPage(), pageSize);
		int end = commfunction.endIndex(researchForm.getPage(), pageSize);
		
		//�˻����� : �μ���, �����, ����
		String groupyn		= researchForm.getGroupyn();
		String sch_title  	= researchForm.getSch_title();
		String initentry	= researchForm.getInitentry();
		String sch_deptcd 	= researchForm.getSch_old_deptcd();
		String sch_userid 	= researchForm.getSch_old_userid();
		String sch_deptnm 	= researchForm.getSch_deptnm();
		String sch_usernm 	= researchForm.getSch_usernm();
				
		//�������ڷ�  ��� ��������
		ResearchManager manager = ResearchManager.instance(); 
		String orgid = manager.getSearch(groupyn, sch_title, sch_deptcd, sch_userid);
		List researchList = manager.getResearchMyList(usrid, deptcd, initentry, isSysMgr, groupyn, sch_title, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, start, end);	
		researchForm.setListrch(researchList);
		researchForm.setSch_title(sch_title);
		
		int totalCount = manager.getResearchTotCnt(usrid, deptcd, initentry, isSysMgr, groupyn, sch_title, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, "");
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