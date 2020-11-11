/**
 * �ۼ���: 2010.05.26
 * �ۼ���: ��� ������
 * ����: System ���� Log ����͸� �˾�ȭ��
 * ����:
 */
package nexti.ejms.systemLogMonitoring.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.systemLogMonitoring.form.SystemLogMonitoringForm;
import nexti.ejms.systemLogMonitoring.model.SystemLogMonitoringManager;
import nexti.ejms.util.DateTime;
import nexti.ejms.util.commfunction;
import nexti.ejms.common.rootAction;

public class SystemLogDetailMonitoringAction extends rootAction {
	

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {
		
		//Form���� �Ѿ�� �� ��������
		SystemLogMonitoringForm systemLogMonitoringForm = (SystemLogMonitoringForm)form;

		String ccdSubCd  	= "";
		String sch_gubun 	= "";
		String sch_usernm	= "";
		sch_gubun = request.getParameter("sch_gubun");
		
		if(!"".equals(systemLogMonitoringForm.getSch_usernm())){
			sch_usernm = systemLogMonitoringForm.getSch_usernm().toString();
		}
		if(!"".equals(systemLogMonitoringForm.getCcdSubCd())){
			ccdSubCd = systemLogMonitoringForm.getCcdSubCd().toString();
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

		List systemLogMonitoringList = manager.getSystemLogDetailMonitoringList(sch_gubun, sch_usernm, ccdSubCd, frDate, toDate, start, end);
		systemLogMonitoringForm.setSystemLogMonitoringList(systemLogMonitoringList);

		// ����� ��������
		request.setAttribute("deptName", manager.getSystemLogCcdSubCd(sch_gubun, ccdSubCd));
		
		//������ �� ���� ����
		HashMap searchCondition = new HashMap();
		searchCondition.put("ccdSubCd", systemLogMonitoringForm.getCcdSubCd());						
		searchCondition.put("frDate", systemLogMonitoringForm.getSearch_frdate());			
		searchCondition.put("toDate", systemLogMonitoringForm.getSearch_todate());		
		request.setAttribute("search",searchCondition);
		
		int totalcount = manager.getSystemLogDetailMonitoringCount(sch_gubun, sch_usernm, ccdSubCd, frDate, toDate);
		int totalpage = (int)Math.ceil((double)totalcount/(double)pagesize);		
		request.setAttribute("totalpage",new Integer(totalpage));		
		request.setAttribute("totalcount", new Integer(totalcount));
		request.setAttribute("currpage", new Integer(systemLogMonitoringForm.getPage()));
		systemLogMonitoringForm.setSch_gubun(sch_gubun);
		systemLogMonitoringForm.setSch_usernm(sch_usernm);
		systemLogMonitoringForm.setCcdSubCd(ccdSubCd);
		
		return mapping.findForward("list");
	}
}