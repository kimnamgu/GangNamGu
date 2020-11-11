/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공지사항 보기 action
 * 설명:
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
import nexti.ejms.util.Utils;

public class NoticeViewAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response)
		throws Exception{
		
		NoticeForm ntForm = (NoticeForm)form;
		int seq = ntForm.getSeq();		
		
		NoticeManager ntManager = NoticeManager.instance();
		NoticeBean ntBean = ntManager.noticeInfo(seq);
		List fList = ntManager.fileList(seq);
		//파일 내용가져오기
		
		//Form으로 값 복사
		BeanUtils.copyProperties(ntForm, ntBean);
		ntForm.setContent(Utils.convertHtmlBrNbsp(ntForm.getContent()));
		ntForm.setFlist(fList);
		
		//방문회수 증가
		ntManager.addVisitNo(seq);
		
		return mapping.findForward("view");
	}
}