/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 신청취소 action
 * 설명:
 */
package nexti.ejms.sinchung.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.sinchung.model.SinchungManager;

public class CancelAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {		 
		
		//세션정보가져오기
		HttpSession session = request.getSession();		
		String userid = session.getAttribute("user_id").toString();
		
		int reqformno = Integer.parseInt(request.getParameter("reqformno"));
		int reqseq = Integer.parseInt(request.getParameter("reqseq"));
		int gubun = Integer.parseInt(request.getParameter("gubun"));
		
		//삭제
		SinchungManager smgr = SinchungManager.instance();
		int result = smgr.cancelSinchung(reqformno, reqseq, gubun, userid, getServlet().getServletContext());
			
		if(result > 0){
			if ( "부산강서3360000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
				nexti.ejms.util.NewHttpClient nhc =
					new nexti.ejms.util.NewHttpClient(nexti.ejms.common.appInfo.getWeb_address() + "client/jsp/sendSMS.jsp");
				nhc.setMethodType(nexti.ejms.util.NewHttpClient.POSTTYPE);
		        nhc.setParam("param", reqformno + "," + reqseq + ",DELETE"); 
		        nhc.execute();
			}
	        
			PrintWriter out = response.getWriter();
			out.write("<script language='javascript'>");
			out.write("	 opener.location.href='/myList.do';"); 
			out.write("	 window.close();");
			out.write("</script>");	
			out.close();
			return null;
		} else {
			throw new Exception();
		}	
	}
}