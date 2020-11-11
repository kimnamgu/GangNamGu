/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 조직관리 자료이관 자료이관처리 action
 * 설명:
 */
package nexti.ejms.dataTransfer.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.rootAction;
import nexti.ejms.dataTransfer.form.DataTransferForm;
import nexti.ejms.dataTransfer.model.DataTransferBean;
import nexti.ejms.dataTransfer.model.DataTransferManager;

public class DataTransferProcAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		DataTransferForm dtform = (DataTransferForm)form;
		
		DataTransferBean dtbean = new DataTransferBean();
		BeanUtils.copyProperties(dtbean, dtform);
		
		DataTransferManager dtmgr = DataTransferManager.instance();
		
		int result = dtmgr.dataTransferProc(dtbean);
		String msg = null;
		if ( result > 0 ) {
			msg = "이관처리되었습니다.";
		} else {
			msg = "이관처리가 실패하였습니다.\\n다시 시도하거나 관리자에게 문의바랍니다.";
		}
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg", msg));
		saveMessages(request,messages);

		return mapping.findForward("dataTransferList");
	}
}