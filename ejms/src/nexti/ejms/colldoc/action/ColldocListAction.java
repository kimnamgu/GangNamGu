/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���չ����ۼ� ��� action
 * ����:
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
		
		//�������� ��������
		HttpSession session = request.getSession();		
		String user_id = (String)session.getAttribute("user_id");
		String isSysMgr = (String)session.getAttribute("isSysMgr");
		
		//Form���� �Ѿ�� �� ��������
		ColldocForm cdform = (ColldocForm)form;			
		String initentry	= cdform.getInitentry();
		String sch_deptcd 	= cdform.getSch_old_deptcd();
		String sch_userid 	= cdform.getSch_old_userid();
		String sch_deptnm 	= cdform.getSch_deptnm();
		String sch_usernm 	= cdform.getSch_usernm();
	
		//������ ���� ����
		int pagesize = 10;		//�ѹ��� ǥ���� ����Ʈ�� ����
		int start = commfunction.startIndex(cdform.getPage(), pagesize);
		int end = commfunction.endIndex(cdform.getPage(), pagesize);
		
		//�ֱ����ո�� ��������
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
		
		//sysdocno�� 0�� ���� ��� ����
		ServletContext context = getServlet().getServletContext();
		String[] docno = {"0"};
		cdmgr.delColldoc(docno, context);
		
		session.removeAttribute("originuserid");

		return mapping.findForward("colldocList");
	}

}