/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 조직관리 자료이관 이관대상사용자 목록 action
 * 설명:
 */
package nexti.ejms.dataTransfer.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.dataTransfer.model.DataTransferManager;

public class GetTgtUserListAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {

		int num = 0;
		if ( request.getParameter("num") != null ) {
			num = Integer.parseInt(request.getParameter("num"));
		}
		int len_tgtdeptid = 0;
		if ( request.getParameter("len_tgtdeptid") != null ) {
			len_tgtdeptid = Integer.parseInt(request.getParameter("len_tgtdeptid"));
		}
		
		String listnm = "";
		if ( request.getParameter("listnm") != null ) {
			listnm = (String)request.getParameter("listnm");
		}
		String sch_deptid = "";
		if ( request.getParameter("tgtdept") != null ) {
			sch_deptid = (String)request.getParameter("tgtdept");
		}
		
		DataTransferManager dtmgr = DataTransferManager.instance();
		String selectList = dtmgr.getTgtUserValueList(sch_deptid);
				
		StringBuffer msg = new StringBuffer();
		msg.append("<script> \n");
		msg.append("parent.setTgtDeptList(\"" + listnm+ "\", \"" + selectList + "\", \"" + num + "\" , \"" + len_tgtdeptid + "\"); \n");
		msg.append("</script> \n");
		
		response.setContentType("text/html;charset=euc-kr");
		response.getWriter().write(msg.toString());
		response.getWriter().flush();
		response.getWriter().close();
		response.flushBuffer();		
		return null;
	}
}