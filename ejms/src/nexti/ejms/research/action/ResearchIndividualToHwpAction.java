/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� ��� ���κ� �ѱ۴ٿ�ε� action
 * ����:
 */
package nexti.ejms.research.action;

import java.util.ArrayList;
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

public class ResearchIndividualToHwpAction extends rootPopupAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//Form���� �Ѿ�� �� ��������
		ResearchForm rchForm = (ResearchForm)form;
		
		//�������� ��������
		HttpSession session = request.getSession();
		String sessionId = session.getId().split("[!]")[0];
		
		int rchno = 0;
		String range = "";
		String sch_deptcd = "%";
		String sch_sex = "%";
		
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
		
		schbean.setSch_deptcd(sch_deptcd);
		schbean.setSch_sex(sch_sex);
		schbean.setSch_age("");
		
		String[] arrExam = rchForm.getSch_exam();
		for(int i = 0; arrExam != null && i < arrExam.length; i++) {
			arrExam[i] = "%";
		}
		schbean.setSch_exam(arrExam);
		
		ResearchManager manager = ResearchManager.instance();
		
		List resultList = null;
		List ansusrList = manager.getRchAnsusrList(rchno);
		for (int i = 0; ansusrList != null && i < ansusrList.size(); i++) {
			ResearchBean rbean = new ResearchBean();
			
			schbean.setSch_ansusrseq(((ResearchBean)ansusrList.get(i)).getAnsusrseq());
			
			//�������� ������  ��������
			ResearchBean bean = manager.getRchMst(rchno, sessionId);
			//�������� ���׹� ���� �������� 
			List rchSubList = manager.getResultSubList(rchno, range, schbean, sessionId);
			bean.setListrch(rchSubList);
			
			BeanUtils.copyProperties(rbean,bean);
			rbean.setCrtusrnm(((ResearchBean)ansusrList.get(i)).getCrtusrnm());
			rbean.setSch_deptcd(schbean.getSch_deptcd());
			rbean.setSch_sex(schbean.getSch_sex());
			rbean.setSch_age(schbean.getSch_age());
			rbean.setSch_exam(schbean.getSch_exam());
			
			String tel = "";
			UserBean ubean = UserManager.instance().getUserInfo(bean.getChrgusrid());
			if ( ubean != null ) {
				tel = ubean.getTel();
			}
			rbean.setColdepttel(tel);
			
			if (resultList == null) {
				resultList = new ArrayList();
				//���������
				request.setAttribute("reqTitle", bean.getTitle());
			}
			resultList.add(rbean);
		}
		
		rchForm.setIndividualResult(resultList);
		
		if (resultList == null || resultList.size() == 0) {
			response.setContentType("text/html;charset=euc-kr");
			response.getWriter().print("<script>alert('���κ� �������� �����ڰ� ���� ��츸 �����մϴ�');</script>");
			response.flushBuffer();
			return null;
		}
				
		return mapping.findForward("hwp");
	}
}