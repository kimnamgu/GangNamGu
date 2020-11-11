/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� ����,����ó�� action
 * ����:
 */
package nexti.ejms.sinchung.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.sinchung.model.SinchungManager;

public class JupsuProcAction extends rootPopupAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {		 
		
		//�ش� ��û�� ����ڸ� ó���Ҽ� �ֵ��� ó���� ��
		
		String gbn = request.getParameter("gbn");  //ó������: �ϰ�����ó�����(all), �����Ϸ�(1), ��������(2)
		int reqformno = Integer.parseInt(request.getParameter("reqformno"));
		String reqseq = (String)request.getParameter("reqseq");
		
		SinchungManager smgr = SinchungManager.instance();
		int result = smgr.procJupsu(gbn, reqformno, reqseq);
		
		if(result > 0){
			PrintWriter out = response.getWriter();
			if ( gbn.equals("all") || gbn.equals("select") ) {
				out.write("<script language='javascript'>");
				out.write("	parent.endProc();");
				out.write(" parent.document.forms[0].submit(); ");
				out.write("</script>");
			} else {
				out.write("<script language='javascript'> \n");
				out.write("	try { \n");
				out.write("	 var opener = window.dialogArguments; \n");
				out.write("  opener.opener.document.forms[0].submit(); \n");
				out.write("  opener.document.forms[0].submit(); \n");
				out.write("	 opener.endProc(); \n");
				out.write("	 window.close(); \n");
				out.write("	} catch (e) { \n");
				out.write("  parent.opener.opener.document.forms[0].submit(); \n");
				out.write("  parent.opener.document.forms[0].submit(); \n");
				out.write("	 parent.opener.endProc(); \n");
				out.write("	 window.parent.close(); \n");
				out.write("	} \n");
				out.write("</script> \n");
			}
			out.close();
			return null;	
		} else {
			throw new Exception();
		}				
	}
}