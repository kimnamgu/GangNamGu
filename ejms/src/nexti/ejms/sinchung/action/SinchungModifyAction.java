/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� ���� action
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

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.elecAppr.model.ElecApprBean;
import nexti.ejms.elecAppr.model.ElecApprManager;
import nexti.ejms.sinchung.form.SinchungForm;
import nexti.ejms.sinchung.model.FrmBean;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.sinchung.model.ReqMstBean;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class SinchungModifyAction extends rootPopupAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {
		
		SinchungForm sForm = (SinchungForm)form;		
		int reqformno = sForm.getReqformno();	
		int reqseq = sForm.getReqseq();
		
		SinchungManager smgr = SinchungManager.instance();
		
		//���������� ��������
		FrmBean frmbean = smgr.reqFormInfo(reqformno);
		BeanUtils.copyProperties(sForm, frmbean);
		sForm.setReqseq(reqseq);
		
		//�����μ� ��ȭ��ȣ
		String tel = frmbean.getColtel();
		UserBean ubean = UserManager.instance().getUserInfo(sForm.getChrgusrid());
		if ( ubean != null ) {
			tel = ubean.getTel();
		}
		sForm.setColtel(tel);
		
		if ( "����3740000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
            sForm.setColtel(sForm.getColtel().substring(4));
        }
		
		//�׸�������������
		List articleList = smgr.reqFormSubList(reqformno);
		sForm.setArticleList(articleList);	
		
		//��û ���� ���� ��������
		ReqMstBean rbean = smgr.reqDataInfo(reqformno, reqseq);
		sForm.setRbean(rbean);
		
		//���缱 ����
		sForm.setSancList1(smgr.approvalInfo("1", reqformno, reqseq)); //����
		sForm.setSancList2(smgr.approvalInfo("2", reqformno, reqseq)); //����
		
		//����,���, ���� ���� ���� ����
		ReqMstBean rmBean = smgr.reqDataInfo(reqformno, reqseq);
		String docstate = "";
		if ( rmBean != null ) {
			docstate = rmBean.getState();
		}
		boolean sancfl = smgr.existSanc(reqformno, reqseq);   //����� ���� �ִ��� Ȯ��
		
		if ( "01".equals(docstate) == true ) {	//�̰���(�����ʿ���)
			request.setAttribute("sanc", "T");                //���缱 ���� ����
			if(sancfl == true){		//�����Ѱ��
				request.setAttribute("modify", "F");          //����, ��� �Ұ�
			} else {				//������Ѱ��
				request.setAttribute("modify", "T");          //����, ��� ����
			}
		} else if ( "02".equals(docstate) == true ) {	//��û��(�����Ͽ��ų������ʿ����)
			request.setAttribute("sanc", "F");                //���缱 ���� �Ұ�
			if ( sForm.getSancneed() == null || sForm.getSancneed().equals("Y") == false ) {	//�����ʿ������
				request.setAttribute("modify", "T");          //����, ��� ����
			} else {	//�����ʿ䶧
				request.setAttribute("modify", "F");          //����, ��� �Ұ�
			}
		} else {
			request.setAttribute("sanc", "F");                //���缱 ���� �Ұ�
			request.setAttribute("modify", "F");              //����, ��� �Ұ�
		}
		
		//���ڰ�������
		ElecApprManager eamgr = ElecApprManager.instance();
		ElecApprBean eaBean = eamgr.selectRequestSancInfo(reqformno, reqseq);
		
		if ( eaBean != null ) {
			String sancresult = Utils.nullToEmptyString(eaBean.getSancresult());
			String submitdt = Utils.nullToEmptyString(eaBean.getSubmitdt());
			String sancusrnm = Utils.nullToEmptyString(eaBean.getSancusrnm());
			if ( eaBean.getSancyn().equals("0") == true ) {
				sancusrnm = "���� ������";
			} else {
				sancusrnm = sancresult + " : " + submitdt + " " + sancusrnm;
			}
			sForm.setSancusrinfo(sancusrnm);
		}
		
		return mapping.findForward("modify");
	}
}