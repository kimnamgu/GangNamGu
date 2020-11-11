/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ������������Ȳ
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
import nexti.ejms.statistics.form.RchsttcsForm;
import nexti.ejms.statistics.model.RchsttcsBean;
import nexti.ejms.statistics.model.StatisticsManager;
import nexti.ejms.util.DateTime;

public class RchsttcsListAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
			throws Exception {

		//Form���� �Ѿ�� �� ��������
		RchsttcsForm myForm = (RchsttcsForm)form;		
		String orggbn = "";
		String orggbn_dt = "";

		//�������� ( 001:�����ú�û, 002:���ӱ��, 003:�����, 004:����ȸ, 005:��/��/��, 006: ��Ÿ)
		HttpSession session = request.getSession();
		String user_gbn = (String)session.getAttribute("user_gbn");
		if(!user_gbn.equals("001")) myForm.setOrggbn(user_gbn);

		if(!"".equals(myForm.getOrggbn())){
			orggbn = myForm.getOrggbn().toString();
		}
		if(!"".equals(myForm.getOrggbn_dt())){
			orggbn_dt = myForm.getOrggbn_dt().toString();
		}		
		if (myForm.getSearch_frdate() == null) {
			myForm.setSearch_frdate(String.valueOf(DateTime.getYear()) + "-01-01");	//������� 1��1�Ϸ� ����
		}
		if (myForm.getSearch_todate() == null) {
			myForm.setSearch_todate(String.valueOf(DateTime.getYear()) + "-12-31");	//������� 12��31�Ϸ� ����
		}
		String frDate = myForm.getSearch_frdate();
		String toDate = myForm.getSearch_todate();
		String range  = myForm.getSearch_range();
		int gbn = myForm.getGbn();
		
		StatisticsManager manager = StatisticsManager.instance(); 
		//�����Ȳ ��������
		List list = manager.getRchsttcs(gbn, orggbn, orggbn_dt, frDate, toDate, range);
		myForm.setList(list);

		//�ѰǼ� ��������
		RchsttcsBean bean = manager.getRchsttcsTotalCount(frDate, toDate, range); 
		myForm.setTotrchcnt(bean.getRchcount());
		myForm.setTotanscnt(bean.getAnscount());
		
		return mapping.findForward("list");
	}
}