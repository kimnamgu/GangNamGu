/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� �������� �����ٿ�ε� action
 * ����:
 */
package nexti.ejms.sinchung.action;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.sinchung.form.DataForm;
import nexti.ejms.sinchung.form.DataListForm;
import nexti.ejms.sinchung.model.ReqMstBean;
import nexti.ejms.sinchung.model.SearchBean;
import nexti.ejms.sinchung.model.SinchungManager;

public class JupsuXlsAction extends rootPopupAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {		 		
				
		DataListForm dataListForm = (DataListForm)form;
		int reqformno = dataListForm.getReqformno();    //��û�� ��Ĺ�ȣ	
		String presentnm = dataListForm.getPresentnm(); //��û��
		String procGbn = dataListForm.getProcGbn();     //��ó���� (0:��ó�� , 1:��ü)
				
		//��ȸ���� ����		
		SearchBean search = new SearchBean();	
		search.setReqformno(reqformno);
		search.setPresentnm(presentnm);
		search.setStrdt(dataListForm.getStrdt());
		search.setEnddt(dataListForm.getEnddt());
		search.setProcFL(procGbn);
		search.setStart_idx(1);
		search.setEnd_idx(99999);
		
		//��� ��������
		SinchungManager smgr = SinchungManager.instance();	
		List dataList = smgr.reqDataList(search);
		dataListForm.setDataList(dataList);
		
		ArrayList arrList = new ArrayList();
		
		for(int i = 0; i < dataList.size(); i++) {
			DataForm dataForm = new DataForm();
			
			int reqseq = ((ReqMstBean)dataList.get(i)).getReqseq();
			
			//���� ����
			ReqMstBean mstBean = smgr.reqDataInfo(reqformno, reqseq);
			BeanUtils.copyProperties(dataForm, mstBean);
			
			//������ ������������
			String title = smgr.reqFormInfo(reqformno).getTitle();
			String basictype = smgr.reqFormInfo(reqformno).getBasictype();
			dataForm.setTitle(title);
			dataForm.setBasictype(basictype);
			
			//�׸� ��� ������ ��������
			List articleList = smgr.reqFormSubList(reqformno);
			dataForm.setArticleList(articleList);
			
			arrList.add(dataForm);
		}
				
		//��û����
		request.setAttribute("reqTitle", smgr.reqFormInfo(reqformno).getTitle());
		//��û�������
		request.setAttribute("dataFormList", arrList);
				
		return mapping.findForward("excel");
	}
}