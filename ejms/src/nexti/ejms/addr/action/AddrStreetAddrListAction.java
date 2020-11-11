/**
 * �ۼ���: 2014.07.16
 * �ۼ���: ���� ������
 * ����: �ñ��� ��� action
 * ����: json return
 */
package nexti.ejms.addr.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import nexti.ejms.addr.form.AddrListForm;
import nexti.ejms.addr.model.AddrBean;
import nexti.ejms.addr.model.AddrManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.util.Base64;


public class AddrStreetAddrListAction extends Action {
	
	private static Logger logger = Logger.getLogger(AddrStreetAddrListAction.class);
	
	public ActionForward execute (
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		//Form���� �Ѿ�� �� ��������
		AddrListForm addrForm = (AddrListForm)form;
		
		//�������� ������  ��������
		AddrManager manager = AddrManager.instance();
		
		AddrBean p = new AddrBean();
		p.setProvince(new String(Base64.decode(request.getParameter("prov")),"UTF-8"));
		p.setCity(new String(Base64.decode(request.getParameter("city")),"UTF-8"));
		p.setStreet(new String(Base64.decode(request.getParameter("street")),"UTF-8"));

		
		List addrList = manager.getStreetAddr(p);
		JSONObject json = new JSONObject();
		json.put("json",new JSONArray(addrList));
		response.setContentType("text/html; charset=EUC-KR");
//		response.setCharacterEncoding("EUC-KR");
		response.getWriter().write(parseStringFielter(json.toString())); 
		
		
		return mapping.findForward("json");
	}
	private static String parseStringFielter(String tempValue) {
		tempValue = tempValue.indexOf("\r")!=-1?tempValue.replaceAll("\r", "\\\\r"):tempValue;
		tempValue = tempValue.indexOf("\n")!=-1?tempValue.replaceAll("\n", "\\\\n"):tempValue;	
				
		return tempValue;
	}
}