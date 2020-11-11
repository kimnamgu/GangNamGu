/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력완료 제본자료형 입력자료 삭제 action
 * 설명:
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
		//기본 정보 설정
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
		
		//제본형 첨부된 파일 삭제
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
			msg = "데이터 저장 완료";
		} else {
			msg = "데이터 저장 실패";
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