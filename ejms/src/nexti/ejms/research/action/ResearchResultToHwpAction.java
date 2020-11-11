/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 결과 조회내용 한글다운로드 action
 * 설명:
 */
package nexti.ejms.research.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.research.form.ResearchForm;
import nexti.ejms.research.model.RchSearchBean;
import nexti.ejms.research.model.ResearchBean;
import nexti.ejms.research.model.ResearchManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;

public class ResearchResultToHwpAction extends rootPopupAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//Form에서 넘어온 값 가져오기
		ResearchForm rchForm = (ResearchForm)form;
		
		//세션정보 가져오기
		HttpSession session = request.getSession();
		String sessionId = session.getId().split("[!]")[0];
		
		int rchno = 0;
		String range = "";
		String sch_orggbn = "%";
		String sch_deptcd = "%";
		String sch_sex = "%";

		if(!"".equals(rchForm.getSch_orggbn())){
			sch_orggbn = rchForm.getSch_orggbn().toString();
		}
		
		if(!"".equals(rchForm.getSch_deptcd())){
			sch_deptcd = rchForm.getSch_deptcd().toString();
		}
		
		if(!"".equals(rchForm.getSch_sex())){
			sch_sex = rchForm.getSch_sex().toString();
		}
		
		if(request.getParameter("rchno") != null) {
			rchno = Integer.parseInt(request.getParameter("rchno"));
		}else{
			rchno = rchForm.getRchno();
		}
		
		if(request.getParameter("range") != null) {
			range = request.getParameter("range");
		}else{
			range = rchForm.getRange();
		}
		RchSearchBean schbean = new RchSearchBean();
		
		schbean.setSch_orggbn(sch_orggbn);
		schbean.setSch_deptcd(sch_deptcd);
		schbean.setSch_sex(sch_sex);
		schbean.setSch_age(rchForm.getSch_age());
		schbean.setSch_exam(rchForm.getSch_exam());
		
		//설문조사 마스터  가져오기
		ResearchManager manager = ResearchManager.instance();
		ResearchBean bean = manager.getRchMst(rchno, sessionId);
		//설문조사 문항및 보기 가져오기 
		List rchSubList = manager.getResultSubList(rchno, range, schbean, sessionId);
		bean.setListrch(rchSubList);
		
		BeanUtils.copyProperties(rchForm,bean);
		rchForm.setSch_orggbn(schbean.getSch_orggbn());
		rchForm.setSch_deptcd(schbean.getSch_deptcd());
		rchForm.setSch_sex(schbean.getSch_sex());
		rchForm.setSch_age(schbean.getSch_age());
		rchForm.setSch_exam(schbean.getSch_exam());
		
		String tel = "";
		UserBean ubean = UserManager.instance().getUserInfo(rchForm.getChrgusrid());
		if ( ubean != null ) {
			tel = ubean.getTel();
		}
		rchForm.setColdepttel(tel);
		
		//설문조사명
		request.setAttribute("reqTitle", bean.getTitle());
		request.setAttribute("sch_orggbn", rchForm.getSch_orggbn());
		
		List ansusrList = manager.getRchAnsusrList(rchno);
		if (ansusrList == null) {
			response.setContentType("text/html;charset=euc-kr");
			response.getWriter().print("<script>alert('조회된 결과보기는 응답자가 있을 경우만 가능합니다');</script>");
			response.flushBuffer();
			return null;
		}
				
		return mapping.findForward("hwp");
	}
}