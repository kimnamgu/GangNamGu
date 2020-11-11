/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���߰���,��������� �ѱ۴ٿ�ε� action
 * ����:
 */
package nexti.ejms.common;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.log4j.Logger;

import nexti.ejms.util.FileBean;
import nexti.ejms.util.FileManager;

public class HwpDownloadAction extends Action {
	
	private static Logger logger = Logger.getLogger(HwpDownloadAction.class);
	
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {
			
		if(request.getParameter("deletefile") != null) {
			String delFile = (String)request.getParameter("deletefile");
			if ( delFile != null && delFile.trim().equals("") == false) {
				FileManager.doFileDelete(getServlet().getServletContext().getRealPath(delFile));
			}
			return null;
		}
		
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
					formatData = (String)request.getParameter("formatData").replaceAll("(?i)bgcolor", "bgcolorz");
				}
			} catch(Exception e) {
				response.setContentType("text/html;charset=euc-kr");
				response.getWriter().println("<script>alert('�ٿ�ε� �� ������ �߻��߽��ϴ�.\\n�����ڿ��� �����ϼ���(�ڵ�:1)');</script>");
				return null;
			}
			
			if( formatData == null || sysdocno == 0 || formseq == 0) {
				response.setContentType("text/html;charset=euc-kr");
				response.getWriter().println("<script>alert('�ٿ�ε� �� ������ �߻��߽��ϴ�.\\n�����ڿ��� �����ϼ���(�ڵ�:2)');</script>");
			} else {	
				String saveDir = appInfo.getTempDir();
				File saveFolder = new File(getServlet().getServletContext().getRealPath(saveDir));
				if(!saveFolder.exists()) {
					saveFolder.mkdirs();
				}
				
				String saveExt = "html";
				FileBean fileBean = FileManager.doAttachFileMake(formatData, saveExt, saveDir, getServlet().getServletContext(), "CONTENT");
				if (fileBean != null) {
					request.setAttribute("filename", saveDir + fileBean.getFilenm() + "." + saveExt);
					return mapping.findForward("formatToHwp");
				} else {
					String delFile = saveDir + fileBean.getFilenm() + "." + saveExt;
					if ( delFile != null && delFile.trim().equals("") == false) {
						FileManager.doFileDelete(getServlet().getServletContext().getRealPath(delFile));
					}
					response.setContentType("text/html;charset=euc-kr");
					response.getWriter().println("<script>alert('�ٿ�ε� �� ������ �߻��߽��ϴ�.\\n�����ڿ��� �����ϼ���(�ڵ�:2)');</script>");
				}
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
		}
		
		return null;
	}
}