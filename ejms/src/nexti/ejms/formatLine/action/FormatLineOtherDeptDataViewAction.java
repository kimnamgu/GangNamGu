/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 타부서 행추가형 보기 action
 * 설명:
 */
package nexti.ejms.formatLine.action;

import java.lang.reflect.InvocationTargetException;

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
import nexti.ejms.formatLine.form.FormatLineForm;
import nexti.ejms.formatLine.model.FormatLineBean;
import nexti.ejms.formatLine.model.FormatLineManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class FormatLineOtherDeptDataViewAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		//세션정보 가져오기
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
		
		int sysdocno = 0;
		int formseq = 0;
		String forward = "formatview";

		//Form에서 넘어온 값 가져오기 
		FormatLineForm formatLineForm = (FormatLineForm)form;
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"), 10);
		}
		
		if(request.getParameter("formseq") != null) {
			formseq = Integer.parseInt(request.getParameter("formseq"), 10);
		}
		
		InputDeptSearchBoxBean idsbbean = new InputDeptSearchBoxBean();
		BeanUtils.copyProperties(idsbbean, formatLineForm);
		String[][] deptInfo = FormatManager.instance().getInputDeptInfo(sysdocno, dept_code);
		int depth = CommTreatManager.instance().getTargetDeptLevel(sysdocno, dept_code);
		int idx = 0;
		if ( true || depth < 1 || request.getRequestURI().indexOf("exhibit") != 0 ) {
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
		
		//행추가형 양식 구조 가져오기
		FormatLineManager flmgr = FormatLineManager.instance();
		FormatLineBean lineBean = flmgr.getFormatLineDataView(sysdocno, formseq, idsbbean, false, false);
		
		//정렬 스크립트&html 삽입
		//Bean.setFormheaderhtml(flmgr.insertSortScriptHtml(Bean.getFormhtml(), Bean.getFormheaderhtml()));
		
		//행추가형 양식에 따른 데이터 가져오기
		boolean isTotalState = formatLineForm.isTotalState();
		boolean isTotalShowStringState = formatLineForm.isTotalShowStringState();
		boolean isIncludeNotSubmitData = formatLineForm.isIncludeNotSubmitData();
		lineBean.setListform(flmgr.getFormDataList(sysdocno, formseq, idsbbean, isTotalState, isTotalShowStringState, isIncludeNotSubmitData));
		
		lineBean.setTotalState(isTotalState);
		lineBean.setTotalShowStringState(isTotalShowStringState);
		lineBean.setIncludeNotSubmitData(isIncludeNotSubmitData);
		
		idsbbean = FormatManager.instance().getInputDeptInfoAll(sysdocno, user_id, dept_code, idsbbean, "01");
		lineBean.setSch_deptcd1(idsbbean.getSch_deptcd1());
		lineBean.setSch_deptcd2(idsbbean.getSch_deptcd2());
		lineBean.setSch_deptcd3(idsbbean.getSch_deptcd3());
		lineBean.setSch_deptcd4(idsbbean.getSch_deptcd4());
		lineBean.setSch_deptcd5(idsbbean.getSch_deptcd5());
		lineBean.setSch_deptcd6(idsbbean.getSch_deptcd6());
		lineBean.setSch_chrgunitcd(idsbbean.getSch_chrgunitcd());
		lineBean.setSch_inputusrid(idsbbean.getSch_inputusrid());
		lineBean.setSch_deptcd1_collection(idsbbean.getSch_deptcd1_collection());
		lineBean.setSch_deptcd2_collection(idsbbean.getSch_deptcd2_collection());
		lineBean.setSch_deptcd3_collection(idsbbean.getSch_deptcd3_collection());
		lineBean.setSch_deptcd4_collection(idsbbean.getSch_deptcd4_collection());
		lineBean.setSch_deptcd5_collection(idsbbean.getSch_deptcd5_collection());
		lineBean.setSch_deptcd6_collection(idsbbean.getSch_deptcd6_collection());
		lineBean.setSch_chrgunitcd_collection(idsbbean.getSch_chrgunitcd_collection());
		lineBean.setSch_inputusrid_collection(idsbbean.getSch_inputusrid_collection());
		
		request.setAttribute("sysdocno", new Integer(sysdocno));
		request.setAttribute("formseq", new Integer(formseq));
		request.setAttribute("forward", forward);
		
		try {
			BeanUtils.copyProperties(formatLineForm, lineBean);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return mapping.findForward("formatlineview");
	}
}