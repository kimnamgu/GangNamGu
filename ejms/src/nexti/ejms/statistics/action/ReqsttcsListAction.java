/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서사용현황
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
import nexti.ejms.statistics.form.ReqsttcsForm;
import nexti.ejms.statistics.model.ReqsttcsBean;
import nexti.ejms.statistics.model.StatisticsManager;
import nexti.ejms.util.DateTime;

public class ReqsttcsListAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
			throws Exception {

		//Form에서 넘어온 값 가져오기
		ReqsttcsForm myForm = (ReqsttcsForm)form;			
		String orggbn = "";
		String orggbn_dt = "";
		
		//조직구분 ( 001:광역시본청, 002:직속기관, 003:사업소, 004:시의회, 005:시/구/군, 006: 기타)
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
			myForm.setSearch_frdate(String.valueOf(DateTime.getYear()) + "-01-01");	//현재년의 1월1일로 세팅
		}
		if (myForm.getSearch_todate() == null) {
			myForm.setSearch_todate(String.valueOf(DateTime.getYear()) + "-12-31");	//현재년의 12월31일로 세팅
		}
		String frDate = myForm.getSearch_frdate();
		String toDate = myForm.getSearch_todate();
		String range  = myForm.getSearch_range();
		int gbn = myForm.getGbn();

		StatisticsManager manager = StatisticsManager.instance(); 
		//사용현황 가져오기
		List list = manager.getReqsttcs(gbn, orggbn, orggbn_dt, frDate, toDate, range);
		myForm.setList(list);

		//총건수 가져오기
		ReqsttcsBean bean = manager.getReqsttcsTotalCount(frDate, toDate, range); 
		myForm.setTotreqcnt(bean.getReqcount());
		myForm.setTotanscnt(bean.getAnscount());
		
		return mapping.findForward("list");
	}
}