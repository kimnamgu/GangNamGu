/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� ���系�� ���� action
 * ����:
 */
package nexti.ejms.sinchung.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.sinchung.form.DataForm;
import nexti.ejms.sinchung.model.ReqMstBean;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;

public class SancViewAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {			
				
		DataForm dForm = (DataForm)form;
		int reqformno = dForm.getReqformno();
		int reqseq = dForm.getReqseq();
		
		//���� ����
		SinchungManager smgr = SinchungManager.instance();
		ReqMstBean mstBean = smgr.reqDataInfo(reqformno, reqseq);		
		BeanUtils.copyProperties(dForm, mstBean);
		
		//������ ������������
		String title = smgr.reqFormInfo(reqformno).getTitle();
		String basictype = smgr.reqFormInfo(reqformno).getBasictype();
		String coldeptnm = smgr.reqFormInfo(reqformno).getColdeptnm();
		String chrgusrid = smgr.reqFormInfo(reqformno).getChrgusrid();
		String chrgusrnm = smgr.reqFormInfo(reqformno).getChrgusrnm();
		dForm.setTitle(title);            //��������
		dForm.setBasictype(basictype);    //�Է±⺻����
		dForm.setColdeptnm(coldeptnm);    //�����μ���Ī
		dForm.setChrgusrnm(chrgusrnm);    //��������� ����
		
		//�����μ� ��ȭ��ȣ
		String tel = smgr.reqFormInfo(reqformno).getColtel();
		UserBean ubean = UserManager.instance().getUserInfo(chrgusrid);
		if ( ubean != null ) {
			tel = ubean.getTel();
		}
		dForm.setDepttel(tel);
		
		//�׸� ��� ������ ��������
		List articleList = smgr.reqFormSubList(reqformno);
		dForm.setArticleList(articleList);
		
		//���缱 ����
		dForm.setSancList1(smgr.approvalInfo("1", reqformno, reqseq)); //����
		dForm.setSancList2(smgr.approvalInfo("2", reqformno, reqseq)); //����
		
		//�����ϱ�(1), ����Ϸ�(2)
		request.setAttribute("gbn", request.getParameter("gbn"));     
		
		return mapping.findForward("view");
	}
}