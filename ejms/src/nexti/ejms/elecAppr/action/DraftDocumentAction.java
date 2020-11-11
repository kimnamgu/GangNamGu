/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 전자결재 기안문작성 action
 * 설명:
 */
package nexti.ejms.elecAppr.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.commtreat.model.CommTreatManager;
import nexti.ejms.colldoc.model.ColldocBean;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.elecAppr.model.ElecApprManager;
import nexti.ejms.format.form.FormatForm;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.format.model.InputDeptSearchBoxBean;
import nexti.ejms.sinchung.model.FrmBean;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class DraftDocumentAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		HttpSession session = request.getSession();
		String dept_code = Utils.nullToEmptyString((String)session.getAttribute("dept_code"));
		String user_id = Utils.nullToEmptyString((String)session.getAttribute("user_id"));
		
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
		
		ElecApprManager eamgr = ElecApprManager.instance();
		UserManager umgr = UserManager.instance();

		FormatForm fform = (FormatForm)form;
		
		int type = fform.getType();
		int reqformno = fform.getReqformno();
		int reqseq = fform.getReqseq();
		int sysdocno = fform.getSysdocno();
		
		InputDeptSearchBoxBean idsbbean = new InputDeptSearchBoxBean();
		
		if (type != 2) {
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
			
			ColldocManager cmgr = ColldocManager.instance();
			ColldocBean cbean = cmgr.getColldoc(sysdocno);
			
			if (cbean != null) {
				fform.setFormtitle(cbean.getDoctitle());
			} else {
				fform.setFormtitle("제목없음");
			}		
			
		} else {

			SinchungManager smgr = SinchungManager.instance();
			FrmBean fbean = smgr.reqFormInfo(reqformno);
			
			if (fbean != null) {
				fform.setFormtitle(fbean.getTitle());
			} else {
				fform.setFormtitle("제목없음");
			}
			
			sysdocno = reqformno;			
		}
		
		fform.setFormhtml(eamgr.makeElecApprAttachFile(sysdocno, reqseq, idsbbean, type, getServlet().getServletContext()));
		fform.setEa_id(umgr.getUserInfo(user_id).getEa_id());

		return mapping.findForward("draftDocument");
	}
}