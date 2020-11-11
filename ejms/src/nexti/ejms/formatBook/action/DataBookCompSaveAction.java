/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합완료 제본자료형 최종자료 등록 action
 * 설명:
 */
package nexti.ejms.formatBook.action;

import java.io.File;
import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.appInfo;
import nexti.ejms.common.rootAction;
import nexti.ejms.dept.model.DeptManager;
import nexti.ejms.formatBook.form.DataBookForm;
import nexti.ejms.formatBook.model.DataBookBean;
import nexti.ejms.formatBook.model.FormatBookManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.FileBean;
import nexti.ejms.util.FileManager;
import nexti.ejms.util.Utils;

public class DataBookCompSaveAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		//기본 정보 설정
		ServletContext context = getServlet().getServletContext();
		HttpSession session = request.getSession();
		String user_id = session.getAttribute("user_id").toString();
		String dept_code = session.getAttribute("dept_code").toString();
		int chrgunitcd = Integer.parseInt(session.getAttribute("chrg_code").toString());
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				user_id = originUserBean.getUser_id();
				dept_code = originUserBean.getDept_id();
				chrgunitcd = DeptManager.instance().getChrgunitcd(user_id);
			}
		}
		
		//Form에서 넘어온 값 가져오기 
		DataBookForm dataBookForm = (DataBookForm)form;
		
		int sysdocno = 0;
		int formseq = 0;
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		if(request.getParameter("formseq") != null) {
			formseq = Integer.parseInt(request.getParameter("formseq"));
		}
		
		//저장할 디렉토리 지정
		Calendar cal = Calendar.getInstance();
		String saveDir = appInfo.getBookFrmCmptDir() + cal.get(Calendar.YEAR) + "/";
		
		File saveFolder = new File(context.getRealPath(saveDir));
		
		if(!saveFolder.exists()) {
			saveFolder.mkdirs();
		}
		
		//파일 저장 FileBean
		FileBean fileBean = new FileBean();
		
		fileBean = FileManager.doFileUpload(dataBookForm.getInputFile(), context, saveDir);
		
		DataBookBean dataBean = new DataBookBean();
		
		//Form 데이터를 Bean으로 복사
		BeanUtils.copyProperties(dataBean, dataBookForm);
		dataBean.setSysdocno(sysdocno);
		dataBean.setFormseq(formseq);
		dataBean.setTgtdeptcd(dept_code);
		dataBean.setInputusrid(user_id);
		dataBean.setChrgunitcd(chrgunitcd);
		
		FormatBookManager manager = FormatBookManager.instance();
		int retCode = manager.DataBookCompFrm(dataBean, fileBean);
		
		request.setAttribute("savecheck", new Integer(retCode));
		request.setAttribute("sysdocno", new Integer(sysdocno));
		request.setAttribute("formseq", new Integer(formseq));
		
		return mapping.findForward("hiddenframe");
	}
}