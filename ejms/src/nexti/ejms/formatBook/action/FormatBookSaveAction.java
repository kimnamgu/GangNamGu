/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식작성 제본자료형 저장 action
 * 설명:
 */
package nexti.ejms.formatBook.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.format.model.FormatBean;
import nexti.ejms.formatBook.form.FormatBookForm;
import nexti.ejms.formatBook.model.FormatBookBean;
import nexti.ejms.formatBook.model.FormatBookManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;


public class FormatBookSaveAction extends rootPopupAction {

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
		
		FormatBookManager fbmgr = FormatBookManager.instance();
		FormatBookForm fbform = (FormatBookForm)form;
		
		//1:새로작성,2:공통양식가져오기,3:사용했던양식가져오기,4:공통양식작성,5:양식수정,6:공통양식수정
		int type = fbform.getType();
		int sysdocno = fbform.getSysdocno();
		int formseq = fbform.getFormseq();
		
		FormatBean fbean = new FormatBean();
		
		//양식정보 테이블에 추가
		fbean.setSysdocno(sysdocno);
		fbean.setFormseq(formseq);
		fbean.setFormtitle(fbform.getFormtitle());
		fbean.setFormkind(fbform.getFormkind());
		fbean.setFormcomment(fbform.getFormcomment());
		fbean.setCrtusrid(user_id);
		fbean.setUptusrid(user_id);
		fbean.setDeptcd(fbform.getDeptcd());
		
		FormatBookBean ffbean = new FormatBookBean();
		
		//양식속성 테이블에 추가
		ffbean.setSysdocno(sysdocno);
		ffbean.setFormseq(formseq);
		ffbean.setListcategorynm1(fbform.getListcategorynm1());
		
		if(type == 1) {
				
			fbmgr.addFormatBook(ffbean, fbean);
			
		} else if(type == 4) {
			
			fbmgr.addCommFormatBook(ffbean, fbean);	

		} else if(type == 5) {
				
			fbmgr.modifyFormatBook(ffbean, fbean);
			
		} else if(type == 6) {
			
			fbmgr.modifyCommFormatBook(ffbean, fbean);	

		}
		
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