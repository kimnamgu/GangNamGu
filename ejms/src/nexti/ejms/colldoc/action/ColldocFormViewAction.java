/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���չ����ۼ� ���վ���ڷ� action
 * ����:
 */
package nexti.ejms.colldoc.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.colldoc.form.ColldocForm;
import nexti.ejms.colldoc.model.ColldocBean;
import nexti.ejms.colldoc.model.ColldocManager;

public class ColldocFormViewAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		int mode = Integer.parseInt((String)request.getParameter("mode"), 10);
		int sysdocno = Integer.parseInt((String)request.getParameter("sysdocno"), 10);
		
		//Form���� �Ѿ�� �� ��������
		ColldocForm cdform = (ColldocForm)form;	
		
		//���վ�ĸ�� ��������
		ColldocManager cdmgr = ColldocManager.instance();
		List listcolldocform = cdmgr.getListFormat(sysdocno);
		cdform.setListcolldocform(listcolldocform);
		cdform.setFormcount(cdmgr.getCountFormat(sysdocno));
		
		ColldocBean cdbean = cdmgr.getColldoc(sysdocno);
		cdform.setEnddt_date(cdbean.getEnddt_date());
		cdform.setTgtdeptnm(cdbean.getTgtdeptnm());
		cdform.setSancusrnm1(cdbean.getSancusrnm1());
		cdform.setSancusrnm2(cdbean.getSancusrnm2());
		cdform.setMode(mode);
		cdform.setSysdocno(sysdocno);

		return mapping.findForward("colldocFormView");
	}
}