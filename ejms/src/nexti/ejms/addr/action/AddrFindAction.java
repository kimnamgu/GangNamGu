/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 주소찾기 action
 * 설명:
 */
package nexti.ejms.addr.action;

import java.util.ArrayList;
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

import com.gpki.util.Base64;

import nexti.ejms.addr.form.AddrListForm;
import nexti.ejms.addr.model.AddrBean;
import nexti.ejms.addr.model.AddrManager;
import nexti.ejms.common.appInfo;

public class AddrFindAction extends Action {
	
	private static Logger logger = Logger.getLogger(AddrFindAction.class);
	
	public ActionForward execute (
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		if((session == null) || (session.getAttribute("user_id") == null)){
			if ( appInfo.getAp_address().indexOf(request.getServerName()) != -1 ) {
				logger.debug("Session이 종료되었습니다.");
				return mapping.findForward("login");
			}
		}
		
		//Form에서 넘어온 값 가져오기
		AddrListForm addrForm = (AddrListForm)form;
		
		System.out.println(">>"+addrForm.getProv());
		System.out.println(">>"+addrForm.getCity());
		System.out.println(">>"+addrForm.getStreet());
		System.out.println(">>");
		String sch_addr = "";
		String retid = "";
		
		if(!"".equals(addrForm.getSch_addr())){
			sch_addr = addrForm.getSch_addr().toString();
		}
		
		if(!"".equals(request.getParameter(retid))){
			retid = request.getParameter(retid);
		}
		
		//설문조사 마스터  가져오기
		AddrManager manager = AddrManager.instance();

		List addrList = manager.getAddrList(sch_addr);
		addrForm.setAddrList(addrList);
		
		request.setAttribute("retid", retid);
		
		List prov = manager.getStreetProv();
		List city;
		JSONArray json = new JSONArray();
		JSONObject obj;
		JSONArray arr;
		AddrBean a;
		AddrBean b;
		for(int i=0;i<prov.size();i++){
			a = (AddrBean)prov.get(i);
			arr = new JSONArray();
			obj = new JSONObject();
			city = new ArrayList();
			city = manager.getStreetCity(a);
			for(int j=0;j<city.size();j++){
				b = (AddrBean)city.get(j);
				arr.put(b.getCity());
			}
			obj.put("name",a.getProvince() );
			obj.put("data",arr);
			json.put(obj);
		}
		request.setAttribute("provList",prov);
		request.setAttribute("json",json);
		List addrList2= new ArrayList();
		if(addrForm.getCity()!=null && addrForm.getCity().length()>1){
			AddrBean param = new AddrBean();
			if(addrForm.getProv().length()>0)param.setProvince(new String(Base64.decode(addrForm.getProv()),"UTF-8"));
			if(addrForm.getCity().length()>0)param.setCity(new String(Base64.decode(addrForm.getCity()),"UTF-8"));
			if(addrForm.getStreet().length()>0)param.setStreet(new String(Base64.decode(addrForm.getStreet()),"UTF-8"));
			System.out.println(">>"+param.getProvince());
			System.out.println(">>"+param.getCity());
			System.out.println(">>"+param.getStreet());
			addrList2 = manager.getStreetAddr(param);
		}
		request.setAttribute("addrList2",addrList2);
		request.setAttribute("gbn",request.getParameter("gbn"));
		request.setAttribute("prov",request.getParameter("prov"));
		request.setAttribute("city",request.getParameter("city"));
		request.setAttribute("street",request.getParameter("street"));
		return mapping.findForward("list");
	}
}