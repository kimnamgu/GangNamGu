/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� ������û���������� ���� action
 * ����:
 */
package nexti.ejms.sinchung.action;

import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.appInfo;
import nexti.ejms.common.rootPopupAction;
import nexti.ejms.sinchung.model.SinchungManager;

public class SelectUsedAction extends rootPopupAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {		 
		
		HttpSession session = request.getSession();
		String sessi = session.getId().split("[!]")[0];
		int reqformno = Integer.parseInt(request.getParameter("reqformno"));
		
		ServletContext context = getServlet().getServletContext();

		//������ ���丮 ����
		Calendar cal = Calendar.getInstance();
		String saveDir = appInfo.getRequestSampleDir() + cal.get(Calendar.YEAR) + "/";
			
		SinchungManager smgr = SinchungManager.instance();
		smgr.deleteTempAll(sessi, getServlet().getServletContext());  //�ӽ����̺� ����
		int result = smgr.selectUsed(reqformno, sessi, context, saveDir);
		
		if(result > 0){
			PrintWriter out = response.getWriter();
			out.write("<script language='javascript'>");
			out.write("	var opener = window.dialogArguments;");
			out.write("	opener.location.href='/formView.do';"); 
			out.write("	window.close();");
			out.write("</script>");
			out.close();
			return null;	
		} else {
			throw new Exception();
		}		
	}
}