/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����ں��� ������ ��� action
 * ����:
 */
package nexti.ejms.commsubdept.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.commsubdept.model.commsubdeptManager;

public class commdeptuserListAction extends rootPopupAction{

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		String deptCode = request.getParameter("deptCode");
		String userName = request.getParameter("userName");
		
		if("".equals(deptCode)|| deptCode == null){
			deptCode = "";
		}else{
			deptCode = request.getParameter("deptCode");
		}
		
		if("".equals(userName)|| userName == null){
			userName = "";
		}else{
			userName = request.getParameter("userName");
		}
		
		commsubdeptManager manager = commsubdeptManager.instance();
		
		String listXML = "";
		listXML = manager.getUserList(deptCode, userName);

		response.setContentType("text/xml;charset=UTF-8");
		
		StringBuffer userListXML = new StringBuffer();
		
		StringBuffer prefixXML = new StringBuffer();
		prefixXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		prefixXML.append("\n<root>");

		
		StringBuffer suffixXML = new StringBuffer();
		suffixXML.append("\n</root>");

		userListXML.append(prefixXML.toString());
		userListXML.append(listXML);
		userListXML.append(suffixXML.toString());
		
		PrintWriter out = response.getWriter();
		out.println(userListXML.toString());
		out.flush();
		out.close();
		
		return null;
	}
}