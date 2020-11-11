/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Է¿Ϸ� �����ڷ��� �Է��ڷ� ���� action
 * ����:
 */
package nexti.ejms.formatBook.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.rootAction;
import nexti.ejms.formatBook.model.FormatBookManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.FileManager;
import nexti.ejms.util.Utils;

public class InpCompDataBookDeleteAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//�⺻ ���� ����
		ServletContext context = getServlet().getServletContext();
		HttpSession session = request.getSession();
		String user_id = session.getAttribute("user_id").toString();
		String dept_code = session.getAttribute("dept_code").toString();
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				user_id = originUserBean.getUser_id();
				dept_code = originUserBean.getDept_id();
			}
		}
		
		int sysdocno = 0;
		int formseq = 0;
		int fileseq = 0;
		String filename = "";
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		if(request.getParameter("formseq") != null) {
			formseq = Integer.parseInt(request.getParameter("formseq"));
		}
		
		if(request.getParameter("fileseq") != null) {
			fileseq = Integer.parseInt(request.getParameter("fileseq"));
		}
		
		if(request.getParameter("filename") != null) {
			filename = request.getParameter("filename");
		}
		
		//������ ÷�ε� ���� ����
		FormatBookManager manager = FormatBookManager.instance();
		int retCode = manager.DeleteDataBookFrm(sysdocno, formseq, dept_code, user_id, fileseq);
		
		if(retCode > 0) {
			String delFile = filename;
			if ( delFile != null && delFile.trim().equals("") == false) {
				FileManager.doFileDelete(context.getRealPath(delFile));
			}
		}
		
		String msg = "";
		if(retCode > 0) {
			msg = "������ ���� �Ϸ�";
		} else {
			msg = "������ ���� ����";
		}
		
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg", msg));
		saveMessages(request,messages);
		
		if(request.getParameter("intComp") != null) {
			request.setAttribute("actionPath", "/inpCompFrmBookModifyView.do");
		} else {
			request.setAttribute("actionPath", "/inputFormatBookView.do");
		}
		
		request.setAttribute("sysdocno", new Integer(sysdocno));
		request.setAttribute("formseq", new Integer(formseq));
		request.setAttribute("retpage", new String("booksavesuccess"));
		//request.setAttribute("retpage", new String("inpcompformatbook"));
		
		return mapping.findForward("hiddenframe");
	}
}