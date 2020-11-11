/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합완료 제본자료형 최종자료삭제 action
 * 설명:
 */
package nexti.ejms.formatBook.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.formatBook.form.DataBookForm;
import nexti.ejms.formatBook.model.DataBookBean;
import nexti.ejms.formatBook.model.FormatBookManager;
import nexti.ejms.util.FileManager;

public class DataBookCompDeleteAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		//기본 정보 설정
		ServletContext context = getServlet().getServletContext();
		int sysdocno = 0;
		int formseq = 0;
		String filenm = null;
		
		//Form에서 넘어온 값 가져오기 
		DataBookForm dataBookForm = (DataBookForm)form;
		
		sysdocno = dataBookForm.getSysdocno();
		formseq = dataBookForm.getFormseq();
		
		DataBookBean dataBean = new DataBookBean();
		
		//Form 데이터를 Bean으로 복사
		BeanUtils.copyProperties(dataBean, dataBookForm);
		
		FormatBookManager manager = FormatBookManager.instance();
		
		//DataBookFrm 최종생성데이터 가져오기
		filenm = manager.getDataBookCompView(sysdocno, formseq).getFilenm();
		
		int retCode = manager.DataBookCompDelete(sysdocno, formseq);
		
		if(retCode > 0) {
			String delFile = filenm;
			if ( delFile != null && delFile.trim().equals("") == false) {
				FileManager.doFileDelete(context.getRealPath(delFile));
			}
		}
		
		request.setAttribute("savecheck", new Integer(retCode));
		request.setAttribute("sysdocno", new Integer(sysdocno));
		request.setAttribute("formseq", new Integer(formseq));
		
		return mapping.findForward("hiddenframe");
	}
}