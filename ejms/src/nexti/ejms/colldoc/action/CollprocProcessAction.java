/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 진행중인취합문서 기안취소
 * 설명: 취합문서발송전 결재프로세스 추가시 필요
 */
package nexti.ejms.colldoc.action;

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
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.commsubdept.model.commsubdeptManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class CollprocProcessAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		String dept_code = "";
		String user_id = "";
		String retPage = "";
		String formkind = "";
		int sysdocno = 0;
		
		//세션정보 가져오기
		HttpSession session = request.getSession();
		user_id = session.getAttribute("user_id").toString();
		dept_code = session.getAttribute("dept_code").toString();
		
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
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		if(request.getParameter("formkind") != null) {
			formkind = request.getParameter("formkind");
		}
		
		if(request.getParameter("retpage") != null) {
			retPage = request.getParameter("retpage");
		}
		
		commsubdeptManager manager1 = commsubdeptManager.instance();
		String processchk = manager1.getProcessChk(sysdocno, dept_code, user_id, "1", "1");
		
		String msg = "";
		if(processchk.equals("04")||processchk.equals("05")||processchk.equals("06")){
			msg = "문서가 취합부서로 배부되어 기안을 취소할 수 없습니다.";
			
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg", msg));
			saveMessages(request,messages);
			
			if(retPage.equals("collprocdataview") == true) {
				if (formkind.equals("01") == true) {
					retPage = "formatLineView";
				} else if (formkind.equals("02") == true) {
					retPage = "formatFixedView";
				} else if (formkind.equals("03") == true) {
					retPage = "formatBookView";
				}
			}
		}else{
			//기안취소하기 : 문서삭제로 처리
			ColldocManager cdmgr = ColldocManager.instance();
			
			//결재내역을 초기화하고 문서를 작성중(01) 상태로 되돌림
			//cdmgr.collprocProcess(sysdocno, sessionId, tgtdeptcd);
			
			String[] arrSysdocno = {Integer.toString(sysdocno, 10)};
			
			ServletContext context = getServlet().getServletContext();
			
			cdmgr.delColldoc(arrSysdocno, context);
			
			retPage = "list";
		}
		
		request.setAttribute("sysdocno", new Integer(sysdocno));
		
		return mapping.findForward(retPage);
	}
}