/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ۼ� ���߰��� �Ӽ����� action
 * ����:
 */
package nexti.ejms.formatLine.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.attr.model.AttrBean;
import nexti.ejms.attr.model.AttrManager;
import nexti.ejms.format.model.FormatBean;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.formatLine.form.FormatLineForm;
import nexti.ejms.formatLine.model.FormatLineBean;
import nexti.ejms.formatLine.model.FormatLineManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class FormatLineAttAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		//�������� ��������
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");		//����� ID
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				user_id = originUserBean.getUser_id();
			}
		}

		FormatManager fmgr = FormatManager.instance();
		FormatLineManager flmgr = FormatLineManager.instance();
		AttrManager attmgr = AttrManager.instance();
		
		FormatLineForm flform = (FormatLineForm)form;
		
		FormatLineBean htmlbean = null;
		try {
			htmlbean = flmgr.FormatLineHtmlSeparator(flform.getFormhtml());
		} catch (Exception e) {
			request.setAttribute("errorMsg", "<script>alert('����� �ùٸ��� �ʽ��ϴ�.')</script>");
			return new ActionForward("/formatLineDef.do", false);
		}
		if(htmlbean == null) {
			request.setAttribute("errorMsg", "<script>alert('����� �ùٸ��� �ʽ��ϴ�.')</script>");
			return new ActionForward("/formatLineDef.do", false);
		}
		if(htmlbean.getTblcols() > 52) {
			request.setAttribute("errorMsg", "<script>alert('�Է� ��(ĭ)�� �ʹ� �����ϴ�. �Է� ���� 52������ �����մϴ�.')</script>");
			return new ActionForward("/formatLineDef.do", false);
		}
		
		String formhtml = htmlbean.getFormhtml();
		String formheaderhtml = htmlbean.getFormheaderhtml();
		String formbodyhtml = htmlbean.getFormbodyhtml();
		String formbodyhtml_ext = htmlbean.getFormbodyhtml_ext();
		String formtailhtml = htmlbean.getFormtailhtml();
		int tblrows = htmlbean.getTblrows();
		int tblcols = htmlbean.getTblcols();
		
		List attlist = null;
		StringBuffer attoption = null;
		
		attoption = new StringBuffer();
		attlist = attmgr.getFormatAttList(user_id, "1");
		for(int i = 0; i < attlist.size(); i++) {
			AttrBean bean = (AttrBean)attlist.get(i);
			attoption.append("<option value=\"" + bean.getListcd() + "," + bean.getListnm());
			List listdtl = bean.getListdtlList();
			for(int j = 0; j < listdtl.size(); j++) {
				AttrBean attdtlbean = (AttrBean)listdtl.get(j);
				attoption.append("," + attdtlbean.getSeq() + "," + attdtlbean.getListdtlnm());
			}
			attoption.append("\" title=\"" + bean.getListnm() + "\">" + bean.getListnm() + "</option>");
		}
		flform.setAttoption1(attoption.toString());
		
		attoption = new StringBuffer();
		attlist = attmgr.getFormatAttList("%", "0");
		for(int i = 0; i < attlist.size(); i++) {
			AttrBean bean = (AttrBean)attlist.get(i);
			attoption.append("<option value=\"" + bean.getListcd() + "," + bean.getListnm());
			List listdtl = bean.getListdtlList();
			for(int j = 0; j < listdtl.size(); j++) {
				AttrBean attdtlbean = (AttrBean)listdtl.get(j);
				attoption.append("," + attdtlbean.getSeq() + "," + attdtlbean.getListdtlnm());
			}
			attoption.append("\" title=\"" + bean.getListnm() + "\">" + bean.getListnm() + "</option>");
		}
		flform.setAttoption2(attoption.toString());
		
		flform.setFormhtml(formhtml);
		flform.setFormheaderhtml(formheaderhtml);
		flform.setFormbodyhtml(formbodyhtml);
		flform.setFormtailhtml(formtailhtml);
		flform.setTblrows(tblrows);
		flform.setTblcols(tblcols);
		flform.setFormkindname(fmgr.getFormkindName(flform.getFormkind()));
		
		//1:�����ۼ�,2:�����İ�������,3:����ߴ���İ�������,4:�������ۼ�,5:��ļ���,6:�����ļ���
		int type = flform.getType();
		int sysdocno = flform.getSysdocno();
		int formseq = flform.getFormseq();
		
		String attCell = null;
		if(type <= 4) {
			
			attCell = flmgr.makeAttCell(formbodyhtml_ext, tblcols, 0, 0);
			if(attCell == null) {
				request.setAttribute("errorMsg", "<script>alert('����� �ùٸ��� �ʽ��ϴ�')</script>");
				return new ActionForward("/formatLineDef.do", false);
			}
			flform.setFormbodyhtml_ext(attCell);
		
		} else if(type == 5) {
			
			FormatBean fbean = fmgr.getFormat(sysdocno, formseq);
			
			if(fbean.getTblcols() == tblcols) {
				attCell = flmgr.makeAttCell(formbodyhtml_ext, tblcols, sysdocno, formseq);
				if(attCell == null) {
					request.setAttribute("errorMsg", "<script>alert('����� �ùٸ��� �ʽ��ϴ�')</script>");
					return new ActionForward("/formatLineDef.do", false);
				}
				flform.setFormbodyhtml_ext(attCell);
			} else {
				attCell = flmgr.makeAttCell(formbodyhtml_ext, tblcols, 0, 0);
				if(attCell == null) {
					request.setAttribute("errorMsg", "<script>alert('����� �ùٸ��� �ʽ��ϴ�')</script>");
					return new ActionForward("/formatLineDef.do", false);
				}
				flform.setFormbodyhtml_ext(attCell);
			}
			
		} else if(type == 6) {
			
			FormatBean fbean = fmgr.getCommFormat(formseq);
			
			if(fbean.getTblcols() == tblcols) {
				attCell = flmgr.makeAttCell(formbodyhtml_ext, tblcols, 0, formseq);
				if(attCell == null) {
					request.setAttribute("errorMsg", "<script>alert('����� �ùٸ��� �ʽ��ϴ�')</script>");
					return new ActionForward("/formatLineDef.do", false);
				}
				flform.setFormbodyhtml_ext(attCell);
			} else {
				attCell = flmgr.makeAttCell(formbodyhtml_ext, tblcols, 0, 0);
				if(attCell == null) {
					request.setAttribute("errorMsg", "<script>alert('����� �ùٸ��� �ʽ��ϴ�')</script>");
					return new ActionForward("/formatLineDef.do", false);
				}
				flform.setFormbodyhtml_ext(attCell);
			}
			
		}
		
		return mapping.findForward("formatLineAtt");
	}
}