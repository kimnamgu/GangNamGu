/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����������չ��� ��� action
 * ����:
 */
package nexti.ejms.colldoc.action;

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
import nexti.ejms.util.Utils;
import nexti.ejms.util.commfunction;

public class CollprocListAction extends rootAction {
	
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
		ColldocForm colldocForm = (ColldocForm)form;	
		String initentry	= colldocForm.getInitentry();	
		String sch_deptcd 	= colldocForm.getSch_old_deptcd();
		String sch_userid 	= colldocForm.getSch_old_userid();
		String sch_deptnm 	= colldocForm.getSch_deptnm();
		String sch_usernm 	= colldocForm.getSch_usernm();
	
		//������ ���� ����
		int pageSize = 10;		//�ѹ��� ǥ���� ����Ʈ�� ����
		int start = commfunction.startIndex(colldocForm.getPage(), pageSize);
		int end = commfunction.endIndex(colldocForm.getPage(), pageSize);
		String docstate = colldocForm.getDocstate();
		
		if(docstate.equals("") || docstate == null) {
			if( "".equals(Utils.nullToEmptyString((String)request.getParameter("docstate"))) ){
				docstate = "0";
			}else{
				docstate = request.getParameter("docstate");
			}
		}
				
		//����ϱ� ��� ��������
		ColldocManager manager = ColldocManager.instance(); 
		String orgid = manager.getSearchProc(docstate, sch_deptcd, sch_userid);
		List collprocList = manager.getCollProcList(usrid, deptcd, initentry, isSysMgr, docstate, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, start, end);	
		colldocForm.setListcolldoc(collprocList);
		colldocForm.setDocstate(docstate);
		
		int totalCount = manager.getCollProcTotCnt(usrid, deptcd, initentry, isSysMgr, docstate, sch_deptcd, sch_deptnm, sch_userid, sch_usernm);
		int totalPage = (int)Math.ceil((double)totalCount/(double)pageSize);

		request.setAttribute("totalPage", new Integer(totalPage));
		request.setAttribute("totalCount", new Integer(totalCount));
		request.setAttribute("currpage", new Integer(colldocForm.getPage()));
		
		request.setAttribute("initentry", initentry);	 
		request.setAttribute("orgid", Utils.nullToEmptyString(orgid));
		request.setAttribute("docDeleteYN", CcdManager.instance().getCcd_SubName("016", "01"));
		
		session.removeAttribute("originuserid");
		
		return mapping.findForward("list");
	}
}