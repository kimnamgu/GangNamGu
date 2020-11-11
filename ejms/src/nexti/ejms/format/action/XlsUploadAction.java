/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������Ͼ��ε� ������ action
 * ����:
 */
package nexti.ejms.format.action;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import nexti.ejms.ccd.model.CcdManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.common.rootFrameAction;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.formatBook.form.DataBookForm;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.FileBean;
import nexti.ejms.util.FileManager;
import nexti.ejms.util.Utils;
import nexti.ejms.util.XlsReadPOI;

public class XlsUploadAction extends rootFrameAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		//�������� ��������
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");		//����� ���̵�
		String dept_code = (String)session.getAttribute("dept_code");	//����� �μ��ڵ�
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				user_id = originUserBean.getUser_id();
				dept_code = originUserBean.getDept_id();
			}
		}
		
		UserManager usrManager = UserManager.instance();
		int chrgunitcd = usrManager.getUsrChrgunitcd(user_id);
		
		DataBookForm dbform = (DataBookForm)form;
		FormatManager fmgr = FormatManager.instance();
		
		int sysdocno = dbform.getSysdocno();
		int formseq = dbform.getFormseq();
		String formkind = fmgr.getFormat(sysdocno, formseq).getFormkind();
		FormFile inputFile = dbform.getInputFile();
		ServletContext sContext = getServlet().getServletContext();
		String fileName = null;
		String msg = null;
		StringBuffer result = new StringBuffer();
		
		try {
			String saveDir = appInfo.getTempDir();
			File saveFolder = new File(sContext.getRealPath(saveDir));
			if ( !saveFolder.exists() ) {
				saveFolder.mkdirs();
			}
			
			FileBean filebean = FileManager.doFileUpload(inputFile, sContext, saveDir);
			if ( filebean != null ) {
				fileName = sContext.getRealPath(filebean.getFilenm()); 
			} else {
				msg = "���ε� �� ������ �߻��߽��ϴ�.\\n�����ڿ��� �����ϼ���.";
				return null;
			}
			
			int maxCnt = 0;
			try {
				maxCnt = Integer.parseInt(CcdManager.instance().getCcd_SubName("016", "02"));
			} catch ( Exception e ) {
				maxCnt = 100;
			}
			
			XlsReadPOI poi = new XlsReadPOI(fileName, 0, 0);
			List xlsData = poi.readXls();
			if ( xlsData == null ) {
				msg = "[Excel 97 - 2003 ���� ���� (*.xls)] ���·� ��ȯ�Ͽ�\\n(�ٸ� �̸����� ����) ���ε� �Ͻñ� �ٶ��ϴ�.";
				return null;
				
			} else if ( xlsData.size() > maxCnt + 1 ) {
				msg = "�߸��� �ڷ� �Է¹����� ���� �Է��ڷ��\\n�ѹ��� " + maxCnt + "��(��) ���Ϸ� �ۼ��ٶ��ϴ�.";
				return null;
			}
			
			//�������� ���� ����
			fmgr.xlsUpload(sysdocno, formseq, formkind, xlsData, dept_code, user_id, chrgunitcd); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ( fileName != null && fileName.trim().equals("") == false ) {
				FileManager.doFileDelete(fileName);
			}
			response.setContentType("text/html;charset=euc-kr");
			result.append("<script> \n");
			if ( msg != null ) {
				result.append("alert(\"" + msg + "\"); \n");
			}
			result.append("parent.location.reload(); \n");
			result.append("</script> \n");
			response.getWriter().write(result.toString());
		}

		return null;
	}
}