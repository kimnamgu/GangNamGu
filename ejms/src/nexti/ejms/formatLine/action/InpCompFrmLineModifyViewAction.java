/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력완료 행추가형 수정 action
 * 설명:
 */
package nexti.ejms.formatLine.action;

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
import nexti.ejms.delivery.model.DeliveryDetailBean;
import nexti.ejms.delivery.model.DeliveryManager;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.format.model.InputDeptSearchBoxBean;
import nexti.ejms.formatLine.form.FormatLineForm;
import nexti.ejms.formatLine.model.FormatLineBean;
import nexti.ejms.formatLine.model.FormatLineManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class InpCompFrmLineModifyViewAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//Form에서 넘어온 값 가져오기 
		FormatLineForm formatLineForm = (FormatLineForm)form;
		
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
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		if(request.getParameter("formseq") != null) {
			formseq = Integer.parseInt(request.getParameter("formseq"));
		}
		
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
		
		//행추가형 양식 구조 가져오기
		FormatLineManager manager = FormatLineManager.instance();
				
		//임시코드 : 대용량 데이터 처리 방안 도출시 새로 작성해야함
		int strIdx = Integer.parseInt("0"+nexti.ejms.util.Utils.nullToEmptyString(request.getParameter("strIdx")));
		int endIdx = Integer.parseInt("0"+nexti.ejms.util.Utils.nullToEmptyString(request.getParameter("endIdx")));
		if ( endIdx == 0 ) endIdx = 50;
		
		//자료입력 폼 생성
		FormatLineBean lineBean = manager.getFormatLineDataView(sysdocno, formseq, idsbbean, true, true);
		//(데이터수정->레이어)
		lineBean.setListform(manager.getFormDataList1(sysdocno, formseq, idsbbean, strIdx, endIdx));
		//(데이터+수정/삭제버튼)
		lineBean.setListform_ext(manager.getFormDataList2(sysdocno, formseq, idsbbean, strIdx, endIdx));
		
		
		BeanUtils.copyProperties(formatLineForm, lineBean);
		
		DeliveryManager dmgr = DeliveryManager.instance();
		DeliveryDetailBean detailBean = dmgr.viewDeliveryDetail(sysdocno);
		
		//마감시한/마감알림말
		request.setAttribute("enddt", detailBean.getEnddt());
		request.setAttribute("endcomment", detailBean.getEndcomment());
		
		formatLineForm.setOpeninput(ColldocManager.instance().getColldoc(sysdocno).getOpeninput());
		
		return mapping.findForward("modifylineview");
	}
}