/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: GPKI ������ ���� �������� action
 * ����:
 */
package nexti.ejms.login.action;

import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gpki.servlet.GPKIHttpServletRequest;

import nexti.ejms.common.form.LoginForm;

public class CertOKAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception{		
		
		String retUrl = request.getHeader("referer");
		
		try {			
			String subDN = new String();
			GPKIHttpServletRequest gpkirequest= null;
			try { 
				gpkirequest = new GPKIHttpServletRequest(request);
				subDN = gpkirequest.getSignerCert().getSubjectDN();				
			} catch (Exception e) {
				com.dsjdf.jdf.Config dsjdf_config = new com.dsjdf.jdf.Configuration();
				StringBuffer sb=new StringBuffer(1500);
				sb.append(dsjdf_config.get("GPKISecureWeb.errorPage"));
				sb.append("?errmsg=");
				sb.append(URLEncoder.encode(e.getMessage(), "euc-kr"));
				throw e;
			}			
			
			String type = (String)gpkirequest.getParameter("type");
			
			LoginForm loginForm = (LoginForm)form;
			
			if ( type.equals("login") == true ) {
				loginForm.setCert(subDN);
				return mapping.findForward("login");
			} else if ( type.equals("loginRchReq") == true ) {
				loginForm.setRetUrl(retUrl);
				loginForm.setCert(subDN);
				return mapping.findForward("loginRchReq");
			} else if ( type.equals("registration") == true ) {
				response.setContentType("text/html;charset=euc-kr");
				PrintWriter out = response.getWriter();
				out.write("<script language=javascript>");
				out.write("parent.document.forms[0].gpki_id.value='" + subDN + "';");
				out.write("alert('�������� ��ϵǾ����ϴ�');");
				out.write("</script>");
				out.close();
				return null;
			} else {
				throw new Exception("�����뵵�� �������� �ʾҽ��ϴ�");
			}
		} catch (Exception e) {
			//�α��� ����
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.write("<script language=javascript>alert('������ ���� �Ͽ����ϴ�.\\n�����ڿ��� �����ϼ���11.');window.location.href='" + retUrl + "';</script>");
			out.close();
			return null;
		}
	}	
}