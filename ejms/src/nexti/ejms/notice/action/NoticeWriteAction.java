/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� �ۼ� action
 * ����:
 */
package nexti.ejms.notice.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.beanutils.BeanUtils;

import nexti.ejms.common.rootAction;
import nexti.ejms.notice.form.NoticeForm;
import nexti.ejms.notice.model.NoticeManager;
import nexti.ejms.notice.model.NoticeBean;

public class NoticeWriteAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {
		
		NoticeForm ntForm = (NoticeForm)form;
		int seq = ntForm.getSeq();
		
		//����ȭ������ �̵��Ҷ� ����Ѵ�.
		if(seq > 0){
			NoticeManager ntManager = NoticeManager.instance();
			NoticeBean ntBean = ntManager.noticeInfo(seq);
			List fList = ntManager.fileList(seq);
			
			BeanUtils.copyProperties(ntForm, ntBean);
			ntForm.setFlist(fList);
		}
		
		return mapping.findForward("write");
	}
}