/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� �̸����� action
 * ����:
 */
package nexti.ejms.sinchung.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootPopupAction;
import nexti.ejms.sinchung.form.SinchungForm;
import nexti.ejms.sinchung.model.FrmBean;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;

public class PreviewAction extends rootPopupAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) 
		throws Exception {
		
		HttpSession session = request.getSession();
		
		SinchungForm sForm = (SinchungForm)form;
		
		int reqformno = sForm.getReqformno();
						
		SinchungManager smgr = SinchungManager.instance();
		
		if("Y".equals(sForm.getViewfl())){
			//���� ��������϶� ó��
			
			//���������� ��������
			FrmBean frmbean = smgr.reqFormInfo(reqformno);
			BeanUtils.copyProperties(sForm, frmbean);
			
			String tel = frmbean.getColtel();
			UserBean ubean = UserManager.instance().getUserInfo(frmbean.getChrgusrid());
			if ( ubean != null ) {
				tel = ubean.getTel();
			}
			sForm.setColtel(tel);
			
			//�׸�������������
			List articleList = smgr.reqFormSubList(reqformno);
			sForm.setArticleList(articleList);	
			
		} else {
			//�ӽ����� �����϶� ó��			
			String sessi = session.getId().split("[!]")[0];
			
			//���������� ��������(TEMP)
			FrmBean frmbean = smgr.reqFormInfo_temp(sessi);
			BeanUtils.copyProperties(sForm, frmbean);

			String tel = frmbean.getColtel();
			UserBean ubean = UserManager.instance().getUserInfo(frmbean.getChrgusrid());
			if ( ubean != null ) {
				tel = ubean.getTel();
			}
			sForm.setColtel(tel);
			
			sForm.setReqformno(0);
			
			//�׸�������������(TEMP)
			List articleList = smgr.reqFormSubList_temp(sessi, smgr.getReqSubExamcount(0, sessi));
			sForm.setArticleList(articleList);						
		}
		
		if ( "����3740000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
            sForm.setColtel(sForm.getColtel().substring(4));
        }
			
		return mapping.findForward("prev");
	}
}