/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ۼ� �����İ������� ��� action
 * ����:
 */
package nexti.ejms.format.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootFrameAction;
import nexti.ejms.format.form.FormatForm;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.format.model.FormatBean;
import nexti.ejms.util.commfunction;

public class FormatGetCommFrameAction extends rootFrameAction {
	
	public ActionForward doService(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response
			) throws Exception {
		
		//Form���� �Ѿ�� �� ��������
		FormatForm fform = (FormatForm)form;
		
		//������ ���� ����
		int pagesize = 10;		//�ѹ��� ǥ���� ����Ʈ�� ����
		int start = commfunction.startIndex(fform.getPage(), pagesize);
		int end = commfunction.endIndex(fform.getPage(), pagesize);
		
		FormatBean fbean = new FormatBean();
		fbean.setSearchdept(fform.getSearchdept());
		fbean.setSearchtitle(fform.getSearchtitle());
		
		FormatManager fmgr = FormatManager.instance();
		
		List listform = fmgr.getListCommFormat(fbean, start, end);
		fform.setListform(listform);
		
		int totalcount = fmgr.getCountCommFormat(fbean);
		int totalpage = (int)Math.ceil((double)totalcount/(double)pagesize);		
		request.setAttribute("totalpage",new Integer(totalpage));		
		request.setAttribute("totalcount", new Integer(totalcount));
		request.setAttribute("currpage", new Integer(fform.getPage()));

		return mapping.findForward("formatGetCommFrame");
	}
}
