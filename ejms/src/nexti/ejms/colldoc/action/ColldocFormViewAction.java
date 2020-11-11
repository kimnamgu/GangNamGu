/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합문서작성 취합양식자료 action
 * 설명:
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
		
		//Form에서 넘어온 값 가져오기
		ColldocForm cdform = (ColldocForm)form;	
		
		//취합양식목록 가져오기
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