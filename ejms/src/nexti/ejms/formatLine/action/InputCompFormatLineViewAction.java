/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Է¿Ϸ� ���߰��� ���� action
 * ����:
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
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.format.model.InputDeptSearchBoxBean;
import nexti.ejms.formatLine.form.FormatLineForm;
import nexti.ejms.formatLine.model.FormatLineBean;
import nexti.ejms.formatLine.model.FormatLineManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class InputCompFormatLineViewAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//Form���� �Ѿ�� �� �������� 
		FormatLineForm formatLineForm = (FormatLineForm)form;
		
		//�������� ��������
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
		
		//���߰��� ��� ���� ��������
		FormatLineManager manager = FormatLineManager.instance();
		FormatLineBean lineBean = manager.getFormatLineDataView(sysdocno, formseq, idsbbean, false, false);
		
		//���߰��� ��Ŀ� ���� ������ ��������
		lineBean.setListform(manager.getFormDataList(sysdocno, formseq, idsbbean, false, false, true));
		
		BeanUtils.copyProperties(formatLineForm, lineBean);
		
		//�������� ��������
		String modifyyn = CommTreatManager.instance().getTgtModifyYn(sysdocno, dept_code);
		
		//�ش� ���� ���翩�� ��������
		int issanc = CommTreatManager.instance().IsDocSanctgt(sysdocno, dept_code, user_id);
		
		//�ش繮�� ������� ��������
		String submitstate = CommTreatManager.instance().getTgtdeptState(sysdocno, dept_code);
		
		request.setAttribute("modifyyn", modifyyn);
		request.setAttribute("issanc", new Integer(issanc));
		request.setAttribute("submitstate", submitstate);
		
		return mapping.findForward("detailview");
	}
}