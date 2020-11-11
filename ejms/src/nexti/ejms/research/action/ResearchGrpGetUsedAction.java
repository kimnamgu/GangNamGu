/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� ���������׷찡������ action
 * ����:
 */
package nexti.ejms.research.action;

import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.research.model.ResearchManager;
import nexti.ejms.research.model.ResearchBean;
import nexti.ejms.common.rootAction;
import nexti.ejms.research.form.ResearchForm;
import nexti.ejms.util.commfunction;


public class ResearchGrpGetUsedAction extends rootAction {

	public ActionForward doService(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		String deptcd = (String)session.getAttribute("dept_code");	//����� �μ��ڵ�;
		
		//Form���� �Ѿ�� �� ��������
		ResearchForm rchForm = (ResearchForm) form;
		
		//������ ���� ����
		int pageSize = 10;		//�ѹ��� ǥ���� ����Ʈ�� ����
		int start = commfunction.startIndex(rchForm.getPage(), pageSize);
		int end = commfunction.endIndex(rchForm.getPage(), pageSize);
		
		String selyear = "";
		String sch_title = "";
		Calendar today = Calendar.getInstance();
		
		if(rchForm.getInitentry().equals("first")) {
			selyear = Integer.toString(today.get(Calendar.YEAR)) + "��";
		} else {
			selyear = rchForm.getSelyear();
		}
		
		if(request.getParameter("sch_title")!= null){
			sch_title = request.getParameter("sch_title");
		}else{
			sch_title = rchForm.getSch_title();
		}
		
		if(rchForm.getSch_deptcd().equals("") == true) {
			rchForm.setSch_deptcd(deptcd);
		}
		
		//�ֱ����ո�� ��������
		ResearchBean Bean = new ResearchBean();
		Bean.setSch_deptcd(rchForm.getSch_deptcd());
		Bean.setSch_title(sch_title);
		Bean.setSelyear(selyear.substring(0, 4));
		
		ResearchManager manager = ResearchManager.instance();
		rchForm.setListrch(manager.getUsedRchGrpList(Bean, start, end));

		//������ �� ���� ����
		HashMap schCondition = new HashMap();
		schCondition.put("schdept", Bean.getSch_deptcd());			
		schCondition.put("schtitle", Bean.getSch_title());			
		schCondition.put("schyear", Bean.getSelyear());									
		request.setAttribute("sch",schCondition);
		
		//ȭ������ ������ �� ����
		int totalCount = manager.getUsedRchGrpTotCnt(Bean);
		int totalPage = (int)Math.ceil((double)totalCount/(double)pageSize);		
		request.setAttribute("totalPage",new Integer(totalPage));		
		request.setAttribute("totalCount", new Integer(totalCount));
		request.setAttribute("currpage", new Integer(rchForm.getPage()));
		request.setAttribute("currentyear", selyear);

		return mapping.findForward("list");
	}
}
