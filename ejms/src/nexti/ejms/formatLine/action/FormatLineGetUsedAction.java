/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ۼ� ���߰��� ����ߴ���� �������� action
 * ����:
 */
package nexti.ejms.formatLine.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class FormatLineGetUsedAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response)
	throws Exception {
		
		//�������� ��������
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
		int formseq = fmgr.getNewFormatseq(sysdocno);
		int usedsysdocno = flform.getUsedsysdocno();
		int usedformseq = flform.getUsedformseq();
		String formkind = flform.getFormkind();

		//������� ���̺��� �߰�
		FormatBean fbean = new FormatBean();

		fbean.setSysdocno(sysdocno);
		fbean.setFormseq(formseq);
		fbean.setUsedsysdocno(usedsysdocno);
		fbean.setUsedformseq(usedformseq);
		fbean.setFormkind(formkind);
		
		fmgr.formatGetUsed(fbean, user_id, null, false);
				
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