/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력하기 제본자료형 입력자료 전체삭제 action
 * 설명:
 */
package nexti.ejms.formatBook.action;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.colldoc.model.ColldocBean;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.common.rootAction;
import nexti.ejms.commtreat.model.CommTreatManager;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.format.model.InputDeptSearchBoxBean;
import nexti.ejms.formatBook.model.DataBookBean;
import nexti.ejms.formatBook.model.FormatBookManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.FileManager;
import nexti.ejms.util.Utils;

public class InputBookDataDelAction extends rootAction {

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
		
		//시스템문서번호와 양식일련번호 얻어오기 - submit시 Parameter로 넘어옴
		int sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		int formseq = Integer.parseInt(request.getParameter("formseq"));
		
		InputDeptSearchBoxBean idsbbean = new InputDeptSearchBoxBean();
		String[][] deptInfo = FormatManager.instance().getInputDeptInfo(sysdocno, dept_code);
		int depth = CommTreatManager.instance().getTargetDeptLevel(sysdocno, dept_code);
		int idx = 0;
		if ( depth < 1 ) {
			ColldocBean cdbean = ColldocManager.instance().getColldoc(sysdocno);
			user_id = cdbean.getChrgusrcd();
			dept_code = cdbean.getColdeptcd();
			deptInfo = FormatManager.instance().getInputDeptInfo(sysdocno, dept_code);
			depth = CommTreatManager.instance().getTargetDeptLevel(sysdocno, dept_code);
		}
		if ( depth > idx ) idsbbean.setSch_deptcd1(deptInfo[idx++][0]);
		if ( depth > idx ) idsbbean.setSch_deptcd2(deptInfo[idx++][0]);
		if ( depth > idx ) idsbbean.setSch_deptcd3(deptInfo[idx++][0]);
		if ( depth > idx ) idsbbean.setSch_deptcd4(deptInfo[idx++][0]);
		if ( depth > idx ) idsbbean.setSch_deptcd5(deptInfo[idx++][0]);
		if ( depth > idx ) idsbbean.setSch_deptcd6(deptInfo[idx++][0]);
		idsbbean.setSch_inputusrid(user_id);

		FormatBookManager manager = FormatBookManager.instance();
		
		List dataList = manager.getDataBookFrm(sysdocno, formseq, idsbbean);
		int retCode = 0;
		for(int i = 0; i < dataList.size(); i++) {
			DataBookBean dbbean = (DataBookBean)dataList.get(i); 
			String filenm = dbbean.getFilenm();
			int fileseq = dbbean.getFileseq();
			
			int ret = manager.DeleteDataBookFrm(sysdocno, formseq, dept_code, user_id, fileseq);
			
			if(ret > 0) {
				String delFile = filenm;
				if ( delFile != null && delFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delFile));
				}
			}
			
			retCode += ret;
		}
		
		String msg = "";
		if(retCode <= 0) {
			msg = "데이터 삭제 실패";			
		}
		
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg", msg));
		saveMessages(request,messages);
		
		request.setAttribute("sysdocno", new Integer(sysdocno));
		request.setAttribute("formseq", new Integer(formseq));
		request.setAttribute("retpage", new String("inputformatbook"));
		
		return mapping.findForward("hiddenframe");
	}
}