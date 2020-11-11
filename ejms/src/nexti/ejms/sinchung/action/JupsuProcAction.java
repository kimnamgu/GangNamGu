/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 접수,보류처리 action
 * 설명:
 */
package nexti.ejms.sinchung.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.sinchung.model.SinchungManager;

public class JupsuProcAction extends rootPopupAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {		 
		
		//해당 신청서 담당자만 처리할수 있도록 처리할 것
		
		String gbn = request.getParameter("gbn");  //처리구분: 일괄접수처리토글(all), 접수완료(1), 접수보류(2)
		int reqformno = Integer.parseInt(request.getParameter("reqformno"));
		String reqseq = (String)request.getParameter("reqseq");
		
		SinchungManager smgr = SinchungManager.instance();
		int result = smgr.procJupsu(gbn, reqformno, reqseq);
		
		if(result > 0){
			PrintWriter out = response.getWriter();
			if ( gbn.equals("all") || gbn.equals("select") ) {
				out.write("<script language='javascript'>");
				out.write("	parent.endProc();");
				out.write(" parent.document.forms[0].submit(); ");
				out.write("</script>");
			} else {
				out.write("<script language='javascript'> \n");
				out.write("	try { \n");
				out.write("	 var opener = window.dialogArguments; \n");
				out.write("  opener.opener.document.forms[0].submit(); \n");
				out.write("  opener.document.forms[0].submit(); \n");
				out.write("	 opener.endProc(); \n");
				out.write("	 window.close(); \n");
				out.write("	} catch (e) { \n");
				out.write("  parent.opener.opener.document.forms[0].submit(); \n");
				out.write("  parent.opener.document.forms[0].submit(); \n");
				out.write("	 parent.opener.endProc(); \n");
				out.write("	 window.parent.close(); \n");
				out.write("	} \n");
				out.write("</script> \n");
			}
			out.close();
			return null;	
		} else {
			throw new Exception();
		}				
	}
}