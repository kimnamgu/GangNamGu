/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ܺθ� ��û�� ����Ʈ action
 * ����:
 */
package nexti.ejms.outside.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.outside.model.OutsideManager;
import nexti.ejms.util.Utils;

public class OutsideReqListAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		try{
			String syear     = Utils.nullToEmptyString((String)request.getParameter("syear"));
			String uid = Utils.nullToEmptyString((String)request.getParameter("uid"));
			String resident = Utils.nullToEmptyString((String)request.getParameter("resident"));
			String committee = Utils.nullToEmptyString((String)request.getParameter("committee"));
			
			OutsideManager manager = OutsideManager.instance();
			
			String resultXML = "";

			resultXML = manager.getReqList(syear, uid, resident, committee);
			
			response.setContentType("text/xml;charset=UTF-8");
			
			StringBuffer listXML = new StringBuffer();
			
			StringBuffer prefixXML = new StringBuffer();
			prefixXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			prefixXML.append("\n<root>");

			StringBuffer suffixXML = new StringBuffer();
			suffixXML.append("\n</root>");

			listXML.append(prefixXML.toString());
			listXML.append(resultXML);
			listXML.append(suffixXML.toString());
			
			PrintWriter out = response.getWriter();
			out.println(listXML.toString());
			out.flush();
			out.close();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return null;
	}
}