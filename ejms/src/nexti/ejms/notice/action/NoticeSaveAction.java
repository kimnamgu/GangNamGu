/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공지사항 저장 action
 * 설명:
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
		
		//기본 설정
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
			//삭제처리 (실제 데이터는 삭제하지 않는다.)
			result = ntManager.deleteNotice(seq);			
			
			if(result >0){
				msg = "삭제되었습니다.";
			} else {
				msg = "삭제에 실패하였습니다.";
			}			
		} else{			
			NoticeBean ntBean = new NoticeBean();
			
			//FormBean의 내용을 데이터 Bean으로 복사한다.
			BeanUtils.copyProperties(ntBean, ntForm);
			ntBean.setCrtusrnm(crtusrnm);
			
			if(seq > 0){
				//수정 처리
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
				//새로작성 처리
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
				msg = "저장하였습니다.";
			} else {
				msg = "저장에 실패하였습니다.";
			}
		}
		
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
		saveMessages(request,messages);
		
		return mapping.findForward("list");
	}
}