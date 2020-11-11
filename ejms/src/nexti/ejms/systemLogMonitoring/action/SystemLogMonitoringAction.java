/**
 * �ۼ���: 2010.05.26
 * �ۼ���: ��� ������
 * ����: System ���� Log ����͸�
 * ����:
 */
package nexti.ejms.systemLogMonitoring.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.systemLogMonitoring.form.SystemLogMonitoringForm;
import nexti.ejms.systemLogMonitoring.model.SystemLogMonitoringManager;
import nexti.ejms.util.DateTime;
import nexti.ejms.util.commfunction;
import nexti.ejms.common.rootAction;

public class SystemLogMonitoringAction extends rootAction {
	

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {

		HttpSession session = request.getSession();
		String user_id  = (String)session.getAttribute("user_id");
		String rep_dept = (String)session.getAttribute("rep_dept");
		String user_gbn = (String)session.getAttribute("user_gbn");
		
		//Form���� �Ѿ�� �� ��������
		SystemLogMonitoringForm systemLogMonitoringForm = (SystemLogMonitoringForm)form;

		String orggbn = "";
		String orggbn_dt = "";
		
		if(!"".equals(systemLogMonitoringForm.getOrggbn())){
			orggbn = systemLogMonitoringForm.getOrggbn().toString();
		}else{
			if(!user_gbn.equals("001")) orggbn = user_gbn;
		}
		
		if(!"".equals(systemLogMonitoringForm.getOrggbn_dt())){
			orggbn_dt = systemLogMonitoringForm.getOrggbn_dt().toString();
		}
		if (systemLogMonitoringForm.getSearch_frdate() == null) {
			systemLogMonitoringForm.setSearch_frdate(String.valueOf(DateTime.getYear()) + "-01-01");	//������� 1��1�Ϸ� ����
		}
		if (systemLogMonitoringForm.getSearch_todate() == null) {
			systemLogMonitoringForm.setSearch_todate(String.valueOf(DateTime.getYear()) + "-12-31");	//������� 12��31�Ϸ� ����
		}
		String frDate = systemLogMonitoringForm.getSearch_frdate();
		String toDate = systemLogMonitoringForm.getSearch_todate();
		
		//������ ���� ����
		int pagesize = 10;		//�ѹ��� ǥ���� ����Ʈ�� ����
		int start = commfunction.startIndex(systemLogMonitoringForm.getPage(), pagesize);
		int end = commfunction.endIndex(systemLogMonitoringForm.getPage(), pagesize);
		
		//System ���� Log ����͸� ��������
		SystemLogMonitoringManager manager = SystemLogMonitoringManager.instance();

		List systemLogMonitoringList = manager.getSystemLogMonitoringList(orggbn, orggbn_dt, rep_dept, user_id, frDate, toDate, start, end);
		systemLogMonitoringForm.setSystemLogMonitoringList(systemLogMonitoringList);

		//������ �� ���� ����
		HashMap searchCondition = new HashMap();
		searchCondition.put("orggbn", systemLogMonitoringForm.getOrggbn());			
		searchCondition.put("orggbn_dt", systemLogMonitoringForm.getOrggbn_dt());			
		searchCondition.put("frDate", systemLogMonitoringForm.getSearch_frdate());			
		searchCondition.put("toDate", systemLogMonitoringForm.getSearch_todate());		
		request.setAttribute("search",searchCondition);
		
		int totalcount = manager.getSystemLogMonitoringCount(orggbn, orggbn_dt, frDate, toDate);
		int totalpage = (int)Math.ceil((double)totalcount/(double)pagesize);		
		request.setAttribute("totalpage",new Integer(totalpage));		
		request.setAttribute("totalcount", new Integer(totalcount));
		request.setAttribute("currpage", new Integer(systemLogMonitoringForm.getPage()));
		
		return mapping.findForward("list");
	}
}