/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Է��ϱ� ��� action
 * ����:
 */
package nexti.ejms.inputing.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.inputing.form.InputingForm;
import nexti.ejms.inputing.model.InputingManager;
import nexti.ejms.util.Utils;
import nexti.ejms.util.commfunction;

public class InputingListAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//�������� ��������
		HttpSession session = request.getSession();
		String userid = "";
		String deptcd = "";
		
		userid = session.getAttribute("user_id").toString();
		deptcd = session.getAttribute("dept_code").toString();
		String isSysMgr = (String)session.getAttribute("isSysMgr");
		
		//Form���� �Ѿ�� �� ��������
		InputingForm inputingForm = (InputingForm)form;
		String initentry	= inputingForm.getInitentry();
		String sch_deptcd 	= inputingForm.getSch_old_deptcd();
		String sch_userid 	= inputingForm.getSch_old_userid();
		String sch_deptnm 	= inputingForm.getSch_deptnm();
		String sch_usernm 	= inputingForm.getSch_usernm();
		
		int gubun = inputingForm.getGubun();
		
		//������ ���� ����
		int pageSize = 10;		//�ѹ��� ǥ���� ����Ʈ�� ����
		int start = commfunction.startIndex(inputingForm.getPage(), pageSize);
		int end = commfunction.endIndex(inputingForm.getPage(), pageSize);
		
		//�Է��ϱ� ��� ��������
		InputingManager manager = InputingManager.instance();
		String orgid = manager.getSearchInputing(gubun, sch_deptcd, sch_userid);
		List inputList = manager.inputingList(userid, deptcd, gubun, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, start, end);
		inputingForm.setInputList(inputList);
		
		int totalCount = manager.inputingCnt(userid, deptcd, gubun, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm);
		int totalPage = (int)Math.ceil((double)totalCount/(double)pageSize);		
		request.setAttribute("totalPage", new Integer(totalPage));		
		request.setAttribute("totalCount", new Integer(totalCount));
		request.setAttribute("currpage", new Integer(inputingForm.getPage()));

		request.setAttribute("initentry", initentry);
		request.setAttribute("orgid", Utils.nullToEmptyString(orgid));
		
		session.removeAttribute("originuserid");
		
		return mapping.findForward("list");
	}
}