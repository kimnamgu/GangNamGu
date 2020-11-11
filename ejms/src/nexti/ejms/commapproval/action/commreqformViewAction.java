/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� ���缱���� ���� action
 * ����:
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
		
		//�������� ��������
		HttpSession session = request.getSession();	
		String deptcd = (String)session.getAttribute("dept_code");
		
		commapprovalManager manager = commapprovalManager.instance();
		
		String designateXML = "";
		
		try {
			designateXML = manager.getReqformView(reqformno, reqseq, deptcd);
		} catch(Exception e) {
			designateXML = "<error>���� Server�� ������ �߻��Ѱ� �����ϴ�.\n" +
					  	   "����� �ٽ� �õ� �Ͻðų� �����ڿ��� ������ �ֽʽÿ�. �ٷ� �ذ��� �帮�ڽ��ϴ�.\n" +
					  	   "������ �߻��� �������� �ּ��� ���Ͽ� �ٷ� ������ġ �ϰڽ��ϴ�. �����մϴ�.</error>";
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