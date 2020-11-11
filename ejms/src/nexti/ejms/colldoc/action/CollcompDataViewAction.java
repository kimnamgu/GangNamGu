/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합완료 취합양식자료 action
 * 설명:
 */
package nexti.ejms.colldoc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.colldoc.form.CollprocForm;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.colldoc.model.CollprocBean;
import nexti.ejms.common.rootAction;

public class CollcompDataViewAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//Form에서 넘어온 값 가져오기
		CollprocForm collprocForm = (CollprocForm)form;

		int sysdocno = 0;
		String forward = "";
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}else if(request.getAttribute("sysdocno")!= null){
			sysdocno = Integer.parseInt(request.getAttribute("sysdocno").toString());
		}
		
		ColldocManager manager = ColldocManager.instance();
		
		//상세 - 마감시한/마감알림말 가져오기
		CollprocBean Bean = manager.getDocState(sysdocno);
		collprocForm.setEnddt(Bean.getEnddt());
		collprocForm.setEndcomment(Bean.getEndcomment());
		//문서상태 가져오기
		collprocForm.setDocstate(Bean.getDocstate());
		collprocForm.setDocstatenm(Bean.getDocstatenm());
		
		request.setAttribute("sysdocno", new Integer(sysdocno));
		
		if(request.getAttribute("forward") != null) {
			forward = (String)request.getAttribute("forward");
			return mapping.findForward(forward);
		}else{
			return mapping.findForward("collcompdataview");
		}
		
	}
}