/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합완료 제본자료형 보기 action
 * 설명:
 */
package nexti.ejms.formatBook.action;

import java.util.List;

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
import nexti.ejms.formatBook.form.DataBookForm;
import nexti.ejms.formatBook.form.FileBookFrameForm;
import nexti.ejms.formatBook.form.FormatBookForm;
import nexti.ejms.formatBook.model.FormatBookBean;
import nexti.ejms.formatBook.model.FormatBookManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class FormatBookViewAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		//세션 정보 가져오기
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
		
		/*조회조건*/
		String rdb_sort = "";
		int extchk = 0;
		
		//Form에서 넘어온 값 가져오기 
		FormatBookForm formatBookForm = (FormatBookForm)form;
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"), 10);
		}
		
		if(request.getParameter("formseq") != null) {
			formseq = Integer.parseInt(request.getParameter("formseq"), 10);
		}
		
		rdb_sort = formatBookForm.getRdb_sort();
		if( rdb_sort== null || rdb_sort.equals("")){
			rdb_sort = "1";
		}
		
		InputDeptSearchBoxBean idsbbean = new InputDeptSearchBoxBean();
		BeanUtils.copyProperties(idsbbean, formatBookForm);
		String[][] deptInfo = FormatManager.instance().getInputDeptInfo(sysdocno, dept_code);
		int depth = CommTreatManager.instance().getTargetDeptLevel(sysdocno, dept_code);
		int idx = 0;
		if ( depth < 1 || request.getRequestURI().indexOf("exhibit") > -1 ) {
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
		
		//제본형 양식 구조 가져오기
		FormatBookManager manager = FormatBookManager.instance();
		FormatBookBean bookBean = manager.getFormatFormView(sysdocno, formseq);
		formatBookForm.setFormcomment(bookBean.getFormcomment());
		
		//제본형 샘플파일 리스트 가져오기
		FileBookFrameForm fileBookForm = new FileBookFrameForm();
		List fileList = manager.getFileBookFrm(sysdocno, formseq);
		fileBookForm.setListfilebook(fileList);
		
		//DataBookFrm 데이터 가져오기
		DataBookForm dataForm = new DataBookForm();
		List dataList = manager.getFormDataList(sysdocno, formseq, idsbbean, rdb_sort, formatBookForm.isIncludeNotSubmitData());
		dataForm.setDataList(dataList);
		
		//DataBookFrm 최종생성데이터 가져오기
		dataForm.setFilenm(manager.getDataBookCompView(sysdocno, formseq).getFilenm());
		dataForm.setOriginfilenm(manager.getDataBookCompView(sysdocno, formseq).getOriginfilenm());
		dataForm.setFilesize(manager.getDataBookCompView(sysdocno, formseq).getFilesize());
		
		//파일확장자체크
		extchk = manager.getExtchk(sysdocno, formseq);

		bookBean.setRdb_sort(rdb_sort);
		bookBean.setIncludeNotSubmitData(formatBookForm.isIncludeNotSubmitData());

		idsbbean = FormatManager.instance().getInputDeptInfoAll(sysdocno, user_id, dept_code, idsbbean, "03");
		bookBean.setSch_deptcd1(idsbbean.getSch_deptcd1());
		bookBean.setSch_deptcd2(idsbbean.getSch_deptcd2());
		bookBean.setSch_deptcd3(idsbbean.getSch_deptcd3());
		bookBean.setSch_deptcd4(idsbbean.getSch_deptcd4());
		bookBean.setSch_deptcd5(idsbbean.getSch_deptcd5());
		bookBean.setSch_deptcd6(idsbbean.getSch_deptcd6());
		bookBean.setSch_chrgunitcd(idsbbean.getSch_chrgunitcd());
		bookBean.setSch_inputusrid(idsbbean.getSch_inputusrid());
		bookBean.setSch_deptcd1_collection(idsbbean.getSch_deptcd1_collection());
		bookBean.setSch_deptcd2_collection(idsbbean.getSch_deptcd2_collection());
		bookBean.setSch_deptcd3_collection(idsbbean.getSch_deptcd3_collection());
		bookBean.setSch_deptcd4_collection(idsbbean.getSch_deptcd4_collection());
		bookBean.setSch_deptcd5_collection(idsbbean.getSch_deptcd5_collection());
		bookBean.setSch_deptcd6_collection(idsbbean.getSch_deptcd6_collection());
		bookBean.setSch_chrgunitcd_collection(idsbbean.getSch_chrgunitcd_collection());
		bookBean.setSch_inputusrid_collection(idsbbean.getSch_inputusrid_collection());
		
		BeanUtils.copyProperties(formatBookForm, bookBean);
		
		request.setAttribute("fileBookForm", fileBookForm);
		request.setAttribute("dataForm", dataForm);
		request.setAttribute("sysdocno", new Integer(sysdocno));
		request.setAttribute("formseq", new Integer(formseq));
		request.setAttribute("forward", forward);
		request.setAttribute("extchk", new Integer(extchk));

		return mapping.findForward("formatbookview");
	}
}