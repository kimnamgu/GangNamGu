/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Է¾��������Ȳ
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
import nexti.ejms.statistics.form.InputsttcsForm;
import nexti.ejms.statistics.model.StatisticsManager;
import nexti.ejms.util.DateTime;

public class InputsttcsListAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
			throws Exception {

		//Form���� �Ѿ�� �� ��������
		InputsttcsForm inputsttcsForm = (InputsttcsForm)form;			
		String orggbn = "";
		String orggbn_dt = "";

		//�������� ( 001:�����ú�û, 002:���ӱ��, 003:�����, 004:����ȸ, 005:��/��/��, 006: ��Ÿ)
		HttpSession session = request.getSession();
		String user_gbn = (String)session.getAttribute("user_gbn");
		if(!user_gbn.equals("001")) inputsttcsForm.setOrggbn(user_gbn);

		if(!"".equals(inputsttcsForm.getOrggbn())){
			orggbn = inputsttcsForm.getOrggbn().toString();
		}
		if(!"".equals(inputsttcsForm.getOrggbn_dt())){
			orggbn_dt = inputsttcsForm.getOrggbn_dt().toString();
		}
		if (inputsttcsForm.getSearch_frdate() == null) {
			inputsttcsForm.setSearch_frdate(String.valueOf(DateTime.getYear()) + "-01-01");	//������� 1��1�Ϸ� ����
		}
		if (inputsttcsForm.getSearch_todate() == null) {
			inputsttcsForm.setSearch_todate(String.valueOf(DateTime.getYear()) + "-12-31");	//������� 12��31�Ϸ� ����
		}
		String frDate = inputsttcsForm.getSearch_frdate();
		String toDate = inputsttcsForm.getSearch_todate();
		int gbn = inputsttcsForm.getGbn();

		StatisticsManager manager = StatisticsManager.instance(); 
		//���վ��������Ȳ ��������
		List list = manager.getInputsttcs(gbn, orggbn, orggbn_dt, frDate, toDate);
		inputsttcsForm.setList(list);

		//���վ��������Ȳ �ѰǼ� ��������
		long totcount = manager.getInputsttcsTotalCount(frDate, toDate);
		inputsttcsForm.setTotcount(totcount);
		
		return mapping.findForward("list");
	}
}