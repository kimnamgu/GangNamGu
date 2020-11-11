/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 신청내역 저장 action
 * 설명:
 */
package nexti.ejms.sinchung.action;

import java.net.URLEncoder;
import java.util.Calendar;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.appInfo;
import nexti.ejms.common.rootAction;
import nexti.ejms.sinchung.form.DataForm;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.util.Utils;

public class DataSaveAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {		 
	
		try{
			//세션가져오기
			HttpSession session = request.getSession();
			String sessi = session.getId().split("[!]")[0];
			String userid = session.getAttribute("user_id").toString();
			
			DataForm dForm = (DataForm)form;
			
			//저장할 디렉토리 지정
			Calendar cal = Calendar.getInstance();
			String saveDir = appInfo.getRequestDataDir() + cal.get(Calendar.YEAR) + "/";
			
			SinchungManager smgr = SinchungManager.instance();
			int result = smgr.saveReqData(dForm, userid, sessi, getServlet().getServletContext(), saveDir);
			
			if ( result > 0 ) {
				String msg = "";
				String script = "";
				int type = Integer.parseInt("0" + Utils.nullToEmptyString((String)request.getParameter("type")), 10);
				
				if ( type == 2 ) {
					int reqseq = dForm.getReqseq();
					if ( "s".equals(dForm.getMode()) ) {
						reqseq = result;
					}
					
					script = "<script>makeDraftDocument(" + dForm.getReqformno() + "," + reqseq + "," + type + ");</script>";
				} else if ( type == 99 ) {
					smgr.procJupsu(Integer.toString(type), dForm.getReqformno(), result);
				}
				
				if ( type == 99 ) {
					msg = "<script>alert('신청사항없음으로 처리되었습니다');window.open('about:blank', '_self').close();</script>";
				} else {
					msg = "<img src=\"/images/request_comp.gif\" alt=\"신청서가 등록 되었습니다\">" + script;
				}
				
				Cookie cookie = new Cookie("cookie_ejms_req", URLEncoder.encode("req"+dForm.getReqformno(),"EUC-KR"));
				cookie.setMaxAge(60*60*24*30);    //30일동안 쿠키가 살아 있도록 셋팅
				response.addCookie(cookie);
				
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
				saveMessages(request,messages);
				
				if ( "부산강서3360000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
					nexti.ejms.util.NewHttpClient nhc =
						new nexti.ejms.util.NewHttpClient(nexti.ejms.common.appInfo.getWeb_address() + "client/jsp/sendSMS.jsp");
					nhc.setMethodType(nexti.ejms.util.NewHttpClient.POSTTYPE);
					if ( "s".equals(dForm.getMode()) ) {
						nhc.setParam("param", dForm.getReqformno() + "," + result + ",INSERT");
					} else {
						nhc.setParam("param", dForm.getReqformno() + "," + dForm.getReqseq() + ",UPDATE");
					}
			        nhc.execute();
				}
			}
	
			request.setAttribute("result", new Integer(result));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return mapping.findForward("hidden");
	}
}