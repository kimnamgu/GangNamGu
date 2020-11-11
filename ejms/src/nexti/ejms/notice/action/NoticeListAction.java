/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� ��� action
 * ����:
 */
package nexti.ejms.notice.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.notice.form.NoticeForm;
import nexti.ejms.notice.model.NoticeManager;
import nexti.ejms.notice.model.SearchBean;
import nexti.ejms.util.commfunction;

public class NoticeListAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {		
		
		//Form���� �Ѿ�� �� ��������
		NoticeForm noticeForm = (NoticeForm)form;	
		int cPage = noticeForm.getPage();
		String gubun = noticeForm.getGubun();
		String searchval = noticeForm.getSearchval();
	
		//������ ���� ����
		int pageSize = 10;		//�ѹ��� ǥ���� ����Ʈ�� ����
		int start = commfunction.startIndex(cPage, pageSize);
		int end = commfunction.endIndex(cPage, pageSize);
				
		//��ȸ���� ����		
		SearchBean search = new SearchBean();
		search.setGubun(gubun);
		search.setSearchval(searchval);
		search.setStartidx(start);
		search.setEndidx(end);	
		
		//�������� ��� ��������
		NoticeManager manager = NoticeManager.instance(); 
		List noticeList = manager.noticeList(search);
		noticeForm.setNoticeList(noticeList);	
		
		//������ �� ���� ����		
		HashMap searchCondition = new HashMap();
		searchCondition.put("gubun",gubun);	
		searchCondition.put("searchval", searchval);			
		request.setAttribute("search",searchCondition);
		
		//ȭ������ ������ �� ����
		int totalCount = manager.noticeTotCnt(search);
		int totalPage = (int)Math.ceil((double)totalCount/(double)pageSize);
		int tbunho = totalCount - ((cPage - 1) * pageSize) ;
		request.setAttribute("totalPage",new Integer(totalPage));		//����������
		request.setAttribute("totalCount", new Integer(totalCount));    //�� ������ �Ǽ�
		request.setAttribute("currpage", new Integer(cPage));           //����������		
		request.setAttribute("tbunho", new Integer(tbunho));            //���������� �����ͰǼ�
		
		return mapping.findForward("list");
	}
}