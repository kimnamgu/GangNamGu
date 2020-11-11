/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� �������� ��� action
 * ����:
 */
package nexti.ejms.sinchung.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.sinchung.form.DataListForm;
import nexti.ejms.sinchung.model.SearchBean;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.sinchung.model.FrmBean;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.DateTime;
import nexti.ejms.util.commfunction;

public class JupsuListAction extends rootPopupAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {
		
		HttpSession session = request.getSession();;
		String user_id = (String)session.getAttribute("user_id");
		
		DataListForm dataForm = (DataListForm)form;
		int reqformno = dataForm.getReqformno();    //��û�� ��Ĺ�ȣ
		int cPage = dataForm.getPage();             //����������
		String gbn = dataForm.getGbn();				//���ļ���
		String presentnm = dataForm.getPresentnm(); //��û��
		String procGbn = dataForm.getProcGbn();     //��ó���� (0:��ó�� , 1:��ü)
		
		SinchungManager smgr = SinchungManager.instance();
		
		//������ ���� ����
		FrmBean fbean = smgr.reqFormInfo(reqformno);
		
		if ( !dataForm.getStrdt().equals("") && !dataForm.getStrdt().equals(fbean.getStrdt()) ) {
			fbean.setStrdt(dataForm.getStrdt());
		}
		if ( !dataForm.getEnddt().equals("") && !dataForm.getEnddt().equals(fbean.getEnddt()) ) {
			fbean.setEnddt(dataForm.getEnddt());
		} else {
			fbean.setEnddt(DateTime.getCurrentDate());
		}
		
		BeanUtils.copyProperties(dataForm, fbean);
		
		//�����μ� ��ȭ��ȣ
		String tel = fbean.getColtel();
		UserBean ubean = UserManager.instance().getUserInfo(fbean.getChrgusrid());
		if ( ubean != null ) {
			tel = ubean.getTel();
		}
		dataForm.setColtel(tel);
		
		//������ ���� ����
		int pageSize = 10;		//�ѹ��� ǥ���� ����Ʈ�� ����
		int start = commfunction.startIndex(cPage, pageSize);
		int end = commfunction.endIndex(cPage, pageSize);
		
		//��ȸ���� ����
		SearchBean search = new SearchBean();
		search.setUserid(user_id);
		search.setReqformno(reqformno);
		search.setGbn(gbn);
		search.setPresentnm(presentnm);
		search.setStrdt(fbean.getStrdt());
		search.setEnddt(fbean.getEnddt());
		search.setProcFL(procGbn);
		search.setStart_idx(start);
		search.setEnd_idx(end);
		
		//��� ��������
		List dataList = smgr.reqDataList(search);
		dataForm.setDataList(dataList);		
		
		//������ �� ���� ����		
		HashMap searchCondition = new HashMap();
		searchCondition.put("reqformno", String.valueOf(reqformno));
		searchCondition.put("presentnm",presentnm);	
		searchCondition.put("procGbn", procGbn);			
		request.setAttribute("search",searchCondition);
		
		//ȭ������ ������ �� ����
		int totalCount = smgr.reqDataTotCnt(search);
		int totalPage = (int)Math.ceil((double)totalCount/(double)pageSize);
		int tbunho = totalCount - ((cPage - 1) * pageSize) ;
		request.setAttribute("totalPage",new Integer(totalPage));		//����������
		request.setAttribute("totalCount", new Integer(totalCount));    //�� ������ �Ǽ�
		request.setAttribute("currpage", new Integer(cPage));           //����������		
		request.setAttribute("tbunho", new Integer(tbunho));            //���������� �����ͰǼ�
		
		return mapping.findForward("list");
	}
}