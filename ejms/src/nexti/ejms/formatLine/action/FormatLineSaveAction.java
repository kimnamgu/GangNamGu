/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식작성 행추가형 저장 action
 * 설명:
 */
package nexti.ejms.formatLine.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.format.model.FormatBean;
import nexti.ejms.formatLine.form.FormatLineForm;
import nexti.ejms.formatLine.model.FormatLineBean;
import nexti.ejms.formatLine.model.FormatLineManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class FormatLineSaveAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response)
	throws Exception {
		
		//세션정보 가져오기
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				user_id = originUserBean.getUser_id();
			}
		}
		
		String[] cellatt = request.getParameterValues("cellatt");
		
		FormatLineForm flform = (FormatLineForm)form;
		
		FormatLineManager flmgr = FormatLineManager.instance();
		FormatLineBean htmlbean = flmgr.FormatLineHtmlSeparator(flform.getFormhtml());
		
		String formhtml = htmlbean.getFormhtml();
		String formheaderhtml = htmlbean.getFormheaderhtml();
		String formbodyhtml = htmlbean.getFormbodyhtml();
		String formtailhtml = htmlbean.getFormtailhtml();
		int tblcols = htmlbean.getTblcols();
		int tblrows = htmlbean.getTblrows();
		
		//1:새로작성,2:공통양식가져오기,3:사용했던양식가져오기,4:공통양식작성,5:양식수정,6:공통양식수정
		int type = flform.getType();
		int sysdocno = flform.getSysdocno();
		int formseq = flform.getFormseq();
		String formtitle = flform.getFormtitle();
		String formkind = flform.getFormkind();
		String formcomment = flform.getFormcomment();

		//양식정보 테이블에 추가
		FormatBean fbean = new FormatBean();

		fbean.setSysdocno(sysdocno);
		fbean.setFormseq(formseq);
		fbean.setFormtitle(formtitle);
		fbean.setFormkind(formkind);
		fbean.setFormcomment(formcomment);
		fbean.setFormhtml(formhtml);
		fbean.setFormheaderhtml(formheaderhtml);
		fbean.setFormbodyhtml(formbodyhtml);
		fbean.setFormtailhtml(formtailhtml);
		fbean.setTblcols(tblcols);
		fbean.setTblrows(tblrows);
		fbean.setCrtusrid(user_id);
		fbean.setUptusrid(user_id);
		fbean.setDeptcd(flform.getDeptcd());
		
		//양식속성 테이블에 추가
		FormatLineBean flbean = new FormatLineBean();
		
		flbean.setSysdocno(sysdocno);
		flbean.setFormseq(formseq);
		flbean.setTblcols(tblcols);
		flbean.setCellatt(cellatt);
		
		if(type == 1) {
			
			flmgr.addFormatLine(flbean, fbean);	
			
		} else if(type == 4) {
			
			flmgr.addCommFormatLine(flbean, fbean);	
			
		} else if(type == 5) {
			
			flmgr.modifyFormatLine(flbean, fbean);		
			
		} else if(type == 6) {
			
			flmgr.modifyCommFormatLine(flbean, fbean);	
			
		}
		
		flmgr.formatLinePreviewEnd(-1, -1);	//미리보기 데이터 삭제
		
		PrintWriter out = response.getWriter();
		out.write("<script language=javascript>" +
				  "  var opener = window.dialogArguments;" +
				  "  opener.document.forms[0].submit();" +
				  "  window.close();" +
				  "</script>");
		out.close();
		
		return null;
	}
}