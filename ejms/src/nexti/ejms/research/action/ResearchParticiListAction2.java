/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� �����ϱ� ��� action
 * ����:
 */
package nexti.ejms.research.action;

import java.util.HashMap;
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
import nexti.ejms.util.commfunction;

public class ResearchParticiListAction2 extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {		 
		
		//���ǰ� ����
		HttpSession session = request.getSession();
		String usrid = session.getAttribute("user_id").toString();
		String deptcd = session.getAttribute("dept_code").toString();
		
		//Form���� �Ѿ�� �� ��������
		ResearchForm rchForm = (ResearchForm)form;	
		int cPage = rchForm.getPage();             //����������
		String groupyn = rchForm.getGroupyn();
		String schtitle = rchForm.getSch_title();         //����

		//������ ���� ����
		int pageSize = 10;		//�ѹ��� ǥ���� ����Ʈ�� ����
		int start = commfunction.startIndex(cPage, pageSize);
		int end = commfunction.endIndex(cPage, pageSize);	
		
		//��� ��������
		ResearchManager manager = ResearchManager.instance(); 
		List rchList = manager.getRchParticiList(usrid, deptcd, schtitle, groupyn, start, end);
		rchForm.setListrch(rchList);		
		
		//������ �� ���� ����		
		HashMap schCondition = new HashMap();
		schCondition.put("schdept", deptcd);			
		schCondition.put("schtitle", schtitle);											
		request.setAttribute("sch",schCondition);
		
		//ȭ������ ������ �� ����
		int totalCount = manager.getRchParticiTotCnt(usrid, deptcd, schtitle, groupyn);
		int totalPage = (int)Math.ceil((double)totalCount/(double)pageSize);		
		request.setAttribute("totalPage",new Integer(totalPage));		
		request.setAttribute("totalCount", new Integer(totalCount));
		request.setAttribute("currpage", new Integer(rchForm.getPage()));
		
		return mapping.findForward("list");
	}
}