/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 다운로드 action
 * 설명:
 */
package nexti.ejms.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.log4j.Logger;

import nexti.ejms.util.commfunction;

public class DownloadAction extends Action {
	
	private static Logger logger = Logger.getLogger(DownloadAction.class);
	
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {
		
		HttpSession session = request.getSession();
		
		if((session == null) || (session.getAttribute("user_id") == null)){
			if ( appInfo.getAp_address().indexOf(request.getServerName()) != -1 ) {
				logger.debug("Session이 종료되었습니다.");
				return mapping.findForward("login");
			}
		}

		String tempFileName = request.getParameter("tempFileName");
		String fileName = request.getParameter("fileName");
		ServletOutputStream servletoutputstream = null;

		try {
			String filePath = getServlet().getServletContext().getRealPath(tempFileName);
			File tempFile = new File(filePath);

			fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
			
			response.setContentType("text/html;charset=euc-kr");
			try {
				if (!tempFile.exists() || !tempFile.canRead()) {
					PrintWriter out = response.getWriter();
					out.println("<script>alert('파일을 찾을 수 없습니다');</script>");
					out.close();
					return null;
				}
			} catch (Exception e) {
				PrintWriter out = response.getWriter();
				out.println("<script>alert('파일을 찾을 수 없습니다');</script>");
				out.close();
				return null;
			}

			response.setContentType("application/unknown;charset=euc-kr");
			response.setHeader(
				"Content-disposition",
				"attachment;filename=" + commfunction.fileNameFix(fileName.replaceAll(";", ":")));
			servletoutputstream = response.getOutputStream();
			dumpFile(tempFile, servletoutputstream);
			servletoutputstream.flush();

		} catch (Exception e) {
			PrintWriter out = response.getWriter();
			out.println("<script>alert('파일을 찾을 수 없습니다');</script>");
			out.close();
			return null;
		} finally {
			try { servletoutputstream.close(); } catch (Exception ex) {}
		}

		return null;
	}

	private void dumpFile(File realFile, OutputStream outputstream) {
		byte readByte[] = new byte[4096];
		BufferedInputStream bufferedinputstream = null;
		try {
			bufferedinputstream = new BufferedInputStream(new FileInputStream(realFile));
			int i;
			while ((i = bufferedinputstream.read(readByte, 0, 1024)) != -1) {
				outputstream.write(readByte, 0, i);
			}
		} catch (Exception ex) {
		} finally {
			try { bufferedinputstream.close(); } catch (Exception e) {}
		}
	}
}