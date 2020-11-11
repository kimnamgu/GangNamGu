/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부완료 고정양식형 보기 action
 * 설명:
 */
package nexti.ejms.formatFixed.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.colldoc.model.ColldocBean;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.common.rootAction;
import nexti.ejms.commtreat.model.CommTreatManager;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.format.model.InputDeptSearchBoxBean;
import nexti.ejms.formatFixed.form.FormatFixedForm;
import nexti.ejms.formatFixed.model.FormatFixedBean;
import nexti.ejms.formatFixed.model.FormatFixedManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class DeliCompFormatFixedViewAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//Form에서 넘어온 값 가져오기 
		FormatFixedForm formatFixedForm = (FormatFixedForm)form;
		
		//세션정보 가져오기
		HttpSession session = request.getSession();
		String user_id = session.getAttribute("user_id").toString();
		String dept_code = session.getAttribute("dept_code").toString();
		String dept_name = session.getAttribute("dept_name").toString();
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				user_id = originUserBean.getUser_id();
				dept_code = originUserBean.getDept_id();
				dept_name = UserManager.instance().getDeptName(user_id);
			}
		}
		
		int sysdocno = 0;
		int formseq = 0;
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		if(request.getParameter("formseq") != null) {
			formseq = Integer.parseInt(request.getParameter("formseq"));
		}
		
		InputDeptSearchBoxBean idsbbean = new InputDeptSearchBoxBean();
		BeanUtils.copyProperties(idsbbean, formatFixedForm);
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
		
		//고정양식형 데이터 가져오기
		FormatFixedManager manager = FormatFixedManager.instance();
		FormatFixedBean fixedBean = manager.getFormatFixedDataView(sysdocno, formseq, idsbbean, dept_name, false, true);
		
		idsbbean = FormatManager.instance().getInputDeptInfoAll(sysdocno, user_id, dept_code, idsbbean, "02");
		fixedBean.setSch_deptcd1(idsbbean.getSch_deptcd1());
		fixedBean.setSch_deptcd2(idsbbean.getSch_deptcd2());
		fixedBean.setSch_deptcd3(idsbbean.getSch_deptcd3());
		fixedBean.setSch_deptcd4(idsbbean.getSch_deptcd4());
		fixedBean.setSch_deptcd5(idsbbean.getSch_deptcd5());
		fixedBean.setSch_deptcd6(idsbbean.getSch_deptcd6());
		fixedBean.setSch_chrgunitcd(idsbbean.getSch_chrgunitcd());
		fixedBean.setSch_inputusrid(idsbbean.getSch_inputusrid());
		fixedBean.setSch_deptcd1_collection(idsbbean.getSch_deptcd1_collection());
		fixedBean.setSch_deptcd2_collection(idsbbean.getSch_deptcd2_collection());
		fixedBean.setSch_deptcd3_collection(idsbbean.getSch_deptcd3_collection());
		fixedBean.setSch_deptcd4_collection(idsbbean.getSch_deptcd4_collection());
		fixedBean.setSch_deptcd5_collection(idsbbean.getSch_deptcd5_collection());
		fixedBean.setSch_deptcd6_collection(idsbbean.getSch_deptcd6_collection());
		fixedBean.setSch_chrgunitcd_collection(idsbbean.getSch_chrgunitcd_collection());
		fixedBean.setSch_inputusrid_collection(idsbbean.getSch_inputusrid_collection());
		
		BeanUtils.copyProperties(formatFixedForm, fixedBean);
		
		return mapping.findForward("compdetailview");
	}
}