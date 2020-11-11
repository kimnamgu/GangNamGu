/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���߰���,��������� �����ٿ�ε� action
 * ����:
 */
package nexti.ejms.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.log4j.Logger;

import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.util.commfunction;

public class XlsDownloadAction extends Action {
	
	private static Logger logger = Logger.getLogger(XlsDownloadAction.class);
	
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {
		
		try {
			int sysdocno = 0;
			int formseq = 0;
			String formatData = null;
			
			try {
				if(request.getParameter("sysdocno") != null) {
					sysdocno = Integer.parseInt(request.getParameter("sysdocno"), 10);
				}
				
				if(request.getParameter("formseq") != null) {
					formseq = Integer.parseInt(request.getParameter("formseq"), 10);
				}
				
				if(request.getParameter("formatData") != null) {
					formatData = (String)request.getParameter("formatData");
				}
			} catch(Exception e) {
				response.setContentType("text/html;charset=euc-kr");
				response.getWriter().println("<script>alert('�ٿ�ε� �� ������ �߻��߽��ϴ�.\\n�����ڿ��� �����ϼ���(�ڵ�:1)');</script>");
				return null;
			}
			
			if ( formatData == null || (formatData == null && sysdocno == 0 ) ) {
				response.setContentType("text/html;charset=euc-kr");
				response.getWriter().println("<script>alert('�ٿ�ε� �� ������ �߻��߽��ϴ�.\\n�����ڿ��� �����ϼ���(�ڵ�:2)');</script>");
			} else if ( formatData != null ) {
				response.setContentType("application/vnd.ms-excel;charset=euc-kr");
				response.setHeader("Content-Disposition", "attachment; filename=");
				response.setHeader("Content-Description", "JSP Generated Data");
				response.getWriter().println("<meta HTTP-EQUIV=\"Content-Type\" content=\"application/vnd.ms-excel;charset=euc-kr\" charset=\"euc-kr\">");
				response.getWriter().println(formatData);
			} else {
				String fileName = ColldocManager.instance().getColldoc(sysdocno).getDoctitle();
				if ( formseq != 0 ) fileName += "_" + FormatManager.instance().getFormat(sysdocno, formseq).getFormtitle();
				fileName += ".xls";
				
				response.setContentType("application/vnd.ms-excel;charset=euc-kr");
				response.setHeader("Content-Disposition", "attachment; filename=" + commfunction.fileNameFix(fileName.replaceAll(";", ":")));
				response.setHeader("Content-Description", "JSP Generated Data");
				response.getWriter().println("<meta HTTP-EQUIV=\"Content-Type\" content=\"application/vnd.ms-excel;charset=euc-kr\" charset=\"euc-kr\">");
				response.getWriter().println(formatData);
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
		}
		
		return null;
	}
}