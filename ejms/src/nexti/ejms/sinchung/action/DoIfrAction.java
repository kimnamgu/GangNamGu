/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� ��û�ϱ� ��� action
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

import nexti.ejms.common.rootFrameAction;
import nexti.ejms.sinchung.form.SinchungListForm;
import nexti.ejms.sinchung.model.SearchBean;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.util.commfunction;

public class DoIfrAction extends rootFrameAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {
		
		//���ǰ� ����
		HttpSession session = request.getSession();
		String userid 	= session.getAttribute("user_id").toString();
			
		//Form���� �Ѿ�� �� ��������
		SinchungListForm sForm = (SinchungListForm)form;
		String deptid = sForm.getDeptcd();       //�μ��ڵ�		
		String title = sForm.getSearch_title();  //����	
		int cPage = sForm.getPage();             //����������
	
		//������ ���� ����
		int pageSize = 10;		//�ѹ��� ǥ���� ����Ʈ�� ����
		int start = commfunction.startIndex(cPage, pageSize);
		int end = commfunction.endIndex(cPage, pageSize);
				
		//��ȸ���� ����		
		SearchBean search = new SearchBean();
		search.setDeptid(deptid);		
		search.setTitle(title);		
		search.setStart_idx(start);
		search.setEnd_idx(end);
		search.setPresentid(userid);
		
		//��� ��������
		SinchungManager mgr = SinchungManager.instance(); 
		List sList = mgr.doSinchungList(search);
		sForm.setSinchungList(sList);		
		
		//������ �� ���� ����		
		HashMap searchCondition = new HashMap();
		searchCondition.put("title",title);			
		searchCondition.put("deptcd", deptid);
		request.setAttribute("search",searchCondition);
		
		//ȭ������ ������ �� ����
		int totalCount = mgr.doSinchungTotCnt(search);
		int totalPage = (int)Math.ceil((double)totalCount/(double)pageSize);
		int tbunho = totalCount - ((cPage - 1) * pageSize) ;
		request.setAttribute("totalPage",new Integer(totalPage));		//����������
		request.setAttribute("totalCount", new Integer(totalCount));    //�� ������ �Ǽ�
		request.setAttribute("currpage", new Integer(cPage));           //����������		
		request.setAttribute("tbunho", new Integer(tbunho));            //���������� �����ͰǼ�
		
		return mapping.findForward("list");
	}
}