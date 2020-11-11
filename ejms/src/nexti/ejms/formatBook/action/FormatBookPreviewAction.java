/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식작성 제본자료형 미리보기 action
 * 설명:
 */
package nexti.ejms.formatBook.action;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.format.model.FormatBean;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.formatBook.form.FormatBookForm;
import nexti.ejms.formatBook.model.DataBookBean;
import nexti.ejms.formatBook.model.FormatBookManager;

public class FormatBookPreviewAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		FormatManager fmgr = FormatManager.instance();
		FormatBookManager fbmgr = FormatBookManager.instance();
		
		FormatBookForm fbform = (FormatBookForm)form;
		
		//1:새로작성,2:공통양식가져오기,3:사용했던양식가져오기,4:공통양식작성,5:양식수정,6:공통양식수정
		int type = fbform.getType();
		int usedsysdocno = fbform.getUsedsysdocno();
		int usedformseq = fbform.getUsedformseq();
		int commformseq = fbform.getCommformseq();
		
		String formkind = "03";
		
		fbform.setFormkind(formkind);
		fbform.setFormkindname(fmgr.getFormkindName(formkind));
		
		String[] listcategorynm = null;
		
		if(type == 2) {
			
			request.setAttribute("msg", "");
			//파일유무 체크
			String fileDir = getServlet().getServletContext().getRealPath("");
			List existFile = fbmgr.getExistCommBookFile(fileDir, commformseq);
			if(existFile != null) {
				StringBuffer msg = new StringBuffer();
				msg.append("양식에 첨부된 파일 없습니다.\\n아래 파일을 제외하고 양식을 가져오려면 확인을 누르세요.\\n");
					
				for(int i = 0; i < existFile.size(); i++) {
					DataBookBean dbbean = (DataBookBean)existFile.get(i);
					msg.append("\\n" + dbbean.getOriginfilenm());
				}

				request.setAttribute("msg", msg);
			}
			
			FormatBean fbean = fmgr.getCommFormat(commformseq);
			
			fbform.setFormcomment(fbean.getFormcomment());
			fbform.setListfilebook(fbmgr.getListCommFileBook(commformseq));
			
			listcategorynm = fbmgr.getListCommCategory(commformseq);
			
		} else if(type == 3) {
			
			request.setAttribute("msg", "");
			//파일유무 체크
			String fileDir = getServlet().getServletContext().getRealPath("");
			List existFile = fbmgr.getExistUsedBookFile(fileDir, usedsysdocno, usedformseq);
			if(existFile != null) {
				StringBuffer msg = new StringBuffer();
				msg.append("양식에 첨부된 파일 없습니다.\\n아래 파일을 제외하고 양식을 가져오려면 확인을 누르세요.\\n");
					
				for(int i = 0; i < existFile.size(); i++) {
					DataBookBean dbbean = (DataBookBean)existFile.get(i);
					msg.append("\\n" + dbbean.getOriginfilenm());
				}

				request.setAttribute("msg", msg);
			}
			
			FormatBean fbean = fmgr.getUsedFormat(usedsysdocno, usedformseq);
			
			fbform.setFormcomment(fbean.getFormcomment());
			fbform.setListfilebook(fbmgr.getListUsedFileBook(usedsysdocno, usedformseq));
			
			listcategorynm = fbmgr.getListUsedCategory(usedsysdocno, usedformseq);
			
		}
		
		if(listcategorynm != null) {
			Vector vt = new Vector();
			
			for(int i = 0; i < listcategorynm.length; i++)
				vt.add(new LabelValueBean(listcategorynm[i], listcategorynm[i]));
			
			Collection col = vt;
			
			fbform.setListcategorynm2(col);
		}
		
		return mapping.findForward("formatBookPreview");
	}
}