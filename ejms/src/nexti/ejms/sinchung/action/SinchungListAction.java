/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� ��� action
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
import nexti.ejms.sinchung.form.SinchungListForm;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.sinchung.model.SearchBean;
import nexti.ejms.util.Utils;
import nexti.ejms.util.commfunction;

public class SinchungListAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {
		System.out.println("ActionForward");
		//���ǰ� ����
		HttpSession session = request.getSession();
		String userid 	= session.getAttribute("user_id").toString();
		String deptid 	= session.getAttribute("dept_code").toString();
		String isSysMgr = (String)session.getAttribute("isSysMgr");
		
		//Form���� �Ѿ�� �� ��������
		SinchungListForm sForm = (SinchungListForm)form;	
		int cPage 			= sForm.getPage();             	//����������
		String title 		= sForm.getSearch_title();  	//����
		String procGbn 		= sForm.getProcGbn();    	 	//��ó���� (0:��ó�� , 1:��ü)
		String initentry	= sForm.getInitentry();
		String sch_deptcd 	= sForm.getSch_old_deptcd();
		String sch_userid 	= sForm.getSch_old_userid();
		String sch_deptnm 	= sForm.getSch_deptnm();
		String sch_usernm 	= sForm.getSch_usernm();
		
		//���۱�û : �⺻���� �����ڰ� ��� ��û�����̰���
		if ( "����3190000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
			if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
				if ( "".equals(Utils.nullToEmptyString(sch_usernm)) ) {
					sch_usernm = "%";
					sForm.setSch_usernm(sch_usernm);
					initentry = "second";
				}
			}
		}
		
		//������ ���� ����
		int pageSize = 10;		//�ѹ��� ǥ���� ����Ʈ�� ����
		int start = commfunction.startIndex(cPage, pageSize);
		int end = commfunction.endIndex(cPage, pageSize);
				
		//��ȸ���� ����		
		SearchBean search = new SearchBean();
		search.setDeptid(deptid);
		search.setUserid(userid);
		search.setTitle(title);
		search.setProcFL(procGbn);
		search.setStart_idx(start);
		search.setEnd_idx(end);	
		
		//��� ��������
		SinchungManager mgr = SinchungManager.instance();
		String orgid = mgr.getSearch(search, sch_deptcd, sch_userid); 
		List sList = mgr.reqFormList(search, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm);
		sForm.setSinchungList(sList);		
		
		//������ �� ���� ����		
		HashMap searchCondition = new HashMap();
		searchCondition.put("title",title);	
		searchCondition.put("procGbn", procGbn);			
		request.setAttribute("search",searchCondition);
		
		//ȭ������ ������ �� ����
		int totalCount = mgr.reqFormTotCnt(search, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm);
		int totalPage = (int)Math.ceil((double)totalCount/(double)pageSize);
		int tbunho = totalCount - ((cPage - 1) * pageSize) ;
		request.setAttribute("totalPage",new Integer(totalPage));		//����������
		request.setAttribute("totalCount", new Integer(totalCount));    //�� ������ �Ǽ�
		request.setAttribute("currpage", new Integer(cPage));           //����������		
		request.setAttribute("tbunho", new Integer(tbunho));            //���������� �����ͰǼ�
		
		request.setAttribute("initentry", initentry);
		request.setAttribute("orgid", Utils.nullToEmptyString(orgid));
		
		session.removeAttribute("originuserid");
		
		return mapping.findForward("list");
	}
}