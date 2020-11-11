/**
 * 작성일: 2014.07.16
 * 작성자: 과장 장지운
 * 모듈명: 시군구 목록 action
 * 설명: json return
 */
package nexti.ejms.addr.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nexti.ejms.addr.form.AddrListForm;
import nexti.ejms.addr.model.AddrBean;
import nexti.ejms.addr.model.AddrManager;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.gpki.util.Base64;




public class AddrStreetCityListAction extends Action {
	
	private static Logger logger = Logger.getLogger(AddrStreetCityListAction.class);
	
	public ActionForward execute (
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		logger.info("------>> receive ok");
		
		
		//Form에서 넘어온 값 가져오기
		AddrListForm addrForm = (AddrListForm)form;
		
		//설문조사 마스터  가져오기
		AddrManager manager = AddrManager.instance();
		
		AddrBean p = new AddrBean();
		p.setProvince(new String(Base64.decode(request.getParameter("prov")),"UTF-8"));
		List addrList = manager.getStreetCity(p);
		JSONObject json = new JSONObject();
		json.put("json",new JSONArray(addrList));
		//System.out.println(p.getProvince());
		response.setContentType("text/html; charset=EUC-KR");
//		response.setCharacterEncoding("EUC-KR");
		response.getWriter().write(parseStringFielter(json.toString())); 
		
		
		return mapping.findForward("json");
	}
//	public static void jsonResponse(HttpServletResponse response, String result, String message) throws Exception {
//		
//		JSONObject jobj = new JSONObject();
//		jobj.put("result", result);  
//		jobj.put("message", message);
//		String strAll = jobj.toString();
//		strAll = parseStringFielter(strAll);
//		
//		response.setContentType("text/html; charset=UTF-8");
//		response.setCharacterEncoding("utf-8");
//		response.getWriter().write(strAll); 
//	}
	private static String parseStringFielter(String tempValue) {
		tempValue = tempValue.indexOf("\r")!=-1?tempValue.replaceAll("\r", "\\\\r"):tempValue;
		tempValue = tempValue.indexOf("\n")!=-1?tempValue.replaceAll("\n", "\\\\n"):tempValue;	
				
		return tempValue;
	}
}