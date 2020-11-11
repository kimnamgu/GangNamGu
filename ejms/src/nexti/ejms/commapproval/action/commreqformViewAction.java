/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 결재선지정 보기 action
 * 설명:
 */
package nexti.ejms.commapproval.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.commapproval.model.commapprovalManager;
import nexti.ejms.common.rootPopupAction;

public class commreqformViewAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		int reqformno = 0;
		int reqseq = 0;
		
		if(request.getParameter("reqformno") != null) {
			reqformno = Integer.parseInt(request.getParameter("reqformno"));
		}
		
		if(request.getParameter("reqseq") != null) {
			reqseq = Integer.parseInt(request.getParameter("reqseq"));
		}
		
		//세션정보 가져오기
		HttpSession session = request.getSession();	
		String deptcd = (String)session.getAttribute("dept_code");
		
		commapprovalManager manager = commapprovalManager.instance();
		
		String designateXML = "";
		
		try {
			designateXML = manager.getReqformView(reqformno, reqseq, deptcd);
		} catch(Exception e) {
			designateXML = "<error>내부 Server에 에러가 발생한것 같습니다.\n" +
					  	   "잠시후 다시 시도 하시거나 관리자에게 연락을 주십시오. 바로 해결해 드리겠습니다.\n" +
					  	   "에러가 발생한 페이지는 최선을 다하여 바로 수정조치 하겠습니다. 감사합니다.</error>";
		} finally {
			StringBuffer userGListXML = new StringBuffer();
			
			StringBuffer prefixXML = new StringBuffer();
			prefixXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			prefixXML.append("\n<root>");

			
			StringBuffer suffixXML = new StringBuffer();
			suffixXML.append("\n</root>");

			userGListXML.append(prefixXML.toString());
			userGListXML.append(designateXML);
			userGListXML.append(suffixXML.toString());

			response.setContentType("text/xml;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println(userGListXML.toString());
			out.flush();
			out.close();
		}
		
		return null;

	}
}