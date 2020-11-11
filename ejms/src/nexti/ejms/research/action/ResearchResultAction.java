/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� ��� action
 * ����:
 */
package nexti.ejms.research.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

//import nexti.ejms.common.rootPopupAction;
import nexti.ejms.research.form.ResearchForm;
import nexti.ejms.research.model.RchSearchBean;
import nexti.ejms.research.model.ResearchBean;
import nexti.ejms.research.model.ResearchManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

//���θ��� ����������⸦ �ܺο� ����(IDüũ ����)���� ����ϱ� ���ؼ� Action Ŭ������ ��ӹ���
//������������ rootPopupAction Ŭ���� ��ӽ� public ActionForward execute�� �����ؾ� ��
//public class ResearchResultAction extends rootPopupAction {
public class ResearchResultAction extends Action {
	
	public ActionForward execute(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response)
		 	throws Exception {
		return doService(mapping, form, request, response);
	}
	
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
		String user_id = (String)session.getAttribute("user_id");
						
		int rchno = 0;
		String range 		= "";
		String sch_orggbn 	= "";
		String sch_deptcd 	= "%";
		String sch_sex 		= "%";

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
		
		//�������� ������  ��������
		ResearchManager manager = ResearchManager.instance();
		
		//TODO : user_id ���� ���� ��� ���������� ���ͳ��̰�, ������ ������ ����� ��ȸ�� �� �ֵ��� ó��
		if ("".equals(user_id) || user_id == null){
			
			int exbitionYN = manager.checkExhibit(rchno);	
			
			if(exbitionYN == 0){
				response.setContentType("text/html;charset=euc-kr");
				PrintWriter out = response.getWriter();
				out.write("<script language=javascript>alert('����� �����Դϴ�.');window.close();</script>");
				out.close();
				return null;
			}
		}
		
		ResearchBean bean = manager.getRchMst(rchno, sessionId);
		
		if ( bean == null ) {
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.write("<script language=javascript>alert('�������� �������簡 �����ϴ�.');window.close();</script>");
			out.close();
			return null;
		}
		
		//�������� ���׹� ���� �������� 
		List rchSubList = manager.getResultSubList(rchno, range, schbean, sessionId);
		bean.setListrch(rchSubList);
		
		BeanUtils.copyProperties(rchForm,bean);
		rchForm.setSch_orggbn(schbean.getSch_orggbn());
		rchForm.setSch_deptcd(schbean.getSch_deptcd());
		rchForm.setSch_sex(schbean.getSch_sex());
		rchForm.setSch_age(schbean.getSch_age());
		rchForm.setSch_exam(schbean.getSch_exam());
		rchForm.setSch_exam(schbean.getSch_exam());
		
		String tel = "";
		UserBean ubean = UserManager.instance().getUserInfo(rchForm.getChrgusrid());
		if ( ubean != null ) {
			tel = ubean.getTel();
		}
		rchForm.setColdepttel(tel);
		
		if ( Utils.nullToEmptyString(user_id).equals(bean.getChrgusrid())
				|| "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			request.setAttribute("isOwner", "true");
		} else {
			request.setAttribute("isOwner", "false");
		}
		
		request.setAttribute("sch_orggbn", sch_orggbn);

		return mapping.findForward("result");
	}
}