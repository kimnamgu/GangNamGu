/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ڷ���� ���
 * ����:
 */
package nexti.ejms.exhibit.action;

import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.commdocinfo.model.CommCollDocInfoManager;
import nexti.ejms.commdocinfo.model.CommCollDocSearchBean;
import nexti.ejms.common.rootAction;
import nexti.ejms.exhibit.form.ExhibitListForm;
import nexti.ejms.util.commfunction;

public class ExhibitListAction extends rootAction {
	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception 
	 */
	public ActionForward doService(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");		//����ھ��̵�;
		String dept_code = (String)session.getAttribute("dept_code");	//����� �μ��ڵ�;
		
		//Form���� �Ѿ�� �� ��������
		ExhibitListForm listForm = (ExhibitListForm) form;
		
		//������ ���� ����
		int pageSize = 10;		//�ѹ��� ǥ���� ����Ʈ�� ����
		int start = commfunction.startIndex(listForm.getPage(), pageSize);
		int end = commfunction.endIndex(listForm.getPage(), pageSize);
		
		if(listForm.getSearchdept().equals("") == true) {
			listForm.setSearchdept(dept_code);
		}
		
		//�ֱ����ո�� ��������
		CommCollDocSearchBean searchBean = new CommCollDocSearchBean();
		searchBean.setUserid(user_id);
		searchBean.setSearchdept(listForm.getSearchdept());
		searchBean.setSearchdoctitle(listForm.getSearchdoctitle());
		searchBean.setSearchformtitle(listForm.getSearchformtitle());
		searchBean.setSearchkey(listForm.getSearchkey());
		searchBean.setSearchcrtdtfr(listForm.getSearchcrtdtfr());
		searchBean.setSearchcrtdtto(listForm.getSearchcrtdtto());
		searchBean.setSearchbasicdatefr(listForm.getSearchbasicdatefr());
		searchBean.setSearchbasicdateto(listForm.getSearchbasicdateto());
		searchBean.setSearchchrgusrnm(listForm.getSearchchrgusrnm());
		searchBean.setSearchinputusrnm(listForm.getSearchinputusrnm());
		
		if ( "".equals(searchBean.getSearchcrtdtfr()) ) {
			int year = Calendar.getInstance().get(Calendar.YEAR);
			searchBean.setSearchcrtdtfr(year + "-01-01");
			searchBean.setSearchcrtdtto(year + "-12-31");
		}
		
		CommCollDocInfoManager manager = CommCollDocInfoManager.instance();
		listForm.setDoclist(manager.getExhibitList(searchBean, start, end));

		//������ �� ���� ����
		HashMap searchCondition = new HashMap();
		searchCondition.put("searchdept", searchBean.getSearchdept());			
		searchCondition.put("searchdoctitle", searchBean.getSearchdoctitle());			
		searchCondition.put("searchformtitle", searchBean.getSearchformtitle());			
		searchCondition.put("searchkey", searchBean.getSearchkey());			
		searchCondition.put("searchcrtdtfr", searchBean.getSearchcrtdtfr());			
		searchCondition.put("searchcrtdtto", searchBean.getSearchcrtdtto());			
		searchCondition.put("searchbasicdatefr", searchBean.getSearchbasicdatefr());			
		searchCondition.put("searchbasicdateto", searchBean.getSearchbasicdateto());			
		searchCondition.put("searchchrgusrnm", searchBean.getSearchchrgusrnm());			
		searchCondition.put("searchinputusrnm", searchBean.getSearchinputusrnm());			
		request.setAttribute("search",searchCondition);
		
		//ȭ������ ������ �� ����
		int totalCount = manager.getExhibitTotCnt(searchBean);
		int totalPage = (int)Math.ceil((double)totalCount/(double)pageSize);		
		request.setAttribute("totalPage",new Integer(totalPage));		
		request.setAttribute("totalCount", new Integer(totalCount));
		request.setAttribute("currpage", new Integer(listForm.getPage()));

		return mapping.findForward("list");
	}
}