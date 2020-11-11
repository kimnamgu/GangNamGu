/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 진행중인위합문서 취합마감정보 action
 * 설명:
 */
package nexti.ejms.colldoc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.colldoc.form.CollprocForm;
import nexti.ejms.colldoc.model.CollprocBean;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.common.rootPopupAction;

public class CollprocCloseViewAction extends rootPopupAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		int sysdocno = 0;
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}
		
		//Form에서 넘어온 값 가져오기
		CollprocForm collprocForm = (CollprocForm)form;
		
		//취합마감하기 상세 - 마감처리
		ColldocManager manager = ColldocManager.instance();
		CollprocBean Bean = manager.getCloseView(sysdocno);
		
		request.setAttribute("sysdocno", new Integer(sysdocno));
		
		BeanUtils.copyProperties(collprocForm, Bean);
		
		return mapping.findForward("collproccloseview");
	}
}