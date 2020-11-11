/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식작성 고정양식형 속성정의 action
 * 설명:
 */
package nexti.ejms.formatFixed.action;

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
import nexti.ejms.formatFixed.form.FormatFixedForm;
import nexti.ejms.formatFixed.model.FormatFixedBean;
import nexti.ejms.formatFixed.model.FormatFixedManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class FormatFixedAttAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {		
		
		//세션정보 가져오기
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");		//사용자 ID
		
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
		FormatFixedManager ffmgr = FormatFixedManager.instance();
		AttrManager attmgr = AttrManager.instance(); 
		
		FormatFixedForm ffform = (FormatFixedForm)form;
		
		FormatFixedBean htmlbean = null;
		try {
			htmlbean = ffmgr.FormatFixedHtmlSeparator(ffform.getFormhtml());
		} catch (Exception e) {
			request.setAttribute("errorMsg", "<script>alert('양식이 올바르지 않습니다.')</script>");
			return new ActionForward("/formatFixedDef.do", false);
		}
		if(htmlbean == null) {
			request.setAttribute("errorMsg", "<script>alert('양식이 올바르지 않습니다.')</script>");
			return new ActionForward("/formatFixedDef.do", false);
		}
		if(htmlbean.getTblcols() > 52) {
			request.setAttribute("errorMsg", "<script>alert('입력 열(칸)이 너무 많습니다. 입력 열은 52개까지 가능합니다.')</script>");
			return new ActionForward("/formatFixedDef.do", false);
		}
		if(htmlbean.getColHeadCount() == 0 && htmlbean.getTblrows() > 1) {
			request.setAttribute("errorMsg", "<script>alert('다중 입력 행(줄)을 작성하시려면 행 머리글을 입력하여야 합니다. ')</script>");
			return new ActionForward("/formatFixedDef.do", false);
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
		ffform.setAttoption1(attoption.toString());
		
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
		ffform.setAttoption2(attoption.toString());
		
		ffform.setFormhtml(formhtml);
		ffform.setFormheaderhtml(formheaderhtml);
		ffform.setFormbodyhtml(formbodyhtml);
		ffform.setFormtailhtml(formtailhtml);
		ffform.setTblrows(tblrows);
		ffform.setTblcols(tblcols);
		ffform.setFormkindname(fmgr.getFormkindName(ffform.getFormkind()));
		
		//1:새로작성,2:공통양식가져오기,3:사용했던양식가져오기,4:공통양식작성,5:양식수정,6:공통양식수정
		int type = ffform.getType();
		int sysdocno = ffform.getSysdocno();
		int formseq = ffform.getFormseq();

		String attCell = null;
		if(type <= 4) {
			
			attCell = ffmgr.makeAttCell(formbodyhtml_ext, tblcols, 0, 0);
			if(attCell == null) {
				request.setAttribute("errorMsg", "<script>alert('양식이 올바르지 않습니다')</script>");
				return new ActionForward("/formatFixedDef.do", false);
			}
			ffform.setFormbodyhtml_ext(attCell);
		
		} else if(type == 5) {
			
			FormatBean fbean = fmgr.getFormat(sysdocno, formseq);
			
			if(fbean.getTblcols() == tblcols) {
				attCell = ffmgr.makeAttCell(formbodyhtml_ext, tblcols, sysdocno, formseq);
				if(attCell == null) {
					request.setAttribute("errorMsg", "<script>alert('양식이 올바르지 않습니다')</script>");
					return new ActionForward("/formatFixedDef.do", false);
				}
				ffform.setFormbodyhtml_ext(attCell);
			} else {
				attCell = ffmgr.makeAttCell(formbodyhtml_ext, tblcols, 0, 0);
				if(attCell == null) {
					request.setAttribute("errorMsg", "<script>alert('양식이 올바르지 않습니다')</script>");
					return new ActionForward("/formatFixedDef.do", false);
				}
				ffform.setFormbodyhtml_ext(attCell);
			}
			
		} else if(type == 6) {
			
			FormatBean fbean = fmgr.getCommFormat(formseq);
			
			if(fbean.getTblcols() == tblcols) {
				attCell = ffmgr.makeAttCell(formbodyhtml_ext, tblcols, 0, formseq);
				if(attCell == null) {
					request.setAttribute("errorMsg", "<script>alert('양식이 올바르지 않습니다')</script>");
					return new ActionForward("/formatFixedDef.do", false);
				}
				ffform.setFormbodyhtml_ext(attCell);
			} else {
				attCell = ffmgr.makeAttCell(formbodyhtml_ext, tblcols, 0, 0);
				if(attCell == null) {
					request.setAttribute("errorMsg", "<script>alert('양식이 올바르지 않습니다')</script>");
					return new ActionForward("/formatFixedDef.do", false);
				}
				ffform.setFormbodyhtml_ext(attCell);
			}
			
		}

		return mapping.findForward("formatFixedAtt");
	}
}