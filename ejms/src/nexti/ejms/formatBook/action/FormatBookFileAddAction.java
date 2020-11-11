/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ۼ� �����ڷ��� ÷������ �߰� action
 * ����:
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
		
		//�⺻ ���� ����
		ServletContext context = getServlet().getServletContext();
		
		//Form���� �Ѿ�� �� �������� 
		FormatBookForm fmform = (FormatBookForm)form;
		
		//1:�����ۼ�,2:�����İ�������,3:����ߴ���İ�������,4:�������ۼ�,5:��ļ���,6:�����ļ���
		int type = fmform.getType();
		int sysdocno = fmform.getSysdocno();
		int formseq = fmform.getFormseq();
		
		String saveDir = null;
		
		if(type <= 3 || type == 5) {
			
			//������ ���丮 ����
			Calendar cal = Calendar.getInstance();
			saveDir = appInfo.getBookFrmSampleDir() + cal.get(Calendar.YEAR) + "/";
			
			File saveFolder = new File(context.getRealPath(saveDir));
			
			if(!saveFolder.exists()) {
				saveFolder.mkdirs();
			}
			
		} else if(type == 4 || type == 6) {
			
			
			//������ ���丮 ����
			saveDir = appInfo.getBookFrmCommSampleDir();
			
			File saveFolder = new File(context.getRealPath(saveDir));
			
			if(!saveFolder.exists()) {
				saveFolder.mkdirs();
			}
			
		}
		
		//���Ͼ��ε�
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