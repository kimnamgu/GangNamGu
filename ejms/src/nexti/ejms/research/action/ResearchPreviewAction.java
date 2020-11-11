/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� �̸����� action
 * ����:
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
import nexti.ejms.research.model.ResearchBean;
import nexti.ejms.research.model.ResearchManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;

public class ResearchPreviewAction extends rootPopupAction {
	
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
		String mode = "preview";
		
		if(request.getParameter("rchno") != null) {
			rchno = Integer.parseInt(request.getParameter("rchno"));
		}
		
		if(request.getParameter("mode") != null) {
			mode = request.getParameter("mode");
		}
		
		//�������� ������  ��������
		ResearchManager manager = ResearchManager.instance();
		ResearchBean bean = manager.getRchMst(rchno, sessionId);
		//�������� ���׹� ���� �������� 
		List rchSubList = manager.getRchSubList(rchno, sessionId, manager.getRchSubExamcount(rchno, sessionId), mode);
		bean.setListrch(rchSubList);
		
		BeanUtils.copyProperties(rchForm,bean);
		
		String tel = "";
		UserBean ubean = UserManager.instance().getUserInfo(rchForm.getChrgusrid());
		if ( ubean != null ) {
			tel = ubean.getTel();
		}
		rchForm.setColdepttel(tel);
		
		rchForm.setMode(mode);
		return mapping.findForward("preview");
	}
}