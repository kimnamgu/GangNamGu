/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ۼ� �����ڷ��� ������ �������� action
 * ����:
 */
package nexti.ejms.formatBook.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.appInfo;
import nexti.ejms.common.rootPopupAction;
import nexti.ejms.format.model.FormatBean;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.formatBook.form.FormatBookForm;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class FormatBookGetCommAction extends rootPopupAction {

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
		
		FormatBookForm fbform = (FormatBookForm)form;
		
		int sysdocno = fbform.getSysdocno();
		int commformseq = fbform.getCommformseq();
		int formseq = fmgr.getNewFormatseq(sysdocno);
		String formkind = fbform.getFormkind();

		//������� ���̺� �߰�
		FormatBean fbean = new FormatBean();

		fbean.setSysdocno(sysdocno);
		fbean.setFormseq(formseq);
		fbean.setCommformseq(commformseq);
		fbean.setFormkind(formkind);
		
		//������ ���丮 ����
		Calendar cal = Calendar.getInstance();
		String saveDir = appInfo.getBookFrmSampleDir() + cal.get(Calendar.YEAR) + "/";
		
		File saveFolder = new File(getServlet().getServletContext().getRealPath(saveDir));
		
		if(!saveFolder.exists()) {
			saveFolder.mkdirs();
		}
		
		String formatfileDir = getServlet().getServletContext().getRealPath("");
		
		boolean force = false;
		if(request.getParameter("force") != null) {
			force = true;
		}
		fmgr.formatGetComm(fbean, user_id, formatfileDir, saveDir, force);
		
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