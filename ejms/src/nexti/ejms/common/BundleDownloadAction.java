/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����ڷ��� �����ٿ�ε� action
 * ����:
 */
package nexti.ejms.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;	
import org.apache.log4j.Logger;

import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.format.model.InputDeptSearchBoxBean;
import nexti.ejms.formatBook.model.DataBookBean;
import nexti.ejms.formatBook.model.FormatBookManager;
import nexti.ejms.util.commfunction;

import net.sf.jazzlib.ZipEntry;
import net.sf.jazzlib.ZipOutputStream;

public class BundleDownloadAction extends Action {
	
	private static Logger logger = Logger.getLogger(BundleDownloadAction.class);
	
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {
		
		try {
			int sysdocno = -1;
			int formseq = -1;
			String rdb_sort = null;
			boolean isIncludeNotSubmitData = false;
			InputDeptSearchBoxBean idsbbean = new InputDeptSearchBoxBean();
			
			try {
				if (request.getParameter("sysdocno") != null) {
					sysdocno = Integer.parseInt("0" + (String)request.getParameter("sysdocno"), 10);
				}
				if (request.getParameter("formseq") != null) {
					formseq = Integer.parseInt("0" + (String)request.getParameter("formseq"), 10);
				}
				if (request.getParameter("rdb_sort") != null) {
					rdb_sort = (String)request.getParameter("rdb_sort");
				}
				if (request.getParameter("isIncludeNotSubmitData") != null) {
					isIncludeNotSubmitData = Boolean.valueOf((String)request.getParameter("isIncludeNotSubmitData")).booleanValue();				
				}
				if (request.getParameter("sch_deptcd1") != null) {
					idsbbean.setSch_deptcd1((String)request.getParameter("sch_deptcd1"));
				}
				if (request.getParameter("sch_deptcd2") != null) {
					idsbbean.setSch_deptcd2((String)request.getParameter("sch_deptcd2"));
				}
				if (request.getParameter("sch_deptcd3") != null) {
					idsbbean.setSch_deptcd3((String)request.getParameter("sch_deptcd3"));
				}
				if (request.getParameter("sch_deptcd4") != null) {
					idsbbean.setSch_deptcd4((String)request.getParameter("sch_deptcd4"));
				}
				if (request.getParameter("sch_deptcd5") != null) {
					idsbbean.setSch_deptcd5((String)request.getParameter("sch_deptcd5"));
				}
				if (request.getParameter("sch_deptcd6") != null) {
					idsbbean.setSch_deptcd6((String)request.getParameter("sch_deptcd6"));
				}
				if (request.getParameter("sch_chrgunitcd") != null) {
					idsbbean.setSch_chrgunitcd((String)request.getParameter("sch_chrgunitcd"));
				}
				if (request.getParameter("sch_inputusrid") != null) {
					idsbbean.setSch_inputusrid((String)request.getParameter("sch_inputusrid"));
				}
			} catch (Exception e) {
				response.setContentType("text/html;charset=euc-kr");
				response.getWriter().println("<script>alert('�ٿ�ε� �� ������ �߻��߽��ϴ�.\\n�����ڿ��� �����ϼ���(�ڵ�:1)');</script>");
				return null;
			}
			
			if (sysdocno == -1 || formseq == -1 || rdb_sort == null) {
				response.setContentType("text/html;charset=euc-kr");
				response.getWriter().println("<script>alert('�ٿ�ε� �� ������ �߻��߽��ϴ�.\\n�����ڿ��� �����ϼ���(�ڵ�:2)');</script>");
			} else {				
				//��ȸ����
				if (rdb_sort.equals("")) {rdb_sort = "1";}
				
				ColldocManager cmgr = ColldocManager.instance();
				FormatManager fmgr = FormatManager.instance();
				
				String fileName = "[" + cmgr.getColldoc(sysdocno).getDoctitle() + "]" +
								  fmgr.getFormat(sysdocno, formseq).getFormtitle() + ".zip";
				
				response.setContentType("application/zip");
				response.setHeader("Content-Disposition", "attachment; filename=" + commfunction.fileNameFix(fileName.replaceAll(";", ":")));
				response.setHeader("Content-Description", "JSP Generated Data");
				
				FormatBookManager fbmgr = FormatBookManager.instance();
				List dataList = fbmgr.getFormDataList(sysdocno, formseq, idsbbean, rdb_sort, isIncludeNotSubmitData);
				
				ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
				for (int i = 0; i < dataList.size(); i++) {
					String fileorgnm = ((DataBookBean)dataList.get(i)).getOriginfilenm();
					String filenm = getServlet().getServletContext().getRealPath(((DataBookBean)dataList.get(i)).getFilenm());
					
		    		File file = new File(filenm);
		    		if(file.exists() == true) {
		    			BufferedInputStream bis = null;
		    			try {
		    				zos.putNextEntry(new ZipEntry(new DecimalFormat("00_").format(i + 1) + fileorgnm));
		    				bis = new BufferedInputStream(new FileInputStream(filenm));
		    				byte readByte[] = new byte[4096];
		    				int readLength;
		    				while ((readLength = bis.read(readByte, 0, readByte.length)) != -1) {
		    					zos.write(readByte, 0, readLength);
		    				}
		    				zos.closeEntry();
		    			} catch (Exception e) {
		    			} finally {
		    				try {bis.close();} catch (Exception e) {}
		    			}
		    		}
				}
				zos.flush();
				try {zos.close();} catch (Exception e) {}
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
		}
		
		return null;
	}
}