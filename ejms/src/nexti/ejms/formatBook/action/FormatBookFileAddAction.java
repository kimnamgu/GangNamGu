/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식작성 제본자료형 첨부파일 추가 action
 * 설명:
 */
package nexti.ejms.formatBook.action;

import java.io.File;
import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.appInfo;
import nexti.ejms.common.rootPopupAction;
import nexti.ejms.formatBook.form.FormatBookForm;
import nexti.ejms.formatBook.model.FileBookBean;
import nexti.ejms.formatBook.model.FormatBookManager;
import nexti.ejms.util.FileBean;
import nexti.ejms.util.FileManager;

public class FormatBookFileAddAction extends rootPopupAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		//기본 정보 설정
		ServletContext context = getServlet().getServletContext();
		
		//Form에서 넘어온 값 가져오기 
		FormatBookForm fmform = (FormatBookForm)form;
		
		//1:새로작성,2:공통양식가져오기,3:사용했던양식가져오기,4:공통양식작성,5:양식수정,6:공통양식수정
		int type = fmform.getType();
		int sysdocno = fmform.getSysdocno();
		int formseq = fmform.getFormseq();
		
		String saveDir = null;
		
		if(type <= 3 || type == 5) {
			
			//저장할 디렉토리 지정
			Calendar cal = Calendar.getInstance();
			saveDir = appInfo.getBookFrmSampleDir() + cal.get(Calendar.YEAR) + "/";
			
			File saveFolder = new File(context.getRealPath(saveDir));
			
			if(!saveFolder.exists()) {
				saveFolder.mkdirs();
			}
			
		} else if(type == 4 || type == 6) {
			
			
			//저장할 디렉토리 지정
			saveDir = appInfo.getBookFrmCommSampleDir();
			
			File saveFolder = new File(context.getRealPath(saveDir));
			
			if(!saveFolder.exists()) {
				saveFolder.mkdirs();
			}
			
		}
		
		//파일업로드
		FileBean fileBean = new FileBean();
		
		fileBean = FileManager.doFileUpload(fmform.getUploadfile(), context, saveDir);
		
		if(fileBean != null) {
			FileBookBean fbbean = new FileBookBean();
			
			fbbean.setSysdocno(sysdocno);
			fbbean.setFormseq(formseq);
			fbbean.setFilenm(fileBean.getFilenm());
			fbbean.setOriginfilenm(fileBean.getOriginfilenm());
			fbbean.setFilesize(fileBean.getFilesize());
			fbbean.setExt(fileBean.getExt());
			
			FormatBookManager fbmgr = FormatBookManager.instance();
			
			if(type <= 3 || type == 5) {
			
				fbmgr.addFileBook(fbbean);
				
			} else if(type == 4 || type == 6) {
				
				fbmgr.addCommFileBook(fbbean);
				
			} 
		}
		
		return mapping.findForward("formatBookDef");
	}
}