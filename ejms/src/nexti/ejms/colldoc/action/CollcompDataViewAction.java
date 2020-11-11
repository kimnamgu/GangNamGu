/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���տϷ� ���վ���ڷ� action
 * ����:
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
		//Form���� �Ѿ�� �� ��������
		CollprocForm collprocForm = (CollprocForm)form;

		int sysdocno = 0;
		String forward = "";
		
		if(request.getParameter("sysdocno") != null) {
			sysdocno = Integer.parseInt(request.getParameter("sysdocno"));
		}else if(request.getAttribute("sysdocno")!= null){
			sysdocno = Integer.parseInt(request.getAttribute("sysdocno").toString());
		}
		
		ColldocManager manager = ColldocManager.instance();
		
		//�� - ��������/�����˸��� ��������
		CollprocBean Bean = manager.getDocState(sysdocno);
		collprocForm.setEnddt(Bean.getEnddt());
		collprocForm.setEndcomment(Bean.getEndcomment());
		//�������� ��������
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