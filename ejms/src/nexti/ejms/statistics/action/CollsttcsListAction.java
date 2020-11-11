/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���վ��������Ȳ action
 * ����:
 */
package nexti.ejms.statistics.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.statistics.form.CollsttcsForm;
import nexti.ejms.statistics.model.StatisticsManager;
import nexti.ejms.util.DateTime;

public class CollsttcsListAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
			throws Exception {

		//Form���� �Ѿ�� �� ��������
		CollsttcsForm collsttcsForm = (CollsttcsForm)form;		
		String orggbn = "";
		String orggbn_dt = "";

		//�������� ( 001:�����ú�û, 002:���ӱ��, 003:�����, 004:����ȸ, 005:��/��/��, 006: ��Ÿ)
		HttpSession session = request.getSession();
		String user_gbn = (String)session.getAttribute("user_gbn");
		if(!user_gbn.equals("001")) collsttcsForm.setOrggbn(user_gbn);

		if(!"".equals(collsttcsForm.getOrggbn())){
			orggbn = collsttcsForm.getOrggbn().toString();
		}
		if(!"".equals(collsttcsForm.getOrggbn_dt())){
			orggbn_dt = collsttcsForm.getOrggbn_dt().toString();
		}
		if (collsttcsForm.getSearch_frdate() == null) {
			collsttcsForm.setSearch_frdate(String.valueOf(DateTime.getYear()) + "-01-01");	//������� 1��1�Ϸ� ����
		}
		if (collsttcsForm.getSearch_todate() == null) {
			collsttcsForm.setSearch_todate(String.valueOf(DateTime.getYear()) + "-12-31");	//������� 12��31�Ϸ� ����
		}
		String frDate = collsttcsForm.getSearch_frdate();
		String toDate = collsttcsForm.getSearch_todate();
		int gbn = collsttcsForm.getGbn();

		StatisticsManager manager = StatisticsManager.instance(); 
		//���վ��������Ȳ ��������
		List list = manager.getCollsttcs(gbn, orggbn, orggbn_dt, frDate, toDate);
		collsttcsForm.setList(list);

		//���վ��������Ȳ �ѰǼ� ��������
		long totcount = manager.getCollsttcsTotalCount(frDate, toDate);
		collsttcsForm.setTotcount(totcount);		
	
		return mapping.findForward("list");
	}
}