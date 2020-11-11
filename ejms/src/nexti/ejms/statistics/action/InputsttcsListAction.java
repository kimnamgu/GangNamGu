/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력업무사용현황
 * 설명:
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

		//Form에서 넘어온 값 가져오기
		InputsttcsForm inputsttcsForm = (InputsttcsForm)form;			
		String orggbn = "";
		String orggbn_dt = "";

		//조직구분 ( 001:광역시본청, 002:직속기관, 003:사업소, 004:시의회, 005:시/구/군, 006: 기타)
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
			inputsttcsForm.setSearch_frdate(String.valueOf(DateTime.getYear()) + "-01-01");	//현재년의 1월1일로 세팅
		}
		if (inputsttcsForm.getSearch_todate() == null) {
			inputsttcsForm.setSearch_todate(String.valueOf(DateTime.getYear()) + "-12-31");	//현재년의 12월31일로 세팅
		}
		String frDate = inputsttcsForm.getSearch_frdate();
		String toDate = inputsttcsForm.getSearch_todate();
		int gbn = inputsttcsForm.getGbn();

		StatisticsManager manager = StatisticsManager.instance(); 
		//취합업무사용현황 가져오기
		List list = manager.getInputsttcs(gbn, orggbn, orggbn_dt, frDate, toDate);
		inputsttcsForm.setList(list);

		//취합업무사용현황 총건수 가져오기
		long totcount = manager.getInputsttcsTotalCount(frDate, toDate);
		inputsttcsForm.setTotcount(totcount);
		
		return mapping.findForward("list");
	}
}