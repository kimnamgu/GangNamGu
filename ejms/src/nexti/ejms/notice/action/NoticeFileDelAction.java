/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공지사항 첨부파일 삭제 action
 * 설명:
 */
package nexti.ejms.notice.action;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.appInfo;
import nexti.ejms.common.rootAction;
import nexti.ejms.notice.form.NoticeForm;
import nexti.ejms.notice.model.NoticeManager;
import nexti.ejms.util.FileManager;

public class NoticeFileDelAction extends rootAction {

	public ActionForward doService (
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {
			
			ServletContext context = getServlet().getServletContext();
			NoticeForm ntForm = (NoticeForm)form;
			int seq = ntForm.getSeq();
			int fileseq = ntForm.getFileseq();				
		
			NoticeManager ntManager = NoticeManager.instance();
			String filenm = ntManager.fileNm(seq, fileseq);
			int result = ntManager.fileDelete(seq, fileseq);
		
		if(result > 0){
			String absolutePath = context.getRealPath(appInfo.getNoticeDir());			
			String save_name = filenm.substring(filenm.lastIndexOf("/")+1);
			String filepath = absolutePath+File.separator+save_name;
			
			String delFile = filepath;
			if ( delFile != null && delFile.trim().equals("") == false) {
				FileManager.doFileDelete(delFile);
			}					
		}
		
		return mapping.findForward("back");
	}
}