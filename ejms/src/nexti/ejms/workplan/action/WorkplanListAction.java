/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ڷ���� ���
 * ����:
 */
package nexti.ejms.workplan.action;

import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.workplan.model.WorkplanManager;
import nexti.ejms.workplan.model.WorkplanSearchBean;
import nexti.ejms.common.rootAction;
import nexti.ejms.workplan.form.WorkplanListForm;
import nexti.ejms.util.commfunction;

public class WorkplanListAction extends rootAction {
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
        String user_id = (String)session.getAttribute("user_id");       //����ھ��̵�;
        String dept_code = (String)session.getAttribute("dept_code");   //����� �μ��ڵ�;
        
        //Form���� �Ѿ�� �� ��������
        WorkplanListForm listForm = (WorkplanListForm) form;
        
        String user_gbn = (String)session.getAttribute("user_gbn");
		if(!user_gbn.equals("001")) listForm.setOrggbn(user_gbn);
        
        //������ ���� ����
        int pageSize = 10;      //�ѹ��� ǥ���� ����Ʈ�� ����
        int start = commfunction.startIndex(listForm.getPage(), pageSize);
        int end = commfunction.endIndex(listForm.getPage(), pageSize);
        
        if(listForm.getSearchdept().equals("") == true) {
            listForm.setSearchdept(dept_code);
        }
        
        //�ֱ����ո�� ��������
        WorkplanSearchBean searchBean = new WorkplanSearchBean();
        searchBean.setUserid(user_id);
        searchBean.setSearchdept(listForm.getSearchdept());
        searchBean.setSearchyear(listForm.getSearchyear());
        searchBean.setSearchupperdeptcd(listForm.getSearchupperdeptcd());
        searchBean.setSearchdeptcd(listForm.getSearchdeptcd());
        searchBean.setSearchchrgunitcd(listForm.getSearchchrgunitcd());
        searchBean.setSearchstatus(listForm.getSearchstatus());
        searchBean.setSearchstrdt(listForm.getSearchstrdt());
        searchBean.setSearchenddt(listForm.getSearchenddt());
        searchBean.setSearchgubun(listForm.getSearchgubun());
        searchBean.setSearchtitle(listForm.getSearchtitle());
        searchBean.setOrggbn(listForm.getOrggbn());
        
        if ( "".equals(searchBean.getSearchstrdt()) ) {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            searchBean.setSearchstrdt(year + "-01-01");
            searchBean.setSearchenddt(year + "-12-31");
        }
        
        listForm.setSearchstrdt(searchBean.getSearchstrdt());
        listForm.setSearchenddt(searchBean.getSearchenddt());
        
        listForm.setSearchupperdeptcd(searchBean.getSearchupperdeptcd());
        
        Calendar today = Calendar.getInstance();
        String searchyear = Integer.toString(today.get(Calendar.YEAR));
        
        WorkplanManager manager = WorkplanManager.instance();
        listForm.setWorklist(manager.getWorkplanList(searchBean, start, end));

        //������ �� ���� ����
        HashMap searchCondition = new HashMap();
        searchCondition.put("searchdept", searchBean.getSearchdept());
        searchCondition.put("searchupperdeptcd", searchBean.getSearchupperdeptcd());
        searchCondition.put("searchdeptcd", searchBean.getSearchdeptcd());
        searchCondition.put("searchchrgunitcd", searchBean.getSearchchrgunitcd());
        searchCondition.put("searchstatus", searchBean.getSearchstatus());
        searchCondition.put("searchstrdt", searchBean.getSearchstrdt());
        searchCondition.put("searchenddt", searchBean.getSearchenddt());
        searchCondition.put("searchgubun", searchBean.getSearchgubun());
        searchCondition.put("searchtitle", searchBean.getSearchtitle());
        request.setAttribute("search",searchCondition);
        
        //ȭ������ ������ �� ����
        int totalCount = manager.getWorkplanTotCnt(searchBean);
        int totalPage = (int)Math.ceil((double)totalCount/(double)pageSize);        
        request.setAttribute("totalPage",new Integer(totalPage));       
        request.setAttribute("totalCount", new Integer(totalCount));
        request.setAttribute("currpage", new Integer(listForm.getPage()));
        
        if(listForm.getCurrentyear().equals("")){
            request.setAttribute("currentyear", searchyear);            
        }else{
            request.setAttribute("currentyear", listForm.getCurrentyear().substring(0, 4));
        }
        

        return mapping.findForward("list");
    }
}