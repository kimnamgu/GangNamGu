/**
 * 작성일: 2010.05.26
 * 작성자: 사원 신종영
 * 모듈명: LDAP 모니터링 action
 * 설명:
 */
package nexti.ejms.ldapMonitoring.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.ldapMonitoring.form.LdapMonitoringForm;
import nexti.ejms.ldapMonitoring.model.LdapMonitoringManager;
import nexti.ejms.util.commfunction;
import nexti.ejms.common.rootAction;

public class LdapMonitoringAction extends rootAction {
	

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
				
		//Form에서 넘어온 값 가져오기
		LdapMonitoringForm ldapMonitoringForm = (LdapMonitoringForm)form;		
		String orggbn = "";
		String orggbn_dt = "";	
		String gbn = "";
		String syncdate = "";
		
		//조직구분 ( 001:광역시본청, 002:직속기관, 003:사업소, 004:시의회, 005:시/구/군, 006: 기타)
		if(!"".equals(ldapMonitoringForm.getOrggbn())){
			orggbn = ldapMonitoringForm.getOrggbn().toString();
		}else{
			if(!user_gbn.equals("001")) orggbn = user_gbn;
		}
		
		if(!"".equals(ldapMonitoringForm.getOrggbn_dt())){
			orggbn_dt = ldapMonitoringForm.getOrggbn_dt().toString();
		}
		if(!"".equals(ldapMonitoringForm.getSyncdate())){
			syncdate = ldapMonitoringForm.getSyncdate().toString();
		}
		gbn = ldapMonitoringForm.getGbn();	//0:부서, 1:사용자
		
		//데이터 범위 결정
		int pagesize = 10;		//한번에 표시한 리스트의 갯수
		int start = commfunction.startIndex(ldapMonitoringForm.getPage(), pagesize);
		int end = commfunction.endIndex(ldapMonitoringForm.getPage(), pagesize);
		
		//LDAP 모니터링  가져오기
		LdapMonitoringManager manager = LdapMonitoringManager.instance();

		List ldapMonitoringList = manager.getLdapMonitoringList(orggbn, orggbn_dt, gbn, syncdate, rep_dept, user_id, start, end);
		ldapMonitoringForm.setLdapMonitoringList(ldapMonitoringList);

		int totalcount = manager.getLdapMonitoringCount(orggbn, orggbn_dt, gbn, syncdate, rep_dept, user_id);
		int totalpage = (int)Math.ceil((double)totalcount/(double)pagesize);		
		request.setAttribute("totalpage",new Integer(totalpage));		
		request.setAttribute("totalcount", new Integer(totalcount));
		request.setAttribute("currpage", new Integer(ldapMonitoringForm.getPage()));
		ldapMonitoringForm.setOrggbn(orggbn);
		
		return mapping.findForward("list");
	}
}