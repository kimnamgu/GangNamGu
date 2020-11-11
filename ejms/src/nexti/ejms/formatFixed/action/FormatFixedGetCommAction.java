/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식작성 고정양식형 공통양식 가져오기 action
 * 설명:
 */
package nexti.ejms.formatFixed.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.format.model.FormatBean;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.formatLine.form.FormatLineForm;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class FormatFixedGetCommAction extends rootPopupAction {

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
		
		FormatManager fmgr = FormatManager.instance();
		
		FormatLineForm flform = (FormatLineForm)form;
		
		int sysdocno = flform.getSysdocno();
		int commformseq = flform.getCommformseq();
		int formseq = fmgr.getNewFormatseq(sysdocno);
		String formkind = flform.getFormkind();

		//양식정보 테이블에 추가
		FormatBean fbean = new FormatBean();

		fbean.setSysdocno(sysdocno);
		fbean.setFormseq(formseq);
		fbean.setCommformseq(commformseq);
		fbean.setFormkind(formkind);
		
		fmgr.formatGetComm(fbean, user_id, null, null, false);
				
		PrintWriter out = response.getWriter();
		out.write("<script language=javascript>" +
				  "  var opener = window.dialogArguments;" +
				  "  opener.parent.opener = opener.parent.dialogArguments;" +
				  "  opener.parent.opener.document.forms[0].submit();" +
				  "  opener.parent.close();" +
				  "  window.close();" +
				  "</script>");
		out.close();
		
		return null;
	}
}