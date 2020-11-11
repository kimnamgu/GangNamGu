/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합문서작성 취합문서삭제 action
 * 설명:
 */
package nexti.ejms.colldoc.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.colldoc.form.ColldocForm;

public class ColldocDelAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		ColldocForm cdform = (ColldocForm)form;
		
		ColldocManager cdmgr = ColldocManager.instance();
		
		ServletContext context = getServlet().getServletContext();
		
		cdmgr.delColldoc(cdform.getListdelete(), context);
		
		String forwardName = "colldocList";
		if ( cdform.getType() == 1 ) {
			forwardName = "colldocList";
		} else if ( cdform.getType() == 2 ) {
			forwardName = "collprocList";
		} else if ( cdform.getType() == 3 ) {
			forwardName = "collcompList";
		}
		
		return mapping.findForward(forwardName);
	}
}