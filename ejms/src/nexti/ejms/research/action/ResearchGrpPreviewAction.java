/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��������׷� �̸����� action
 * ����:
 */
package nexti.ejms.research.action;

import java.io.PrintWriter;

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

public class ResearchGrpPreviewAction extends rootPopupAction {
	
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
		String deptcd = (String)session.getAttribute("dept_code");
		String userid = (String)session.getAttribute("user_id");
		
		int rchgrpno = 0;
		String mode = "preview";
		
		if(request.getParameter("rchgrpno") != null) {
			rchgrpno = Integer.parseInt(request.getParameter("rchgrpno"));
		}
		
		if(request.getParameter("mode") != null) {
			mode = request.getParameter("mode");
		}
		
		//�������� ������  ��������
		ResearchManager manager = ResearchManager.instance();
		
		if ( "research".equals(mode) ) {			
			int cnt = manager.rchGrpState(rchgrpno, deptcd, userid);
			
			if ( cnt == 0 ) {
		    	response.setContentType("text/html;charset=euc-kr");
				PrintWriter out = response.getWriter();
				out.write("<script language=javascript>alert('�������簡 ����Ǿ��ų� ��������� �ƴմϴ�.');window.close();</script>");
				out.close();
				return null;
			}
		}
		
		ResearchBean bean = manager.getRchGrpMst(rchgrpno, sessionId);
		
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