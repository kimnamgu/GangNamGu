/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ܺθ� ��û�� �ۼ� ���� action
 * ����:
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
				response.getWriter().write("<script>alert('÷�������� 1MB ���Ϸ� �����ϼ���');history.back();</script>");
				response.getWriter().close();
			}
			
			ServletContext context = getServlet().getServletContext();

			//������ ���丮 ����
			Calendar cal = Calendar.getInstance();
			String saveDir = appInfo.getRequestDataDir() + cal.get(Calendar.YEAR) + "/";
			
			OutsideManager manager = OutsideManager.instance();
			int result = manager.reqAnsSave(dForm, uid, "", context, saveDir);
			
			if(result>0){
                //������û ��û�� �󼼸޽��� ���(JHkim, 14.01.06)
                if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
                    msg = "<br>��û���� ��� �Ǿ����ϴ�.";
                }else{
                    //msg = "<img src=\"/images/request_comp.gif\" alt=\"��û���� ��� �Ǿ����ϴ�\">";
                	  msg = "<br>��û���� ��� �Ǿ����ϴ�.";
                }
				
				Cookie cookie = new Cookie("cookie_ejms_outside_req", URLEncoder.encode("outside_req"+dForm.getReqformno(),"EUC-KR"));
				cookie.setMaxAge(60*60*24*30);    //30�ϵ��� ��Ű�� ��� �ֵ��� ����
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