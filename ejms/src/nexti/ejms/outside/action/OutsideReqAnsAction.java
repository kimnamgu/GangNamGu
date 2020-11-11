/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 외부망 신청서 작성 저장 action
 * 설명:
 */
package nexti.ejms.outside.action;

import java.net.URLEncoder;
import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.appInfo;
import nexti.ejms.outside.model.OutsideManager;
import nexti.ejms.sinchung.form.DataForm;
import nexti.ejms.util.Utils;

public class OutsideReqAnsAction extends Action {
	
	public ActionForward execute(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {		 
	
		try{
			String uid = Utils.nullToEmptyString((String)request.getParameter("uid"));
			
			String msg = "";
			
			DataForm dForm = (DataForm)form;
			
			if ( dForm.getAttachFile() != null && dForm.getAttachFile().getFileSize() - 1024000 > 0 ) {
				response.setContentType("text/html;charset=euc-kr");
				response.getWriter().write("<script>alert('첨부파일은 1MB 이하로 선택하세요');history.back();</script>");
				response.getWriter().close();
			}
			
			ServletContext context = getServlet().getServletContext();

			//저장할 디렉토리 지정
			Calendar cal = Calendar.getInstance();
			String saveDir = appInfo.getRequestDataDir() + cal.get(Calendar.YEAR) + "/";
			
			OutsideManager manager = OutsideManager.instance();
			int result = manager.reqAnsSave(dForm, uid, "", context, saveDir);
			
			if(result>0){
                //강남구청 신청서 상세메시지 출력(JHkim, 14.01.06)
                if ( "강남3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
                    msg = "<br>신청서가 등록 되었습니다.";
                }else{
                    //msg = "<img src=\"/images/request_comp.gif\" alt=\"신청서가 등록 되었습니다\">";
                	  msg = "<br>신청서가 등록 되었습니다.";
                }
				
				Cookie cookie = new Cookie("cookie_ejms_outside_req", URLEncoder.encode("outside_req"+dForm.getReqformno(),"EUC-KR"));
				cookie.setMaxAge(60*60*24*30);    //30일동안 쿠키가 살아 있도록 셋팅
				response.addCookie(cookie);
				
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
				saveMessages(request,messages);
			}
	
			request.setAttribute("result", new Integer(result));
		}catch(Exception e){
			e.printStackTrace();
		}	
		
		return mapping.findForward("hidden");
	}
}