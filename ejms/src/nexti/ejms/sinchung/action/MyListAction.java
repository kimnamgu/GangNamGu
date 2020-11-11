/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� ������û�ѽ�û�� ��� action
 * ����:
 */
package nexti.ejms.sinchung.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.sinchung.form.DataListForm;
import nexti.ejms.sinchung.model.SearchBean;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.util.commfunction;

public class MyListAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {		 
			
		//���ǰ� ��������
		HttpSession session = request.getSession();	
		String userid = session.getAttribute("user_id").toString();
		
		//Form���� �Ѿ�� �� ��������
		DataListForm sForm = (DataListForm)form;		
		String title = sForm.getTitle();         //����	
		String gbn   = sForm.getGbn();           //��û����(0:��û��, 1:��ü)
		int cPage    = sForm.getPage();          //����������
	
		//������ ���� ����
		int pageSize = 10;		//�ѹ��� ǥ���� ����Ʈ�� ����
		int start = commfunction.startIndex(cPage, pageSize);
		int end = commfunction.endIndex(cPage, pageSize);
				
		//��ȸ���� ����		
		SearchBean search = new SearchBean();
		search.setUserid(userid);		
		search.setTitle(title);	
		search.setGbn(gbn);
		search.setStart_idx(start);
		search.setEnd_idx(end);	
		
		//��� ��������
		SinchungManager mgr = SinchungManager.instance(); 
		List myList = mgr.mySinchungList(search);
		sForm.setDataList(myList);	
		
		//������ �� ���� ����		
		HashMap searchCondition = new HashMap();
		searchCondition.put("title",title);	
		searchCondition.put("gbn", gbn);
		request.setAttribute("search",searchCondition);
		
		//ȭ������ ������ �� ����
		int totalCount = mgr.mySinchungTotCnt(search);
		int totalPage = (int)Math.ceil((double)totalCount/(double)pageSize);
		int tbunho = totalCount - ((cPage - 1) * pageSize) ;
		request.setAttribute("totalPage",new Integer(totalPage));		//����������
		request.setAttribute("totalCount", new Integer(totalCount));    //�� ������ �Ǽ�
		request.setAttribute("currpage", new Integer(cPage));           //����������		
		request.setAttribute("tbunho", new Integer(tbunho));            //���������� �����ͰǼ�
		
		return mapping.findForward("list");
	}
}