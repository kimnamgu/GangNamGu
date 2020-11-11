/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Է¿Ϸ� ��� action
 * ����:
 */
package nexti.ejms.inputing.action;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.inputing.form.InputCompleteForm;
import nexti.ejms.inputing.model.InputingManager;
import nexti.ejms.util.commfunction;

public class InputCompleteListAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//�������� ��������
		HttpSession session = request.getSession();
		String user_id = "";
		String dept_code = "";

		user_id = session.getAttribute("user_id").toString();
		dept_code = session.getAttribute("dept_code").toString();
		String isSysMgr = (String)session.getAttribute("isSysMgr");
		
		//Form���� �Ѿ�� �� ��������
		InputCompleteForm inputCompleteForm = (InputCompleteForm)form;
		String initentry	= inputCompleteForm.getInitentry();
		String sch_deptcd 	= inputCompleteForm.getSch_old_deptcd();
		String sch_userid 	= inputCompleteForm.getSch_old_userid();
		String sch_deptnm 	= inputCompleteForm.getSch_deptnm();
		String sch_usernm 	= inputCompleteForm.getSch_usernm();
		
		String selyear = "";
		Calendar today = Calendar.getInstance();
		
		if(inputCompleteForm.getInitentry().equals("first")) {
			selyear = Integer.toString(today.get(Calendar.YEAR)) + "��";
		} else {
			selyear = inputCompleteForm.getSelyear();
		}
		
		//������ ���� ����
		int pageSize = 10;		//�ѹ��� ǥ���� ����Ʈ�� ����
		int start = commfunction.startIndex(inputCompleteForm.getPage(), pageSize);
		int end = commfunction.endIndex(inputCompleteForm.getPage(), pageSize);
		
		//�Է¿Ϸ� ��� ��������
		InputingManager manager = InputingManager.instance();
		List compList = manager.inputCompleteList(user_id, dept_code, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, start, end, inputCompleteForm.getSearchvalue(), selyear.substring(0, 4));
		inputCompleteForm.setCompleteList(compList);
		
		int totalCount = manager.inputCompleteCnt(user_id, dept_code, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, inputCompleteForm.getSearchvalue(), selyear.substring(0, 4));
		int totalPage = (int)Math.ceil((double)totalCount/(double)pageSize);
		request.setAttribute("totalPage", new Integer(totalPage));		
		request.setAttribute("totalCount", new Integer(totalCount));
		request.setAttribute("currpage", new Integer(inputCompleteForm.getPage()));
		request.setAttribute("currentyear", selyear);

		request.setAttribute("initentry", initentry);
		
		session.removeAttribute("originuserid");
		
		return mapping.findForward("list");
	}
}