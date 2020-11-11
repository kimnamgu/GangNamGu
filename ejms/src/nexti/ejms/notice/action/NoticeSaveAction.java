/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� ���� action
 * ����:
 */
package nexti.ejms.notice.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.common.appInfo;
import nexti.ejms.common.rootAction;
import nexti.ejms.notice.form.NoticeForm;
import nexti.ejms.notice.model.NoticeManager;
import nexti.ejms.notice.model.NoticeBean;
import nexti.ejms.util.FileBean;
import nexti.ejms.util.FileManager;

public class NoticeSaveAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {
		
		//�⺻ ����
		ServletContext context = getServlet().getServletContext();
		HttpSession session = request.getSession();
		String crtusrnm = (String)session.getAttribute("user_name");
		ActionMessages messages = new ActionMessages();
		
		NoticeForm ntForm = (NoticeForm)form;
		String gbn = ntForm.getGbn();
		int seq = ntForm.getSeq();
		String msg = "";
		int result = 0;
		
		NoticeManager ntManager = NoticeManager.instance();
		
		if("D".equals(gbn)){
			//����ó�� (���� �����ʹ� �������� �ʴ´�.)
			result = ntManager.deleteNotice(seq);			
			
			if(result >0){
				msg = "�����Ǿ����ϴ�.";
			} else {
				msg = "������ �����Ͽ����ϴ�.";
			}			
		} else{			
			NoticeBean ntBean = new NoticeBean();
			
			//FormBean�� ������ ������ Bean���� �����Ѵ�.
			BeanUtils.copyProperties(ntBean, ntForm);
			ntBean.setCrtusrnm(crtusrnm);
			
			if(seq > 0){
				//���� ó��
				result = ntManager.updateNotice(ntBean);
				
				if(result > 0){
					for(int i=0;i<ntForm.getFileList().length;i++){
						FileBean fileBean = FileManager.doFileUpload(ntForm.getFileList()[i], context, appInfo.getNoticeDir());
						if(fileBean != null){
							ntManager.fileinsert(ntBean.getSeq(), crtusrnm, fileBean);
						}
					}
				}
			} else {
				//�����ۼ� ó��
				result = ntManager.insertNotice(ntBean);
				
				if(result > 0){
					for(int i=0;i<ntForm.getFileList().length;i++){
						FileBean fileBean = FileManager.doFileUpload(ntForm.getFileList()[i], context, appInfo.getNoticeDir());
						if(fileBean != null){
							ntManager.fileinsert(result, crtusrnm, fileBean);
						}
					}
				}
			}		
			
			if(result >0){
				msg = "�����Ͽ����ϴ�.";
			} else {
				msg = "���忡 �����Ͽ����ϴ�.";
			}
		}
		
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
		saveMessages(request,messages);
		
		return mapping.findForward("list");
	}
}