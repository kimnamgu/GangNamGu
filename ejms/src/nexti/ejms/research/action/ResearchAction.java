/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� ���� action
 * ����:
 */
package nexti.ejms.research.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.rootDirectAction;
import nexti.ejms.research.form.ResearchForm;
import nexti.ejms.research.model.ResearchBean;
import nexti.ejms.research.model.ResearchManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;

public class ResearchAction extends rootDirectAction {
	
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
		
		int rchno = 0;
		
		if(request.getParameter("rchno") != null) {
			rchno = Integer.parseInt(request.getParameter("rchno"));
		}
		
		//�������� ������  ��������
		ResearchManager manager = ResearchManager.instance();
		
		int grpDuplicheck = -1;
		int grpLimitcount = -1;
		if ( "y".equals(request.getParameter("grp")) ) {
			if ( request.getParameter("rchgrpno") != null ) {
				ResearchBean grpBean = manager.getRchGrpMst(Integer.parseInt(request.getParameter("rchgrpno")), sessionId);
				grpDuplicheck = Integer.parseInt(grpBean.getDuplicheck());
				grpLimitcount = grpBean.getLimitcount();
			}
		}
		
		int cnt = manager.rchState(rchno, deptcd, userid, grpDuplicheck, grpLimitcount);
		
		if ( (cnt != 0 && "1".equals(manager.getRchMst(rchno, "").getDuplicheck()))
				|| grpDuplicheck == 1 ) {
			Cookie[] cookies = request.getCookies();
			if ( cookies != null ) {
				for ( int i = 0; i < cookies.length; i++ ) {
					Cookie c = cookies[i];
					String name = c.getName();
					String value = c.getValue();
					if ( name.equals("cookie_ejms_rch") && value.equals("rch"+rchno) ) cnt = -1;
				}		
			}
		}
		
		if ( cnt == 0 ) {
	    	response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.write("<script language=javascript>alert('�������簡 ����Ǿ��ų� ��������� �ƴմϴ�.');window.close();</script>");
			out.close();
			return null;
		} else if ( cnt < 0 ) {
			String msg = "<img src=\"/images/research_comp2.gif\" alt=\"�亯�Ͻ� ���������Դϴ�\">";
			
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
			saveMessages(request,messages);

			return mapping.findForward("hidden");
		}
		
		ResearchBean bean = manager.getRchMst(rchno, sessionId);
		//�������� ���׹� ���� ��������
		List rchSubList = manager.getRchSubList(rchno, sessionId, manager.getRchSubExamcount(rchno, sessionId), "research");
		
		bean.setListrch(rchSubList);
		
		BeanUtils.copyProperties(rchForm,bean);
		
		String tel = "";
		UserBean ubean = UserManager.instance().getUserInfo(rchForm.getChrgusrid());
		if ( ubean != null ) {
			tel = ubean.getTel();
		}
		rchForm.setColdepttel(tel);
		
		return mapping.findForward("view");
	}
}