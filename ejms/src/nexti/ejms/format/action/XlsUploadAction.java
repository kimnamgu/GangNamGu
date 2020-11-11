/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 엑셀파일업로드 프레임 action
 * 설명:
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
		
		//세션정보 가져오기
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");		//사용자 아이디
		String dept_code = (String)session.getAttribute("dept_code");	//사용자 부서코드
		
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
				msg = "업로드 중 문제가 발생했습니다.\\n관리자에게 문의하세요.";
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
				msg = "[Excel 97 - 2003 통합 문서 (*.xls)] 형태로 변환하여\\n(다른 이름으로 저장) 업로드 하시기 바랍니다.";
				return null;
				
			} else if ( xlsData.size() > maxCnt + 1 ) {
				msg = "잘못된 자료 입력방지를 위해 입력자료는\\n한번에 " + maxCnt + "건(행) 이하로 작성바랍니다.";
				return null;
			}
			
			//엑셀파일 내용 저장
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