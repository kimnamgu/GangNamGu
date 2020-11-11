/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 조직관리 자료이관 자료이관목록 action
 * 설명:
 */
package nexti.ejms.dataTransfer.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.dataTransfer.form.DataTransferForm;
import nexti.ejms.dataTransfer.model.DataTransferManager;
import nexti.ejms.util.Utils;

public class DataTransferListAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {

		HttpSession session = request.getSession();
		String user_gbn = (String)session.getAttribute("user_gbn");	//사용자 ID
		String isFirst = Utils.nullToEmptyString((String)request.getParameter("isFirst"));
		
		DataTransferForm dtform = (DataTransferForm)form;
		
		String sch_deptid = dtform.getSch_deptid();
		String sch_userid = dtform.getSch_userid();
		String sch_orggbn = dtform.getSch_orggbn();
		
		if ( sch_orggbn.equals("") ) {
			sch_orggbn = user_gbn;
			dtform.setSch_orggbn(user_gbn);
		}
		
		DataTransferManager dtmgr = DataTransferManager.instance();
		if ( !"true".equals(isFirst) ) {
			dtform.setDatalist(dtmgr.getOrgDataList(sch_orggbn, sch_deptid, sch_userid));
		}
		dtform.setOrgdept(dtmgr.getOrgDeptList(sch_deptid, sch_orggbn));
		dtform.setOrguser(dtmgr.getOrgUserList(sch_deptid, sch_orggbn, sch_userid));
		dtform.setTgtdept(dtmgr.getTgtDeptList(sch_orggbn));
		dtform.setTgtuser(dtmgr.getTgtUserList(""));
		
		return mapping.findForward("dataTransferList");
	}
}